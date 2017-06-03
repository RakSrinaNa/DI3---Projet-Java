package fr.polytech.projectjava.mainapp.company.staff;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck;
import fr.polytech.projectjava.mainapp.company.staff.checking.WorkDay;
import org.junit.Before;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import static fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck.CheckType.IN;
import static fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck.CheckType.OUT;
import static java.time.DayOfWeek.MONDAY;
import static java.time.temporal.ChronoUnit.MINUTES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
	private Company company;
	
	@Test
	public void getOverMinutes() throws Exception
	{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Employee employee = new Employee(company, "A", "B", LocalTime.of(8, 0), LocalTime.of(17, 0));
		
		employee.addWorkingDay(null);
		
		EmployeeCheck chk = new EmployeeCheck(employee, LocalDate.now());
		
		employee.addCheck(null);
		employee.addCheck(chk);
		employee.addCheck(chk);
		
		employee.addCheckInOut(IN, LocalDate.of(2017, 1, 2), LocalTime.of(8, 0));
		employee.addCheckInOut(OUT, LocalDate.of(2017, 1, 2), LocalTime.of(17, 0));
		assertEquals(0, employee.updateOvertime(LocalDate.of(2017, 1, 2)), 0);
		employee.removeWorkingDay(MONDAY);
		assertEquals(540, employee.updateOvertime(LocalDate.of(2017, 1, 2)), 0);
		
		WorkDay wkd = new WorkDay(employee, MONDAY, Employee.DEFAULT_ARRIVAL_TIME, Employee.DEFAULT_DEPARTURE_TIME);
		employee.addWorkingDay(wkd);
		employee.addWorkingDay(wkd);
		
		employee.addCheckInOut(IN, LocalDate.of(2017, 1, 3), LocalTime.of(8, 10));
		employee.addCheckInOut(OUT, LocalDate.of(2017, 1, 3), LocalTime.of(17, 0));
		assertEquals(-15, employee.updateOvertime(LocalDate.of(2017, 1, 3)), 0);
		
		employee.addCheckInOut(IN, LocalDate.of(2017, 1, 4), LocalTime.of(8, 0));
		employee.addCheckInOut(OUT, LocalDate.of(2017, 1, 4), LocalTime.of(17, 30));
		assertEquals(15, employee.updateOvertime(LocalDate.of(2017, 1, 4)), 0);
		
		employee.addCheckInOut(IN, LocalDate.of(2017, 1, 5), LocalTime.of(7, 45));
		employee.addCheckInOut(OUT, LocalDate.of(2017, 1, 5), LocalTime.of(17, 30));
		assertEquals(60, employee.updateOvertime(LocalDate.of(2017, 1, 5)), 0);
		
		assertEquals(60 - (employee.getWorkingDays().get(0).getEndTime().toSecondOfDay() - employee.getWorkingDays().get(0).getStartTime().toSecondOfDay()) / 60, employee.updateOvertime(LocalDate.of(2017, 1, 6)), 0);
		
		employee.addCheckInOut(IN, LocalDate.of(2017, 1, 7), LocalTime.of(8, 0));
		employee.addCheckInOut(OUT, LocalDate.of(2017, 1, 7), LocalTime.of(17, 0));
		assertEquals(60, employee.updateOvertime(LocalDate.of(2017, 1, 7)), 0);
	}
	
	@Before
	public void setUp() throws IllegalArgumentException
	{
		company = new Company("A", new Boss("A", "B"));
		Manager manager = new Manager(company, "A", "B");
		workingDepartment = new StandardDepartment(company, "RND", manager);
		
		employee = new Employee(company, PersonTest.LAST_NAME, PersonTest.FIRST_NAME);
		workingDepartment.addEmployee(employee);
	}
	
	@Test
	public void getID() throws Exception
	{
		int ID = Employee.NEXT_ID;
		
		Employee employee1 = new Employee(company, PersonTest.LAST_NAME, PersonTest.FIRST_NAME);
		Employee employee2 = new Employee(company, PersonTest.LAST_NAME, PersonTest.FIRST_NAME);
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
	public void getSetTimes() throws Exception
	{
		Employee employee1 = new Employee(company, "A", "B");
		
		assertEquals(Employee.DEFAULT_ARRIVAL_TIME, employee1.getWorkingDays().get(0).getStartTime());
		assertEquals(Employee.DEFAULT_DEPARTURE_TIME, employee1.getWorkingDays().get(0).getEndTime());
		
		Employee employee2 = new Employee(company, "A", "B", LocalTime.of(1, 2, 3), LocalTime.of(2, 3, 4));
		assertEquals(LocalTime.of(1, 0), employee2.getWorkingDays().get(0).getStartTime());
		assertEquals(LocalTime.of(2, 0), employee2.getWorkingDays().get(0).getEndTime());
	}
	
	@Test
	public void isPresent() throws Exception
	{
		Employee employee = new Employee(company, "A", "B");
		assertFalse(employee.isPresent());
		employee.addCheck(new EmployeeCheck(employee, LocalDate.now()));
		assertFalse(employee.isPresent());
		employee.addCheckInOut(IN, LocalDate.now(), LocalTime.now());
		assertTrue(employee.isPresent());
		employee.addCheckInOut(OUT, LocalDate.now(), LocalTime.now().plus(15, MINUTES));
		assertFalse(employee.isPresent());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void badConstructor()
	{
		new Employee(company, "A", "B", LocalTime.of(10, 0), LocalTime.of(8, 0));
	}
}