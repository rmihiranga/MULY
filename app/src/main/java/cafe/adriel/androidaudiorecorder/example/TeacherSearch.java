package cafe.adriel.androidaudiorecorder.example;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cafe.adriel.androidaudiorecorder.example.logics.TeachersDO;
import cafe.adriel.androidaudiorecorder.example.logics.paser;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeacherSearch extends AppCompatActivity {

    private RecyclerView mRecyclerview;
    private RecyclerView.Adapter mApdapter;
    private RecyclerView.LayoutManager mLayoutmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.stu_teacher_add_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.teacher_search234);
        // searchItem.expandActionView();
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                createItems(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void createItems(String query){
        ArrayList<stu_search_teacher_item> searchList = new ArrayList<>();


        Map<String, String> attributeNames2 = new HashMap<String, String>();
        attributeNames2.put("#name", "name");
        Map<String, AttributeValue> attributeValues2 = new HashMap<String, AttributeValue>();
        attributeValues2.put(":from", new AttributeValue().withS(query));
        DynamoDBScanExpression dynamoDBScanExpression2 = new DynamoDBScanExpression()
                .withFilterExpression("#name = :from")
                .withExpressionAttributeNames(attributeNames2)
                .withExpressionAttributeValues(attributeValues2);
        PaginatedScanList<TeachersDO> scan2 = paser.dynamoDBMapper.scan(TeachersDO.class, dynamoDBScanExpression2);


        if(scan2.size()>0){
            for(int i=0; i<scan2.size();++i){
                searchList.add(new stu_search_teacher_item("https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png",scan2.get(i).getName(),scan2.get(i).getEmail()));
            }
        }


        mRecyclerview=findViewById(R.id.teacher_search_recyclerView);
        mRecyclerview.setHasFixedSize(true);//Performance
        mLayoutmanager=new LinearLayoutManager(this);
        mApdapter=new RecyclerViewAdapterSt_search_teacher(searchList);

        mRecyclerview.setLayoutManager(mLayoutmanager);
        mRecyclerview.setAdapter(mApdapter);


    }
}
