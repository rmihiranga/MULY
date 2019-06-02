package cafe.adriel.androidaudiorecorder.example;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import de.hdodenhof.circleimageview.CircleImageView;

public class bottomsheetfragment extends BottomSheetDialogFragment {
    NavigationView navigation;


    bottomsheetfragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottomnav_sheet, container, false);

        CircleImageView img;
        NavigationView navigation1=v.findViewById(R.id.navigation_view);
        final View header = navigation1.getHeaderView(0);
        img = header.findViewById(R.id.profile_imageH);

        TextView t1 = header.findViewById(R.id.writeonme1);
        TextView t2 = header.findViewById(R.id.writeonme2);
        try {
            t1.setText(AWSMobileClient.getInstance().getUserAttributes().get("name"));
            t2.setText(AWSMobileClient.getInstance().getUsername());
        }catch(Exception e){

        }

        Picasso.get().load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNo6N1Q-CvUQOkusR_NyMW0Tff7K4oE8Lq8P0US-vO3Y_mkNkS").fit().centerInside().into(img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(header.getContext(), edit_profile.class);
                startActivity(intent1);
            }
        });



        navigation1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav1) {
                    Toast.makeText(getActivity(), "Message option is pressed",
                            Toast.LENGTH_LONG).show();
                    return true;
                }

                if (id == R.id.nav2) {
                    Toast.makeText(getActivity(), "Instrument option is pressed",
                            Toast.LENGTH_LONG).show();
                    return true;
                }

                return true;
            }
        });
        return v;



    }



}


