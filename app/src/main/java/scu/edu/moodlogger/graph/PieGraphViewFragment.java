package scu.edu.moodlogger.graph;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import scu.edu.moodlogger.DBUserAdapter;
import scu.edu.moodlogger.DataTypeChart;
import scu.edu.moodlogger.PieChart;
import scu.edu.moodlogger.R;

public class PieGraphViewFragment extends Fragment {

    View mView;

    public PieGraphViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_pie_graph_view, container, false);
        init();
        return mView;
    }

    private void init() {
        //getting the user id that has been temporarily stored
        Activity activity = getActivity();
        SharedPreferences sp = activity.getSharedPreferences("user_pref", Activity.MODE_PRIVATE);
        String userId = sp.getString("user_key","");
        DataTypeChart data = new DataTypeChart();

        //connect to the database.
//        try {
//            DBUserAdapter dbUser = new DBUserAdapter(activity);
//            dbUser.open();
//            data=dbUser.getData(userId);
//        }
//
//        catch(Exception e)
//        {
//            Context context = activity.getApplicationContext();
//            CharSequence text = userId;
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
//        }

        PieChart pieChart = (PieChart) mView.findViewById(R.id.pieGraph);
        float[] chartData = new float[6];
        chartData[0] = 34;
        chartData[1] = 24;
        chartData[2] = 32;
        chartData[3] = 24;
        chartData[4] = 53;
        chartData[5] = 23;
        pieChart.setData(chartData);

        String[] labels = new String[6];
        labels[0] = "JOHN";
        labels[1] = "GEORGE";
        labels[2] = "RAYMOND";
        labels[3] = "STEPHEN";
        labels[4] = "JACK";
        labels[5] = "BOBBY";
        pieChart.setLabels(labels);
    }
}
