package cafe.adriel.androidaudiorecorder.example;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.Toast;

public class StudentMActivity extends AppCompatActivity {

    /**
     * The {@link PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    private static PinpointManager pinpointManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_m);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mSectionsPagerAdapter =new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager= findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout= findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        PinpointConfiguration config = new PinpointConfiguration(
                StudentMActivity.this,
                AWSMobileClient.getInstance(),
                AWSMobileClient.getInstance().getConfiguration()
        );
        pinpointManager = new PinpointManager(config);
        pinpointManager.getSessionClient().startSession();


        try {
            getSupportActionBar().setTitle( AWSMobileClient.getInstance().getUserAttributes().get("name"));
        }catch(Exception e) {
            getSupportActionBar().setTitle("Unknown Student");
        }


    }


    private void setupViewPager(ViewPager viewpager){
        SectionsPagerAdapter adapter=new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new STab1Teacher(), "Teacher");
        adapter.addFragment(new STab2History(), "History");
        viewpager.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu1, menu)   ;
        //switch()
        return true;
    }

    @Override
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
        else if (id == R.id.signout){
            pinpointManager.getSessionClient().stopSession();
            //pinpointManager.getAnalyticsClient().submitEvents();
            AWSMobileClient.getInstance().signOut();
            Intent intent1 = new Intent(StudentMActivity.this, login.class);
            startActivity(intent1);

        }

        else if(id==R.id.action_add_teacher){
            Intent intent = new Intent(this,TeacherSearch.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    }





