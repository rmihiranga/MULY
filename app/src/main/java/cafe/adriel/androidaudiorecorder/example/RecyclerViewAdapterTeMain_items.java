package cafe.adriel.androidaudiorecorder.example;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cafe.adriel.androidaudiorecorder.example.logics.paser;

public class RecyclerViewAdapterTeMain_items extends RecyclerView.Adapter<RecyclerViewAdapterTeMain_items.ExampleViewHolder3> {
    private ArrayList<teacherM_card_item> tStuList;
    private OnItemClickListner tListner;

    public interface OnItemClickListner{
        void onItemclick(int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        tListner=listner;

    }

      class ExampleViewHolder3 extends RecyclerView.ViewHolder{

         ImageView tImageView;
         TextView tTextView;
         TextView ttextEmailview;

         ExampleViewHolder3(final View itemView, final OnItemClickListner listner) {
            super(itemView);
             tImageView=itemView.findViewById(R.id.studentimage);
             tTextView=itemView.findViewById(R.id.studenttext1);
             ttextEmailview=itemView.findViewById(R.id.cardstudentemail);


             itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { //Automatic change
                    if(listner !=null){
                        int position=getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION){
                            listner.onItemclick(position);
                        }
                        paser.TAG = tStuList.get(position).getEmail();
                        paser.TAG2 = tStuList.get(position).getmText4();
                        itemView.getContext().startActivity(new Intent(v.getContext(),teachercardtest.class));

                    }
                }
            });

             tImageView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(listner !=null){
                         int position=getAdapterPosition();
                         if(position !=RecyclerView.NO_POSITION){
                             listner.onItemclick(position);
                         }
                         paser.TAG = tStuList.get(position).getEmail();
                         paser.TAG2 = tStuList.get(position).getmText4();
                         itemView.getContext().startActivity(new Intent(v.getContext(),StudentProfile.class));
                     }
                 }
             });
        }
    }

      RecyclerViewAdapterTeMain_items(ArrayList<teacherM_card_item> itemlist){
        tStuList=itemlist;
    }

    @NonNull
    @Override
    public ExampleViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.teacherm_student_item,parent,false);
        return new ExampleViewHolder3(v,tListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder3 holder, int position) {
            teacherM_card_item currentItem=tStuList.get(position);

        Picasso.get().load(currentItem.getmImagerResourcestudent()).fit().centerInside().into(holder.tImageView);
        holder.tTextView.setText(currentItem.getmText4());
        holder.ttextEmailview.setText(currentItem.getEmail());
    }

    @Override
    public int getItemCount() {
        return tStuList.size();
    }
}
