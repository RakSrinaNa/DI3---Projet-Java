package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.departments.StandardDepartment;
import org.junit.Before;
import org.junit.Test;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalTime;
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
	
	@Test
	public void getOverMinutes() throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Employee employee = new Employee("A", "B", Time.valueOf("08:00:00").toLocalTime(), Time.valueOf("17:00:00").toLocalTime());
		employee.addWorkingDay(DayOfWeek.MONDAY);
		employee.addWorkingDay(DayOfWeek.TUESDAY);
		employee.addWorkingDay(DayOfWeek.WEDNESDAY);
		employee.addWorkingDay(DayOfWeek.THURSDAY);
		employee.addWorkingDay(DayOfWeek.FRIDAY);
		
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.IN, formatter.parse("2017/01/02 08:00:00")));
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.OUT, formatter.parse("2017/01/02 17:00:00")));
		assertEquals(0, employee.updateOvertime(Date.valueOf("2017-01-02").toLocalDate()));
		
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.IN, formatter.parse("2017/01/03 08:10:00")));
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.OUT, formatter.parse("2017/01/03 17:00:00")));
		assertEquals(-15, employee.updateOvertime(Date.valueOf("2017-01-03").toLocalDate()));
		
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.IN, formatter.parse("2017/01/04 08:00:00")));
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.OUT, formatter.parse("2017/01/04 17:30:00")));
		assertEquals(15, employee.updateOvertime(Date.valueOf("2017-01-04").toLocalDate()));
		
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.IN, formatter.parse("2017/01/05 07:45:00")));
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.OUT, formatter.parse("2017/01/05 17:30:00")));
		assertEquals(60, employee.updateOvertime(Date.valueOf("2017-01-05").toLocalDate()));
		
		assertEquals(60 - (employee.getDepartureTime().toSecondOfDay() - employee.getArrivalTime().toSecondOfDay()) / 60, employee.updateOvertime(Date.valueOf("2017-01-06").toLocalDate()));
		
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.IN, formatter.parse("2017/01/07 08:00:00")));
		employee.addCheckInOut(new CheckInOut(CheckInOut.CheckType.OUT, formatter.parse("2017/01/07 17:00:00")));
		assertEquals(60, employee.updateOvertime(Date.valueOf("2017-01-07").toLocalDate()));
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
		
		assertEquals(1, employee.getChecks().size());
	}
	
	@Test
	public void getSetTimes() throws Exception
	{
		Employee employee1 = new Employee("A", "B");
		
		assertEquals(Employee.DEFAULT_ARRIVAL_TIME, employee1.getArrivalTime());
		assertEquals(Employee.DEFAULT_DEPARTURE_TIME, employee1.getDepartureTime());
		
		Employee employee2 = new Employee("A", "B", Time.valueOf("01:02:03").toLocalTime(), Time.valueOf("02:03:04").toLocalTime());
		assertEquals(Time.valueOf("01:02:03").toLocalTime(), employee2.getArrivalTime());
		assertEquals(Time.valueOf("02:03:04").toLocalTime(), employee2.getDepartureTime());
	}
	
	@Test
	public void setArrivalDepartureTime()
	{
		Employee employee1 = new Employee("A", "B");
		LocalTime time = LocalTime.of(10, 0);
		
		employee1.setArrivalTime(time);
		employee1.setDepartureTime(time);
		assertEquals(time, employee1.getArrivalTime());
		assertEquals(time, employee1.getDepartureTime());
		
		time = LocalTime.of(15, 34);
		employee1.setDepartureTime(time);
		employee1.setArrivalTime(time);
		assertEquals(time, employee1.getArrivalTime());
		assertEquals(time, employee1.getDepartureTime());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void setArrivalDepartureTimeFail()
	{
		Employee employee1 = new Employee("A", "B");
		
		LocalTime time = LocalTime.of(0, 0);
		employee1.setDepartureTime(time);
		
		time = LocalTime.of(23, 59);
		employee1.setArrivalTime(time);
	}
}