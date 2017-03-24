package fr.polytech.projectjava;

import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.company.staff.Boss;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Main
{
	/**
	 * Temporary main method.
	 *
	 * @param args Program's arguments.
	 */
	public static void main(String[] args)
	{
		System.out.println(new Company("Company 1", new Boss("T'Kindt", "Vincent")));
	}
}
