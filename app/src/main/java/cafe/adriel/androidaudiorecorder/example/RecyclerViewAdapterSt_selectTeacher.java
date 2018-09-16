package cafe.adriel.androidaudiorecorder.example;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapterSt_selectTeacher extends RecyclerView.Adapter<RecyclerViewAdapterSt_selectTeacher.ExampleViewHolder2> {

    private ArrayList<stu_selectTeacher_item> mExampleList;
    private OnItemClickListner mListner;

    public interface OnItemClickListner{
        void onItemclick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        mListner=listner;

    }


    public static class ExampleViewHolder2 extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;

        public ExampleViewHolder2(final View itemView,final OnItemClickListner listner) {
            super(itemView);

            mImageView= (ImageView) itemView.findViewById(R.id.teacherimage);//had to be casted??
            mTextView1= (TextView) itemView.findViewById(R.id.teachertext1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //Automatic change
                    if(listner !=null){
                        int position=getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            listner.onItemclick(position);
                        }

                    }
                    itemView.getContext().startActivity(new Intent(v.getContext(),studentSelectInstrumentsMain.class));
                }
            });
        }
    }

    public RecyclerViewAdapterSt_selectTeacher(ArrayList<stu_selectTeacher_item> exampleList){
        mExampleList=exampleList;

    }

    @Override
    public ExampleViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.stu_select_teacher_item,null);
        ExampleViewHolder2 evh=new ExampleViewHolder2(v,mListner);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder2 holder, int position) {
        stu_selectTeacher_item currentItem=mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getmImagerResource2());
        holder.mTextView1.setText(currentItem.getmText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();//Number of items
    }
}
