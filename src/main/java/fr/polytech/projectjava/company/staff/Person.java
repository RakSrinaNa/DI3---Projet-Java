package fr.polytech.projectjava.company.staff;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
abstract class Person
{
	private final String lastName;
	private final String firstName;
	
	public Person(String lastName, String firstName)
	{
		this.lastName = lastName;
		this.firstName = firstName;
	}
	
	@Override
	public String toString()
	{
		return "Name: \t" + getLastName().toUpperCase() + " " + getFirstName();
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
}
