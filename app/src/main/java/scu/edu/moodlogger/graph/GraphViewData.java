package scu.edu.moodlogger.graph;

import com.jjoe64.graphview.series.DataPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

/**
 * Created by seanschiff on 6/2/15.
 */
public class GraphViewData {

    String mDateTime;
    int mMoodId;

    public GraphViewData(String dateTime, int moodId) {
        mDateTime = dateTime;
        mMoodId = moodId;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try {
            calendar.setTime(sdf.parse(mDateTime));// all done
        }
        catch(ParseException exception) {
            exception.printStackTrace();
        }
        return calendar;
    }

    public Date getDate() {
        Calendar cal = getCalendar();
        return cal.getTime();
    }

    public int getMoodValue() {
        return translateMoodIdToMoodValue(mMoodId);
    }

    public DataPoint getDataPoint() {
        Calendar cal = getCalendar();
        Date date = cal.getTime();
        return new DataPoint(date, getMoodValue());
    }

    public static enum Category {
        BAD         (0),
        MODERATE    (1),
        GOOD        (2);

        final int mCategory;

        Category(int value) {
            mCategory = value;
        }
    }

    public static enum Mood {
        CRYING      (10, 0, Category.BAD ),
        SAD         (12, 1, Category.BAD ),
        ANGRY       (4, 2, Category.BAD ),
        CONFUSED    (2, 3, Category.BAD ),
        BORED       (7, 4, Category.BAD ),
        SLEEPY      (8, 5, Category.MODERATE ),
        NEUTRAL     (9, 6, Category.MODERATE ),
        HAPPY       (1, 7, Category.GOOD ),
        COOL        (6, 8, Category.GOOD ),
        EXCITED     (5, 9, Category.GOOD ),
        ROMANTIC    (11, 10, Category.GOOD ),
        NAUGHTY     (3, 11, Category.GOOD );

        public final int mId;
        public final int mMoodValue;
        public final Category mCategory;

        Mood(int id, int value, Category category) { // id comes from the mood logging screen.
            mId = id;
            mMoodValue = value;
            mCategory = category;
        }
    }

    private static HashMap<Integer, Integer> moodValueByMoodId;
    static {
        moodValueByMoodId = new HashMap<Integer, Integer>();
        moodValueByMoodId.put(10, 0); // crying
        moodValueByMoodId.put(12, 1); // sad
        moodValueByMoodId.put(4, 2); // angry
        moodValueByMoodId.put(2, 3); // confused
        moodValueByMoodId.put(7, 4); // bored
        moodValueByMoodId.put(8, 5); // sleepy
        moodValueByMoodId.put(9, 6); // neutral
        moodValueByMoodId.put(1, 7); // happy
        moodValueByMoodId.put(6, 8); // cool
        moodValueByMoodId.put(5, 9); // excited
        moodValueByMoodId.put(11, 10); // romantic
        moodValueByMoodId.put(3, 11); // naughty
    }

    private static int currentValue = 6;

    /**
     * Use this function to translate the moodId from the database to the value the mood represents.
     */
    public static int translateMoodIdToMoodValue(int moodId) {
        return moodValueByMoodId.get(moodId);
    }

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

        return points.toArray(new DataPoint[count]);
    }

}
