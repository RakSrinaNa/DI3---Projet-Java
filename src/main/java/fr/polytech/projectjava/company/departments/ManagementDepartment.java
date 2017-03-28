package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.company.staff.Boss;
import fr.polytech.projectjava.company.staff.Manager;

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
	 * @param company The company the department is in.
	 * @param boss The boss of the company.
	 */
	public ManagementDepartment(Company company, Boss boss)
	{
		super(company, "Management department");
		this.manager = boss;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "\nManager: \t" + getManager();
	}
	
	/**
	 * Remove a manager from the management department.
	 *
	 * @param manager The manager to remove.
	 */
	public void removeManager(Manager manager)
	{
		getEmployees().remove(manager);
	}
	
	/**
	 * Add a manager to the management department.
	 *
	 * @param manager The manager to add.
	 */
	public void addManager(Manager manager)
	{
		if(!getEmployees().contains(manager))
			addEmployee(manager);
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
