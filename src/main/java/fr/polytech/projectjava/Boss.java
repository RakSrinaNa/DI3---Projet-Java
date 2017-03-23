package fr.polytech.projectjava;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Boss extends Person
{
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
