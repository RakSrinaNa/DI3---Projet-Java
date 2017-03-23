package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.staff.Boss;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class ManagementDepartmentTest
{
	private Boss boss;
	private ManagementDepartment department;

	@Before
	public void setUp()
	{
		boss = new Boss("A", "B");

		department = new ManagementDepartment(boss);
	}

	@Test
	public void getManager() throws Exception
	{
		assertEquals(boss, department.getManager());
	}
}