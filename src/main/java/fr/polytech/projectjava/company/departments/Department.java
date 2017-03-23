package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.staff.Employee;
import java.util.ArrayList;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 *
 * Represent a department
 */
abstract class Department
{
	private final String name;
	private final ArrayList<Employee> employees = new ArrayList<>();

	/**
	 * Construct a department with its name.
	 *
	 * @param name The name of the department.
	 */
	public Department(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Department " + getName() + "\nWorkers: \t" + getEmployees();
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

	/**
	 * Get the employees of the department.
	 *
	 * @return A list of the employees.
	 */
	public ArrayList<Employee> getEmployees()
	{
		return employees;
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
