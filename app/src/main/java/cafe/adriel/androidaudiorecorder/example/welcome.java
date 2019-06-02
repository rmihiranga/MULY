package cafe.adriel.androidaudiorecorder.example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import cafe.adriel.androidaudiorecorder.example.logics.paser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class welcome extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("INIT", "onResult: " + userStateDetails.getUserState());
                        try{
                            AWSCredentials credentialsProvider = AWSMobileClient.getInstance().getCredentials();
                            AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();

                            AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);
                            paser.dynamoDBMapper = DynamoDBMapper.builder()
                                    .dynamoDBClient(dynamoDBClient)
                                    .awsConfiguration(configuration)
                                    .build();

                            if(AWSMobileClient.getInstance().isSignedIn()){
                                if(AWSMobileClient.getInstance().getUserAttributes().get("custom:role").equals("Student")){
                                    Intent intent1 = new Intent(welcome.this, StudentMActivity.class);
                                    startActivity(intent1);
                                }else{
                                    Intent intent1 = new Intent(welcome.this, TeacherMain.class);
                                    startActivity(intent1);
                                }
                            }else{
                                Thread.sleep(3000);
                                Intent intent1 = new Intent(welcome.this, login.class);
                                startActivity(intent1);
                            }


                            paser.STAMP = AWSMobileClient.getInstance().getUsername();


                        }catch(Exception e){
                            alert("Network Error");
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("INIT", "Initialization error.", e);
                        alert("Ohhh.. Something Went Wrong");
                    }
                }
        );

    }
    private void alert(String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(welcome.this);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "close",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
