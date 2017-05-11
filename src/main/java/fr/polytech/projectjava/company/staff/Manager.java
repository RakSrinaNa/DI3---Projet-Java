package fr.polytech.projectjava.company.staff;

import javafx.beans.property.SimpleBooleanProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalTime;

/**
 * Represent a manager in the company.
 * A manager can only manage less or equal than one department.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Manager extends Employee implements Serializable
{
	private static final long serialVersionUID = -2861031212711385809L;
	private SimpleBooleanProperty managing;
	
	/**
	 * Construct a manager with his/her name and his affected department.
	 *
	 * @param lastName  His/her last name.
	 * @param firstName His/her first name.
	 *
	 * @throws IllegalArgumentException If the arrival time is after the departure time.
	 */
	public Manager(String lastName, String firstName) throws IllegalArgumentException
	{
		super(lastName, firstName);
		managing = new SimpleBooleanProperty(false);
	}
	
	/**
	 * Create an manager with his/her name and its departure and arrival times.
	 *
	 * @param lastName      His/her last name.
	 * @param firstName     His/her first name.
	 * @param arrivalTime   The arrival time.
	 * @param departureTIme The departure time.
	 *
	 * @throws IllegalArgumentException If the arrival time is after the departure time.
	 */
	public Manager(String lastName, String firstName, LocalTime arrivalTime, LocalTime departureTIme) throws IllegalArgumentException
	{
		super(lastName, firstName, arrivalTime, departureTIme);
		managing = new SimpleBooleanProperty(false);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + (isManaging() ? "\nManager" : "");
	}
	
	/**
	 * Return the managing status of the manager.
	 *
	 * @return True he/she is managing a department, false otherwise.
	 */
	public boolean isManaging()
	{
		return managingProperty().get();
	}
	
	private SimpleBooleanProperty managingProperty()
	{
		return managing;
	}
	
	/**
	 * Set the managing status of the manager.
	 *
	 * @param managing The status to set.
	 */
	public void setManaging(boolean managing)
	{
		this.managing.set(managing);
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
			oos.writeBoolean(isManaging());
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
	{
		managing = new SimpleBooleanProperty(ois.readBoolean());
	}
}
