package fr.polytech.projectjava.company;

import fr.polytech.projectjava.company.departments.Department;
import fr.polytech.projectjava.company.departments.ManagementDepartment;
import fr.polytech.projectjava.company.staff.Boss;
import fr.polytech.projectjava.company.staff.Employee;
import java.util.ArrayList;
import java.util.Optional;

/**
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
	private final ArrayList<Department> departments = new ArrayList<>();
	
	public Company(String name, Boss boss)
	{
		this.name = name;
		this.boss = boss;
		this.managementDepartment = new ManagementDepartment(boss);
	}
	
	public Optional<Employee> getEmployee(int ID)
	{
		for(Employee employee : employees)
			if(employee.getID() == ID)
				return Optional.of(employee);
		return Optional.empty();
	}
	
	public void addEmployee(Employee employee)
	{
		employees.add(employee);
		departments.add(employee.getWorkingDepartment());
	}
	
	public Boss getBoss()
	{
		return boss;
	}
	
	public ManagementDepartment getManagementDepartment()
	{
		return managementDepartment;
	}
	
	public String getName()
	{
		return name;
	}
}
