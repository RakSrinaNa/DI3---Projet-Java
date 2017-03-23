package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.company.departments.StandardDepartment;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Employee extends Person
{
	private final int ID;
	private StandardDepartment workingDepartment;

	public Employee(int ID, String lastName, String firstName, StandardDepartment workingDepartment)
	{
		super(lastName, firstName);
		this.ID = ID;
		this.workingDepartment = workingDepartment;
	}

	public int getID()
	{
		return ID;
	}

	public StandardDepartment getWorkingDepartment()
	{
		return workingDepartment;
	}

	@Override
	public String toString()
	{
		return super.toString() + "\nID: \t" + getID() + "\nDpt: \t" + getWorkingDepartment();
	}
}
