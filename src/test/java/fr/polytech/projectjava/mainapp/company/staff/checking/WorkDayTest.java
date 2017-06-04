package fr.polytech.projectjava.mainapp.company.staff.checking;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.staff.Boss;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.utils.jfx.MinutesDuration;
import org.junit.Before;
import org.junit.Test;
import java.time.DayOfWeek;
import java.time.LocalTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/06/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-06-03
 */
public class WorkDayTest
{
	private Employee employee;
	
	@Before
	public void setUp() throws Exception
	{
		employee = new Employee(new Company("C", new Boss("AA", "BB")), "A", "B");
	}
	
	@Test
	public void getStartTime() throws Exception
	{
		WorkDay day = new WorkDay(employee, DayOfWeek.MONDAY, LocalTime.of(3, 3), null);
		assertEquals(LocalTime.of(3, 0), day.getStartTime());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void badConstructor()
	{
		new WorkDay(employee, null, null, null);
	}
	
	@Test
	public void getEndTime() throws Exception
	{
		WorkDay day = new WorkDay(employee, DayOfWeek.MONDAY, null, LocalTime.of(3, 3));
		assertEquals(LocalTime.of(3, 0), day.getEndTime());
	}
	
	@Test
	public void getEmployee() throws Exception
	{
		WorkDay day = new WorkDay(employee, DayOfWeek.MONDAY, null, null);
		assertEquals(employee, day.getEmployee());
	}
	
	@Test
	public void getWorkTime() throws Exception
	{
		WorkDay day = new WorkDay(employee, DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(8, 15));
		assertEquals(new MinutesDuration(15), day.getWorkTime());
		day.endTimeProperty().set(LocalTime.of(10, 0));
		assertEquals(new MinutesDuration(120), day.getWorkTime());
	}
	
	@Test
	public void isValid() throws Exception
	{
		WorkDay day = new WorkDay(employee, DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(8, 15));
		assertTrue(day.isValid());
		day.endTimeProperty().set(LocalTime.of(7, 0));
		assertFalse(day.isValid());
	}
}