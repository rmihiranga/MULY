package cafe.adriel.androidaudiorecorder.example;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class sortbydialog extends AppCompatDialogFragment {

    private RadioGroup radioGroup;
    private RadioButton radiobutton;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());

        LayoutInflater inflater=getActivity().getLayoutInflater();
        final View view=inflater.inflate(R.layout.sortby_dialog,null);
        radioGroup = view.findViewById(R.id.sortgroup);
        builder.setView(view)
                .setTitle("Sort By")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int radioId = radioGroup.getCheckedRadioButtonId();

                        radiobutton = view.findViewById(radioId);



                        Toast.makeText(getActivity(), "Selected Radio Button: " + radiobutton.getText(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                )
        ;


        return builder.create();



    }


    public void sortselect(){
        int radioid=radioGroup.getCheckedRadioButtonId();





    }
}
