package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.company.departments.StandardDepartment;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class EmployeeTest
{
	private Employee employee;
	private StandardDepartment workingDepartment;

	@Before
	public void setUp()
	{
		workingDepartment = new StandardDepartment("RND");
		Manager manager = new Manager("A", "B", workingDepartment);
		workingDepartment.setManager(manager);
		
		employee = new Employee(PersonTest.LAST_NAME, PersonTest.FIRST_NAME, workingDepartment);
	}

	@Test
	public void getID() throws Exception
	{
		int ID = Employee.NEXT_ID;
		
		Employee employee1 = new Employee(PersonTest.LAST_NAME, PersonTest.FIRST_NAME, workingDepartment);
		Employee employee2 = new Employee(PersonTest.LAST_NAME, PersonTest.FIRST_NAME, workingDepartment);
		assertEquals(ID - 1, employee.getID());
		assertEquals(ID, employee1.getID());
		assertEquals(ID + 1, employee2.getID());
	}

	@Test
	public void getWorkingDepartment() throws Exception
	{
		assertEquals(workingDepartment, employee.getWorkingDepartment());
	}
}