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
	private boolean notifiedArrival = false;
	private boolean notifiedDeparture = false;
	
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
	 * Read a work day from the CSV.
	 *
	 * @param employee  The employee having this work day.
	 * @param csv       The CSV to read.
	 * @param delimiter The delimiter used.
	 *
	 * @return The created work day.
	 */
	public static EmployeeCheck fromCSV(Employee employee, String csv, String delimiter)
	{
		if(csv.equals(""))
			return null;
		String parts[] = csv.split(delimiter);
		EmployeeCheck check = new EmployeeCheck(employee, LocalDate.parse(parts[0]));
		if(!parts[1].equals("NULL"))
			check.setIn(LocalTime.parse(parts[1]));
		if(!parts[2].equals("NULL"))
			check.setOut(LocalTime.parse(parts[2]));
		if(parts[3].equals("t"))
			check.setArrivalNotified();
		if(parts[4].equals("t"))
			check.setDepartureNotified();
		return check;
	}
	
	/**
	 * Set the arrival time as notified.
	 */
	private void setArrivalNotified()
	{
		notifiedArrival = true;
	}
	
	/**
	 * Set the departure time as notified.
	 */
	private void setDepartureNotified()
	{
		notifiedDeparture = true;
	}
	
	/**
	 * Transform a check into a CSV form.
	 *
	 * @param delimiter The delimiter to use.
	 *
	 * @return The CSV string.
	 */
	public String asCSV(String delimiter)
	{
		return getDate().toString() + delimiter + (getCheckIn() == null ? "NULL" : getCheckIn().toString()) + delimiter + (getCheckOut() == null ? "NULL" : getCheckOut()) + delimiter + (notifiedArrival ? "t" : "f") + delimiter + (notifiedDeparture ? "t" : "f");
	}
	
	/**
	 * Notify the manager about the arrival time.
	 */
	public void notifyManagerArrival()
	{
		if(!notifiedArrival)
		{
			setArrivalNotified();
			if(getEmployee().getWorkingDepartment() != null && getEmployee().getWorkingDepartment().getLeader() != null)
			{
				WorkDay day = getEmployee().getWorkDay(getDate().getDayOfWeek());
				getEmployee().getWorkingDepartment().getLeader().mailManager(getDate() + ": Employee " + getEmployee() + " in department " + getEmployee().getWorkingDepartment() + " is " + (getArrivalOffset().getMinutes() > 0 ? "in advance" : "late"), "This employee was supposed to arrive at " + (day == null ? "NONE" : day.getStartTime()) + " but checked at " + (getCheckIn() == null ? "NONE" : getCheckIn()) + ".");
			}
		}
	}
	
	/**
	 * Get the offset between the actual arrival time and the theoretical time.
	 *
	 * @return The offset, negative if late, positive if in advance.
	 */
	public MinutesDuration getArrivalOffset()
	{
		WorkDay workDay = getEmployee().getWorkDay(getDate().getDayOfWeek());
		return MinutesDuration.seconds(getCheckIn() == null ? 0 : getCheckIn().toSecondOfDay()).substract(MinutesDuration.seconds(workDay == null ? 0 : workDay.getStartTime().toSecondOfDay()));
	}
	
	/**
	 * Notify the manager about the departure time.
	 */
	public void notifyManagerDeparture()
	{
		if(!notifiedDeparture)
		{
			setDepartureNotified();
			if(getEmployee().getWorkingDepartment() != null && getEmployee().getWorkingDepartment().getLeader() != null)
			{
				WorkDay day = getEmployee().getWorkDay(getDate().getDayOfWeek());
				getEmployee().getWorkingDepartment().getLeader().mailManager(getDate() + ": Employee " + getEmployee() + " in department " + getEmployee().getWorkingDepartment() + " is " + (getDepartureOffset().getMinutes() > 0 ? "leaving late" : "leaving early"), "This employee was supposed to leave at " + (day == null ? "NONE" : day.getEndTime()) + " but checked at " + (getCheckOut() == null ? "NONE" : getCheckOut()) + ".");
			}
		}
	}
	
	/**
	 * Get the offset between the actual departure time and the theoretical time.
	 *
	 * @return The offset, negative if late, positive if in advance.
	 */
	public MinutesDuration getDepartureOffset()
	{
		WorkDay workDay = getEmployee().getWorkDay(getDate().getDayOfWeek());
		return MinutesDuration.seconds(getCheckOut() == null ? 0 : getCheckOut().toSecondOfDay()).substract(MinutesDuration.seconds(workDay == null ? 0 : workDay.getEndTime().toSecondOfDay()));
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
		oos.writeBoolean(notifiedArrival);
		oos.writeBoolean(notifiedDeparture);
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
		notifiedArrival = ois.readBoolean();
		notifiedDeparture = ois.readBoolean();
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
