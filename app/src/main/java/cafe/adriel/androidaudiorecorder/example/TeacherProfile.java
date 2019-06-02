package cafe.adriel.androidaudiorecorder.example;

import androidx.appcompat.app.AppCompatActivity;
import cafe.adriel.androidaudiorecorder.example.logics.RatingDO;
import cafe.adriel.androidaudiorecorder.example.logics.StudentsDO;
import cafe.adriel.androidaudiorecorder.example.logics.TeachersDO;
import cafe.adriel.androidaudiorecorder.example.logics.paser;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherProfile extends AppCompatActivity {
    private ImageView profilepic;
    private TextView username;
    private TextView email;
    private TextView telephone;
    private TextView city;
    private TextView Instruments;
    private RatingBar ratingbar;
    private TextView Studentsno;
    private Button  Rbutton;
    private TeachersDO teacherObj;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);




        profilepic=findViewById(R.id.profile_image_teacher);
        username=findViewById(R.id.name_teacher_profile);
        email=findViewById(R.id.email_teacher_profile);
        telephone=findViewById(R.id.telephone_teacher_profile);
        city=findViewById(R.id.city_teacher_profile);
        Instruments=findViewById(R.id.instumenttypes_teacher_profile);
        ratingbar=findViewById(R.id.rating_teacher_profile);
        Studentsno=findViewById(R.id.no_of_students_teacher_profile);
        Rbutton=findViewById(R.id.removebtn_teacher_profile);

        Rbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeTeacher();
            }
        });

        try {

            Map<String, String> attributeNames = new HashMap<String, String>();
            attributeNames.put("#teacherid", "teacherid");
            Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
            attributeValues.put(":from", new AttributeValue().withS(paser.TAG));
            DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                    .withFilterExpression("#teacherid = :from")
                    .withExpressionAttributeNames(attributeNames)
                    .withExpressionAttributeValues(attributeValues);
            PaginatedScanList<TeachersDO> scan = paser.dynamoDBMapper.scan(TeachersDO.class, dynamoDBScanExpression);
            teacherObj = scan.get(0);


           username.setText(teacherObj.getName());
           email.setText(teacherObj.getEmail());
           telephone.setText(teacherObj.getTelephone());
           city.setText(teacherObj.getAddress());
           Instruments.setText(teacherObj.getInstruments().size());
           Studentsno.setText(teacherObj.getStudents().size());
           ratingbar.setNumStars(4);


        } catch (Exception e){}

    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        username.setText("");
        email.setText("");
        telephone.setText("");
        city.setText("");
        Instruments.setText("");
        Studentsno.setText("");
        ratingbar.setNumStars(0);
    }

    private void removeTeacher(){
        try {

            Map<String, String> attributeNames = new HashMap<String, String>();
            attributeNames.put("#stdId", "stdId");
            Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
            attributeValues.put(":from", new AttributeValue().withS(AWSMobileClient.getInstance().getUsername()));
            DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                    .withFilterExpression("#stdId = :from")
                    .withExpressionAttributeNames(attributeNames)
                    .withExpressionAttributeValues(attributeValues);
            PaginatedScanList<StudentsDO> scan = paser.dynamoDBMapper.scan(StudentsDO.class, dynamoDBScanExpression);
            StudentsDO old = scan.get(0);

            final TeachersDO updateTeacher = new TeachersDO();
            updateTeacher.setTeacherid(teacherObj.getTeacherid());
            updateTeacher.setName(teacherObj.getName());
            updateTeacher.setEmail(teacherObj.getEmail());
            updateTeacher.setTelephone(teacherObj.getTelephone());
            updateTeacher.setAddress(teacherObj.getAddress());
            updateTeacher.setInstruments(teacherObj.getInstruments());
            List<String> stu = teacherObj.getStudents();
            stu.remove(AWSMobileClient.getInstance().getUsername()+":::"+AWSMobileClient.getInstance().getUserAttributes().get("name"));
            updateTeacher.setStudents(stu);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    paser.dynamoDBMapper.save(updateTeacher);
                }
            }).start();

            final StudentsDO updateStudent = new StudentsDO();
            updateStudent.setStdId(old.getStdId());
            updateStudent.setName(old.getName());
            updateStudent.setEmail(old.getEmail());
            updateStudent.setTelephone(old.getTelephone());
            updateStudent.setAddress(old.getAddress());
            updateStudent.setInstruments(old.getInstruments());
            List<String> tse = old.getTeachers();
            tse.remove(teacherObj.getEmail()+":::"+teacherObj.getName());
            updateStudent.setTeachers(tse);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    paser.dynamoDBMapper.save(updateStudent);
                }
            }).start();


        } catch (Exception e) {
            // Log.e("ERROR",e.toString());
            Toast.makeText(TeacherProfile.this,e.toString(),Toast.LENGTH_LONG);

        }
    }

    private void setrating(){
        Map<String, String> attributeNames = new HashMap<String, String>();
        attributeNames.put("#userId", "userId");
        Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
        attributeValues.put(":from", new AttributeValue().withS(paser.TAG));
        DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                .withFilterExpression("#userId = :from")
                .withExpressionAttributeNames(attributeNames)
                .withExpressionAttributeValues(attributeValues);
        PaginatedScanList<RatingDO> scan = paser.dynamoDBMapper.scan(RatingDO.class, dynamoDBScanExpression);
        RatingDO old = scan.get(0);
        double new_rate = ((old.getRate()*old.getCount())+ratingbar.getRating())/(old.getCount()+1);
        ratingbar.setNumStars((int)new_rate);
        Toast.makeText(TeacherProfile.this,"Your Rating Acceted",Toast.LENGTH_LONG);
    }

}