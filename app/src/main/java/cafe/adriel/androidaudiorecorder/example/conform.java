package cafe.adriel.androidaudiorecorder.example;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.google.android.material.textfield.TextInputLayout;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class conform extends AppCompatActivity {

    private TextInputLayout email;
    private TextInputLayout code;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform);

        email = findViewById(R.id.input_email_cnf);
        code  = findViewById(R.id.input_password_cnf);
        btn  = findViewById(R.id.btn_cnf);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conform_email_code();
            }
        });

    }

    private void conform_email_code(){

        try{
            SignUpResult signUpResult = AWSMobileClient.
                    getInstance().confirmSignUp(email.getEditText().getText().toString(), code.getEditText().getText().toString());

            if(signUpResult.getConfirmationState()){
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(conform.this, login.class);
                startActivity(intent1);
            }
            else{
                alert("Wrong Conformation Code");
            }
        }catch(Exception e){
            alert("Wrong Conformation Code");
        }
    }
    private void alert(String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(conform.this);
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

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}