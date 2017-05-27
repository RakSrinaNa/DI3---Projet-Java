package fr.polytech.projectjava.mainapp.company.staff;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class PersonTest
{
	protected final static String FIRST_NAME = "Jean";
	protected final static String LAST_NAME = "Dupont";

	private Person person;

	@Before
	public void setUp()
	{
		person = new Person(LAST_NAME, FIRST_NAME){};
	}

	@Test
	public void getLastName() throws Exception
	{
		assertEquals(LAST_NAME, person.getLastName());
	}

	@Test
	public void getFirstName() throws Exception
	{
		assertEquals(FIRST_NAME, person.getFirstName());
	}
}