package cafe.adriel.androidaudiorecorder.example;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class  studentSelectInstrumentsMain extends AppCompatActivity {
    private ArrayList<stu_selectinstrument_item> mInsList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapterSt_insSelect mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_select_instrument_main);

        createInslist();
        buildRecyclerView();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mRecyclerView = findViewById(R.id.stu_ins_recyclerview);//Had to be casted?
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapterSt_insSelect(mInsList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void createInslist() {
        //Adding items to instrument RecycleView
        mInsList = new ArrayList<>();
        mInsList.add(new stu_selectinstrument_item(R.drawable.pianokeys, "Piano"));


    }

    public void buildRecyclerView() {
        mRecyclerView =  findViewById(R.id.stu_ins_recyclerview);//Had to be casted?
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapterSt_insSelect(mInsList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListner(new RecyclerViewAdapterSt_insSelect.OnItemClickListner() {
            @Override
            public void onItemclick(int position) {


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stinsmenu, menu);
        return true;


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.profile) {

            Intent intent = new Intent(this,edit_profile.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
