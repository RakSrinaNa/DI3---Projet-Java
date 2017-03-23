package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.company.departments.StandardDepartment;
import org.junit.Before;
import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.assertEquals;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class EmployeeTest
{
	private static final int EMPLOYEE_ID = new Random().nextInt(Integer.MAX_VALUE);

	private Employee employee;
	private StandardDepartment workingDepartment;

	@Before
	public void setUp()
	{
		workingDepartment = new StandardDepartment("RND");
		Manager manager = new Manager(0, "A", "B", workingDepartment, true);
		workingDepartment.setManager(manager);

		employee = new Employee(EMPLOYEE_ID, PersonTest.LAST_NAME, PersonTest.FIRST_NAME, workingDepartment);
	}

	@Test
	public void getID() throws Exception
	{
		assertEquals(EMPLOYEE_ID, employee.getID());
	}

	@Test
	public void getWorkingDepartment() throws Exception
	{
		assertEquals(workingDepartment, employee.getWorkingDepartment());
	}
}