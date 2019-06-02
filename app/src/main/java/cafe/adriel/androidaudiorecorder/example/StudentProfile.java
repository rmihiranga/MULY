package cafe.adriel.androidaudiorecorder.example;

import androidx.appcompat.app.AppCompatActivity;
import cafe.adriel.androidaudiorecorder.example.logics.StudentsDO;
import cafe.adriel.androidaudiorecorder.example.logics.TeachersDO;
import cafe.adriel.androidaudiorecorder.example.logics.paser;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentProfile extends AppCompatActivity {

    private ImageView profilepic;
    private TextView username;
    private TextView email;
    private TextView telephone;
    private TextView city;
    private TextView Instruments;
    private TextView Studentsno;
    private Button Rbutton;
    private StudentsDO stdObjPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        profilepic=findViewById(R.id.image_student_profile);
        username=findViewById(R.id.username_student_profile);
        email=findViewById(R.id.email_student_profile);
        telephone=findViewById(R.id.telephone_student_profile);
        city=findViewById(R.id.city_student_profile);
        Instruments=findViewById(R.id.instruments_student_profile);
        Studentsno=findViewById(R.id.no_of_teachers_student_profile);
        Rbutton=findViewById(R.id.rbtn_student_profile);

        Rbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeStudent();
            }
        });

    try{
        Map<String, String> attributeNames = new HashMap<String, String>();
        attributeNames.put("#stdId", "stdId");
        Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
        attributeValues.put(":from", new AttributeValue().withS(paser.TAG));
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                .withFilterExpression("#stdId = :from")
                .withExpressionAttributeNames(attributeNames)
                .withExpressionAttributeValues(attributeValues);
        PaginatedScanList<StudentsDO> scan = paser.dynamoDBMapper.scan(StudentsDO.class, dynamoDBScanExpression);
        stdObjPro = scan.get(0);

        username.setText(stdObjPro.getName());
        email.setText(stdObjPro.getEmail());
        telephone.setText(stdObjPro.getTelephone());
        city.setText(stdObjPro.getAddress());
        Studentsno.setText(stdObjPro.getTeachers().size());

        paser.TAG = null;


    } catch (Exception e) {
        // Log.e("ERROR",e.toString());
        Toast.makeText(StudentProfile.this,e.toString(),Toast.LENGTH_LONG);

    }

    }

    @Override

    protected  void onDestroy(){
        super.onDestroy();
        username.setText("");
        email.setText("");
        telephone.setText("");
        city.setText("");
        Studentsno.setText("");
    }

    private void removeStudent(){
        try {

            Map<String, String> attributeNames = new HashMap<String, String>();
            attributeNames.put("#teacherid", "teacherid");
            Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
            attributeValues.put(":from", new AttributeValue().withS(AWSMobileClient.getInstance().getUsername()));
            DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                    .withFilterExpression("#teacherid = :from")
                    .withExpressionAttributeNames(attributeNames)
                    .withExpressionAttributeValues(attributeValues);
            PaginatedScanList<TeachersDO> scan = paser.dynamoDBMapper.scan(TeachersDO.class, dynamoDBScanExpression);
            TeachersDO me = scan.get(0);


            final StudentsDO updateStudent = new StudentsDO();
            updateStudent.setStdId(stdObjPro.getStdId());
            updateStudent.setName(stdObjPro.getName());
            updateStudent.setEmail(stdObjPro.getEmail());
            updateStudent.setTelephone(stdObjPro.getTelephone());
            updateStudent.setAddress(stdObjPro.getAddress());
            updateStudent.setInstruments(stdObjPro.getInstruments());
            List<String> tse = stdObjPro.getTeachers();
            tse.remove(AWSMobileClient.getInstance().getUsername()+":::"+AWSMobileClient.getInstance().getUserAttributes().get("name"));
            updateStudent.setTeachers(tse);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    paser.dynamoDBMapper.save(updateStudent);
                }
            }).start();


            final TeachersDO updateTeacher = new TeachersDO();
            updateTeacher.setTeacherid(me.getTeacherid());
            updateTeacher.setName(me.getName());
            updateTeacher.setEmail(me.getEmail());
            updateTeacher.setTelephone(me.getTelephone());
            updateTeacher.setAddress(me.getAddress());
            updateTeacher.setInstruments(me.getInstruments());
            List<String> stu = me.getStudents();
            stu.remove(stdObjPro.getEmail()+":::"+stdObjPro.getName());
            updateTeacher.setStudents(stu);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    paser.dynamoDBMapper.save(updateTeacher);
                }
            }).start();

        } catch (Exception e) {
//
        }
    }


}
