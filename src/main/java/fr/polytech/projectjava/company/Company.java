package fr.polytech.projectjava.company;

import fr.polytech.projectjava.company.departments.Department;
import fr.polytech.projectjava.company.departments.ManagementDepartment;
import fr.polytech.projectjava.company.departments.StandardDepartment;
import fr.polytech.projectjava.company.staff.Boss;
import fr.polytech.projectjava.company.staff.Employee;
import fr.polytech.projectjava.company.staff.Manager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represent a company.
 *
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Company
{
	private final String name;
	private final Boss boss;
	private final ManagementDepartment managementDepartment;
	private final ArrayList<StandardDepartment> departments = new ArrayList<>();
	
	/**
	 * Construct a company with its name and boss.
	 *
	 * @param name The company's name.
	 * @param boss The company's boss.
	 */
	public Company(String name, Boss boss)
	{
		this.name = name;
		this.boss = boss;
		this.managementDepartment = new ManagementDepartment(this, boss);
	}
	
	/**
	 * Get an employee by its ID.
	 *
	 * @param ID The ID of the employee to find.
	 * @return An optional of the requested employee.
	 */
	public Optional<Employee> getEmployee(int ID)
	{
		for(Employee employee : getEmployees())
			if(employee.getID() == ID)
				return Optional.of(employee);
		return Optional.empty();
	}
	
	/**
	 * Get a department by its ID.
	 *
	 * @param ID The ID of the department to look for.
	 *
	 * @return An optional of the requested department.
	 */
	public Optional<StandardDepartment> getDepartment(int ID)
	{
		for(StandardDepartment department : departments)
			if(department.getID() == ID)
				return Optional.of(department);
		return Optional.empty();
	}
	
	@Override
	public String toString()
	{
		return "Company: \t" + getName() + "\nManagement department: \t[" + getManagementDepartment() + "]\nDepartments: \t" + departments + "\nEmployees: \t" + getEmployees();
	}
	
	/**
	 * Remove a manager from the management department.
	 *
	 * @param manager The manager to remove.
	 */
	public void removeFromManagementTeam(Manager manager)
	{
		managementDepartment.removeManager(manager);
		manager.setManaging(false);
	}
	
	/**
	 * Add a manager to the management department.
	 *
	 * @param manager The manager to add.
	 */
	public void addToManagementTeam(Manager manager)
	{
		managementDepartment.addManager(manager);
		manager.setManaging(true);
	}
	
	/**
	 * Add a department to the company.
	 *
	 * @param department The department to add.
	 */
	public void addDepartment(StandardDepartment department)
	{
		if(!departments.contains(department))
			departments.add(department);
	}
	
	/**
	 * Get the boss of the company.
	 *
	 * @return The boss.
	 */
	public Boss getBoss()
	{
		return boss;
	}
	
	/**
	 * Get the number of departments in the company.
	 *
	 * @return The department count.
	 */
	public int getDepartmentCount()
	{
		return departments.size();
	}
	
	/**
	 * Get the number of employees in the company.
	 *
	 * @return The employee count.
	 */
	public int getEmployeeCount()
	{
		return getEmployees().size();
	}
	
	/**
	 * Get all the employees of the company.
	 *
	 * @return A list of the employees.
	 */
	private List<Employee> getEmployees()
	{
		return departments.parallelStream().map(Department::getEmployees).flatMap(List::stream).collect(Collectors.toList());
	}
	
	/**
	 * Get the management department of the company.
	 *
	 * @return The management department.
	 */
	public ManagementDepartment getManagementDepartment()
	{
		return managementDepartment;
	}
	
	/**
	 * Get the name of the company.
	 *
	 * @return The company's name.
	 */
	public String getName()
	{
		return name;
	}
}
