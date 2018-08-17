package com.hung.testsqlite.model;

public class Employee {
	/**
	 * primary key ,auto increment
	 */
	int _id;
	String _name;
	int _age;
	/**
	 * foreign key to Department table
	 */
	int _deptId;

	public Employee(){}

	public Employee(String Name,int Age,int Dept)
	{
		
		this._name=Name;
		this._age=Age;
		this._deptId =Dept;
	}
	
	public Employee(String Name,int Age)
	{
		this._name=Name;
		this._age=Age;
	}
	
	public int getID()
	{
		return this._id;
	}
	public void setId(int ID)
	{
		this._id=ID;
	}
	
	public String getName()
	{
		return this._name;
	}
	
	public int getAge()
	{
		return this._age;
	}
	
	public void setName(String Name)
	{
		this._name=Name;
	}
	public void setAge(int Age)
	{
		this._age=Age;
	}
	

	public void setDeptId(int Dept)
	{
		this._deptId =Dept;
	}
	

	public int getDeptId()
	{
		return this._deptId;
	}

    @Override
    public String toString() {
        return "{"+ this._id + "," + this._name+ "," + this._age + "," + this._deptId + "},";
    }
}
