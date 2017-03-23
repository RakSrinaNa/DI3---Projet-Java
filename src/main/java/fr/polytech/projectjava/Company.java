package fr.polytech.projectjava;

import java.util.ArrayList;
import java.util.Collection;
/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Company
{
	private final String name;
	private final Boss boss;
	private final ArrayList<Employee> employees = new ArrayList<>();

	public Company(String name, Boss boss)
	{
		this(name, boss, null);
	}

	public Company(String name, Boss boss, Collection<Employee> employees)
	{
		this.name = name;
		this.boss = boss;
		if(employees != null)
			this.employees.addAll(employees);
	}
}
