package fr.polytech.projectjava.checkingSimulation;

import java.io.Serializable;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public class Employee implements Serializable
{
	private static final long serialVersionUID = 4718302840198002134L;
	private final int ID;
	private final String first;
	private final String last;
	
	public Employee(int ID, String first, String last)
	{
		this.ID = ID;
		this.first = first;
		this.last = last;
	}
	
	public static Employee parse(String s)
	{
		String parts[] = s.split(";");
		return new Employee(Integer.parseInt(parts[0]), parts[1], parts[2]);
	}
	
	public String getName()
	{
		return first + " " + last;
	}
	
	public String getFirst()
	{
		return first;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public String getLast()
	{
		return last;
	}
}
