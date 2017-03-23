package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.staff.Employee;
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

	private Department department;

	@Before
	public void setUp()
	{
		department = new Department(DEPARTMENT_NAME){};
	}

	@Test
	public void addGetEmployee() throws Exception
	{
		department.getEmployees().clear();

		Employee employee1 = new Employee(0, "A", "B", new StandardDepartment("Dpt"));
		Employee employee2 = new Employee(1, "A", "B", new StandardDepartment("Dpt"));
		Employee employee3 = new Employee(2, "A", "B", new StandardDepartment("Dpt"));

		ArrayList<Employee> employees = new ArrayList<>();
		employees.add(employee1);
		employees.add(employee2);
		employees.add(employee3);

		employees.forEach(department::addEmployee);

		assertTrue(department.getEmployees().contains(employee1));
		assertTrue(department.getEmployees().contains(employee2));
		assertTrue(department.getEmployees().contains(employee3));
	}

	@Test
	public void getName() throws Exception
	{
		assertEquals(DEPARTMENT_NAME, department.getName());
	}
}