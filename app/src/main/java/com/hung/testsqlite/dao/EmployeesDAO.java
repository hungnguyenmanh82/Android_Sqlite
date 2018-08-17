package com.hung.testsqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hung.testsqlite.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeesDAO {
    private static String TAG = EmployeesDAO.class.getSimpleName();
    // table name
    public static final String tableName = "Employees";
    // column name
    public static final String colId = "EmployeeID";  //Primary Key
    public static final String colName = "EmployeeName";
    public static final String colAge = "Age";
    public static final String colDeptId = "Dept"; //Foreign Key

    //
    private Context mContext;
    DatabaseHelper mDataHelper;

    public EmployeesDAO(Context context) {
        this.mContext = context;
        this.mDataHelper = new DatabaseHelper(context);
    }

    /**
     * static function called from DatabaseHelper
     */
    public static void createTable(SQLiteDatabase db){
        Log.d(TAG,"createTable()");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + EmployeesDAO.tableName +
                " (" + EmployeesDAO.colId + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EmployeesDAO.colName + " TEXT, " +
                EmployeesDAO.colAge + " Integer, " +
                EmployeesDAO.colDeptId + " INTEGER NOT NULL ,"+
                "FOREIGN KEY (" + EmployeesDAO.colDeptId + ") REFERENCES " + DepartmentDAO.tableName + " (" + DepartmentDAO.colDeptID + "));");
    }
    /**
     * static function called from DatabaseHelper
     */
    public static void dropTable(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXISTS " + EmployeesDAO.tableName);
    }

    /**
     * static function called from DatabaseHelper
     */
    public static void insertEmployees(SQLiteDatabase db) {
        //tên column ko cần quote
        // value là String nhất định phải dùng Single Quote
        //cách này có nhược điểm là ký tự đặc biệt trong Value như Double và Single Quote
        String sql = "INSERT INTO Employees (EmployeeName,Age,Dept) VALUES  " +
                "('hung', 18, 1),"+
                "('hung gay', 19, 2),"+
                "('SonX', 20, 3);";
        db.execSQL(sql);
    }
    /**
     *  ============================= CRUD =============================
     */
    /**
     * Dùng Thư viện Android để insert Row an toàn hơn là dùng SQL raw
     * Vì trong values có thể chứa ký tự đặc biệt như Single/double Quote
     */
    public void insertEmployee(Employee employee){

        ContentValues cv = new ContentValues();
        cv.put(colName, "Hùng béo");
        cv.put(colAge, 12);
        cv.put(colDeptId, 1);
        //
        SQLiteDatabase db = mDataHelper.getReadableDatabase();
        //tham số 2: thường để là NULL (đây là check điều kiện của Android ko phải của SQlite)
        //tham số 2: nếu colName = empty => ở Android colName trong row đó là NULL trong SQL command
        db.insert(tableName, null, cv);
        db.close();
    }

    /**
     * Dùng Thư viện Android để insert Row an toàn hơn là dùng SQL raw
     * Vì trong values có thể chứa ký tự đặc biệt như Single/double Quote
     */
    public void insertEmployee2(Employee employee){
        //============= Cách 2: dùng SQL command =============
        // Lưu ý Double, Single Quote trên SQL là ký tự đặc biệt
        String sql = "INSERT INTO TABLE_NAME (EmployeeName,Age,Dept) VALUES  " +
                     "('"+ employee.getName() +"',"+ employee.getAge() +","+ employee.getDeptId()+");";
        SQLiteDatabase db = mDataHelper.getWritableDatabase();

        db.execSQL(sql);
        db.close();
    }

    public void deleteEmployee(Employee emp) {
        SQLiteDatabase db = mDataHelper.getWritableDatabase();
        db.delete(tableName, colId + "=?", new String[]{String.valueOf(emp.getID())});
        db.close();

    }

    public int getEmployeeCount() {
        SQLiteDatabase db = mDataHelper.getWritableDatabase();
        Cursor cur = db.rawQuery("Select * from " + tableName, null);
        int x = cur.getCount();
        cur.close(); // phải gọi hàm này để giải phòng RAM
        return x;
    }

    public List<Employee> getAllEmployees() {
        SQLiteDatabase db = mDataHelper.getWritableDatabase();

        //Cursor cur= db.rawQuery("Select "+colId+" as _id , "+colName+", "+colAge+" from "+tableName, new String [] {});
        Cursor c = db.rawQuery("SELECT * FROM " + tableName, null);

        ArrayList<Employee> mArrayList = new ArrayList<Employee>();
        Employee e;

        // dữ liệu Cursor trỏ vào đã lưu trên RAM, sau khi dùng xong cần đc giải phóng
        c.moveToFirst();
        while(!c.isAfterLast()) {
            e = new Employee();
            // get Data from Curror (or Row)
            e.setId(c.getInt(c.getColumnIndex(colId)));
            e.setName(c.getString(c.getColumnIndex(colName)));
            e.setAge(c.getInt(c.getColumnIndex(colAge)));
            e.setDeptId(c.getInt(c.getColumnIndex(colDeptId)));
            //
            mArrayList.add(e); //add the item
            c.moveToNext();
        }
        // RAM mà cursor trỏ vào ko liên quan tới db sau Query
        // phải gọi hàm này để giải phòng RAM
        c.close();

        return mArrayList;
    }

    public Employee getEmployee(int id){
        SQLiteDatabase db = mDataHelper.getReadableDatabase();

        String[] params = new String[]{String.valueOf(id)};
        Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + colId + "=?", params);
        c.moveToFirst();
        if(!c.isAfterLast()){
            Employee e;
            e = new Employee();
            // get Data from Curror (or Row)
            e.setId(c.getInt(c.getColumnIndex(colId)));
            e.setName(c.getString(c.getColumnIndex(colName)));
            e.setAge(c.getInt(c.getColumnIndex(colAge)));
            e.setDeptId(c.getInt(c.getColumnIndex(colDeptId)));
            return e;
        }
        return null;
    }

    public int updateEmp(Employee emp) {
        SQLiteDatabase db = mDataHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(colName, emp.getName());
        cv.put(colAge, emp.getAge());
        cv.put(colDeptId, emp.getDeptId());
        return db.update(tableName, cv, colId + "=?", new String[]{String.valueOf(emp.getID())});

    }
}
