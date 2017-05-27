package fr.polytech.projectjava.mainapp.company.departments;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
import fr.polytech.projectjava.utils.Log;
import java.io.Serializable;

/**
 * Represent a standard department.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class StandardDepartment extends Department<Manager, Employee> implements Serializable
{
	private static final long serialVersionUID = 5920779023965916148L;
	
	/**
	 * Construct a department with its name and the manager.
	 *
	 * @param company The company the department is in.
	 * @param name    The department's name.
	 * @param manager The manager of the department.
	 *
	 * @throws IllegalArgumentException If the manager is already working elsewhere.
	 */
	public StandardDepartment(Company company, String name, Manager manager) throws IllegalArgumentException
	{
		super(company, name, manager);
		if(manager.getWorkingDepartment() != null)
			throw new IllegalArgumentException("The manager is already managing elsewhere.");
		addEmployee(manager);
		setLeader(manager);
		company.addDepartment(this);
	}
	
	/**
	 * Set the manager of the department.
	 *
	 * @param manager The manager to set, null allows to reset the manager.
	 */
	public void setLeader(Manager manager)
	{
		if(getLeader() != null)
			company.removeFromManagementTeam(getLeader());
			
		if(manager != null)
		{
			if(manager.getWorkingDepartment() != null)
				manager.getWorkingDepartment().removeEmployee(manager);
			manager.setWorkingDepartment(this);
			company.addToManagementTeam(manager);
		}
		
		super.setLeader(manager);
	}
	
	@Override
	public void addEmployee(Employee employee)
	{
		if(employee.getWorkingDepartment() != this)
		{
			if(employee.getWorkingDepartment() != null)
				employee.getWorkingDepartment().removeEmployee(employee);
			employee.setWorkingDepartment(this);
		}
		super.addEmployee(employee);
	}
	
	@Override
	public void removeEmployee(Employee employee)
	{
		if(employee instanceof Manager)
			if(employee == getLeader())
			{
				Log.warning(this + "\tManager left the department");
				setLeader(null);
			}
		employee.setWorkingDepartment(null);
		super.removeEmployee(employee);
	}
}
