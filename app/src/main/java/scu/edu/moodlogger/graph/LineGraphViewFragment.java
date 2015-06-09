package scu.edu.moodlogger.graph;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import scu.edu.moodlogger.DBUserAdapter;
import scu.edu.moodlogger.DataTypeChart;
import scu.edu.moodlogger.graph.GraphViewData;
import scu.edu.moodlogger.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;

import java.util.Calendar;
import java.util.Date;

public class LineGraphViewFragment extends Fragment {

    View mRootView;
    ListView mListView;

    public LineGraphViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_graph_view, container, false);
        initGraph();
        //buildCharts();

        mListView = (ListView) mRootView.findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
            }
        });

        return mRootView;
    }

    private void initGraph() {
        //getting the user id that has been temporarily stored
        Activity activity = getActivity();
        Context context = activity.getApplicationContext();

        // Getting the user id that has been temporarily stored.
        SharedPreferences sp = activity.getSharedPreferences("user_pref", Activity.MODE_PRIVATE);
        String userId = sp.getString("user_key", "");
        GraphViewData[] data = null;
        CharSequence text = "";
        int duration = Toast.LENGTH_SHORT;

        /**
         * Connection with the database.
         */
        try {
            DBUserAdapter dbUser = new DBUserAdapter(activity);
            dbUser.open();
            data = dbUser.getLineGraphData(userId);
        }
        catch(Exception e) {
            text = "Failed";

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        /**
         * Here, we populate the graph and change its visuals.
         */
        GraphView graph = (GraphView) mRootView.findViewById(R.id.linear_graph);

        // Important: get the points you need.
        DataPoint[] points = null;
        if (data != null && data.length >= 2) {
            // helps with a bug in line chart when logging within a day.
            int diff = (int) (data[data.length-1].getDate().getTime() - data[0].getDate().getTime());
            int diffInDays = (int) (diff / (1000 * 60 * 60 * 24));
            if (diffInDays >= 1) {
                points = new DataPoint[data.length];

                for (int i = 0; i < data.length; i++) {
                    points[i] = data[i].getDataPoint();
                }
            }
            else {
                points = GraphViewData.getSimulatedData();
            }
        }
        else { // use simulated data.
            points = GraphViewData.getSimulatedData();
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>( points );

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(activity));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 3 because of the space

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(points[0].getX());
        graph.getViewport().setMaxX(points[points.length - 1].getX());
        graph.getViewport().setXAxisBoundsManual(true);

        // set manual y bounds to have nice steps
        graph.getViewport().setMinY( 0 );
        graph.getViewport().setMaxY( 12 );
        graph.getViewport().setYAxisBoundsManual(true);

        graph.addSeries(series);
    }

    /**
     * I used this for simulated data.
     */
    private void buildCharts() {
        GraphView graph = (GraphView) mRootView.findViewById(R.id.linear_graph);

        DataPoint[] points = GraphViewData.getSimulatedData();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>( points );
//        GridLabelRenderer glr = graph.getGridLabelRenderer();
//        glr.setGridColor(0xFFFFFFFF);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 3 because of the space

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(points[0].getX());
        graph.getViewport().setMaxX(points[points.length - 1].getX());
        graph.getViewport().setXAxisBoundsManual(true);

        // set manual y bounds to have nice steps
        graph.getViewport().setMinY( 0 );
        graph.getViewport().setMaxY( 12 );
        graph.getViewport().setYAxisBoundsManual(true);

        graph.addSeries(series);
    }
}
