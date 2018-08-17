package com.hung.testsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hung.testsqlite.model.Department;
import com.hung.testsqlite.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO {
    private static String TAG = DepartmentDAO.class.getSimpleName();
    // table name
    public static final String tableName = "Dept";
    // column name
    public static final String colDeptID = "DeptID";
    public static final String colDeptName = "DeptName";

    private Context mContext;
    DatabaseHelper mDataHelper;

    public DepartmentDAO(Context context) {
        this.mContext = context;
        this.mDataHelper = new DatabaseHelper(context);
    }

    public static void createTable(SQLiteDatabase db){
        Log.d(TAG,"createTable()");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DepartmentDAO.tableName +
                " (" + DepartmentDAO.colDeptID + " INTEGER PRIMARY KEY , " +
                DepartmentDAO.colDeptName + " TEXT)");
    }

    public static void dropTable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + EmployeesDAO.tableName);
    }

    /**
     * static function called from DatabaseHelper
     */
    public static void insertDepts(SQLiteDatabase db) {
        //================================== cach 1 ======================
        ContentValues cv = new ContentValues();
        cv.put(colDeptID, 1);
        cv.put(colDeptName, "Sales");
        //tham số 2: thường để là NULL (đây là check điều kiện của Android ko phải của SQlite)
        //tham số 2: nếu colName = empty => ở Android colName trong row đó là NULL trong SQL command
        db.insert(tableName, colDeptID, cv);

        //
        cv.put(colDeptID, 2);
        cv.put(colDeptName, "IT");
        db.insert(tableName, colDeptID, cv);

        //
        cv.put(colDeptID, 3);
        cv.put(colDeptName, "HR");
        db.insert(tableName, colDeptID, cv);

    }

    public List<Department> getAllDepartments() {
        SQLiteDatabase db = mDataHelper.getWritableDatabase();

        //Cursor cur= db.rawQuery("Select "+colId+" as _id , "+colName+", "+colAge+" from "+tableName, new String [] {});
        Cursor c = db.rawQuery("SELECT * FROM " + tableName, null);

        ArrayList<Department> mArrayList = new ArrayList<Department>();
        Department d;

        // dữ liệu Cursor trỏ vào đã lưu trên RAM, sau khi dùng xong cần đc giải phóng
        c.moveToFirst();
        while(!c.isAfterLast()) {
            d = new Department();
            // get Data from Curror (or Row)
            d.setId(c.getInt(c.getColumnIndex(colDeptID)));
            d.setName(c.getString(c.getColumnIndex(colDeptName)));

            //
            mArrayList.add(d); //add the item
            c.moveToNext();
        }
        // RAM mà cursor trỏ vào ko liên quan tới db sau Query
        // phải gọi hàm này để giải phòng RAM
        c.close();

        return mArrayList;
    }
}
