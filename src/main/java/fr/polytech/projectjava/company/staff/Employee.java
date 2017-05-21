package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.checking.EmployeeCheck;
import fr.polytech.projectjava.company.departments.StandardDepartment;
import javafx.beans.property.SimpleBooleanProperty;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import static java.time.temporal.ChronoUnit.SECONDS;

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
	private ObservableList<EmployeeCheck> checks = FXCollections.observableArrayList();
	private ObservableList<DayOfWeek> workingDays = FXCollections.observableArrayList();
	private SimpleObjectProperty<Duration> lateDuration;
	private SimpleBooleanProperty isPresent;
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
		this.lateDuration = new SimpleObjectProperty<>(Duration.ZERO);
		workingDepartment = new SimpleObjectProperty<>(null);
		isPresent = new SimpleBooleanProperty(false);
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
		boolean found = false;
		for(EmployeeCheck check : checks)
			if(check.isDateOf(checkInOut))
			{
				check.setInOut(checkInOut);
				found = true;
				break;
			}
		if(!found)
			checks.add(new EmployeeCheck(checkInOut));
		updateOvertime(null);
		updatePresence();
	}
	
	/**
	 * Update the presence of the employee based on the checks.
	 */
	private void updatePresence()
	{
		EmployeeCheck lastCheck = null;
		for(EmployeeCheck check : checks)
			if(lastCheck == null || lastCheck.getDate().isBefore(check.getDate()))
				lastCheck = check;
		if(lastCheck != null)
			isPresent.set(lastCheck.isInProgress());
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
	 * @param maxDate The maximum date to check for the times. If null, the current time will be used.
	 *
	 * @return The number of minutes overtime.
	 *
	 * @throws IllegalStateException If the checks are in an invalid state (more than 2 checks a day or 2 times the same type of check).
	 */
	public long updateOvertime(LocalDate maxDate) throws IllegalStateException
	{
		if(maxDate == null)
			maxDate = new Date(System.currentTimeMillis()).toLocalDate();
		
		Map<LocalDate, EmployeeCheck> checksByDate = checks.stream().collect(Collectors.toMap(EmployeeCheck::getDate, Function.identity()));
		LocalDate currentDate = checksByDate.keySet().stream().sorted(Comparator.naturalOrder()).findFirst().orElseGet(() -> new Date(System.currentTimeMillis()).toLocalDate());
		Duration overtime = Duration.ZERO;
		while(currentDate.compareTo(maxDate) <= 0)
		{
			if(checksByDate.containsKey(currentDate))
			{
				overtime = overtime.plus(checksByDate.get(currentDate).getWorkedTime()).minus(getWorkTimeForDay(currentDate.getDayOfWeek()));
			}
			else
			{
				overtime = overtime.minus(getWorkTimeForDay(currentDate.getDayOfWeek()));
			}
			currentDate = currentDate.plusDays(1);
		}
		
		
		lateDuration.set(overtime);
		return overtime.toMinutes();
	}
	
	/**
	 * Get the duration the employee should work for this day.
	 *
	 * @param dayOfWeek The day of the week concerned.
	 *
	 * @return The duration to work.
	 */
	private Duration getWorkTimeForDay(DayOfWeek dayOfWeek)
	{
		if(workingDays.contains(dayOfWeek))
			return Duration.of(getDepartureTime().toSecondOfDay() - getArrivalTime().toSecondOfDay(), SECONDS);
		return Duration.ZERO;
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
	
	/**
	 * Get the arrival time property.
	 *
	 * @return The arrival time property.
	 */
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
	public ObservableList<EmployeeCheck> getChecks()
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
	
	/**
	 * Get the departure time property.
	 *
	 * @return The departure time property.
	 */
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
	
	/**
	 * Get the working department property.
	 *
	 * @return The working department property.
	 */
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
		oos.writeObject(getWorkingDepartment());
		oos.writeObject(getArrivalTime());
		oos.writeObject(getDepartureTime());
		oos.writeInt(workingDays.size());
		for(DayOfWeek workingDay : workingDays)
			oos.writeObject(workingDay);
		oos.writeInt(checks.size());
		for(EmployeeCheck check : checks)
			oos.writeObject(check);
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
		ID = ois.readInt();
		NEXT_ID = Math.max(ID, NEXT_ID);
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
			checks.add((EmployeeCheck) ois.readObject());
		
		lateDuration = new SimpleObjectProperty<>(Duration.ZERO);
		isPresent = new SimpleBooleanProperty(false);
		
		updateOvertime(null);
		updatePresence();
	}
}
