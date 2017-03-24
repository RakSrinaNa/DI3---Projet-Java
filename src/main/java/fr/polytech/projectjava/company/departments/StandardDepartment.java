package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.staff.Manager;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 * <p>
 * Represent a standard department.
 */
public class StandardDepartment extends Department
{
	private Manager manager;
	
	/**
	 * Construct a department with its name.
	 *
	 * @param name The department's name.
	 */
	public StandardDepartment(String name)
	{
		super(name);
	}
	
	/**
	 * Set the manager of the department.
	 *
	 * @param manager The manager to set, null allows to reset the manager.
	 *
	 * @return true if the manager was set, false if another manager was already present.
	 */
	public boolean setManager(Manager manager)
	{
		if(manager == null || getManager() != null)
			return false;
		this.manager = manager;
		return true;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "\nManager: \t" + getManager();
	}
	
	/**
	 * Get the manager of the department.
	 *
	 * @return The manager.
	 */
	public Manager getManager()
	{
		return manager;
	}
}
