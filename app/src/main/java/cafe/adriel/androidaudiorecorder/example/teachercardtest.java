package cafe.adriel.androidaudiorecorder.example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import cafe.adriel.androidaudiorecorder.example.logics.UserDataTimeDO;
import cafe.adriel.androidaudiorecorder.example.logics.paser;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.levitnudi.legacytableview.LegacyTableView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class teachercardtest extends AppCompatActivity {

    private LineChart tLineChart;
    private UserDataTimeDO DATA;
//    private RecyclerView tRecyclerView;
//    private RecyclerViewAdapterTeMain_items tAdapter;
//    private RecyclerView.LayoutManager tLayoutManager;
//    private ArrayList<teacherM_card_item> mactList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachercardtest);


        Map<String, String> attributeNames = new HashMap<String, String>();
        attributeNames.put("#userId", "userId");

        Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
        attributeValues.put(":from", new AttributeValue().withS(paser.TAG+":::"+AWSMobileClient.getInstance().getUsername()));

    try {
    DynamoDBScanExpression dynamoDBScanExpression = new DynamoDBScanExpression()
            .withFilterExpression("#userId = :from")
            .withExpressionAttributeNames(attributeNames)
            .withExpressionAttributeValues(attributeValues);

    PaginatedScanList<UserDataTimeDO> scan = paser.dynamoDBMapper.scan(UserDataTimeDO.class, dynamoDBScanExpression);

    DATA = scan.get(0);
    }catch(Exception e){

        alert(paser.TAG);
    }


        // table...........................

        LegacyTableView.insertLegacyTitle("Mon", "Tue", "Wed", "Thur","Fri","Sat","Sun");

        LegacyTableView.insertLegacyContent("11/01/19", "12/01/19", "13/01/19", "14/01/19","15/01/19","16/01/19","17/01/19",
                DATA.getD1().toString(),
                DATA.getD2().toString(),
                DATA.getD3().toString(),
                DATA.getD4().toString(),
                DATA.getD5().toString(),
                DATA.getD6().toString(),
                DATA.getD7().toString());

        LegacyTableView legacyTableView = findViewById(R.id.weekly_data);
        legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
        legacyTableView.setContent(LegacyTableView.readLegacyContent());


        legacyTableView.setInitialScale(100);//default initialScale is zero (0)


        legacyTableView.setTablePadding(3);

        legacyTableView.build();

        //Bar_Chart....................

            BarChart barChart = findViewById(R.id.barGraph);

            ArrayList barentries = new ArrayList();
            barentries.add(new BarEntry(0f, DATA.getD1().floatValue()));
            barentries.add(new BarEntry(1f, DATA.getD2().floatValue()));
            barentries.add(new BarEntry(2f, DATA.getD3().floatValue()));
            barentries.add(new BarEntry(3f, DATA.getD4().floatValue()));
            barentries.add(new BarEntry(4f, DATA.getD5().floatValue()));
            barentries.add(new BarEntry(5f, DATA.getD6().floatValue()));
            barentries.add(new BarEntry(6f, DATA.getD7().floatValue()));


            BarDataSet dataset = new BarDataSet(barentries, "accuracy");

            BarData data = new BarData(dataset);
            barChart.setData(data);
            XAxis xxAxis = barChart.getXAxis();
            xxAxis.setValueFormatter(new IndexAxisValueFormatter(new String[]{"Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6", "Day 7"}));


            barChart.animateY(2000);


        //Line_chart...............

        tLineChart =  findViewById(R.id.graphView);



        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(0,DATA.getD1().floatValue()));
        entries.add(new Entry(1,DATA.getD2().floatValue()));
        entries.add(new Entry(2,DATA.getD3().floatValue()));
        entries.add(new Entry(3,DATA.getD4().floatValue()));
        entries.add(new Entry(4,DATA.getD5().floatValue()));
        entries.add(new Entry(5,DATA.getD6().floatValue()));
        entries.add(new Entry(6,DATA.getD7().floatValue()));

        XAxis xAxis = tLineChart.getXAxis();
        xAxis.setAxisMinimum(0f);
        xAxis.setLabelCount(7);
        xAxis.setAxisMaximum(7f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis yAxis = tLineChart.getAxisLeft();
        tLineChart.getAxisRight().setEnabled(false);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(24f);

        tLineChart.animateXY(2000,1500);



        LineDataSet dataSet = new LineDataSet(entries, "Student Progress");

        LineData lineData = new LineData(dataSet);
        dataSet.setDrawCircles(false);
        dataSet.setColor(Color.BLUE);
        tLineChart.setData(lineData);
        tLineChart.invalidate();

    }

    private void alert(String msg){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(teachercardtest.this);
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
