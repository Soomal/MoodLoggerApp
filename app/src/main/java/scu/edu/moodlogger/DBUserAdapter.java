package scu.edu.moodlogger;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.security.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import scu.edu.moodlogger.graph.GraphViewData;
/**
 * @author Soomal Choudhary
 *         This class provides functionality to interact with database.
 */

public class DBUserAdapter {

    public static final String KEY_FIRSTNAME = "username";
    public static final String KEY_LASTNAME = "password";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    public static final String KEY_ROWID = "rowid";
    public static final String KEY_USERID = "userid";
    public static final String KEY_MOODID = "moodid";
    public static final String KEY_MOOD = "mood";
    public static final String KEY_DATE = "date";
    public static final String KEY_PICTURE = "picture";
    public static final String KEY_NOTE = "notes";
    public static final String KEY_LOCATION = "location";
    public static final String[] ALL_KEYS_MOOD = new String[]{KEY_ROWID, KEY_USERID, KEY_MOODID, KEY_MOOD, KEY_DATE, KEY_PICTURE, KEY_NOTE, KEY_LOCATION};

    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "usersdb";
    private static final int DATABASE_VERSION = 1;

    /* NAME OF TABLES */
    private static final String DATABASE_TABLE_USERS = "users";
    private static final String DATABASE_TABLE_MOODS = "mood";

    private static final String DATABASE_CREATE =
            "create table users (_id integer primary key autoincrement, "
                    + "username text not null, "
                    + "password text not null)";

    private static final String DATABASE_CREATE_MOOD =
            "    CREATE TABLE IF NOT EXISTS `mood` (" +
                    "  `rowid` INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "  `userid` TEXT NOT NULL," +
                    "  `moodid` INTEGER NOT NULL," +
                    "  `mood` TEXT NOT NULL," +
                    "  `date` TEXT NOT NULL," +
                    //  "  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +

                    "  `picture` TEXT NOT NULL," +
                    "  `notes` TEXT NOT NULL," +
                    "  `location` TEXT NOT NULL" +
                    ")";


    // Add a new set of values to be inserted into the database.
    public long insertMood(Mood currentMood) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERID, currentMood.getUserId());
        initialValues.put(KEY_MOODID, currentMood.getMoodId());
        initialValues.put(KEY_MOOD, currentMood.getMood());
        initialValues.put(KEY_DATE, currentMood.getDate());
        initialValues.put(KEY_PICTURE, currentMood.getPicture());
        initialValues.put(KEY_NOTE, currentMood.getNotes());
        initialValues.put(KEY_LOCATION, currentMood.getLocation());

        // Insert the data into the database.
        return db.insert(DATABASE_TABLE_MOODS, null, initialValues);
    }


    private Context context = null;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBUserAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE_MOOD);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS users");
            onCreate(db);
        }
    }


    public void open() throws SQLException {
        db = DBHelper.getWritableDatabase();
    }


    public void close() {
        DBHelper.close();
    }

    public long AddUser(String fname, String lname, String username, String password) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_FIRSTNAME, fname);
        initialValues.put(KEY_LASTNAME, lname);
        initialValues.put(KEY_USERNAME, username);
        initialValues.put(KEY_PASSWORD, password);
        return db.insert(DATABASE_TABLE_USERS, null, initialValues);

    }

    public boolean Login(String username, String password) throws SQLException {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE_USERS + " WHERE username=? AND password=?", new String[]{username, password});
        if (mCursor != null) {
            if (mCursor.getCount() > 0) {
                return true;
            }
        }
        return false;
    }


    public int getCount(String username, String mood) {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE_MOODS + " WHERE userid=? AND mood=?", new String[]{username, mood});

        return mCursor.getCount();
    }


    /**
     * @input : user id
     * @output: data Structure with the number of moods
     */

    public DataTypeChart getData(String username) {


        DataTypeChart data = new DataTypeChart();


        String where = KEY_DATE + " LIKE '%" + "2015" + "%' AND " + KEY_USERID + " = '" + username + "'";
        String orderBy = KEY_ROWID + " DESC";
        Cursor c = db.query(true, DATABASE_TABLE_MOODS, ALL_KEYS_MOOD, where, null, null, null, orderBy, null);

        String cMood = null;
        if (c != null && (c.getCount() > 0)) {
            c.moveToFirst();
            cMood = c.getString(c.getColumnIndex(KEY_MOOD));


        }


        data.numberOfData = c.getCount();

        for (int i = 0; i < 12; i++) {
            data.datas[i] = getCount(username, data.labels[i]);

            Log.i("myAppTag", "Found log-entry for my app:" + data.labels[i] + getCount(username, data.labels[i]));

        }


        return data;
    }


    /**
     * @input :  user id,  date
     * @output: mood
     */

    public String getMood(String sDate, String userid) {

        String where = KEY_DATE + " LIKE '%" + sDate + "%' AND " + KEY_USERID + " = '" + userid + "'";
        String orderBy = KEY_ROWID + " DESC";
        Cursor c = db.query(true, DATABASE_TABLE_MOODS, ALL_KEYS_MOOD, where, null, null, null, orderBy, null);

        String cMood = null;
        if (c != null && (c.getCount() > 0)) {
            c.moveToFirst();
            cMood = c.getString(c.getColumnIndex(KEY_MOOD));

        }
        return cMood;

    }

    /**
     * Query used to populate line graph.
     */
    public GraphViewData[] getLineGraphData(String userId) {
        final String query =
                "SELECT " + KEY_DATE + ", " + KEY_MOODID + " " +
                "FROM " + DATABASE_TABLE_MOODS + " " +
                "WHERE " + KEY_USERID + " = '" + userId + "' " +
                "ORDER BY " + KEY_DATE + " ASC;";

        GraphViewData data;
        ArrayList<GraphViewData> dataArray = new ArrayList<GraphViewData>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                data = new GraphViewData(
                        cursor.getString(cursor.getColumnIndex(KEY_DATE)),
                        cursor.getInt(cursor.getColumnIndex(KEY_MOODID)));
                dataArray.add(data);
            }
            while (cursor.moveToNext());
        }

        GraphViewData[] dataList = null;
        if (dataArray.size() > 0) {
            dataList = dataArray.toArray(new GraphViewData[dataArray.size()]);
        }

        return dataList;
    }
}