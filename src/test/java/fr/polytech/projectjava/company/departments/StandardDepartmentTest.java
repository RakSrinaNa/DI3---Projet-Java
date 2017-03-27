package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.company.staff.Boss;
import fr.polytech.projectjava.company.staff.Manager;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
		
		manager1 = new Manager("A", "B");
		department1 = new StandardDepartment(company, "StandardDepartment1", manager1);
		
		manager2 = new Manager("A", "B");
		department2 = new StandardDepartment(company, "StandardDepartment2", manager2);
		
		manager3 = new Manager("A", "B");
		manager3.setWorkingDepartment(department2);
	}

	@Test
	public void getSetManager() throws Exception
	{
		assertEquals(manager1, department1.getManager());
		assertEquals(manager2, department2.getManager());
		assertFalse(department1.setManager(manager3));
		assertTrue(department2.setManager(manager3));
		assertEquals(manager3, department2.getManager());
	}
}