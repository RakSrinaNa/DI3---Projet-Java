package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.company.departments.StandardDepartment;

/**
 * Represent an employee in the company.
 * Each one have a unique ID that is also their card ID.
 *
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Employee extends Person
{
	private final int ID;
	protected static int NEXT_ID = 0;
	private StandardDepartment workingDepartment;
	
	/**
	 * Create an employee with his/her name.
	 *
	 * @param lastName          His/her last name.
	 * @param firstName         His/her first name.
	 */
	public Employee(String lastName, String firstName)
	{
		super(lastName, firstName);
		this.ID = NEXT_ID++;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "\nID: \t" + getID() + "\nDpt: \t" + getWorkingDepartment();
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Employee && ID == ((Employee) obj).getID();
	}
	
	/**
	 * Get the ID of the employee.
	 *
	 * @return Its ID.
	 */
	public int getID()
	{
		return ID;
	}
	
	/**
	 * Get the department the employee is working in.
	 *
	 * @return The worker's department.
	 */
	public StandardDepartment getWorkingDepartment()
	{
		return workingDepartment;
	}
	
	/**
	 * Set the working department for this employee.
	 *
	 * @param workingDepartment The department to affect him to.
	 */
	public void setWorkingDepartment(StandardDepartment workingDepartment)
	{
		this.workingDepartment = workingDepartment;
	}
}
