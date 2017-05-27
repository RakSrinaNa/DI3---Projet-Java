package fr.polytech.projectjava.checkingsimulation;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Represents an employee.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public class Employee implements Serializable
{
	private static final long serialVersionUID = 4718302840198002134L;
	private SimpleIntegerProperty ID;
	private SimpleStringProperty first;
	private SimpleStringProperty last;
	private boolean inside;
	
	/**
	 * Constructor.
	 *
	 * @param ID    The employee ID.
	 * @param first The first name of the employee.
	 * @param last  The last name of the employee.
	 */
	public Employee(int ID, String first, String last)
	{
		this.ID = new SimpleIntegerProperty(ID);
		this.first = new SimpleStringProperty(first);
		this.last = new SimpleStringProperty(last);
		inside = false;
	}
	
	/**
	 * Parse an employee from a string sent by the server.
	 *
	 * @param s The string to parse.
	 *
	 * @return The employee, or null if failed.
	 */
	public static Employee parse(String s)
	{
		if(s.equals("ERROR"))
			return null;
		String parts[] = s.split(";");
		try
		{
			return new Employee(Integer.parseInt(parts[0]), parts[1], parts[2]);
		}
		catch(NumberFormatException ignored)
		{
		}
		return null;
	}
	
	/**
	 * Serialize the object.
	 *
	 * @param oos The object stream.
	 *
	 * @throws IOException If the serialization failed.
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.writeInt(getID());
		oos.writeObject(getFirst());
		oos.writeObject(getLast());
	}
	
	/**
	 * Get the ID.
	 *
	 * @return The ID.
	 */
	public int getID()
	{
		return IDProperty().get();
	}
	
	/**
	 * Get the first name.
	 *
	 * @return The first name.
	 */
	public String getFirst()
	{
		return firstnameProperty().get();
	}
	
	/**
	 * Get the last name.
	 *
	 * @return The last name.
	 */
	public String getLast()
	{
		return lastnameProperty().get();
	}
	
	/**
	 * Get the ID property.
	 *
	 * @return The ID property.
	 */
	public SimpleIntegerProperty IDProperty()
	{
		return ID;
	}
	
	/**
	 * Get the first name property.
	 *
	 * @return The first name property.
	 */
	public SimpleStringProperty firstnameProperty()
	{
		return first;
	}
	
	/**
	 * Get the last name property.
	 *
	 * @return The last name property.
	 */
	public SimpleStringProperty lastnameProperty()
	{
		return last;
	}
	
	/**
	 * Deserialize an object.
	 *
	 * @param ois The object stream.
	 *
	 * @throws IOException            If the deserialization failed.
	 * @throws ClassNotFoundException If the file doesn't represent the correct class.
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
	{
		ID = new SimpleIntegerProperty(ois.readInt());
		first = new SimpleStringProperty((String) ois.readObject());
		last = new SimpleStringProperty((String) ois.readObject());
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Employee && getID() == ((Employee) obj).getID();
	}
	
	/**
	 * Get the full name.
	 *
	 * @return The full name.
	 */
	public String getFullName()
	{
		return getFirst() + " " + getLast();
	}
	
	/**
	 * Tell if an employee is currently inside the building.
	 *
	 * @return True if inside, false else.
	 */
	public boolean isInside()
	{
		return inside;
	}
	
	/**
	 * Set if the employee is inside.
	 *
	 * @param inside Its location: true inside, false outside.
	 */
	public void setInside(boolean inside)
	{
		this.inside = inside;
	}
}
