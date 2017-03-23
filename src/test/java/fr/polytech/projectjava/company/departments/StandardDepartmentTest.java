package fr.polytech.projectjava.company.departments;

import fr.polytech.projectjava.company.staff.Manager;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
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
		department1 = new StandardDepartment("StandardDepartment1");
		manager1 = new Manager(0, "A", "B", department1);

		department2 = new StandardDepartment("StandardDepartment2");
		manager2 = new Manager(1, "A", "B", department2);

		manager3 = new Manager(2, "A", "B", department2);
	}

	@Test
	public void getSetManager() throws Exception
	{
		assertEquals(manager1, department1.getManager());
		assertEquals(manager2, department2.getManager());
		assertFalse(department1.setManager(manager3));
	}
}