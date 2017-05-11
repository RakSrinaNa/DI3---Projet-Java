package fr.polytech.projectjava.company.staff;

import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Represent a parson in the company.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public abstract class Person implements Serializable
{
	private static final long serialVersionUID = -451843709154049172L;
	private SimpleStringProperty lastName;
	private SimpleStringProperty firstName;
	
	/**
	 * Construct a person with his/her name.
	 *
	 * @param lastName  His/her last name.
	 * @param firstName His/her first name.
	 */
	public Person(String lastName, String firstName)
	{
		this.lastName = new SimpleStringProperty(lastName);
		this.firstName = new SimpleStringProperty(firstName);
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
		return firstNameProperty().get();
	}
	
	public SimpleStringProperty firstNameProperty()
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
		return lastNameProperty().get();
	}
	
	public SimpleStringProperty lastNameProperty()
	{
		return lastName;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.writeObject(lastName.get());
		oos.writeObject(firstName.get());
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
	{
		lastName = new SimpleStringProperty((String) ois.readObject());
		firstName = new SimpleStringProperty((String) ois.readObject());
	}
}
