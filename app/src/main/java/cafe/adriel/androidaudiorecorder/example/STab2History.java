package cafe.adriel.androidaudiorecorder.example;

import androidx.fragment.app.Fragment;
import cafe.adriel.androidaudiorecorder.example.logics.SummaryDO;
import cafe.adriel.androidaudiorecorder.example.logics.paser;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.levitnudi.legacytableview.LegacyTableView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class STab2History extends Fragment {

    //PieChart pieChart1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.history, container, false);

        // table...........................


        Map<String, String> attributeNames2 = new HashMap<String, String>();
        attributeNames2.put("#userId", "userId");
        Map<String, AttributeValue> attributeValues2 = new HashMap<String, AttributeValue>();
        attributeValues2.put(":from", new AttributeValue().withS(AWSMobileClient.getInstance().getUsername()));
        DynamoDBScanExpression dynamoDBScanExpression2 = new DynamoDBScanExpression()
                .withFilterExpression("#userId = :from")
                .withExpressionAttributeNames(attributeNames2)
                .withExpressionAttributeValues(attributeValues2);
        PaginatedScanList<SummaryDO> getsummery  = paser.dynamoDBMapper.scan(SummaryDO.class, dynamoDBScanExpression2);

        SummaryDO obj = getsummery.get(0);



        LegacyTableView.insertLegacyTitle("Mon", "Tue", "Wed", "Thur","Fri","Sat","Sun");

        LegacyTableView.insertLegacyContent("11/01/19", "12/01/19", "13/01/19", "14/01/19","15/01/19","16/01/19","17/01/19",
                obj.getDays().get("d1"),
                obj.getDays().get("d2"),
                obj.getDays().get("d3"),
                obj.getDays().get("d4"),
                obj.getDays().get("d5"),
                obj.getDays().get("d6"),
                obj.getDays().get("d7"));

        LegacyTableView legacyTableView =  rootView.findViewById(R.id.my_table_data);
        legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
        legacyTableView.setContent(LegacyTableView.readLegacyContent());
        legacyTableView.setContentTextSize(25);
        legacyTableView.setTitleTextSize(25);


        legacyTableView.setInitialScale(100);//default initialScale is zero (0)


        legacyTableView.setTablePadding(3);

        legacyTableView.build();


        //pie chart
        PieChart pieChart1 = (PieChart) rootView.findViewById(R.id.pieChart);
        ArrayList yvals = new ArrayList();

        for(String s:obj.getTeachers().keySet()){
            if(s.endsWith(":::null")){
             yvals.add(new PieEntry(Float.valueOf(obj.getTeachers().get(s)),s.substring(0,5)));
            }
        }

        PieDataSet dataset1 = new PieDataSet(yvals,"hours");

//

        PieData data1 = new PieData(dataset1);

        pieChart1.setData(data1);
        dataset1.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart1.setHoleRadius(10f);
        pieChart1.setTransparentCircleAlpha(0);
        pieChart1.setDescription(null);

        //Line_chart...............

        LineChart tLineChart =  rootView.findViewById(R.id.linechart1);



        ArrayList<Entry> entries = new ArrayList<>();

        entries.add(new Entry(0,Float.valueOf(obj.getDays().get("d1"))));
        entries.add(new Entry(1,Float.valueOf(obj.getDays().get("d2"))));
        entries.add(new Entry(2,Float.valueOf(obj.getDays().get("d3"))));
        entries.add(new Entry(3,Float.valueOf(obj.getDays().get("d4"))));
        entries.add(new Entry(4,Float.valueOf(obj.getDays().get("d5"))));
        entries.add(new Entry(5,Float.valueOf(obj.getDays().get("d6"))));
        entries.add(new Entry(6,Float.valueOf(obj.getDays().get("d7"))));

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



        return rootView;
    }
}