package cafe.adriel.androidaudiorecorder.example;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.results.SignInResult;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import java.util.Map;


public class login extends AppCompatActivity {

    private TextInputLayout textInputPassword;
    private TextInputLayout textInputEmail;
    private Button buttonlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputPassword = findViewById(R.id.input_password);
        textInputEmail =findViewById(R.id.input_email);
        buttonlogin = findViewById(R.id.btn_login);

        buttonlogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               if(validate())sync();
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


    }

    public void login_fun() {

        String password = textInputPassword.getEditText().getText().toString().trim();
        String email = textInputEmail.getEditText().getText().toString().trim();


    try {
        SignInResult signInResult = AWSMobileClient.getInstance().signIn(email, password, null);

        if (signInResult.getSignInState() != null) {
            Map<String, String> m = AWSMobileClient.getInstance().getUserAttributes();
            if(AWSMobileClient.getInstance().getUserAttributes().get("custom:role").equals("Student")){
                Intent intent1 = new Intent(login.this, StudentMActivity.class);
                startActivity(intent1);
            }else{
                Intent intent1 = new Intent(login.this, TeacherMain.class);
                startActivity(intent1);
            }
        } else {
            alert("Wrong Username or Password");
        }
    } catch (Exception e) {
        alert("Wrong Username or Password");
    }

    }

    public boolean validate() {
        boolean valid = true;

        String email = textInputEmail.getEditText().getText().toString();
        String password = textInputPassword.getEditText().getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textInputEmail.setError("Enter a valid email address");
            valid = false;
        } else {
            textInputEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            textInputPassword.setError("Password must be 4 to 10 characters");
            valid = false;
        } else {
            textInputPassword.setError(null);
        }

        return valid;
    }

    public void popsignup(View v){
        Intent intent3=new Intent(login.this,signup.class);
        startActivity(intent3);
    }

    private void alert(String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(login.this);
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

    private void sync(){
        final ProgressDialog progressDialog = new ProgressDialog(login.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        login_fun();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}

