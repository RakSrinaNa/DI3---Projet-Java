package fr.polytech.projectjava.checkingSimulation;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private SimpleIntegerProperty ID;
	private SimpleStringProperty first;
	private SimpleStringProperty last;
	
	public Employee(int ID, String first, String last)
	{
		this.ID = new SimpleIntegerProperty(ID);
		this.first = new SimpleStringProperty(first);
		this.last = new SimpleStringProperty(last);
	}
	
	public static Employee parse(String s)
	{
		if(s.equals("ERROR"))
			return null;
		String parts[] = s.split(";");
		return new Employee(Integer.parseInt(parts[0]), parts[1], parts[2]);
	}
	
	public SimpleIntegerProperty IDProperty()
	{
		return ID;
	}
	
	public SimpleStringProperty firstnameProperty()
	{
		return first;
	}
	
	public SimpleStringProperty lastnameProperty()
	{
		return last;
	}
	
	public String getName()
	{
		return getFirst() + " " + getLast();
	}
	
	public String getFirst()
	{
		return firstnameProperty().get();
	}
	
	public int getID()
	{
		return IDProperty().get();
	}
	
	public String getLast()
	{
		return lastnameProperty().get();
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.writeInt(getID());
		oos.writeObject(getFirst());
		oos.writeObject(getLast());
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
	{
		ID = new SimpleIntegerProperty(ois.readInt());
		first = new SimpleStringProperty((String) ois.readObject());
		last = new SimpleStringProperty((String) ois.readObject());
	}
}
