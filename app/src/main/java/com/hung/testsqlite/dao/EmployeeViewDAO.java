package com.hung.testsqlite.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hung.testsqlite.model.EmployeeView;

import java.util.ArrayList;
import java.util.List;

public class EmployeeViewDAO {
    public static final String viewName = "ViewEmps";

    //
    private Context mContext;
    DatabaseHelper mDataHelper;

    public EmployeeViewDAO(Context context) {
        this.mContext = context;
        // new connect to SQLite
        this.mDataHelper = new DatabaseHelper(context);
    }
    /**
     * static function called from DatabaseHelper
     */
    public static void createView(SQLiteDatabase db){

        db.execSQL("CREATE VIEW " + viewName +
                " AS SELECT " + EmployeesDAO.tableName + "." + EmployeesDAO.colId + " AS _id," +
                " " + EmployeesDAO.tableName + "." + EmployeesDAO.colName + "," +
                " " + EmployeesDAO.tableName + "." + EmployeesDAO.colAge + "," +
                " " + DepartmentDAO.tableName + "." + DepartmentDAO.colDeptName + "" +
                " FROM " + EmployeesDAO.tableName + " JOIN " + DepartmentDAO.tableName +
                " ON " + EmployeesDAO.tableName + "." + EmployeesDAO.colDeptId + " =" + DepartmentDAO.tableName + "." + DepartmentDAO.colDeptID
        );
    }
    /**
     * static function called from DatabaseHelper
     */
    public static void dropView(SQLiteDatabase db){
        db.execSQL("DROP VIEW IF EXISTS " + viewName);
    }

    public List<EmployeeView> getAllEmployees() {
        SQLiteDatabase db = mDataHelper.getWritableDatabase();

        //Cursor cur= db.rawQuery("Select "+colId+" as _id , "+colName+", "+colAge+" from "+tableName, new String [] {});
        Cursor c = db.rawQuery("SELECT * FROM " + viewName, null);

        ArrayList<EmployeeView> mArrayList = new ArrayList<EmployeeView>();
        EmployeeView e;

        // dữ liệu Cursor trỏ vào đã lưu trên RAM, sau khi dùng xong cần đc giải phóng
        c.moveToFirst();
        while(!c.isAfterLast()) {
            e = new EmployeeView();
            // get Data from Curror (or Row)
            e.setName(c.getString(c.getColumnIndex(EmployeesDAO.colName)));
            e.setAge(c.getInt(c.getColumnIndex(EmployeesDAO.colAge)));
            e.setDeptName(c.getString(c.getColumnIndex(DepartmentDAO.colDeptName)));
            //
            mArrayList.add(e); //add the item
            c.moveToNext();
        }
        // RAM mà cursor trỏ vào ko liên quan tới db sau Query
        // phải gọi hàm này để giải phòng RAM
        c.close();

        return mArrayList;
    }

    public EmployeeView getEmployee(int id){
        SQLiteDatabase db = mDataHelper.getReadableDatabase();

        String[] params = new String[]{String.valueOf(id)};
        /**
         * Search on View là tuần tự => performance is low
         */
        Cursor c = db.rawQuery("SELECT * FROM " + viewName + " WHERE " + EmployeesDAO.colId + "=?", params);
        c.moveToFirst();
        if(!c.isAfterLast()){
            EmployeeView e = new EmployeeView();
            // get Data from Curror (or Row)
            e.setName(c.getString(c.getColumnIndex(EmployeesDAO.colName)));
            e.setAge(c.getInt(c.getColumnIndex(EmployeesDAO.colAge)));
            e.setDeptName(c.getString(c.getColumnIndex(DepartmentDAO.colDeptName)));
            return e;
        }
        return null;
    }
}
