package fr.polytech.projectjava.company;

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
		this(name, null);
	}

	public StandardDepartment(String name, Manager manager)
	{
		super(name);
		this.manager = manager;
	}

	public Manager getManager()
	{
		return manager;
	}

	public void setManager(Manager manager)
	{
		this.manager = manager;
	}

	@Override
	public String toString()
	{
		return super.toString() + "\nManager: \t" + getManager();
	}
}
