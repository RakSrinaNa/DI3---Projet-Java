package fr.polytech.projectjava.mainapp.company.staff.checking;

import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.EmployeeRoundedLocalTimeProperty;
import fr.polytech.projectjava.utils.Log;
import fr.polytech.projectjava.utils.jfx.MinutesDuration;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Represent a day of work.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-28
 */
public class WorkDay implements Serializable
{
	private static final long serialVersionUID = 2615912793708990817L;
	private DayOfWeek day;
	private EmployeeRoundedLocalTimeProperty startTime;
	private EmployeeRoundedLocalTimeProperty endTime;
	private Employee employee;
	
	/**
	 * Constructor.
	 *
	 * @param employee  The employee concerned.
	 * @param day       The day concerned.
	 * @param startTime The start time for this day and employee.
	 * @param endTime   The end time for this day and employee.
	 *
	 * @throws IllegalArgumentException If the day is null.
	 */
	public WorkDay(Employee employee, DayOfWeek day, LocalTime startTime, LocalTime endTime) throws IllegalArgumentException
	{
		if(day == null)
			throw new IllegalArgumentException("Day cannot be null");
		this.employee = employee;
		this.day = day;
		this.startTime = new EmployeeRoundedLocalTimeProperty(employee, startTime);
		this.startTime.addListener(((observable, oldValue, newValue) -> Log.info(getEmployee() + " now starts at " + newValue + " on " + getDay())));
		this.endTime = new EmployeeRoundedLocalTimeProperty(employee, endTime);
		this.endTime.addListener(((observable, oldValue, newValue) -> Log.info(getEmployee() + " now ends at " + newValue + " on " + getDay())));
	}
	
	/**
	 * Read a work day from the CSV.
	 *
	 * @param employee  The employee having this work day.
	 * @param csv       The CSV to read.
	 * @param delimiter The delimiter used.
	 *
	 * @return The created work day.
	 */
	public static WorkDay fromCSV(Employee employee, String csv, String delimiter)
	{
		String parts[] = csv.split(delimiter);
		return new WorkDay(employee, DayOfWeek.of(Integer.parseInt(parts[0])), LocalTime.parse(parts[1]), LocalTime.parse(parts[2]));
	}
	
	/**
	 * Transform a work day into a CSV form.
	 *
	 * @param delimiter The delimiter to use.
	 *
	 * @return The CSV string.
	 */
	public String asCSV(String delimiter)
	{
		return getDay().getValue() + delimiter + getStartTime().toString() + delimiter + getEndTime();
	}
	
	/**
	 * Get the day.
	 *
	 * @return The day.
	 */
	public DayOfWeek getDay()
	{
		return day;
	}
	
	/**
	 * Get the start time.
	 *
	 * @return The start time.
	 */
	public LocalTime getStartTime()
	{
		return startTimeProperty().get();
	}
	
	/**
	 * Get the start time property.
	 *
	 * @return The start time property.
	 */
	public EmployeeRoundedLocalTimeProperty startTimeProperty()
	{
		return startTime;
	}
	
	/**
	 * Get the end time.
	 *
	 * @return The end time.
	 */
	public LocalTime getEndTime()
	{
		return endTimeProperty().get();
	}
	
	/**
	 * Get the end time property.
	 *
	 * @return The end time property.
	 */
	public EmployeeRoundedLocalTimeProperty endTimeProperty()
	{
		return endTime;
	}
	
	/**
	 * Get the employee.
	 *
	 * @return The employee.
	 */
	public Employee getEmployee()
	{
		return employee;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return (obj instanceof WorkDay && getDay().equals(((WorkDay) obj).getDay())) || (obj instanceof DayOfWeek && getDay().equals(obj));
	}
	
	/**
	 * Get the duration the employee should work for this day.
	 *
	 * @return The duration to work.
	 */
	public MinutesDuration getWorkTime()
	{
		return MinutesDuration.seconds(getEndTime().toSecondOfDay() - getStartTime().toSecondOfDay());
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
		oos.writeObject(getDay());
		oos.writeObject(getStartTime());
		oos.writeObject(getEndTime());
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
		employee = (Employee) ois.readObject();
		day = (DayOfWeek) ois.readObject();
		startTime = new EmployeeRoundedLocalTimeProperty(employee, (LocalTime) ois.readObject());
		startTime.addListener(((observable, oldValue, newValue) -> Log.info(getEmployee() + " now starts at " + newValue + " on " + getDay())));
		endTime = new EmployeeRoundedLocalTimeProperty(employee, (LocalTime) ois.readObject());
		endTime.addListener(((observable, oldValue, newValue) -> Log.info(getEmployee() + " now ends at " + newValue + " on " + getDay())));
	}
	
	/**
	 * Tell if a working day is in a valid state.
	 *
	 * @return True if the state is valid, false else.
	 */
	public boolean isValid()
	{
		return getStartTime() != null && getEndTime() != null && getStartTime().isBefore(getEndTime());
	}
}
