package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.company.departments.StandardDepartment;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Manager extends Employee
{
	private boolean managing;

	public Manager(int ID, String lastName, String firstName, StandardDepartment workingDepartment)
	{
		super(ID, lastName, firstName, workingDepartment);
		managing = workingDepartment.setManager(this);
	}

	public boolean isManaging()
	{
		return managing;
	}

	@Override
	public String toString()
	{
		return super.toString() + (isManaging() ? "\nManager" : "");
	}
}
