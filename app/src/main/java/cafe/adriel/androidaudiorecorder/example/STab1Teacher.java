package cafe.adriel.androidaudiorecorder.example;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


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
//        buildRecyclerView();
        final RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.stu_teacherselect_recyclerview);//Had to be casted Gives errors?
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mAdapter = new RecyclerViewAdapterSt_selectTeacher(mInsList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;

    }

    public void createInslist() {
        //Adding items to instrument RecycleView
        mInsList = new ArrayList<>();
        mInsList.add(new stu_selectTeacher_item(R.drawable.person, "Details about the teacher"));//deatils for the card view
    }

//    public void buildRecyclerView() {
//
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this.getContext());
//        mAdapter = new RecyclerViewAdapterSt_selectTeacher(mInsList);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);
//
//        mAdapter.setOnItemClickListner(new RecyclerViewAdapterSt_selectTeacher.OnItemClickListner() {
//            @Override
//            public void onItemclick(int position) {
//
//
//            }
//        });
//
//
//    }
}
