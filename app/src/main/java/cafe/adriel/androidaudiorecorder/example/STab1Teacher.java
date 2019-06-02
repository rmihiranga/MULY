package cafe.adriel.androidaudiorecorder.example;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cafe.adriel.androidaudiorecorder.example.logics.StudentsDO;
import cafe.adriel.androidaudiorecorder.example.logics.paser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class STab1Teacher extends Fragment {
    private ArrayList<stu_selectTeacher_item> mInsList;
//    private RecyclerView mRecyclerView;
    private RecyclerViewAdapterSt_selectTeacher mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.select_teacher, container, false);


        createInslist();

//      buildRecyclerView();
        final RecyclerView mRecyclerView =rootView.findViewById(R.id.stu_teacherselect_recyclerview);//Had to be casted Gives errors?
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new RecyclerViewAdapterSt_selectTeacher(mInsList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;

    }

    private void createInslist() {
                mInsList = new ArrayList<>();
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
                    List<String> teachers = scan.get(0).getTeachers();


                    for(int i=1 ;i<teachers.size();++i) {
                        mInsList.add(new stu_selectTeacher_item("https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png",
                                teachers.get(i).split(":::")[1],teachers.get(i).split(":::")[0]));
                    }

                } catch (Exception e) {
                   // Log.e("ERROR",e.toString());
                    Toast.makeText(getContext(),e.toString(),Toast.LENGTH_LONG);

                }

    }

}
