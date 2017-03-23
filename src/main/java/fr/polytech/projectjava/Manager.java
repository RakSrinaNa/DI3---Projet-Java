package fr.polytech.projectjava;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Manager extends Employee
{
	private boolean managing;

	public Manager(int ID, String lastName, String firstName, StandardDepartment workingDepartment, boolean isManaging)
	{
		super(ID, lastName, firstName, workingDepartment);
		managing = isManaging;
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
