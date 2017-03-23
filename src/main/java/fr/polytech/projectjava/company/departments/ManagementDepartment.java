package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.staff.Boss;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class ManagementDepartment extends Department
{
	private final Boss manager;

	public ManagementDepartment(String name, Boss manager)
	{
		super(name);
		this.manager = manager;
	}

	public Boss getManager()
	{
		return manager;
	}

	@Override
	public String toString()
	{
		return super.toString() + "\nManager: \t" + getManager();
	}
}
