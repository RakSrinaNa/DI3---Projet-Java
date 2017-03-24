package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.staff.Boss;

/**
 * Represent the management department.
 * This one contains every manager of the company and the manager is the boss.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class ManagementDepartment extends Department
{
	private final Boss manager;
	
	/**
	 * Construct the department with the boss.
	 *
	 * @param boss The boss of the company.
	 */
	public ManagementDepartment(Boss boss)
	{
		super("Management department");
		this.manager = boss;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "\nManager: \t" + getManager();
	}
	
	/**
	 * Get the manager of this department.
	 *
	 * @return The manager.
	 */
	public Boss getManager()
	{
		return manager;
	}
}
