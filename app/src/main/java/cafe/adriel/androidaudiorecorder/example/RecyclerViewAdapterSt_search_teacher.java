package cafe.adriel.androidaudiorecorder.example;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cafe.adriel.androidaudiorecorder.example.logics.StudentsDO;
import cafe.adriel.androidaudiorecorder.example.logics.SummaryDO;
import cafe.adriel.androidaudiorecorder.example.logics.TeachersDO;
import cafe.adriel.androidaudiorecorder.example.logics.UserDataTimeAccDO;
import cafe.adriel.androidaudiorecorder.example.logics.UserDataTimeDO;
import cafe.adriel.androidaudiorecorder.example.logics.paser;

import static cafe.adriel.androidaudiorecorder.example.logics.paser.dynamoDBMapper;

public class RecyclerViewAdapterSt_search_teacher extends RecyclerView.Adapter<RecyclerViewAdapterSt_search_teacher.TeacherSearchViewHolder>{
    private ArrayList<stu_search_teacher_item> mSearchList;
//    private ArrayList<stu_search_teacher_item> mSearchListFull;
    private Context context;

    public static class TeacherSearchViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public Button  addbtn;



        public TeacherSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.search_teacher_image);
            mTextView1=itemView.findViewById(R.id.search_teacher_name_text);
            mTextView2=itemView.findViewById(R.id.search_teacher_description);
            addbtn=itemView.findViewById(R.id.search_teacher_add_btn);
        }
    }

    public RecyclerViewAdapterSt_search_teacher(ArrayList<stu_search_teacher_item> searchList){
        mSearchList=searchList;
    }

    @NonNull
    @Override
    public TeacherSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.stu_teacher_search_item,parent,false);
        TeacherSearchViewHolder tsvh=new TeacherSearchViewHolder(v);
        context = parent.getContext();
        return tsvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final TeacherSearchViewHolder holder, int position) {
        final stu_search_teacher_item currentItem=mSearchList.get(position);


        //--------------------------------------------------------------------------


        Picasso.get().load(currentItem.getmImageResource()).fit().centerInside().into(holder.mImageView);

        //--------------------------------------------------------------------------

        holder.mTextView1.setText(currentItem.gettext1());
        holder.mTextView2.setText(currentItem.gettext2());

//        Click event for Add button
        holder.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid="a";

                try {

                    final String nameUser = AWSMobileClient.getInstance().getUsername();
                    final String name = AWSMobileClient.getInstance().getUserAttributes().get("name");



                    Map<String, String> attributeNames1 = new HashMap<String, String>();
                    attributeNames1.put("#stdId", "stdId");
                    Map<String, AttributeValue> attributeValues1 = new HashMap<String, AttributeValue>();
                    attributeValues1.put(":from", new AttributeValue().withS(nameUser));
                    DynamoDBScanExpression dynamoDBScanExpression1 = new DynamoDBScanExpression()
                            .withFilterExpression("#stdId = :from")
                            .withExpressionAttributeNames(attributeNames1)
                            .withExpressionAttributeValues(attributeValues1);

                    PaginatedScanList<StudentsDO> scan_student = paser.dynamoDBMapper.scan(StudentsDO.class, dynamoDBScanExpression1);



                    Map<String, String> attributeNames = new HashMap<String, String>();
                    attributeNames.put("#teacherid", "teacherid");
                    Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
                    attributeValues.put(":from", new AttributeValue().withS(currentItem.gettext2()));
                    DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                            .withFilterExpression("#teacherid = :from")
                            .withExpressionAttributeNames(attributeNames)
                            .withExpressionAttributeValues(attributeValues);
                    PaginatedScanList<TeachersDO> scan_teacher  = paser.dynamoDBMapper.scan(TeachersDO.class, dynamoDBScanExpression);


                    TeachersDO objTech = scan_teacher.get(0);

                    final TeachersDO teacher = new TeachersDO();
                    teacher.setTeacherid(currentItem.gettext2());
                    teacher.setName(currentItem.gettext1());
                    teacher.setAddress(objTech.getAddress());
                    teacher.setEmail(objTech.getEmail());
                    teacher.setTelephone(objTech.getTelephone());
                    teacher.setInstruments(objTech.getInstruments());
                    List<String> addStd = objTech.getStudents();
                    addStd.add(nameUser+":::"+name);
                    teacher.setStudents(addStd);

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            dynamoDBMapper.save(teacher);
                        }
                    });


                    StudentsDO objStd = scan_student.get(0);

                    final StudentsDO student = new StudentsDO();
                    student.setStdId(objStd.getStdId());
                    student.setName(objStd.getName());
                    student.setEmail(objStd.getEmail());
                    student.setAddress(objStd.getAddress());
                    student.setTelephone(objStd.getTelephone());
                    student.setInstruments(objStd.getInstruments());
                    List<String> addTec = objStd.getTeachers();
                    addTec.add(currentItem.gettext2()+":::"+currentItem.gettext1());
                    student.setTeachers(addTec);

                  AsyncTask.execute(new Runnable() {
                      @Override
                      public void run() {
                          dynamoDBMapper.save(student);
                      }
                  });


                    final UserDataTimeDO data = new UserDataTimeDO();
                    data.setUserId(AWSMobileClient.getInstance().getUsername()+":::"+currentItem.gettext2());
                    data.setD1(0.0);
                    data.setD2(0.0);
                    data.setD3(0.0);
                    data.setD4(0.0);
                    data.setD5(0.0);
                    data.setD6(0.0);
                    data.setD7(0.0);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            paser.dynamoDBMapper.save(data);
                        }
                    }).start();


                    final UserDataTimeAccDO data2 = new UserDataTimeAccDO();
                    data2.setUserId(AWSMobileClient.getInstance().getUsername()+":::"+currentItem.gettext2());
                    data2.setD1("");
                    data2.setD2("");
                    data2.setD3("");
                    data2.setD4("");
                    data2.setD5("");
                    data2.setD6("");
                    data2.setD7("");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            paser.dynamoDBMapper.save(data2);
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

                    final SummaryDO putData = new SummaryDO();

                    putData.setUserId(AWSMobileClient.getInstance().getUsername());
                    Map<String, String> tec = obj.getTeachers();
                    tec.put(currentItem.gettext2()+":::"+currentItem.gettext1(),"0");
                    putData.setTeachers(tec);
                    Map<String,String> day = obj.getDays();
                    day.put("d1","0");
                    day.put("d2","0");
                    day.put("d3","0");
                    day.put("d4","0");
                    day.put("d5","0");
                    day.put("d6","0");
                    day.put("d7","0");
                    putData.setDays(day);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            paser.dynamoDBMapper.save(putData);
                        }
                    }).start();


                }catch(Exception e){
                    //alert(e.toString());
                    Toast.makeText(context, e.toString(),Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(context, "Add button is pressed "+currentItem.gettext1()+currentItem.gettext1()+uid,Toast.LENGTH_SHORT).show();
                holder.addbtn.setText("done");
                holder.addbtn.setEnabled(false);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mSearchList.size();
    }
}
