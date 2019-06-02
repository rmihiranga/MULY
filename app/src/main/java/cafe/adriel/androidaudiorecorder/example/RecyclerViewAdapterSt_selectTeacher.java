package cafe.adriel.androidaudiorecorder.example;

import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import cafe.adriel.androidaudiorecorder.example.logics.paser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapterSt_selectTeacher extends RecyclerView.Adapter<RecyclerViewAdapterSt_selectTeacher.ExampleViewHolder2> {

    private ArrayList<stu_selectTeacher_item> mExampleList;
    private OnItemClickListner mListner = null;

    public interface OnItemClickListner{
        void onItemclick(int position);
        void onProfileClick(int position);
    }
/*
    public void setOnItemClickListner(OnItemClickListner listner){
        mListner=listner;

    }
*/

    class ExampleViewHolder2 extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView1;

        ExampleViewHolder2(final View itemView,final OnItemClickListner listner) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.teacherimage);
            mTextView1 = itemView.findViewById(R.id.teachertext1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner !=null){
                        int position=getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            listner.onItemclick(position);
                        }

                    }
                    paser.TAG = mExampleList.get(getAdapterPosition()).getmEmail();
                    itemView.getContext().startActivity(new Intent(v.getContext(),studentSelectInstrumentsMain.class));
                }
            });

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner !=null){
                        int position=getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            listner.onProfileClick(position);
                        }
                    }
                    paser.TAG = mExampleList.get(getAdapterPosition()).getmEmail();
                    itemView.getContext().startActivity(new Intent(v.getContext(),TeacherProfile.class));
                }
            });
        }
    }

    RecyclerViewAdapterSt_selectTeacher(ArrayList<stu_selectTeacher_item> exampleList){
        mExampleList=exampleList;

    }

    @Override
    public ExampleViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.stu_select_teacher_item,null);
        return new ExampleViewHolder2(v,mListner);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder2 holder, int position) {
        stu_selectTeacher_item currentItem=mExampleList.get(position);
        Picasso.get().load(currentItem.getmImagerResource2()).fit().centerInside().into(holder.mImageView);
        holder.mTextView1.setText(currentItem.getmText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();//Number of items
    }
}
