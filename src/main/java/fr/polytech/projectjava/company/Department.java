package fr.polytech.projectjava.company;

import fr.polytech.projectjava.company.staff.Employee;
import java.util.ArrayList;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public abstract class Department
{
	private final String name;
	private final ArrayList<Employee> employees = new ArrayList<>();

	public Department(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Department " + getName() + "\nWorkers: \t" + getEmployees();
	}

	public void addEmployee(Employee employee)
	{
		employees.add(employee);
	}

	public ArrayList<Employee> getEmployees()
	{
		return employees;
	}

	public String getName()
	{
		return name;
	}
}
