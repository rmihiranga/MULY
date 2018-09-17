package cafe.adriel.androidaudiorecorder.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;


public class studentSelectInstrumentsMain extends AppCompatActivity {
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mRecyclerView = (RecyclerView) findViewById(R.id.stu_ins_recyclerview);//Had to be casted?
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
        mRecyclerView = (RecyclerView) findViewById(R.id.stu_ins_recyclerview);//Had to be casted?
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


}
