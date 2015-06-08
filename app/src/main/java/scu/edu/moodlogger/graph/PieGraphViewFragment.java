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
        Context context = activity.getApplicationContext();

        // Getting the user id that has been temporarily stored.
        SharedPreferences sp = activity.getSharedPreferences("user_pref", Activity.MODE_PRIVATE);
        String userId = sp.getString("user_key", "");
        DataTypeChart data = new DataTypeChart();
        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;

        /**
         * Connection with the database.
         */
        try {
            DBUserAdapter dbUser = new DBUserAdapter(activity);
            dbUser.open();
            data = dbUser.getData(userId);

            text = userId + " " + data.numberOfData;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        catch(Exception e) {
            text = "Failed";

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        final int limit = 12;
        PieChart pieChart = (PieChart) mView.findViewById(R.id.pieGraph);
        float[] datas = new float[limit];
        String[] labels = new String[limit];
        int count=0;
        for(int i=0; i<limit; i++) {
            if(data.datas[i]!=0) {
                datas[count]=data.datas[i];
                labels[count++]=data.labels[i];
            }
        }

        pieChart.setData(datas);
        pieChart.setLabels(labels);
    }
}
