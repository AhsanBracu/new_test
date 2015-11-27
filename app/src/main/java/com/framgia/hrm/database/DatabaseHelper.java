package com.framgia.hrm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.framgia.hrm.model.Department;
import com.framgia.hrm.model.Position;
import com.framgia.hrm.model.SearchStaff;
import com.framgia.hrm.model.Staff;
import com.framgia.hrm.model.Status;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HRM.db";
    private static final int DATABASE_VERSION = 1;
    private DatabaseHelper myDbHelper;
    private Context context;
    private SQLiteDatabase mDb;
    /**
     * table FTS
     */
    public static final String COLUMN_NAME = "fruits";
    public static final String COLUMN_PHONE = "phone";
    private static final String FTS_VIRTUAL_TABLE = "staff_info";
    public static final String SEARCH_STAFF = "search_item";
    public static final String FTS_BIRTH_PLACE_FIELD = "birth_place";
    public static final String FTS_BIRTH_DATE_FIELD = "birth_date";
    private static final String FTS_TABLE_CREATE =
            "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +
                    " USING fts3 (" +
                    COLUMN_NAME + ", " +
                    FTS_BIRTH_DATE_FIELD + ", " +
                    FTS_BIRTH_PLACE_FIELD + ", " +
                    COLUMN_PHONE + ", " +
                    SEARCH_STAFF +
                    ")";
    /**
     * table names
     */
    private static final String TABLE_DEPARTMENT = "department";
    private static final String TABLE_STAFF = "staff";
    private static final String TABLE_STATUS = "status";
    private static final String TABLE_POSITION = "position";
    private static final String TABLE_ACTIVITY = "activity";

    /**
     * Department table column names
     */
    private static final String DEPARTMENT_COLUMN_ID = "dept_id";
    private static final String DEPARTMENT_COLUMN_NAME = "dept_name";

    /**
     * Status table column names
     */
    private static final String STATUS_COLUMN_ID = "status_id";
    private static final String STATUS_COLUMN_NAME = "status_name";

    /**
     * Position table column names
     */
    private static final String POSITION_COLUMN_ID = "position_id";
    private static final String POSITION_COLUMN_NAME = "position_name";

    /**
     * Activity table column names
     */
    private static final String ACTIVITY_COLUMN_ID = "activity_id";
    private static final String ACTIVITY_COLUMN_NAME = "activity_name";

    /**
     * Staff table column names
     */
    private static final String STAFF_COLUMN_ID = "staff_id";
    private static final String STAFF_COLUMN_NAME = "name";
    private static final String STAFF_COLUMN_DATE_OF_BIRTH = "date_of_birth";
    private static final String STAFF_COLUMN_BIRTH_PLACE = "birth_place";
    private static final String STAFF_COLUMN_PHONE_NUMBER = "phone_number";
    private static final String STAFF_COLUMN_DEPT = DEPARTMENT_COLUMN_ID;
    private static final String STAFF_COLUMN_STATUS = STATUS_COLUMN_ID;
    private static final String STAFF_COLUMN_ACTIVITY = ACTIVITY_COLUMN_ID;
    private static final String STAFF_COLUMN_POSITION = POSITION_COLUMN_ID;
    /**
     * department table create statement
     */
    private static final String CREATE_TABLE_DEPARTMENT = "CREATE TABLE " + TABLE_DEPARTMENT
            + "(" + DEPARTMENT_COLUMN_ID + " INTEGER PRIMARY KEY, " + DEPARTMENT_COLUMN_NAME + " " +
            "text)";
    /**
     * status table create statement
     */
    private static final String CREATE_TABLE_STATUS = "CREATE TABLE " + TABLE_STATUS
            + "(" + STATUS_COLUMN_ID + " INTEGER PRIMARY KEY, " + STATUS_COLUMN_NAME + " " +
            "text)";
    /**
     * position table create statement
     */
    private static final String CREATE_TABLE_POSITION = "CREATE TABLE " + TABLE_POSITION
            + "(" + POSITION_COLUMN_ID + " INTEGER PRIMARY KEY, " + POSITION_COLUMN_NAME + " " +
            "text)";
    /**
     * activity table create statement
     */
    private static final String CREATE_TABLE_ACTIVITY = "CREATE TABLE " + TABLE_ACTIVITY
            + "(" + ACTIVITY_COLUMN_ID + " INTEGER PRIMARY KEY, " + ACTIVITY_COLUMN_NAME + " " +
            "text)";
    /**
     * staff table create statement
     */
    private static final String CREATE_TABLE_STAFF = "CREATE TABLE " + TABLE_STAFF
            + "(" + STAFF_COLUMN_ID + " INTEGER PRIMARY KEY, " + STAFF_COLUMN_NAME + " " +
            "TEXT, " + STAFF_COLUMN_DATE_OF_BIRTH + " TEXT, " + STAFF_COLUMN_BIRTH_PLACE + " TEXT, " +
            "" + STAFF_COLUMN_PHONE_NUMBER + " TEXT, " + STAFF_COLUMN_DEPT + " INTEGER, " +
            "" + STAFF_COLUMN_STATUS + " INTEGER, " + STAFF_COLUMN_ACTIVITY + " INTEGER, " +
            "" + STAFF_COLUMN_POSITION + " INTEGER, FOREIGN KEY (" + STAFF_COLUMN_DEPT + ") " +
            "REFERENCES " + TABLE_DEPARTMENT + "(" + DEPARTMENT_COLUMN_ID + "), FOREIGN KEY" +
            "(" + STAFF_COLUMN_STATUS + ") REFERENCES " + TABLE_STATUS + "(" + STATUS_COLUMN_ID +
            "), FOREIGN" +
            " KEY(" + STAFF_COLUMN_ACTIVITY + ") REFERENCES " + TABLE_ACTIVITY + "" +
            "(" + ACTIVITY_COLUMN_ID + "), FOREIGN KEY (" + STAFF_COLUMN_POSITION + ") REFERENCES " +
            "" + TABLE_POSITION + "(" + POSITION_COLUMN_ID + "))";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_DEPARTMENT);
        db.execSQL(CREATE_TABLE_STATUS);
        db.execSQL(CREATE_TABLE_POSITION);
        db.execSQL(CREATE_TABLE_ACTIVITY);
        db.execSQL(CREATE_TABLE_STAFF);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPARTMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSITION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY);
        /**Create new tables*/
        onCreate(db);
    }
    public DatabaseHelper open() throws SQLException {
        myDbHelper = new DatabaseHelper(context);
        mDb = myDbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        if (myDbHelper != null) {
            myDbHelper.close();
        }
    }
    /**
     * Creating department
     */
    public long createDepartment(Department dept) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DEPARTMENT_COLUMN_NAME, dept.getDept_name());
        long dept_id = db.insert(TABLE_DEPARTMENT, null, values);
        return dept_id;
    }
    /**
     * Creating status
     */
    public long createStatus(Status status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS_COLUMN_NAME, status.getStatus_name());
        long status_id = db.insert(TABLE_STATUS, null, values);
        return status_id;
    }
    /**
     * Creating Position
     */
    public long createPosition(Position pos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(POSITION_COLUMN_NAME, pos.getPosition_name());
        long pos_id = db.insert(TABLE_POSITION, null, values);
        return pos_id;
    }
    /**
     * Insert Staff
     *
     * @param searchfts
     * @return
     */
    long insert_searchitem(SearchStaff searchfts) {
        String searchValue = searchfts.name + " " +
                searchfts.phone;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, searchfts.name);
        values.put(COLUMN_PHONE, searchfts.phone);
        values.put(FTS_BIRTH_DATE_FIELD, searchfts.birth_date);
        values.put(FTS_BIRTH_DATE_FIELD, searchfts.birth_place);
        values.put(SEARCH_STAFF, searchValue);
        Log.e("user data ", "n :" + searchfts.phone);
        long inserted = database.insert(FTS_VIRTUAL_TABLE, null, values);
        return inserted;
    }
    /**
     * Search Staff
     *
     * @param inputText
     * @return
     * @throws SQLException
     */
    public Cursor searchByInputText(String inputText) throws SQLException {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT docid as _id," +
                COLUMN_NAME + " , " + COLUMN_PHONE + " , " + FTS_BIRTH_PLACE_FIELD + " , " + FTS_BIRTH_DATE_FIELD + " from " + FTS_VIRTUAL_TABLE +
                " where " + SEARCH_STAFF + " MATCH '" + inputText + "';";
        Cursor mCursor = database.rawQuery(query, null);
        if (mCursor != null) {mCursor.moveToFirst();
        }
        return mCursor;
    }
    /**
     * Creating activity
     */
    public long createActivity(com.framgia.hrm.model.Activity act) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ACTIVITY_COLUMN_NAME, act.getActivity_name());
        long act_id = db.insert(TABLE_ACTIVITY, null, values);
        return act_id;
    }
    /**
     * Creating staff
     */
    public long createStaff(Staff staff) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STAFF_COLUMN_NAME, staff.getName());
        values.put(STAFF_COLUMN_DATE_OF_BIRTH, staff.getDate_of_birth());
        values.put(STAFF_COLUMN_BIRTH_PLACE, staff.getBirth_place());
        values.put(STAFF_COLUMN_PHONE_NUMBER, staff.getPhone_number());
        values.put(STAFF_COLUMN_DEPT, staff.getDept_id());
        values.put(STAFF_COLUMN_STATUS, staff.getStatus_id());
        values.put(STAFF_COLUMN_ACTIVITY, staff.getActivity_id());
        values.put(STAFF_COLUMN_POSITION, staff.getPosition_id());
        long staff_id = db.insert(TABLE_STAFF, null, values);
        return staff_id;
    }
    /**
     * Getting a single Department
     */
    public Department getDepartment(long dept_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_DEPARTMENT + " WHERE " + DEPARTMENT_COLUMN_ID + " = " +
                dept_id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) c.moveToFirst();
        Department dept = new Department();
        dept.setDept_id(c.getInt(c.getColumnIndex(DEPARTMENT_COLUMN_ID)));
        dept.setDept_name(c.getString(c.getColumnIndex(DEPARTMENT_COLUMN_NAME)));
        return dept;
    }
    /**
     * get department id
     */
    public int getDepartmentid(String department) {
        SQLiteDatabase db = this.getReadableDatabase();
        int id = 0;
        String query = "SELECT " + DEPARTMENT_COLUMN_ID + " FROM " + TABLE_DEPARTMENT + " where "
                + DEPARTMENT_COLUMN_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{department});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex(DEPARTMENT_COLUMN_ID));
        } else
            Log.e("problem", "department id  is not inculded in department table");
        cursor.close();
        db.close();
        return id;
    }
    /**
     * get last staff id
     */
    public int laststaffid(){

        SQLiteDatabase db = this.getReadableDatabase();
        int id=0;
        String query = "SELECT " + STAFF_COLUMN_ID + " FROM " + TABLE_STAFF ;
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            id = cursor.getInt(cursor.getColumnIndex(STAFF_COLUMN_ID));
        } else
            Log.e("problem", "status id  is not inculded in status table");
        cursor.close();
        db.close();
        return id;
    }
    /**
     * get status id
     */
    public int getstatusid(String status) {
        SQLiteDatabase db = this.getReadableDatabase();
        int id = 0;
        String query = "SELECT " + STATUS_COLUMN_ID + " FROM " + TABLE_STATUS + " where "
                + STATUS_COLUMN_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{status});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex(STATUS_COLUMN_ID));
        } else
            Log.e("problem", "status id  is not inculded in status table");

        cursor.close();
        db.close();
        return id;
    }
    /**
     * get position id
     */
    public int getpostionid(String position) {
        SQLiteDatabase db = this.getReadableDatabase();
        int id = 0;
        String query = "SELECT " + POSITION_COLUMN_ID + " FROM " + TABLE_POSITION + " where "
                + POSITION_COLUMN_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{position});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex(POSITION_COLUMN_ID));
        } else
            Log.e("problem", "position id  is not inculded in position table");
        cursor.close();
        db.close();
        return id;
    }
    /**
     * get Activity id
     */
    public int getActivityid(String activity) {
        SQLiteDatabase db = this.getReadableDatabase();
        int id = 0;
        String query = "SELECT " + ACTIVITY_COLUMN_ID + " FROM " + TABLE_ACTIVITY + " where "
                + ACTIVITY_COLUMN_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{activity});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(cursor.getColumnIndex(ACTIVITY_COLUMN_ID));
        } else
            Log.e("problem", "activity id  is not inculded in activity table");
        cursor.close();
        db.close();
        return id;

    }
    /**
     * Getting staff by id
     */


    public Staff getStaffById(long staff_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_STAFF + " WHERE " + STAFF_COLUMN_ID + " =" +
                " " + staff_id;
        Cursor c = db.rawQuery(selectQuery, null);
        Staff staff = new Staff();
        if (c != null) c.moveToFirst();
        staff.setStaff_id(c.getInt(c.getColumnIndex(STAFF_COLUMN_ID)));
        staff.setName(c.getString(c.getColumnIndex(STAFF_COLUMN_NAME)));
        staff.setDate_of_birth(c.getString(c.getColumnIndex(STAFF_COLUMN_DATE_OF_BIRTH)));
        staff.setBirth_place(c.getString(c.getColumnIndex(STAFF_COLUMN_BIRTH_PLACE)));
        staff.setPhone_number(c.getString(c.getColumnIndex(STAFF_COLUMN_PHONE_NUMBER)));
        staff.setDept_id(c.getInt(c.getColumnIndex(STAFF_COLUMN_DEPT)));
        staff.setStatus_id(c.getInt(c.getColumnIndex(STAFF_COLUMN_STATUS)));
        staff.setActivity_id(c.getInt(c.getColumnIndex(STAFF_COLUMN_ACTIVITY)));
        staff.setPosition_id(c.getInt(c.getColumnIndex(STAFF_COLUMN_POSITION)));
        return staff;
    }
    public ArrayList<Department> getAllDepartments() {
        ArrayList departments = new ArrayList();
        String selectQuery = "SELECT * FROM " + TABLE_DEPARTMENT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        /**looping through all rows and adding to list*/
        if (c.moveToFirst()) {
            do {
                //Department dept = new Department();
                //dept.setDept_id(c.getInt(c.getColumnIndex(DEPARTMENT_COLUMN_ID)));
                //dept.setDept_name(c.getString(c.getColumnIndex(DEPARTMENT_COLUMN_NAME)));
                String name = c.getString(c.getColumnIndex(DEPARTMENT_COLUMN_NAME));
                int id = c.getInt(c.getColumnIndex(DEPARTMENT_COLUMN_ID));
                Department dept = new Department(id, name);
                //  departments.add(c.getString(c.getColumnIndex(DEPARTMENT_COLUMN_NAME)));
                // departments.add(c.getString(c.getColumnIndex(DEPARTMENT_COLUMN_NAME)));
                departments.add(dept);
            } while (c.moveToNext());
        }
        return departments;
    }

    /**
     * Getting staffs by department
     */
    public List<Staff> getStaffByDepartment(long dept_id) {
        ArrayList<Staff> staffs = new ArrayList<Staff>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_STAFF + " WHERE " + STAFF_COLUMN_DEPT + " =" +
                " " + dept_id;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null && c.getCount()>0) c.moveToFirst();
        if (c.moveToFirst()) {
            do {
                Staff staff = new Staff();
                staff.setStaff_id(c.getInt(c.getColumnIndex(STAFF_COLUMN_ID)));
                staff.setName(c.getString(c.getColumnIndex(STAFF_COLUMN_NAME)));
                staff.setDate_of_birth(c.getString(c.getColumnIndex(STAFF_COLUMN_DATE_OF_BIRTH)));
                staff.setBirth_place(c.getString(c.getColumnIndex(STAFF_COLUMN_BIRTH_PLACE)));
                staff.setPhone_number(c.getString(c.getColumnIndex(STAFF_COLUMN_PHONE_NUMBER)));
                staff.setDept_id(c.getInt(c.getColumnIndex(STAFF_COLUMN_DEPT)));
                staff.setStatus_id(c.getInt(c.getColumnIndex(STAFF_COLUMN_STATUS)));
                staff.setActivity_id(c.getInt(c.getColumnIndex(STAFF_COLUMN_ACTIVITY)));
                staff.setPosition_id(c.getInt(c.getColumnIndex(STAFF_COLUMN_POSITION)));
                staffs.add(staff);
            } while (c.moveToNext());
        }

        return staffs;
    }
    /**
     * Checks if department table exists
     */
    public boolean departmentTableExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from " + TABLE_DEPARTMENT + "", null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.close();
                return true;
            } else {
                cur.close();
                return false;
            }
        } else {
            cur.close();
            return false;
        }
    }
    /**
     * Checks if status table exists
     */
    public boolean statusTableExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from " + TABLE_STATUS + "", null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.close();
                return true;
            } else {
                cur.close();
                return false;
            }
        } else {
            cur.close();
            return false;
        }
    }
    /**
     * Checks if position table exists
     */
    public boolean positionTableExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from " + TABLE_POSITION + "", null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.close();
                return true;
            } else {
                cur.close();
                return false;
            }
        } else {
            cur.close();
            return false;
        }
    }
    /**
     * Checks if activity table exists
     */
    public boolean activityTableExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from " + TABLE_ACTIVITY + "", null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.close();
                return true;
            } else {
                cur.close();
                return false;
            }
        } else {
            cur.close();
            return false;
        }
    }

    /**
     * Checks if staff table exists
     */
    public boolean staffTableExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from " + TABLE_STAFF + "", null);
        if (cur != null) {
            if (cur.getCount() > 0) {
                cur.close();
                return true;
            } else {
                cur.close();
                return false;
            }
        } else {
            cur.close();
            return false;
        }
    }
    /**
     * closing database
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}