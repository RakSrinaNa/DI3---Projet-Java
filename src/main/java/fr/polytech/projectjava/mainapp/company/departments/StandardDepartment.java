package fr.polytech.projectjava.mainapp.company.departments;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
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
	 * @param name The department's name.
	 * @param manager The manager of the department.
	 */
	public StandardDepartment(Company company, String name, Manager manager)
	{
		super(company, name, manager);
		setLeader(manager);
		company.addDepartment(this);
	}

	@Override
	public void addEmployee(Employee employee)
	{
		if(employee != null && employee.getWorkingDepartment() != this) // If the employee wasn't in this department
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
		if(employee instanceof Manager) //Verify if it is the manager leaving
			if(employee == getLeader())
			{
				company.removeFromManagementTeam((Manager) employee);
				setLeader(null);
			}
		employee.setWorkingDepartment(null);
		super.removeEmployee(employee);
	}

	/**
	 * Set the manager of the department.
	 *
	 * @param manager The manager to set, null allows to reset the manager.
	 */
	public void setLeader(Manager manager)
	{
		if(getLeader() != null && getLeader() != manager) //Handle the previous manager
			company.removeFromManagementTeam(getLeader());

		if(manager != null)
		{
			if(manager.getWorkingDepartment() != null && manager.getWorkingDepartment() != this)
				manager.getWorkingDepartment().removeEmployee(manager);
			manager.setWorkingDepartment(this);
			addEmployee(manager);
			company.addToManagementTeam(manager);
		}

		super.setLeader(manager);
	}

	/**
	 * Tell if this department is in a valid state.
	 *
	 * @return True if the state is valid, false else.
	 */
	public boolean isValidState()
	{
		return !getName().equals("") && getLeader() != null;
	}
}
