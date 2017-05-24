package fr.polytech.projectjava.mainapp.company.staff;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
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
	
	@Test
	public void isManaging() throws Exception
	{
		Company company = new Company("A", new Boss("A", "B"));
		Manager manager1 = new Manager(company, "A", "B");
		Manager manager2 = new Manager(company, "A", "B");
		new StandardDepartment(company, "RND", manager1);
		
		assertTrue(manager1.isManaging());
		assertFalse(manager2.isManaging());
	}
}