package com.hung.testsqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/*
* each instance SQLiteOpenHelper is 1 socket connection to Database
* + if two connections of 2 threads write to a Database at the same time => 1 connection return false
* + 1 connect to write, others to reads at the same time is OK
* + xem phần Android Test
* */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static String TAG = DatabaseHelper.class.getSimpleName();

    /* Các biến dùng trung giữa các instance của Class này*/
    private static final String dbName = "demoDB";
    /**
     * Neu change version thi onUpgrade() sẽ đc goi
     */
    private static final int dbVersion = 1;


    /*Tại hàm này ta tạo Data base*/
    public DatabaseHelper(Context context) {
        // dbName moi dc tao ra se luu lai dbVersion de check cho nhung lan sau
        super(context, dbName, null, dbVersion);
    }

    /**
     * Ham nay chi dc goi duy nhat 1 lan khi dbName đc tao ra
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate()");
		/*
        * hàm execSQL là hàm thực hiện câu lệnh SQL dưới dạng commandline
		* */
        DepartmentDAO.createTable(db);
        EmployeesDAO.createTable(db);
        EmployeeViewDAO.createView(db);


        db.execSQL("CREATE TRIGGER fk_empdept_deptid " +
                " BEFORE INSERT " +
                " ON " + EmployeesDAO.tableName +

                " FOR EACH ROW BEGIN" +
                " SELECT CASE WHEN ((SELECT " + DepartmentDAO.colDeptID + " FROM "
                                     + DepartmentDAO.tableName + " WHERE " + DepartmentDAO.colDeptID + "=new." + EmployeesDAO.colDeptId + " ) IS NULL)" +
                " THEN RAISE (ABORT,'Foreign Key Violation') END;" +
                "  END;");

        DepartmentDAO.insertDepts(db);
        EmployeesDAO.insertEmployees(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // compare oldVersion and newVersion then make the decision to update or not
        if(newVersion > oldVersion){
            //Xóa toàn bộ bảng cũ trước khi tạo lại bảng mới
            DepartmentDAO.dropTable(db);
            EmployeesDAO.dropTable(db);
            EmployeeViewDAO.dropView(db);

            db.execSQL("DROP TRIGGER IF EXISTS dept_id_trigger");
            db.execSQL("DROP TRIGGER IF EXISTS dept_id_trigger22");
            db.execSQL("DROP TRIGGER IF EXISTS fk_empdept_deptid");

            //tạo lại bảng mới
            onCreate(db);
        }

        

    }





}
