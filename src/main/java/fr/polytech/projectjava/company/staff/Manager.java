package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.company.departments.StandardDepartment;

/**
 * Represent a manager in the company.
 * A manager can only manage less or equal than one department.
 *
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Manager extends Employee
{
	private boolean managing;
	
	/**
	 * Construct a manager with his/her name and his affected department.
	 *
	 * @param lastName          His/her last name.
	 * @param firstName         His/her first name.
	 * @param workingDepartment The working department of the manager.
	 *                          If the department already have a manager, the manager won't be the affected manager and will be considered as an employee.
	 */
	public Manager(String lastName, String firstName, StandardDepartment workingDepartment)
	{
		super(lastName, firstName, workingDepartment);
		managing = workingDepartment.setManager(this);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + (isManaging() ? "\nManager" : "");
	}
	
	/**
	 * Return the managing status of the manager.
	 *
	 * @return True he/she is managing a department, false otherwise.
	 */
	public boolean isManaging()
	{
		return managing;
	}
}
