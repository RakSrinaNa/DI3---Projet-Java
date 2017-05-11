package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.company.staff.Boss;
import fr.polytech.projectjava.company.staff.Employee;
import fr.polytech.projectjava.company.staff.Manager;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class DepartmentTest
{
	private static final String DEPARTMENT_NAME = "Department";
	
	private Department<Manager, Employee> department;
	private Company company;
	private Manager manager;
	
	@Before
	public void setUp()
	{
		company = new Company("A", new Boss("A", "B"));
		manager = new Manager("A", "B");
		//noinspection serial
		department = new Department<Manager, Employee>(company, DEPARTMENT_NAME, manager)
		{};
	}
	
	@Test
	public void addGetEmployee() throws Exception
	{
		department.getEmployees().clear();
		
		ArrayList<Employee> employees = new ArrayList<>();
		for(int i = 0; i < 100; i++)
			employees.add(new Employee("A", "B"));
		
		employees.forEach(department::addEmployee);
		employees.forEach(employee -> assertTrue(department.hasEmployee(employee)));
	}
	
	@Test
	public void getName() throws Exception
	{
		assertEquals(DEPARTMENT_NAME, department.getName());
	}
	
	@Test
	public void getLeader() throws Exception
	{
		assertEquals(manager, department.getLeader());
	}
	
	@Test
	public void getID() throws Exception
	{
		int ID = Department.NEXT_ID;
		Department<Employee, Employee> department1 = new Department(company, DEPARTMENT_NAME, null){};
		Department<Employee, Employee> department2 = new Department(company, DEPARTMENT_NAME, null){};
		Department<Employee, Employee> department3 = new Department(company, DEPARTMENT_NAME, null){};
		assertEquals(ID, department1.getID());
		assertEquals(ID + 1, department2.getID());
		assertEquals(ID + 2, department3.getID());
	}
}