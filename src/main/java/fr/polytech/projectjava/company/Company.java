package fr.polytech.projectjava.company;

import fr.polytech.projectjava.company.departments.ManagementDepartment;
import fr.polytech.projectjava.company.departments.StandardDepartment;
import fr.polytech.projectjava.company.staff.Boss;
import fr.polytech.projectjava.company.staff.Employee;
import java.util.ArrayList;
import java.util.Optional;

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
	private final ArrayList<Employee> employees = new ArrayList<>();
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
		this.managementDepartment = new ManagementDepartment(boss);
	}
	
	/**
	 * Get an employee by its ID.
	 *
	 * @param ID The ID of the employee to find.
	 * @return An optional of the requested employee.
	 */
	public Optional<Employee> getEmployee(int ID)
	{
		for(Employee employee : employees)
			if(employee.getID() == ID)
				return Optional.of(employee);
		return Optional.empty();
	}
	
	/**
	 * Add an employee to the company.
	 *
	 * @param employee The employee to add.
	 */
	public void addEmployee(Employee employee)
	{
		if(!employees.contains(employee))
		{
			employees.add(employee);
			if(!departments.contains(employee.getWorkingDepartment()))
				departments.add(employee.getWorkingDepartment());
		}
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
		return employees.size();
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
