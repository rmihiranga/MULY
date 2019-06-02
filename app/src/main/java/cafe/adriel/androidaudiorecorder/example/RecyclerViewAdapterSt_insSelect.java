package cafe.adriel.androidaudiorecorder.example;

import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapterSt_insSelect extends RecyclerView.Adapter<RecyclerViewAdapterSt_insSelect.ExampleViewHolder> {

    private ArrayList<stu_selectinstrument_item> mExampleList;
    private OnItemClickListner mListner;

    public interface OnItemClickListner{
        void onItemclick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        mListner=listner;

    }


     class ExampleViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView1;

        ExampleViewHolder(final View itemView, final OnItemClickListner listner) {
            super(itemView);

            mImageView= itemView.findViewById(R.id.st_ins_imageview);//had to be casted??
            mTextView1= itemView.findViewById(R.id.st_ins_textview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //Automatic change
                    if(listner !=null){
                        int position=getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            listner.onItemclick(position);
                        }

                    }
                    itemView.getContext().startActivity(new Intent(v.getContext(),MainActivity.class));
                }
            });
        }
    }

    RecyclerViewAdapterSt_insSelect(ArrayList<stu_selectinstrument_item> exampleList){
        mExampleList=exampleList;


    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.stu_select_instrument_item,parent,false);
        return new ExampleViewHolder(v,mListner);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        stu_selectinstrument_item currentItem=mExampleList.get(position);
        holder.mImageView.setImageResource(currentItem.getmImagerResource());
        holder.mTextView1.setText(currentItem.getmText1());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();//Number of items
    }
}
