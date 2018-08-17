package com.hung.testsqlite.model;

import android.content.Context;

import com.hung.testsqlite.dao.DatabaseHelper;

public class EmployeeView {
	/**
	 * primary key
	 */
	int id;
	String name;
	int age;
	/**
     * foreign key to Department table
	 */
	String deptName;

	public EmployeeView(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}
