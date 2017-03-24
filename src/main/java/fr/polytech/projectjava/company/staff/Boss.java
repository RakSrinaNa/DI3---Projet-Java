package fr.polytech.projectjava.company.staff;

/**
 * Represent the boss of a company.
 * He's not an employee and does not need to check in/out.
 *
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Boss extends Person
{
	/**
	 * Construct the boss with its name.
	 *
	 * @param lastName His/her last name.
	 * @param firstName His/her first name.
	 */
	public Boss(String lastName, String firstName)
	{
		super(lastName, firstName);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "\nBoss";
	}
}
