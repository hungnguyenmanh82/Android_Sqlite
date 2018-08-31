package com.hung.testsqlite;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hung.testsqlite.dao.DepartmentDAO;
import com.hung.testsqlite.dao.EmployeesDAO;
import com.hung.testsqlite.model.Department;
import com.hung.testsqlite.model.Employee;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Button mBtnAddEmployee;
    private Button mBtnListEmployee;
    private Button mBtnUpdateEmployee;
    private Button mBtnDeleteEmployee;
    private Button mBtnListDepartment;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Xem o phan Android TEST
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //Add employee
            case 1:

                break;
        }
        super.onOptionsItemSelected(item);
        return false;
    }


    private void initView() {

        mBtnAddEmployee = (Button) findViewById(R.id.btn_add_employee);
        mBtnListEmployee = (Button) findViewById(R.id.btn_list_employee);
        mBtnUpdateEmployee = (Button) findViewById(R.id.btn_update_employee);
        mBtnDeleteEmployee = (Button) findViewById(R.id.btn_delete_employee);
        mBtnListDepartment = (Button) findViewById(R.id.btn_list_department);
        //
        mBtnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeesDAO dao = new EmployeesDAO(MainActivity.this);
                //
                Employee e = new Employee();
                e.setName("HiHi");
                e.setAge(12);
                e.setDeptId(1);
                // ko cần primary id => no đc generate ở SQLite server
                //
                dao.insertEmployee(e);
                //
                String TAG = "test";
                Log.d(TAG,"======================== insert to Employee table");
                Log.d(TAG, e.toString());
            }
        });

        mBtnListEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeesDAO dao = new EmployeesDAO(MainActivity.this);
                List<Employee> employees =  dao.getAllEmployees();

                // print
                String TAG = "test";
                Log.d(TAG,"======================== get all Employees from Employee Table");
                for (Employee e: employees ) {
                    Log.d(TAG, e.toString());
                }
            }
        });

        mBtnUpdateEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeesDAO dao = new EmployeesDAO(MainActivity.this);
                //
                Employee e = new Employee();
                e.setName("HiHi");
                e.setAge(12);
                e.setDeptId(1);
                e.setId(1); //primary key
                //
                dao.updateEmp(e);
                //
                String TAG = "test";
                Log.d(TAG,"======================== update to Employee table");
                Log.d(TAG, e.toString());
            }
        });

        mBtnDeleteEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeesDAO dao = new EmployeesDAO(MainActivity.this);
                //
                Employee e = new Employee();
                e.setId(1); //primary key
                //
                dao.deleteEmployee(e);
                //
                String TAG = "test";
                Log.d(TAG,"======================== delete an Employee from Employee table");
                Log.d(TAG, e.toString());
            }
        });

        mBtnListDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DepartmentDAO dao = new DepartmentDAO(MainActivity.this);
                //
                List<Department> departments =  dao.getAllDepartments();

                // print
                String TAG = "test";
                Log.d(TAG,"******************* get all Departments from Department Table");
                for (Department d: departments ) {
                    Log.d(TAG, d.toString());
                }
            }
        });
    }
}