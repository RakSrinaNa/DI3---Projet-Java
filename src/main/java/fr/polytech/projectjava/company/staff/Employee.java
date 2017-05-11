package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.departments.StandardDepartment;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * Represent an employee in the company.
 * Each one have a unique ID that is also their card ID.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Employee extends Person implements Serializable
{
	protected final static LocalTime DEFAULT_ARRIVAL_TIME = Time.valueOf("08:30:00").toLocalTime();
	protected final static LocalTime DEFAULT_DEPARTURE_TIME = Time.valueOf("17:30:00").toLocalTime();
	private static final long serialVersionUID = -8611138931676775765L;
	private int ID;
	private ObservableList<CheckInOut> checks = FXCollections.observableArrayList();
	private ObservableList<DayOfWeek> workingDays = FXCollections.observableArrayList();
	protected static int NEXT_ID = 0;
	private SimpleObjectProperty<StandardDepartment> workingDepartment;
	private SimpleObjectProperty<LocalTime> arrivalTime;
	private SimpleObjectProperty<LocalTime> departureTime;
	
	/**
	 * Create an employee with his/her name.
	 *
	 * @param lastName  His/her last name.
	 * @param firstName His/her first name.
	 *
	 * @throws IllegalArgumentException If the arrival time is after the departure time.
	 */
	public Employee(String lastName, String firstName) throws IllegalArgumentException
	{
		this(lastName, firstName, DEFAULT_ARRIVAL_TIME, DEFAULT_DEPARTURE_TIME);
	}
	
	/**
	 * Create an employee with his/her name and its departure and arrival times.
	 *
	 * @param lastName      His/her last name.
	 * @param firstName     His/her first name.
	 * @param arrivalTime   The arrival time.
	 * @param departureTIme The departure time.
	 *
	 * @throws IllegalArgumentException If the arrival time is after the departure time.
	 */
	public Employee(String lastName, String firstName, LocalTime arrivalTime, LocalTime departureTIme) throws IllegalArgumentException
	{
		super(lastName, firstName);
		if(arrivalTime.isAfter(departureTIme))
			throw new IllegalArgumentException("Arrival time can't be after the departure time.");
		this.ID = NEXT_ID++;
		this.arrivalTime = new SimpleObjectProperty<>(arrivalTime);
		this.departureTime = new SimpleObjectProperty<>(departureTIme);
		workingDepartment = new SimpleObjectProperty<>(null);
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "\nID: \t" + getID() + "\nDpt: \t" + (getWorkingDepartment() != null ? getWorkingDepartment().getName() : "~None~");
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Employee && ID == ((Employee) obj).getID();
	}
	
	/**
	 * Add a checking to this employee.
	 *
	 * @param checkInOut The checking to add.
	 */
	public void addCheckInOut(CheckInOut checkInOut)
	{
		checks.add(checkInOut);
	}
	
	/**
	 * Add a working day for this employee.
	 *
	 * @param day The day to add.
	 */
	public void addWorkingDay(DayOfWeek day)
	{
		if(!workingDays.contains(day))
			workingDays.add(day);
	}
	
	/**
	 * Get the number of minutes the employee done more.
	 *
	 * @return The number of minutes. If the number is negative it represents the number of minutes dues.
	 */
	public long getOverMinutes(LocalDate maxDate) throws IllegalStateException
	{
		if(maxDate == null)
			maxDate = new Date(System.currentTimeMillis()).toLocalDate();
		Map<LocalDate, List<CheckInOut>> checksByDate = checks.stream().collect(Collectors.groupingBy(CheckInOut::getDay));
		
		LocalDate currentDate = checksByDate.keySet().stream().sorted(Comparator.naturalOrder()).findFirst().orElseGet(() -> new Date(System.currentTimeMillis()).toLocalDate());
		
		long total = 0;
		
		while(currentDate.compareTo(maxDate) <= 0)
		{
			List<CheckInOut> checks = checksByDate.containsKey(currentDate) ? checksByDate.get(currentDate) : new ArrayList<>();
			if(checks.size() == 1 || checks.size() > 2 || (checks.size() == 2 && checks.get(0).getCheckType() == checks.get(1).getCheckType()))
				throw new IllegalStateException("Problem with checks on day" + currentDate + "!");
			
			if(workingDays.contains(currentDate.getDayOfWeek()))
			{
				total -= getArrivalTime().until(getDepartureTime(), MINUTES);
				if(checks.size() == 2)
					for(CheckInOut check : checks)
						total += (check.getCheckType() == CheckInOut.CheckType.IN ? -1 : 1) * check.getTime().toSecondOfDay() / 60;
			}
			else if(checks.size() == 2)
				for(CheckInOut check : checks)
					total += (check.getCheckType() == CheckInOut.CheckType.IN ? -1 : 1) * check.getTime().toSecondOfDay() / 60;
			currentDate = currentDate.plusDays(1);
		}
		
		return total;
	}
	
	/**
	 * Get the arrival time of this employee.
	 *
	 * @return The arrival time.
	 */
	public LocalTime getArrivalTime()
	{
		return arrivalTimeProperty().get();
	}
	
	private SimpleObjectProperty<LocalTime> arrivalTimeProperty()
	{
		return arrivalTime;
	}
	
	/**
	 * Set the arrival time for this employee.
	 *
	 * @param arrivalTime The arrival time to set.
	 *
	 * @throws IllegalArgumentException If the arrival time is after the departure time.
	 */
	public void setArrivalTime(LocalTime arrivalTime) throws IllegalArgumentException
	{
		if(arrivalTime.isAfter(getDepartureTime()))
			throw new IllegalArgumentException("Arrival time can't be after the departure time.");
		this.arrivalTime.set(arrivalTime);
	}
	
	/**
	 * Get the list of checking the employee did.
	 *
	 * @return A list of the checking.
	 */
	public ObservableList<CheckInOut> getChecks()
	{
		return checks;
	}
	
	/**
	 * Get the departure time of this employee.
	 *
	 * @return The departure time.
	 */
	public LocalTime getDepartureTime()
	{
		return departureTimeProperty().get();
	}
	
	private SimpleObjectProperty<LocalTime> departureTimeProperty()
	{
		return departureTime;
	}
	
	/**
	 * Set the departure time for this employee.
	 *
	 * @param departureTime The departure time to set.
	 *
	 * @throws IllegalArgumentException If the arrival time is after the departure time.
	 */
	public void setDepartureTime(LocalTime departureTime) throws IllegalArgumentException
	{
		if(getArrivalTime().isAfter(departureTime))
			throw new IllegalArgumentException("Arrival time can't be after the departure time.");
		this.departureTime.set(departureTime);
	}
	
	/**
	 * Get the ID of the employee.
	 *
	 * @return Its ID.
	 */
	public int getID()
	{
		return ID;
	}
	
	/**
	 * Get the department the employee is working in.
	 *
	 * @return The worker's department.
	 */
	public StandardDepartment getWorkingDepartment()
	{
		return workingDepartmentProperty().get();
	}
	
	public SimpleObjectProperty<StandardDepartment> workingDepartmentProperty()
	{
		return workingDepartment;
	}
	
	/**
	 * Set the working department for this employee.
	 *
	 * @param workingDepartment The department to affect him to.
	 */
	public void setWorkingDepartment(StandardDepartment workingDepartment)
	{
		this.workingDepartment.set(workingDepartment);
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.writeInt(getID());
		oos.writeObject(getWorkingDepartment());
		oos.writeObject(getArrivalTime());
		oos.writeObject(getDepartureTime());
		oos.writeInt(workingDays.size());
		for(DayOfWeek workingDay : workingDays)
			oos.writeObject(workingDay);
		oos.writeInt(checks.size());
		for(CheckInOut check : checks)
			oos.writeObject(check);
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
	{
		ID = ois.readInt();
		workingDepartment = new SimpleObjectProperty<>((StandardDepartment) ois.readObject());
		arrivalTime = new SimpleObjectProperty<>((LocalTime) ois.readObject());
		departureTime = new SimpleObjectProperty<>((LocalTime) ois.readObject());
		
		workingDays = FXCollections.observableArrayList();
		int wkdCount = ois.readInt();
		for(int i = 0; i < wkdCount; i++)
			workingDays.add((DayOfWeek) ois.readObject());
		
		checks = FXCollections.observableArrayList();
		int chkCount = ois.readInt();
		for(int i = 0; i < wkdCount; i++)
			checks.add((CheckInOut) ois.readObject());
	}
}
