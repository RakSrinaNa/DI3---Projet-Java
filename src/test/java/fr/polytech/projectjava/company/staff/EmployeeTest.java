package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.departments.StandardDepartment;
import org.junit.Before;
import org.junit.Test;
import java.sql.Time;
import java.text.SimpleDateFormat;
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
	
	@Test
	public void getOverMinutes() throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Employee employee = new Employee("A", "B", Time.valueOf("08:00:00"), Time.valueOf("17:00:00"));
		
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.IN, formatter.parse("08:00:00")));
		assertEquals(0, employee.getOverMinutes());
		
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.IN, formatter.parse("08:05:00")));
		assertEquals(0, employee.getOverMinutes());
		
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.IN, formatter.parse("08:10:00")));
		assertEquals(-15, employee.getOverMinutes());
		
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.IN, formatter2.parse("2016/01/21 07:30:00")));
		assertEquals(15, employee.getOverMinutes());
		
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.OUT, formatter.parse("17:00:00")));
		assertEquals(15, employee.getOverMinutes());
		
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.OUT, formatter.parse("17:15:00")));
		assertEquals(30, employee.getOverMinutes());
		
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.OUT, formatter2.parse("2016/01/21 16:00:00")));
		assertEquals(-30, employee.getOverMinutes());
	}

	@Before
	public void setUp() throws IllegalArgumentException
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
	
	@Test
	public void getSetTimes() throws Exception
	{
		Employee employee1 = new Employee("A", "B");
		
		assertEquals(Employee.DEFAULT_ARRIVAL_TIME, employee1.getArrivalTime());
		assertEquals(Employee.DEFAULT_DEPARTURE_TIME, employee1.getDepartureTime());
		
		Employee employee2 = new Employee("A", "B", Time.valueOf("01:02:03"), Time.valueOf("02:03:04"));
		assertEquals(Time.valueOf("01:02:03"), employee2.getArrivalTime());
		assertEquals(Time.valueOf("02:03:04"), employee2.getDepartureTime());
	}
}