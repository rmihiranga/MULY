package cafe.adriel.androidaudiorecorder.example;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.obsez.android.lib.filechooser.ChooserDialog;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class edit_profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Button buttonUpdate;

    private TextInputLayout textInputUsername;
    private TextInputLayout textInputEmail;
    private TextInputLayout textInputTelephone;
    final Calendar myCalendar2= Calendar.getInstance();
    private CountryCodePicker ccp2;
    //private TextInputLayout textName;
    private TextInputLayout lDob;
    private TextInputLayout lAdresss;
    private TextInputLayout lPassword;
    private TextInputLayout lconfirmpassword;
    private CircleImageView viewImg;

    private TextInputEditText dob2;
    private TextInputEditText telephone2;
    private TextInputEditText email2;
    private TextInputEditText username2;
    private Spinner Gender2;
    private TextInputEditText address2;
    private TextInputEditText passwordFirst;
    private TextInputEditText passwordNew;


    private String name;
    private String email;
    private String telephone;
    private String dob;
    private String address;
    private String password;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textInputUsername = findViewById(R.id.text_input_username2);
        textInputEmail = findViewById(R.id.text_input_email2);
        textInputTelephone=findViewById(R.id.text_input_telephone12);
        lDob=findViewById(R.id.text_input_dob2);
        lAdresss=findViewById(R.id.text_input_address2);
        lPassword=findViewById(R.id.text_input_password12);
        lconfirmpassword=findViewById(R.id.text_input_passwordConfirm123);
        passwordFirst=findViewById(R.id.text_passwordFirst);
        passwordNew=findViewById(R.id.text_passwordNew);
        dob2=findViewById(R.id.text_dob2);
        telephone2=findViewById(R.id.edit_text_telephone2);
        email2=findViewById(R.id.email_text2);
        username2=findViewById(R.id.username_text2);
        ccp2 = findViewById(R.id.ccp2);
        ccp2.registerCarrierNumberEditText(telephone2);
        address2 = findViewById(R.id.text_addressofstd);
        viewImg = findViewById(R.id.profile_image);


//      Disabling fie//lds
        email2.setEnabled(false);
        username2.setEnabled(false);


        Picasso.get().load(new File(Environment.getExternalStorageDirectory() +
                File.separator + "MulyTestFolder"+File.separator+AWSMobileClient.getInstance().getUsername()+".png")).error(R.drawable.person).into(viewImg);




        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        //Date of birth text field click
        dob2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog=new DatePickerDialog(edit_profile.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, date, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        Gender2=findViewById(R.id.Gender2);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.Gender,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender2.setAdapter(adapter2);
        Gender2.setOnItemSelectedListener(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

       // UpdateFields();

        syncMe();

        buttonUpdate = findViewById(R.id.button_update);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateFields();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        syncMe();
                    }
                });
            }
        });

        viewImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find_file();
            }
        });

    }

    private String getFileName(){

        return "a";
    }


    void UpdateFields(){
        try{
            String nameUp = username2.getText().toString();
            String emailUp = email2.getText().toString();
            String telephoneUp = ccp2.getFullNumberWithPlus();
            String dobUp = dob2.getText().toString();
            String addressUp = address2.getText().toString();
            String passwordUp = passwordNew.getText().toString().trim();
            String genderUp = Gender2.getSelectedItem().toString();
            Toast.makeText(this, nameUp+emailUp+telephoneUp+dobUp+addressUp+passwordUp+genderUp, Toast.LENGTH_SHORT).show();

            Map<String , String> updateData2 = new HashMap<>();

            if(!nameUp.equals(name)) updateData2.put("name",nameUp);
            if(!emailUp.equals(email)) updateData2.put("email",emailUp);
            if(!telephoneUp.equals(telephone)) updateData2.put("phone_number",telephoneUp);
            if(!dobUp.equals(dob)) updateData2.put("birthdate",dobUp);
            if(!addressUp.equals(address)) updateData2.put("address",addressUp);
            if(!genderUp.equals(gender)) updateData2.put("gender",genderUp);

            AWSMobileClient.getInstance().updateUserAttributes(updateData2);
        }
        catch (Exception e){
            Toast.makeText(this,e.toString(),Toast.LENGTH_LONG);
        }


    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob2.setText(sdf.format(myCalendar2.getTime()));
    }


    // Basic Email validation
    private boolean validateEmail() {
        String emailInput = textInputEmail.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail.setError("Field can't be Empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail.setError("Please Enter valid email adress");
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    //Basic username Validation
    private boolean validateUsername() {
        String usernameinput = textInputUsername.getEditText().getText().toString().trim();
        if (usernameinput.isEmpty()) {
            textInputUsername.setError("Field can't be Empty");
            return false;
        } else if (usernameinput.length() > 10) {
            textInputUsername.setError("Username Too long");
            return false;
        } else {
            textInputUsername.setError(null);
            return true;
        }
    }

    // Basic Telephone Number validation
    private boolean validateTelephone() {
        String TelephoneInput = textInputTelephone.getEditText().getText().toString().trim();
        if (TelephoneInput.isEmpty()) {
            textInputTelephone.setError("Field can't be Empty");
            return false;
        } else if (TelephoneInput.length() > 13) {
            textInputTelephone.setError("Telephone Number Too long");
            return false;
        } else {
            textInputTelephone.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validateTelephone() | !validateUsername()) {
            return;

        }

        Toast.makeText(this, "input", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void syncMe(){
        try {
            Map<String, String> updateData = AWSMobileClient.getInstance().getUserAttributes();
            name = updateData.get("name");
            email = updateData.get("email");
            telephone = updateData.get("phone_number");
            gender = updateData.get("gender");
            address = updateData.get("address");
            dob = updateData.get("birthdate");

            username2.setText(name);
            ccp2.setCountryForPhoneCode(Integer.parseInt(telephone.substring(1,3)));
            telephone2.setText(telephone.substring(3,telephone.length()));
            if(gender.equals("Male")) Gender2.setSelection(0);
            else if(gender.equals("Female")) Gender2.setSelection(1);
            else Gender2.setSelection(2);
            email2.setText(email);
            dob2.setText(dob);
            address2.setText(address);


        }catch(Exception e){

        }
    }

    private void find_file(){
        new ChooserDialog().with(this)
                .withStartFile(Environment.getExternalStorageDirectory().getPath())
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        createFolder();
                        Toast.makeText(edit_profile.this, "FILE: " + path, Toast.LENGTH_SHORT).show();
                        upladImg(path);

                    }
                })
                .build()
                .show();
    }

    private void createFolder(){
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "MulyTestFolder");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            //dostuff
        } else {
            // Do something else on failure
            new File(Environment.getExternalStorageDirectory() +
                    File.separator + "MulyTestFolder"+File.separator+AWSMobileClient.getInstance().getUsername()+".png").delete();
        }

    }


    private void downImg(){
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();

        TransferObserver downloadObserver =
                transferUtility.download(
                        "public"+File.separator+AWSMobileClient.getInstance().getUsername()+".png",
                        new File(Environment.getExternalStorageDirectory() +
                                File.separator + "MulyTestFolder"+File.separator+AWSMobileClient.getInstance().getUsername()+".png"));


        // Attach a listener to the observer to get state update and progress notifications
        downloadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    alert("Success","ok");
                    // Handle a completed upload.
                    Picasso.get().invalidate(new File(Environment.getExternalStorageDirectory() +
                            File.separator + "MulyTestFolder"+File.separator+AWSMobileClient.getInstance().getUsername()+".png"));
                    Picasso.get().load(new File(Environment.getExternalStorageDirectory() +
                            File.separator + "MulyTestFolder"+File.separator+AWSMobileClient.getInstance().getUsername()+".png")).into(viewImg);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;

                Log.d("Your Activity", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
                alert("Fail. Try again"+ex.toString(),"ok");
            }

        });

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == downloadObserver.getState()) {
            // Handle a completed upload.
        }

        Log.d("Your Activity", "Bytes Transferred: " + downloadObserver.getBytesTransferred());
        Log.d("Your Activity", "Bytes Total: " + downloadObserver.getBytesTotal());

    }


    private void upladImg(String AUDIO_FILE_PATH){
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(new AmazonS3Client(AWSMobileClient.getInstance()))
                        .build();

        TransferObserver uploadObserver =
                transferUtility.upload(
                        "public"+File.separator+AWSMobileClient.getInstance().getUsername()+".png",
                        new File(AUDIO_FILE_PATH));

        // Attach a listener to the observer to get state update and progress notifications
        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed upload.
                    alert("completed upload","Done");
                    downImg();
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

        // If you prefer to poll for the data, instead of attaching a
        // listener, check for the state and progress in the observer.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
            alert("completed upload","Done");
        }
    }

    private void alert(String msg,String itm){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(edit_profile.this);
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

}