package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.company.StandardDepartment;
import org.junit.Before;
import org.junit.Test;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class ManagerTest
{
	private Manager manager1;
	private Manager manager2;

	@Before
	public void setUp()
	{
		StandardDepartment workingDepartment = new StandardDepartment("RND");
		manager1 = new Manager(0, "A", "B", workingDepartment, true);
		manager2 = new Manager(0, "A", "B", workingDepartment, false);
	}

	@Test
	public void isManaging() throws Exception
	{
		assertTrue(manager1.isManaging());
		assertFalse(manager2.isManaging());
	}
}