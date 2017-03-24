package fr.polytech.projectjava.company.staff;

/**
 * Represent a parson in the company.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
abstract class Person
{
	private final String lastName;
	private final String firstName;
	
	/**
	 * Construct a person with his/her name.
	 *
	 * @param lastName  His/her last name.
	 * @param firstName His/her first name.
	 */
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
	
	/**
	 * Get the first name of the person.
	 *
	 * @return His/her first name.
	 */
	public String getFirstName()
	{
		return firstName;
	}
	
	/**
	 * Get the last name of the person.
	 *
	 * @return His/her last name.
	 */
	public String getLastName()
	{
		return lastName;
	}
}
