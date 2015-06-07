package scu.edu.moodlogger.graph;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;

/**
 * Created by seanschiff on 6/2/15.
 */
public class GraphViewData {

    enum Category {
        BAD         (0),
        MODERATE    (1),
        GOOD        (2);

        final int mCategory;

        Category(int value) {
            mCategory = value;
        }
    }

    enum Mood {
        CRYING      ( 0, Category.BAD ),
        SAD         ( 1, Category.BAD ),
        ANGRY       ( 2, Category.BAD ),
        CONFUSED    ( 3, Category.BAD ),
        BORED       ( 4, Category.BAD ),
        SLEEPY      ( 5, Category.MODERATE ),
        NEUTRAL     ( 6, Category.MODERATE ),
        HAPPY       ( 7, Category.GOOD ),
        COOL        ( 8, Category.GOOD ),
        EXCITED     ( 9, Category.GOOD ),
        ROMANTIC    ( 10, Category.GOOD ),
        NAUGHTY     ( 11, Category.GOOD );

        private final int mMoodValue;
        private final Category mCategory;

        Mood(int value, Category category) {
            mMoodValue = value;
            mCategory = category;
        }
    }

    private static int currentValue = 6;

    /**
     * Use this function to get a set of fake data for your graphs.
     */
    public static DataPoint[] getSimulatedData() {
        final int count = 30;
        ArrayList<DataPoint> points = new ArrayList<DataPoint>( count ); // reserve.
        Calendar calendar = Calendar.getInstance();
        calendar.add( Calendar.DAY_OF_MONTH, -count );
        Random random = new Random();

        for( int i = 0; i < count; ++i ) {
            calendar.add( Calendar.DAY_OF_MONTH, 1 );
            final int nextRandom = random.nextInt( 12 );
            if( nextRandom > 5 ) {
                if( currentValue < 12 ) {
                    currentValue++;
                }
            }
            else {
                if( currentValue > 0 ) {
                    currentValue--;
                }
            }
            points.add( new DataPoint( calendar.getTime(), currentValue ) );
        }

        return points.toArray( new DataPoint[count] );
    }
}
