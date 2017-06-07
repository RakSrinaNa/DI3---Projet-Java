package fr.polytech.projectjava.mainapp.company.staff;

import javafx.beans.property.SimpleStringProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Queue;
import java.util.regex.Pattern;

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
	private SimpleStringProperty mail;
	
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
		mail = new SimpleStringProperty("");
	}
	
	@Override
	public String toString()
	{
		return getFullName();
	}
	
	/**
	 * Transform a person into a CSV form.
	 *
	 * @param delimiter The delimiter to use.
	 *
	 * @return The CSV string.
	 */
	public String asCSV(String delimiter)
	{
		return getFirstName() + delimiter + getLastName() + delimiter + getMail();
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
	 * Get the mail of the person.
	 *
	 * @return Its mail.
	 */
	public String getMail()
	{
		return mail.get();
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
		oos.writeObject(mail.get());
	}
	
	/**
	 * Set the mail for this person.
	 *
	 * @param mail The mail to set.
	 */
	public void setMail(String mail)
	{
		mailProperty().set(mail);
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
		mail = new SimpleStringProperty((String) ois.readObject());
	}
	
	/**
	 * Parse the csv to fill the person fields.
	 *
	 * @param csv The CSV parts to parse.
	 */
	protected void parseCSV(Queue<String> csv)
	{
		firstName.set(csv.poll());
		lastName.set(csv.poll());
		mail.set(csv.poll());
	}
	
	/**
	 * Get the mail property for this employee.
	 *
	 * @return The mail property.
	 */
	public SimpleStringProperty mailProperty()
	{
		return mail;
	}
	
	/**
	 * Tell if the mail is in a valid state.
	 *
	 * @return The mail validity.
	 */
	protected boolean isValidMail()
	{
		return getMail().equals("") || Pattern.matches("[\\w_\\-.]+@[\\w_\\-]+\\.\\w+", getMail());
	}
}
