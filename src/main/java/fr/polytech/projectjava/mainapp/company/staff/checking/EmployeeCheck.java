package fr.polytech.projectjava.mainapp.company.staff.checking;

import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.EmployeeRoundedLocalTimeProperty;
import fr.polytech.projectjava.utils.Log;
import fr.polytech.projectjava.utils.jfx.MinutesDuration;
import javafx.beans.property.SimpleObjectProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represent a day check.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 17/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-17
 */
public class EmployeeCheck implements Serializable
{
	private static final long serialVersionUID = 2289845323375640933L;
	private SimpleObjectProperty<Employee> employee;
	private SimpleObjectProperty<LocalDate> date;
	private EmployeeRoundedLocalTimeProperty checkIn;
	private EmployeeRoundedLocalTimeProperty checkOut;
	
	/**
	 * Enumeration of the different types of checks possible.
	 */
	public enum CheckType
	{
		IN, OUT
	}
	
	/**
	 * Constructor.
	 *
	 * @param employee The employee of the check.
	 * @param date     The date of the check.
	 */
	public EmployeeCheck(Employee employee, LocalDate date)
	{
		this.date = new SimpleObjectProperty<>(date);
		this.employee = new SimpleObjectProperty<>(employee);
		checkIn = new EmployeeRoundedLocalTimeProperty(employee);
		checkOut = new EmployeeRoundedLocalTimeProperty(employee);
		Log.info("New check added for " + employee + " on " + date);
	}
	
	/**
	 * Constructor with an initial check.
	 *
	 * @param employee  The employee of the check.
	 * @param checkType The type of the check.
	 * @param date      The date of the check.
	 * @param time      The time of the check.
	 */
	public EmployeeCheck(Employee employee, CheckType checkType, LocalDate date, LocalTime time)
	{
		this(employee, date);
		if(checkType == CheckType.IN)
			setIn(time);
		else
			setOut(time);
	}
	
	/**
	 * Set the in check.
	 *
	 * @param check The time to set.
	 */
	public void setIn(LocalTime check)
	{
		checkIn.set(check);
		Log.info(employee + " checked in on " + getDate() + " at " + check);
	}
	
	/**
	 * Set the out check.
	 *
	 * @param check The time to set.
	 */
	public void setOut(LocalTime check)
	{
		checkOut.set(check);
		Log.info(employee + " checked out on " + getDate() + " at " + check);
	}
	
	/**
	 * Get the date of the check.
	 *
	 * @return The date.
	 */
	public LocalDate getDate()
	{
		return dateProperty().get();
	}
	
	/**
	 * Get the dat property.
	 *
	 * @return The day property.
	 */
	public SimpleObjectProperty<LocalDate> dateProperty()
	{
		return date;
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
		oos.writeObject(getEmployee());
		oos.writeObject(getDate());
		oos.writeInt(((checkIn.get() != null ? 1 : 0) << 1) + (checkOut.get() != null ? 1 : 0)); // Write in binary what will be writer: 01 - Only out / 10 - Only in / 11 - Both
		if(checkIn.get() != null)
			oos.writeObject(checkIn.get());
		if(checkOut.get() != null)
			oos.writeObject(checkOut.get());
	}
	
	/**
	 * Get the employee.
	 *
	 * @return The employee.
	 */
	public Employee getEmployee()
	{
		return employeeProperty().get();
	}
	
	/**
	 * Get the employee property.
	 *
	 * @return The employee property.
	 */
	public SimpleObjectProperty<Employee> employeeProperty()
	{
		return employee;
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
		employee = new SimpleObjectProperty<>((Employee) ois.readObject());
		date = new SimpleObjectProperty<>((LocalDate) ois.readObject());
		int infos = ois.readInt();
		if((infos & 0x02) == 0x02)
			checkIn = new EmployeeRoundedLocalTimeProperty(getEmployee(), (LocalTime) ois.readObject());
		else
			checkIn = new EmployeeRoundedLocalTimeProperty(getEmployee());
		if((infos & 0x01) == 0x01)
			checkOut = new EmployeeRoundedLocalTimeProperty(getEmployee(), (LocalTime) ois.readObject());
		else
			checkOut = new EmployeeRoundedLocalTimeProperty(getEmployee());
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof EmployeeCheck && getEmployee().equals(((EmployeeCheck) obj).getEmployee()) && getDate().equals(((EmployeeCheck) obj).getDate());
	}
	
	@Override
	public String toString()
	{
		return employee + " " + date + " IN: " + (checkIn == null ? "?" : checkIn) + " / OUT: " + (checkOut == null ? "?" : checkOut);
	}
	
	/**
	 * Get the time worked for this day.
	 *
	 * @return The time worked.
	 */
	public MinutesDuration getWorkedTime()
	{
		if(checkIn.get() == null || checkOut.get() == null)
			return MinutesDuration.ZERO;
		return MinutesDuration.seconds(checkOut.get().toSecondOfDay() - checkIn.get().toSecondOfDay());
	}
	
	/**
	 * Tells if a day is in progress (one check is present).
	 *
	 * @return True if one check is present, false else.
	 */
	public boolean isInProgress()
	{
		return ((getCheckIn() == null ? 1 : 0) + (getCheckOut() == null ? 1 : 0)) == 1;
	}
	
	/**
	 * Tell if the checks are in a valid order.
	 *
	 * @return True if valid, false else.
	 */
	public boolean isValidState()
	{
		return getCheckIn() == null || getCheckOut() == null || getCheckIn().isBefore(getCheckOut());
	}
	
	/**
	 * Get the check in time.
	 *
	 * @return The check in time.
	 */
	public LocalTime getCheckIn()
	{
		return checkInProperty().get();
	}
	
	/**
	 * Get the check out time.
	 *
	 * @return The check out time.
	 */
	public LocalTime getCheckOut()
	{
		return checkOutProperty().get();
	}
	
	/**
	 * Get the checkIn property.
	 *
	 * @return The checkIn property.
	 */
	public SimpleObjectProperty<LocalTime> checkInProperty()
	{
		return checkIn;
	}
	
	/**
	 * Get the checkOut property.
	 *
	 * @return The checkOut property.
	 */
	public SimpleObjectProperty<LocalTime> checkOutProperty()
	{
		return checkOut;
	}
}
