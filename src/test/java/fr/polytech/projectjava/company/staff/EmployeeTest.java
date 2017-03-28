package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.InvalidArgumentException;
import fr.polytech.projectjava.company.CheckInOut;
import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.company.departments.StandardDepartment;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
	public void setUp() throws InvalidArgumentException
	{
		Company company = new Company("A", new Boss("A", "B"));
		Manager manager = new Manager("A", "B");
		workingDepartment = new StandardDepartment(company, "RND", manager);
		
		employee = new Employee(PersonTest.LAST_NAME, PersonTest.FIRST_NAME);
		workingDepartment.addEmployee(employee);
	}

	@Test
	public void getID() throws Exception
	{
		int ID = Employee.NEXT_ID;
		
		Employee employee1 = new Employee(PersonTest.LAST_NAME, PersonTest.FIRST_NAME);
		Employee employee2 = new Employee(PersonTest.LAST_NAME, PersonTest.FIRST_NAME);
		assertEquals(ID - 1, employee.getID());
		assertEquals(ID, employee1.getID());
		assertEquals(ID + 1, employee2.getID());
	}

	@Test
	public void getWorkingDepartment() throws Exception
	{
		assertEquals(workingDepartment, employee.getWorkingDepartment());
	}
	
	@Test
	public void getSetCheckInOut() throws Exception
	{
		CheckInOut check1 = new CheckInOut(CheckInOut.CheckType.IN);
		CheckInOut check2 = new CheckInOut(CheckInOut.CheckType.OUT);
		
		employee.addCheckInOut(check1);
		employee.addCheckInOut(check2);
		
		assertTrue(employee.getChecks().contains(check1));
		assertTrue(employee.getChecks().contains(check2));
	}
}