package scu.edu.moodlogger.graph;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import scu.edu.moodlogger.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;


public class BarGraphViewFragment extends Fragment {

    public BarGraphViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bar_graph_view, container, false);

        GraphView graph2 = (GraphView) rootView.findViewById(R.id.bar_graph);
        BarGraphSeries<DataPoint> series2 = new BarGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, -1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        series2.setSpacing(50);
        graph2.addSeries(series2);

        return rootView;
    }

}
