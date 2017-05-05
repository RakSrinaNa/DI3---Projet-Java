package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.company.staff.Employee;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represent a department
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public abstract class Department implements Serializable
{
	private static final long serialVersionUID = 3644405617796041285L;
	protected final Company company;
	private final int ID;
	private final String name;
	private final ArrayList<Employee> employees = new ArrayList<>();
	protected static int NEXT_ID = 0;
	
	/**
	 * Construct a department of a company with its name.
	 *
	 * @param company The company the department is in.
	 * @param name The name of the department.
	 */
	public Department(Company company, String name)
	{
		this.ID = NEXT_ID++;
		this.company = company;
		this.name = name;
	}
	
	/**
	 * Add an employee to the department.
	 *
	 * @param employee The employee to add.
	 */
	public void addEmployee(Employee employee)
	{
		if(!employees.contains(employee))
		{
			employees.add(employee);
			company.addEmployee(employee);
		}
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Department && ID == ((Department) obj).getID();
	}
	
	@Override
	public String toString()
	{
		return "[Department " + getName() + "\nWorkers: \t" + getEmployees() + "]";
	}
	
	/**
	 * Remove an employee from the department.
	 *
	 * @param employee The employee to remove.
	 */
	public void removeEmployee(Employee employee)
	{
		this.employees.remove(employee);
		employee.setWorkingDepartment(null);
		company.removeEmployee(employee);
	}
	
	/**
	 * Used to know if the department have this employee.
	 *
	 * @param employee The employee to check.
	 *                 @return true if in the department, false else.
	 */
	public boolean hasEmployee(Employee employee)
	{
		return getEmployees().contains(employee);
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
	 * Get the unique ID of the department.
	 *
	 * @return Its ID.
	 */
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
