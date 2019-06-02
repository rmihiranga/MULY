package cafe.adriel.androidaudiorecorder.example;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cafe.adriel.androidaudiorecorder.example.logics.TeachersDO;
import cafe.adriel.androidaudiorecorder.example.logics.paser;

import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherMain extends AppCompatActivity {
    private ArrayList<teacherM_card_item> mactList; //list for card items

    private RecyclerView tRecyclerView;
    private RecyclerViewAdapterTeMain_items tAdapter;
    private RecyclerView.LayoutManager tLayoutManager;
    public NavigationView navigation;

    private static PinpointManager pinpointManager;


    FloatingActionButton floatingActionButton; //floating button
    BottomAppBar bottomAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_teacher_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /*Creating the recycler view*/
        createitemlist();
        buildRecyclerView();
        /*Creating top toolbar*/
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floatingActionButton = findViewById(R.id.fab);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        /*The back button*/
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  getSupportActionBar().setDisplayShowHomeEnabled(true);

        /*bottom menu icon listners*/
        BottomAppBar bar = findViewById(R.id.bottomAppBar);
        bar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_search1) {
                    Toast.makeText(TeacherMain.this, "Search button is pressed",
                            Toast.LENGTH_LONG).show();
                    return true;
                }

                return true;
            }
        });
        /*Bottom sheet navigation creation*/
        BottomAppBar bar1 =  findViewById(R.id.bottomAppBar);
        bar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initializing a bottom sheet
                BottomSheetDialogFragment bottomSheetDialogFragment = new bottomsheetfragment();

                //show it
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

                // Handle the navigation click by showing a BottomDrawer etc.


            }
        });



        PinpointConfiguration config = new PinpointConfiguration(
                TeacherMain.this,
                AWSMobileClient.getInstance(),
                AWSMobileClient.getInstance().getConfiguration()
        );
        pinpointManager = new PinpointManager(config);
        pinpointManager.getSessionClient().startSession();



        try {
            getSupportActionBar().setTitle( AWSMobileClient.getInstance().getUserAttributes().get("name"));
        }catch(Exception e) {
            getSupportActionBar().setTitle("Unknown Teacher");
        }

    }

    public void createitemlist() {
                mactList = new ArrayList<>();
                try {

                    Map<String, String> attributeNames = new HashMap<String, String>();
                    attributeNames.put("#teacherid", "teacherid");

                    Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
                    attributeValues.put(":from", new AttributeValue().withS(AWSMobileClient.getInstance().getUsername()));


                    DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
                            .withFilterExpression("#teacherid = :from")
                            .withExpressionAttributeNames(attributeNames)
                            .withExpressionAttributeValues(attributeValues);

                    PaginatedScanList<TeachersDO> scan = paser.dynamoDBMapper.scan(TeachersDO.class, dynamoDBScanExpression);

                    List<String> students = scan.get(0).getStudents();

                    for (int i = 1; i < students.size(); i++) {

                        mactList.add(new teacherM_card_item("https://cdn.iconscout.com/icon/free/png-256/avatar-369-456321.png",
                                students.get(i).split(":::")[1],students.get(i).split(":::")[0]));

                    }
                } catch (Exception e) {
                    alert(e.toString());

                }
    }
    public void buildRecyclerView() {
        tRecyclerView = (RecyclerView) findViewById(R.id.stu_teacherselect_recyclerview);//Had to be casted?
        tRecyclerView.setHasFixedSize(true);
        tLayoutManager = new LinearLayoutManager(this);
        tAdapter = new RecyclerViewAdapterTeMain_items(mactList);
        tRecyclerView.setLayoutManager(tLayoutManager);
        tRecyclerView.setAdapter(tAdapter);

        tAdapter.setOnItemClickListner(new RecyclerViewAdapterTeMain_items.OnItemClickListner() {
            @Override
            public void onItemclick(int position) {
                try {
                    //Thread.sleep(6000);
                }catch(Exception e){}
                    Toast.makeText(TeacherMain.this,"Done",Toast.LENGTH_LONG);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teachermenu1, menu);
        bottomAppBar.replaceMenu(R.menu.teacherbottom);

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

            Intent intent = new Intent(this, edit_profile.class);
            this.startActivity(intent);
            return true;
        }

        if (id == R.id.sorting) {

            openSortDialog();
            return true;
        }

        if(id == R.id.signout){
            pinpointManager.getSessionClient().stopSession();
            //pinpointManager.getAnalyticsClient().submitEvents();
            AWSMobileClient.getInstance().signOut();
            Intent intent1 = new Intent(TeacherMain.this, login.class);
            startActivity(intent1);

        }

        return super.onOptionsItemSelected(item);
    }

    /*Sort dialog box*/
    public void openSortDialog() {
        sortbydialog sortDialog = new sortbydialog();
        sortDialog.show(getSupportFragmentManager(), "Sort by dialog");

    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    private void alert(String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(TeacherMain.this);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Try again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
