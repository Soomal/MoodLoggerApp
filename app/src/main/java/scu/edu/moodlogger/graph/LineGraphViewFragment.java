package scu.edu.moodlogger.graph;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ListView;

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

    ListView mListView;

    public LineGraphViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_graph_view, container, false);
        buildCharts(rootView);

        mListView = (ListView) rootView.findViewById(R.id.listView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
            }
        });

        return rootView;
    }

    private void buildCharts(View rootView)
    {
        GraphView graph = (GraphView) rootView.findViewById(R.id.linear_graph);

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
