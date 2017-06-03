package fr.polytech.projectjava.mainapp.company.staff.checking;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.staff.Boss;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.utils.jfx.MinutesDuration;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import static fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck.CheckType.IN;
import static fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck.CheckType.OUT;
import static java.time.temporal.ChronoUnit.MINUTES;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/06/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-06-03
 */
public class EmployeeCheckTest
{
	private Employee employee;
	
	@Before
	public void setUp() throws Exception
	{
		employee = new Employee(new Company("C", new Boss("AA", "BB")), "A", "B");
	}
	
	@Test
	public void setIn() throws Exception
	{
		EmployeeCheck check1 = new EmployeeCheck(employee, LocalDate.now());
		assertNull(check1.getCheckIn());
		check1.setIn(LocalTime.of(2, 34));
		assertEquals(LocalTime.of(2, 30), check1.getCheckIn());
		
		EmployeeCheck check2 = new EmployeeCheck(employee, IN, LocalDate.now(), LocalTime.of(2, 56));
		assertEquals(LocalTime.of(3, 0), check2.getCheckIn());
	}
	
	@Test
	public void setOut() throws Exception
	{
		EmployeeCheck check1 = new EmployeeCheck(employee, LocalDate.now());
		assertNull(check1.getCheckOut());
		check1.setOut(LocalTime.of(2, 34));
		assertEquals(LocalTime.of(2, 30), check1.getCheckOut());
		
		EmployeeCheck check2 = new EmployeeCheck(employee, OUT, LocalDate.now(), LocalTime.of(2, 56));
		assertEquals(LocalTime.of(3, 0), check2.getCheckOut());
	}
	
	@Test
	public void isInProgress() throws Exception
	{
		EmployeeCheck check = new EmployeeCheck(employee, LocalDate.now());
		assertFalse(check.isInProgress());
		check.setIn(LocalTime.now());
		assertTrue(check.isInProgress());
		check.setOut(LocalTime.now().plus(15, MINUTES));
		assertFalse(check.isInProgress());
		check.setIn(null);
		assertTrue(check.isInProgress());
	}
	
	@Test
	public void isValidState() throws Exception
	{
		EmployeeCheck check = new EmployeeCheck(employee, LocalDate.now());
		assertTrue(check.isValidState());
		check.setIn(LocalTime.now());
		check.setOut(LocalTime.now());
		assertFalse(check.isValidState());
		check.setOut(LocalTime.now().plus(15, MINUTES));
		assertTrue(check.isValidState());
	}
	
	@Test
	public void getWorkingTime() throws Exception
	{
		EmployeeCheck check = new EmployeeCheck(employee, LocalDate.now());
		assertEquals(new MinutesDuration(0), check.getWorkedTime());
		check.setIn(LocalTime.now());
		assertEquals(new MinutesDuration(0), check.getWorkedTime());
		check.setOut(LocalTime.now().plus(15, MINUTES));
		assertEquals(new MinutesDuration(15), check.getWorkedTime());
		check.setIn(null);
		assertEquals(new MinutesDuration(0), check.getWorkedTime());
	}
}