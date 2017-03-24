package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.staff.Employee;
import java.util.ArrayList;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 * <p>
 * Represent a department
 */
public abstract class Department
{
	private final int ID;
	private final String name;
	private final ArrayList<Employee> employees = new ArrayList<>();
	private static int NEXT_ID = 0;
	
	/**
	 * Construct a department with its name.
	 *
	 * @param name The name of the department.
	 */
	public Department(String name)
	{
		this.ID = NEXT_ID++;
		this.name = name;
	}
	
	/**
	 * Add an employee to the department.
	 *
	 * @param employee The employee to add.
	 */
	public void addEmployee(Employee employee)
	{
		employees.add(employee);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Department && ID == ((Department) obj).getID();
	}
	
	@Override
	public String toString()
	{
		return "Department " + getName() + "\nWorkers: \t" + getEmployees();
	}
	
	/**
	 * Get the employees of the department.
	 *
	 * @return A list of the employees.
	 */
	public ArrayList<Employee> getEmployees()
	{
		return employees;
	}
	
	public int getID()
	{
		return ID;
	}
	
	/**
	 * Get the name of the department.
	 *
	 * @return Its name.
	 */
	public String getName()
	{
		return name;
	}
}
