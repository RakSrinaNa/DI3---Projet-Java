package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.staff.Manager;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class StandardDepartment extends Department
{
	private Manager manager;

	public StandardDepartment(String name)
	{
		super(name);
	}

	public Manager getManager()
	{
		return manager;
	}

	public boolean setManager(Manager manager)
	{
		if(getManager() != null)
			return false;
		this.manager = manager;
		return true;
	}

	@Override
	public String toString()
	{
		return super.toString() + "\nManager: \t" + getManager();
	}
}
