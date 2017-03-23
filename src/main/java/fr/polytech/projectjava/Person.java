package fr.polytech.projectjava;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public abstract class Person
{
	private final String lastName;
	private final String firstName;

	public Person(String lastName, String firstName)
	{
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	@Override
	public String toString()
	{
		return "Name: \t" + getLastName().toUpperCase() + " " + getFirstName();
	}
}
