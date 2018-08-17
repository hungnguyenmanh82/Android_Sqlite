package com.hung.testsqlite;

import android.content.Context;
import android.util.Log;

import com.hung.testsqlite.dao.EmployeesDAO;
import com.hung.testsqlite.model.Employee;

import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void testEmployeeTable() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();



        //
        assertEquals("com.hung.testsqlite", appContext.getPackageName());
    }
}
