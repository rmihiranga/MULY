package cafe.adriel.androidaudiorecorder.example;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Patterns;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cafe.adriel.androidaudiorecorder.example.logics.RatingDO;
import cafe.adriel.androidaudiorecorder.example.logics.StudentsDO;
import cafe.adriel.androidaudiorecorder.example.logics.SummaryDO;
import cafe.adriel.androidaudiorecorder.example.logics.TeachersDO;
import cafe.adriel.androidaudiorecorder.example.logics.paser;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;


public class signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextInputLayout textName;
    private TextInputLayout textInputUsername1;
    private TextInputLayout textInputEmail1;
    private TextInputLayout textInputTelephone1;
    private TextInputLayout password;
    private TextInputLayout confirmpassword;
    private TextInputLayout textaddr;
    private Spinner Role;
    private Spinner Gender;
    private TextInputLayout textdob;
    private TextInputEditText dob;
    private TextInputEditText telephone;
    final Calendar myCalendar = Calendar.getInstance();
    private CountryCodePicker ccp;
    private CircleImageView cimg;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textName = findViewById(R.id.text_input_name);
        textInputUsername1 = findViewById(R.id.text_input_username1);
        textInputEmail1 =  findViewById(R.id.text_input_email1);
        textInputTelephone1=  findViewById(R.id.text_input_telephone1);
        password= findViewById(R.id.text_input_password1);
        confirmpassword=  findViewById(R.id.text_input_passwordConfirm1);
        textaddr = findViewById(R.id.text_input_address);
        dob=findViewById(R.id.text_dob);
        textdob=findViewById(R.id.text_input_dob);
        ccp = findViewById(R.id.ccp);
        telephone=findViewById(R.id.edit_text_telephone);
        ccp.registerCarrierNumberEditText(telephone);
        cimg = findViewById(R.id.profile_image1);

        Picasso.get().load("https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png").fit().centerInside().into(cimg);

        //date click
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        //Date of birth text field click
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog=new DatePickerDialog(signup.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });



        //Spinners
        Role=findViewById(R.id.Role);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this,R.array.Role,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Role.setAdapter(adapter1);
        Role.setOnItemSelectedListener(this);

        Gender=findViewById(R.id.Gender);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this,R.array.Gender,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(adapter2);
        Gender.setOnItemSelectedListener(this);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

    }
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
    }
    // Basic Email validation
    private boolean validateEmail() {
        String emailInput = textInputEmail1.getEditText().getText().toString().trim();
        if (emailInput.isEmpty()) {
            textInputEmail1.setError("Field can't be Empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            textInputEmail1.setError("Please Enter valid email adress");
            return false;
        } else {
            textInputEmail1.setError(null);
            return true;
        }
    }

    //Basic username Validation
    private boolean validateUsername() {
        String usernameinput = textInputUsername1.getEditText().getText().toString().trim();
        if (usernameinput.isEmpty()) {
            textInputUsername1.setError("Field can't be Empty");
            return false;
        } else if (usernameinput.length() > 10) {
            textInputUsername1.setError("Username Too long");
            return false;
        } else {
            textInputUsername1.setError(null);
            return true;
        }
    }
    private boolean valid() {
        String usernameinput1 = textName.getEditText().getText().toString().trim();
        String usernameinput2 = textaddr.getEditText().getText().toString().trim();
        if (usernameinput1.isEmpty() | usernameinput2.isEmpty()) {
            textInputUsername1.setError("Field can't be Empty");
            return false;
        } else {
            textInputUsername1.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = password.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password too weak");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }


    // Basic Telephone Number validation
    private boolean validateTelephone() {
        String TelephoneInput = textInputTelephone1.getEditText().getText().toString().trim();
        if (TelephoneInput.isEmpty()) {
            textInputTelephone1.setError("Field can't be Empty");
            return false;
        } else if (TelephoneInput.length() > 13) {
            textInputTelephone1.setError("Telephone Number Too long");
            return false;
        } else {
            textInputTelephone1.setError(null);
            return true;
        }
    }

    public void confirmInput(View v) {
        if (!validateEmail() | !validateTelephone() | !validateUsername()|
                !validatePassword() | !valid() ) {
            return;

        }


        final Map<String, String> attributes = new HashMap<>();
        attributes.put("email",textInputEmail1.getEditText().getText().toString());
        attributes.put("name",textName.getEditText().getText().toString());
        attributes.put("phone_number",ccp.getFullNumberWithPlus());
        attributes.put("address",textaddr.getEditText().getText().toString());
        attributes.put("birthdate",textdob.getEditText().getText().toString());
        attributes.put("gender", Gender.getSelectedItem().toString());
        attributes.put("custom:role",Role.getSelectedItem().toString());



        try {
            SignUpResult signUpResult = AWSMobileClient.getInstance()
                    .signUp(textInputUsername1.getEditText().getText().toString(),
                            password.getEditText().getText().toString(), attributes, null);
            if(signUpResult.getConfirmationState()){
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Not Done", Toast.LENGTH_SHORT).show();
                List<String> dump = new LinkedList<String>();
                dump.add("Init");
                if(Role.getSelectedItem().toString().equals("Student")) {
                    final StudentsDO obj = new StudentsDO();
                    obj.setStdId(textInputEmail1.getEditText().getText().toString());
                    obj.setName(textName.getEditText().getText().toString());
                    obj.setAddress(textaddr.getEditText().getText().toString());
                    obj.setEmail(textInputEmail1.getEditText().getText().toString());
                    obj.setTelephone(ccp.getFullNumberWithPlus());
                    obj.setTeachers(dump);
                    obj.setInstruments(dump);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            paser.dynamoDBMapper.save(obj);
                        }
                    }).start();


                    Map<String, String> teachers =new HashMap();
                    Map<String, String> days = new HashMap();

                    final SummaryDO summery = new SummaryDO();
                    summery.setUserId(textInputEmail1.getEditText().getText().toString());
                    summery.setDays(teachers);
                    summery.setTeachers(days);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            paser.dynamoDBMapper.save(summery);
                        }
                    }).start();

                }else{
                    final TeachersDO obj = new TeachersDO();
                    obj.setTeacherid(textInputEmail1.getEditText().getText().toString());
                    obj.setName(textName.getEditText().getText().toString());
                    obj.setAddress(textaddr.getEditText().getText().toString());
                    obj.setEmail(textInputEmail1.getEditText().getText().toString());
                    obj.setTelephone(ccp.getFullNumberWithPlus());
                    obj.setStudents(dump);
                    obj.setInstruments(dump);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            paser.dynamoDBMapper.save(obj);
                        }
                    }).start();


                    final RatingDO rating = new RatingDO();
                    rating.setUserId(textInputEmail1.getEditText().getText().toString());
                    rating.setCount(0.0);
                    rating.setRate(0.0);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            paser.dynamoDBMapper.save(rating);
                        }
                    }).start();


                }
                Intent intent1 = new Intent(signup.this, conform.class);
                startActivity(intent1);
            }
        }catch(Exception e){
            alert("Hmm.... seems i know you! \nemail and username already exists");
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void alert(String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(signup.this);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Try again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}