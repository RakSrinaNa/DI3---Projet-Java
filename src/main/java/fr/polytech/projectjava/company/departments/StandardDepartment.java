package fr.polytech.projectjava.company.departments;

import com.sun.javaws.exceptions.InvalidArgumentException;
import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.company.staff.Employee;
import fr.polytech.projectjava.company.staff.Manager;

/**
 * Represent a standard department.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class StandardDepartment extends Department
{
	private Manager manager;
	
	/**
	 * Construct a department with its name and the manager.
	 *
	 * @param company The company the department is in.
	 * @param name The department's name.
	 * @param manager The manager of the department.
	 *
	 * @throws InvalidArgumentException If the manager is already working elsewhere.
	 */
	public StandardDepartment(Company company, String name, Manager manager) throws InvalidArgumentException
	{
		super(company, name);
		if(manager.getWorkingDepartment() != null)
			throw new InvalidArgumentException(new String[]{"The manager is already managing elsewhere."});
		manager.setWorkingDepartment(this);
		setManager(manager);
		company.addDepartment(this);
	}
	
	/**
	 * Set the manager of the department.
	 *
	 * @param manager The manager to set, null allows to reset the manager.
	 *
	 * @return true if the manager was set, false if not.
	 */
	public boolean setManager(Manager manager)
	{
		if(manager.getWorkingDepartment() == this)
		{
			if(this.manager != null)
			{
				this.manager.setWorkingDepartment(null);
				company.removeFromManagementTeam(this.manager);
			}
			
			this.manager = manager;
			manager.setWorkingDepartment(this);
			company.addToManagementTeam(manager);
			return true;
		}
		return false;
	}
	
	@Override
	public void addEmployee(Employee employee)
	{
		super.addEmployee(employee);
		if(employee.getWorkingDepartment() != this)
		{
			if(employee.getWorkingDepartment() != null)
				employee.getWorkingDepartment().removeEmployee(employee);
			employee.setWorkingDepartment(this);
		}
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
