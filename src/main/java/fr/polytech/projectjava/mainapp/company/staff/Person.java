package fr.polytech.projectjava.mainapp.company.staff;

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
	private SimpleStringProperty fullName;
	
	/**
	 * Construct a person with his/her name.
	 *
	 * @param lastName  His/her last name.
	 * @param firstName His/her first name.
	 */
	public Person(String lastName, String firstName)
	{
		this.lastName = new SimpleStringProperty(lastName);
		this.lastName.addListener((observable -> fullNameProperty().set(getFirstName() + " " + getLastName())));
		this.firstName = new SimpleStringProperty(firstName);
		this.firstName.addListener((observable -> fullNameProperty().set(getFirstName() + " " + getLastName())));
		fullName = new SimpleStringProperty(getFirstName() + " " + getLastName());
	}
	
	@Override
	public String toString()
	{
		return getFullName();
	}
	
	/**
	 * Get the full name of the person.
	 *
	 * @return The full name.
	 */
	public String getFullName()
	{
		return fullNameProperty().get();
	}
	
	/**
	 * Get the full name string expression.
	 *
	 * @return The full name expression.
	 */
	public SimpleStringProperty fullNameProperty()
	{
		return fullName;
	}
	
	/**
	 * Get the first name property.
	 *
	 * @return The first name property.
	 */
	public SimpleStringProperty firstNameProperty()
	{
		return firstName;
	}
	
	/**
	 * Get the last name property.
	 *
	 * @return The last name property.
	 */
	public SimpleStringProperty lastNameProperty()
	{
		return lastName;
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
		oos.writeObject(lastName.get());
		oos.writeObject(firstName.get());
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
		lastName = new SimpleStringProperty((String) ois.readObject());
		lastName.addListener((observable -> fullNameProperty().set(getFirstName() + " " + getLastName())));
		firstName = new SimpleStringProperty((String) ois.readObject());
		firstName.addListener((observable -> fullNameProperty().set(getFirstName() + " " + getLastName())));
		fullName = new SimpleStringProperty(getFirstName() + " " + getLastName());
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
	
	/**
	 * Get the last name of the person.
	 *
	 * @return His/her last name.
	 */
	public String getLastName()
	{
		return lastNameProperty().get();
	}
}
