package cafe.adriel.androidaudiorecorder.example;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import cafe.adriel.androidaudiorecorder.AndroidAudioRecorder;
import cafe.adriel.androidaudiorecorder.example.logics.SummaryDO;
import cafe.adriel.androidaudiorecorder.example.logics.UserDataTimeDO;
import cafe.adriel.androidaudiorecorder.example.logics.paser;
import cafe.adriel.androidaudiorecorder.model.AudioChannel;
import cafe.adriel.androidaudiorecorder.model.AudioSampleRate;
import cafe.adriel.androidaudiorecorder.model.AudioSource;

import static cafe.adriel.androidaudiorecorder.example.logics.paser.STAMP;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_RECORD_AUDIO = 0;
    private String AUDIO_FILE_PATH;
    private  long start;
    private long end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String date  = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        STAMP = STAMP + "-" +date+"--"+ Calendar.getInstance().getTime();
        AUDIO_FILE_PATH = Environment.getExternalStorageDirectory().getPath()+"/"+STAMP+".wav";


        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(
                    new ColorDrawable(ContextCompat.getColor(this, R.color.colorPrimaryDark)));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Util.requestPermission(this, Manifest.permission.RECORD_AUDIO);
        Util.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RECORD_AUDIO) {
            if (resultCode == RESULT_OK) {
                end = System.nanoTime();
                Toast.makeText(this, "Audio recorded successfully!", Toast.LENGTH_SHORT).show();
                try {
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            uploadWithTransferUtility();
                        }
                    });
                }catch(Exception e){
                    alert(e.toString(),"Try Again");
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Audio was not recorded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void recordAudio(View v) {
        AndroidAudioRecorder.with(this)
                // Required
                .setFilePath(AUDIO_FILE_PATH)
                .setColor(ContextCompat.getColor(this, R.color.recorder_bg))
                .setRequestCode(REQUEST_RECORD_AUDIO)

                // Optional
                .setSource(AudioSource.MIC)
                .setChannel(AudioChannel.STEREO)
                .setSampleRate(AudioSampleRate.HZ_48000)
                .setAutoStart(false)
                .setKeepDisplayOn(true)

                // Start recording
                .record();
        start =System.nanoTime();
    }
    public void uploadWithTransferUtility() {

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();

        TransferObserver uploadObserver =
                transferUtility.upload(
                        "public/"+STAMP+".wav",
                        new File(AUDIO_FILE_PATH));

        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                    alert("completed upload","Done");
                    try {
                        double duration = (end-start)/1.0e+11;
                        addData(duration);
                        alert(String.valueOf(duration),"ok");
                    }catch(Exception e){
                        alert(e.toString(),"ok");
                    }
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("YourActivity", "ID:" + id + " bytesCurrent: " + bytesCurrent
                        + " bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
                alert("Failed  upload "+ex.toString(),"Try Again");

            }

        });


    }
    private void alert(String msg,String itm){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                itm,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }



    private void addData(double len){
        Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
        final UserDataTimeDO timeData = new UserDataTimeDO();
        timeData.setUserId(AWSMobileClient.getInstance().getUsername()+":::"+paser.TAG);


        Map<String, String> attributeNames = new HashMap<String, String>();
        attributeNames.put("#userId", "userId");

        Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
        attributeValues.put(":from", new AttributeValue().withS(AWSMobileClient.getInstance().getUsername()+":::"+paser.TAG));


        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                .withFilterExpression("#userId = :from")
                .withExpressionAttributeNames(attributeNames)
                .withExpressionAttributeValues(attributeValues);

        PaginatedScanList<UserDataTimeDO> scan = paser.dynamoDBMapper.scan(UserDataTimeDO.class, dynamoDBScanExpression);

        final UserDataTimeDO pastData = scan.get(0);

        switch ((localCalendar.get(Calendar.DATE)%7)+1){
            case 1:
                timeData.setD1(pastData.getD1()+len);
                timeData.setD2(pastData.getD2());
                timeData.setD3(pastData.getD3());
                timeData.setD4(pastData.getD4());
                timeData.setD5(pastData.getD5());
                timeData.setD6(pastData.getD6());
                timeData.setD7(pastData.getD7());
                break;
            case 2:
                timeData.setD1(pastData.getD1());
                timeData.setD2(pastData.getD2()+len);
                timeData.setD3(pastData.getD3());
                timeData.setD4(pastData.getD4());
                timeData.setD5(pastData.getD5());
                timeData.setD6(pastData.getD6());
                timeData.setD7(pastData.getD7());
                break;
            case 3:
                timeData.setD1(pastData.getD1());
                timeData.setD2(pastData.getD2());
                timeData.setD3(pastData.getD3()+len);
                timeData.setD4(pastData.getD4());
                timeData.setD5(pastData.getD5());
                timeData.setD6(pastData.getD6());
                timeData.setD7(pastData.getD7());
                break;
            case 4:
                timeData.setD1(pastData.getD1());
                timeData.setD2(pastData.getD2());
                timeData.setD3(pastData.getD3());
                timeData.setD4(pastData.getD4()+len);
                timeData.setD5(pastData.getD5());
                timeData.setD6(pastData.getD6());
                timeData.setD7(pastData.getD7());
                break;
            case 5:
                timeData.setD1(pastData.getD1());
                timeData.setD2(pastData.getD2());
                timeData.setD3(pastData.getD3());
                timeData.setD4(pastData.getD4());
                timeData.setD5(pastData.getD5()+len);
                timeData.setD6(pastData.getD6());
                timeData.setD7(pastData.getD7());
                break;
            case 6:
                timeData.setD1(pastData.getD1());
                timeData.setD2(pastData.getD2());
                timeData.setD3(pastData.getD3());
                timeData.setD4(pastData.getD4());
                timeData.setD5(pastData.getD5());
                timeData.setD6(pastData.getD6()+len);
                timeData.setD7(pastData.getD7());
                break;
            default:
                timeData.setD1(pastData.getD1());
                timeData.setD2(pastData.getD2());
                timeData.setD3(pastData.getD3());
                timeData.setD4(pastData.getD4());
                timeData.setD5(pastData.getD5());
                timeData.setD6(pastData.getD6());
                timeData.setD7(pastData.getD7()+len);
                break;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                paser.dynamoDBMapper.save(timeData);
            }
        }).start();

        Map<String, String> attributeNames2 = new HashMap<String, String>();
        attributeNames2.put("#userId", "userId");
        Map<String, AttributeValue> attributeValues2 = new HashMap<String, AttributeValue>();
        attributeValues2.put(":from", new AttributeValue().withS(AWSMobileClient.getInstance().getUsername()));
        DynamoDBScanExpression dynamoDBScanExpression2 = new DynamoDBScanExpression()
                .withFilterExpression("#userId = :from")
                .withExpressionAttributeNames(attributeNames2)
                .withExpressionAttributeValues(attributeValues2);
        PaginatedScanList<SummaryDO> getsummery  = paser.dynamoDBMapper.scan(SummaryDO.class, dynamoDBScanExpression2);

        SummaryDO obj = getsummery.get(0);

        final SummaryDO update  = new SummaryDO();
        update.setUserId(AWSMobileClient.getInstance().getUsername());
        Map<String,String> hold = obj.getTeachers();
        float total = (float)(timeData.getD1()+
                timeData.getD2()+
                timeData.getD3()+
                timeData.getD4()+
                timeData.getD5()+
                timeData.getD6()+
                timeData.getD7());

        hold.put(paser.TAG+":::"+paser.TAG2,String.valueOf(total));
        update.setTeachers(hold);
        final Map<String,String> day_pratice = obj.getDays();
        day_pratice.put("d1",String.valueOf(timeData.getD1()));
        day_pratice.put("d2",String.valueOf(timeData.getD2()));
        day_pratice.put("d3",String.valueOf(timeData.getD3()));
        day_pratice.put("d4",String.valueOf(timeData.getD4()));
        day_pratice.put("d5",String.valueOf(timeData.getD5()));
        day_pratice.put("d6",String.valueOf(timeData.getD6()));
        day_pratice.put("d7",String.valueOf(timeData.getD7()));
        update.setDays(day_pratice);

        new Thread(new Runnable() {
            @Override
            public void run() {
                paser.dynamoDBMapper.save(update);
            }
        }).start();

    }
}