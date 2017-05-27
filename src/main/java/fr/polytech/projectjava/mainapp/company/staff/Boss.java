package fr.polytech.projectjava.mainapp.company.staff;

import java.io.Serializable;

/**
 * Represent the boss of a company.
 * He's not an employee and does not need to check in/out.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Boss extends Person implements Serializable
{
	private static final long serialVersionUID = -6683924518878506618L;
	
	/**
	 * Construct the boss with his/her name.
	 *
	 * @param lastName  His/her last name.
	 * @param firstName His/her first name.
	 */
	public Boss(String lastName, String firstName)
	{
		super(lastName, firstName);
	}
}
