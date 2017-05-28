package fr.polytech.projectjava.mainapp.company.departments;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.staff.Boss;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class StandardDepartmentTest
{
	private Manager manager1;
	private Manager manager2;
	private Manager manager3;
	private StandardDepartment department1;
	private StandardDepartment department2;

	@Before
	public void setUp() throws Exception
	{
		Company company = new Company("A", new Boss("A", "B"));
		
		manager1 = new Manager(company, "A", "B");
		department1 = new StandardDepartment(company, "StandardDepartment1", manager1);
		
		manager2 = new Manager(company, "A", "B");
		department2 = new StandardDepartment(company, "StandardDepartment2", manager2);
		
		manager3 = new Manager(company, "A", "B");
		manager3.setWorkingDepartment(department2);
	}

	@Test
	public void getSetManager() throws Exception
	{
		assertEquals(manager1, department1.getLeader());
		assertEquals(manager2, department2.getLeader());
		
		department1.setLeader(manager3);
		assertTrue(manager3.isManaging());
		assertEquals(department1, manager3.getWorkingDepartment());
		
		department2.setLeader(manager3);
		assertFalse(manager2.isManaging());
		assertTrue(manager3.isManaging());
		assertEquals(department2, manager2.getWorkingDepartment());
		assertEquals(department2, manager3.getWorkingDepartment());
		assertEquals(manager3, department2.getLeader());
		assertNull(department1.getLeader());
	}
	
	public void conflictManager() throws Exception
	{
		Company company = new Company("A", new Boss("A", "B"));
		
		Manager manager1 = new Manager(company, "A", "B");
		StandardDepartment department1 = new StandardDepartment(company, "StandardDepartment1", manager1);
		
		Manager manager2 = new Manager(company, "A", "B");
		
		department1.setLeader(manager2);
		assertTrue(manager2.isManaging());
		assertFalse(manager1.isManaging());
		assertEquals(department1, manager2.getWorkingDepartment());
		assertEquals(manager2, department1.getLeader());
		
		StandardDepartment department2 = new StandardDepartment(company, "StandardDepartment2", manager2);
		assertTrue(manager2.isManaging());
		assertEquals(department2, manager2.getWorkingDepartment());
		assertEquals(manager2, department2.getLeader());
	}
}