package fr.polytech.projectjava.mainapp.company.staff;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck;
import fr.polytech.projectjava.mainapp.company.staff.checking.WorkDay;
import fr.polytech.projectjava.utils.Log;
import fr.polytech.projectjava.utils.jfx.MinutesDuration;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import static fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck.CheckType.IN;

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
	protected final static DayOfWeek[] DEFAULT_WORKING_DAYS = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY};
	protected final static LocalTime DEFAULT_ARRIVAL_TIME = Time.valueOf("08:30:00").toLocalTime();
	protected final static LocalTime DEFAULT_DEPARTURE_TIME = Time.valueOf("17:30:00").toLocalTime();
	private static final long serialVersionUID = -8611138931676775765L;
	protected static int NEXT_ID = 0;
	private int ID;
	private Company company;
	private ObservableList<EmployeeCheck> checks = FXCollections.observableArrayList();
	private ObservableList<WorkDay> workingDays = FXCollections.observableArrayList();
	private SimpleObjectProperty<MinutesDuration> lateDuration;
	private SimpleBooleanProperty isPresent;
	private SimpleObjectProperty<StandardDepartment> workingDepartment;
	
	/**
	 * Constructor used to parse an employee from CSV.
	 *
	 * @param company The company the employee is from.
	 */
	protected Employee(Company company)
	{
		super("", "");
		this.ID = NEXT_ID++;
		this.company = company;
		lateDuration = new SimpleObjectProperty<>(MinutesDuration.ZERO);
		workingDepartment = new SimpleObjectProperty<>(null);
		isPresent = new SimpleBooleanProperty(false);
		company.addEmployee(this);
	}
	
	/**
	 * Create an employee with his/her name and its departure and arrival times.
	 *
	 * @param company       The company the employee is from.
	 * @param lastName      His/her last name.
	 * @param firstName     His/her first name.
	 * @param arrivalTime   The arrival time.
	 * @param departureTIme The departure time.
	 *
	 * @throws IllegalArgumentException If the arrival time is after the departure time.
	 */
	public Employee(Company company, String lastName, String firstName, LocalTime arrivalTime, LocalTime departureTIme) throws IllegalArgumentException
	{
		super(lastName, firstName);
		this.company = company;
		if(arrivalTime.isAfter(departureTIme))
			throw new IllegalArgumentException("Arrival time can't be after the departure time.");
		this.ID = NEXT_ID++;
		this.lateDuration = new SimpleObjectProperty<>(MinutesDuration.ZERO);
		workingDepartment = new SimpleObjectProperty<>(null);
		isPresent = new SimpleBooleanProperty(false);
		for(DayOfWeek day : DEFAULT_WORKING_DAYS)
			workingDays.add(new WorkDay(this, day, arrivalTime, departureTIme));
		updateOvertime(null);
		Log.info("New employee created " + this);
	}
	
	/**
	 * Create an employee with his/her name.
	 *
	 * @param company   The company the employee is from.
	 * @param lastName  His/her last name.
	 * @param firstName His/her first name.
	 *
	 * @throws IllegalArgumentException If the arrival time is after the departure time.
	 */
	public Employee(Company company, String lastName, String firstName) throws IllegalArgumentException
	{
		this(company, lastName, firstName, DEFAULT_ARRIVAL_TIME, DEFAULT_DEPARTURE_TIME);
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
		oos.writeObject(company);
		oos.writeInt(getID());
		oos.writeObject(getWorkingDepartment());
		oos.writeInt(workingDays.size());
		for(WorkDay workingDay : workingDays)
			oos.writeObject(workingDay);
		oos.writeInt(checks.size());
		for(EmployeeCheck check : checks)
			oos.writeObject(check);
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
	public double updateOvertime(LocalDate maxDate) throws IllegalStateException
	{
		if(maxDate == null) //If no max date provided, use the current one.
			maxDate = new Date(System.currentTimeMillis()).toLocalDate();
		
		Map<LocalDate, EmployeeCheck> checksByDate = checks.stream().collect(Collectors.toMap(EmployeeCheck::getDate, Function.identity())); //Map every check to its date
		LocalDate currentDate = checksByDate.keySet().stream().sorted(Comparator.naturalOrder()).findFirst().orElseGet(() -> new Date(System.currentTimeMillis()).toLocalDate()); //Get the oldest day
		MinutesDuration overtime = MinutesDuration.ZERO;
		while(currentDate.compareTo(maxDate) <= 0) //For each day up to the maximum one
		{
			if(checksByDate.containsKey(currentDate)) //If we have a record for this day, add it to the time worked
				overtime = overtime.add(checksByDate.get(currentDate).getWorkedTime());
			overtime = overtime.substract(getWorkTimeForDay(currentDate.getDayOfWeek())); //Remove the time the employee should have worked
			currentDate = currentDate.plusDays(1);
		}
		
		Log.info("New overtime for " + this + ": " + overtime);
		
		lateDuration.set(overtime);
		return overtime.getMinutes();
	}
	
	/**
	 * Get the duration the employee should work for this day.
	 *
	 * @param dayOfWeek The day of the week concerned.
	 *
	 * @return The duration to work.
	 */
	private MinutesDuration getWorkTimeForDay(DayOfWeek dayOfWeek)
	{
		for(WorkDay day : workingDays)
			if(day.getDay().equals(dayOfWeek))
				return day.getWorkTime();
		return MinutesDuration.ZERO;
	}
	
	/**
	 * Read an employee from the CSV.
	 *
	 * @param company The company the employee is from.
	 * @param csv     The CSV parts to read.
	 *
	 * @return The created employee.
	 */
	@SuppressWarnings("UnusedReturnValue")
	public static Employee fromCSV(Company company, Queue<String> csv)
	{
		Employee employee = new Employee(company);
		company.addEmployee(employee);
		employee.parseCSV(csv);
		if(employee.getWorkingDepartment() != null)
			employee.getWorkingDepartment().addEmployee(employee);
		return employee;
	}
	
	/**
	 * Parse the csv to fill the employee fields.
	 *
	 * @param csv The CSV parts to parse.
	 */
	protected void parseCSV(Queue<String> csv)
	{
		super.parseCSV(csv);
		setWorkingDepartment(getCompany().getDepartment(Integer.parseInt(csv.poll())).orElse(null));
		Arrays.stream(csv.poll().split("!")).forEach(day -> addWorkingDay(WorkDay.fromCSV(this, day, "/")));
		if(csv.size() > 0)
			Arrays.stream(csv.poll().split("!")).forEach(check -> addCheck(EmployeeCheck.fromCSV(this, check, "/")));
	}
	
	@Override
	public boolean equals(Object obj)
	{
		return obj instanceof Employee && ID == ((Employee) obj).getID();
	}
	
	/**
	 * Tel if the employee is present or not.
	 *
	 * @return True if present, false if not.
	 */
	public boolean isPresent()
	{
		return isPresentProperty().get();
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
	 * Add a check to the employee.
	 *
	 * @param check he check to add.
	 */
	public void addCheck(EmployeeCheck check)
	{
		if(check != null && !checks.contains(check))
		{
			checks.add(check);
			company.registerCheck(check);
		}
	}
	
	/**
	 * Transform an employee into a CSV form.
	 *
	 * @param delimiter The delimiter to use.
	 *
	 * @return The CSV string.
	 */
	public String asCSV(String delimiter)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(getCategory());
		sb.append(delimiter);
		sb.append(super.asCSV(delimiter));
		sb.append(delimiter);
		sb.append(getWorkingDepartment() == null ? -1 : getWorkingDepartment().getID());
		sb.append(delimiter);
		sb.append(getWorkingDays().stream().map(day -> day.asCSV("/")).collect(Collectors.joining("!")));
		sb.append(delimiter);
		sb.append(getChecks().stream().map(check -> check.asCSV("/")).collect(Collectors.joining("!")));
		return sb.toString();
	}
	
	/**
	 * Get the working days.
	 *
	 * @return The working days.
	 */
	public ObservableList<WorkDay> getWorkingDays()
	{
		return workingDays;
	}
	
	/**
	 * Add a checking to this employee.
	 *
	 * @param checkType The type of the check.
	 * @param date      The date of the check.
	 * @param time      The time of the check.
	 */
	public void addCheckInOut(EmployeeCheck.CheckType checkType, LocalDate date, LocalTime time)
	{
		boolean found = false;
		for(EmployeeCheck check : checks) // Find if there's already a check for this date.
			if(check.getDate().equals(date))
			{
				if(checkType == IN)
					check.setIn(time);
				else
					check.setOut(time);
				
				found = true;
				break;
			}
		if(!found) //Else create it.
			addCheck(new EmployeeCheck(this, checkType, date, time));
		updateOvertime(null);
		updatePresence();
	}
	
	/**
	 * Update the presence of the employee based on the checks.
	 */
	public void updatePresence()
	{
		EmployeeCheck lastCheck = null;
		for(EmployeeCheck check : checks) //Get the last check
			if(lastCheck == null || lastCheck.getDate().isBefore(check.getDate()))
				lastCheck = check;
		if(lastCheck != null)
			isPresent.set(lastCheck.isInProgress());
	}
	
	/**
	 * tell if the employee schedule is valid.
	 *
	 * @return True if valid, false else.
	 */
	public boolean isValidSchedule()
	{
		return workingDays.stream().mapToInt(day -> day.isValid() ? 0 : 1).sum() == 0;
	}
	
	/**
	 * Add a working day for this employee.
	 *
	 * @param day The working day to add.
	 */
	public void addWorkingDay(WorkDay day)
	{
		if(day != null && !workingDays.contains(day))
		{
			workingDays.add(day);
			Log.info(this + " now works on " + day.getDay().name() + " from " + day.getStartTime() + " to " + day.getEndTime());
		}
	}
	
	/**
	 * Remove a working day for this employee.
	 *
	 * @param day The working day to remove.
	 */
	public void removeWorkingDay(WorkDay day)
	{
		workingDays.remove(day);
		Log.info(this + " doesn't work on " + day.getDay() + " anymore");
	}
	
	/**
	 * Remove a working day for this employee.
	 *
	 * @param day The day to remove.
	 */
	public void removeWorkingDay(DayOfWeek day)
	{
		Iterator<WorkDay> dayIterator = workingDays.iterator();
		while(dayIterator.hasNext())
			if(dayIterator.next().getDay().equals(day))
				dayIterator.remove();
		Log.info(this + " doesn't work on " + day + " anymore");
	}
	
	/**
	 * Get the overtime property.
	 *
	 * @return The overtime property.
	 */
	public SimpleObjectProperty<MinutesDuration> lateDurationProperty()
	{
		return lateDuration;
	}
	
	/**
	 * Remove a check from this employee.
	 *
	 * @param check The check to remove.
	 */
	public void removeCheck(EmployeeCheck check)
	{
		checks.remove(check);
		company.unregisterCheck(check);
	}
	
	/**
	 * Tell if this employee have a check for a date.
	 *
	 * @param date The date to look for.
	 *
	 * @return True if the employee have a check on this date, false else.
	 */
	public boolean hasCheckForDate(LocalDate date)
	{
		for(EmployeeCheck check : checks)
			if(check.getDate().equals(date))
				return true;
		return false;
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
		Log.info(this + " now works in " + workingDepartment);
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
		company = (Company) ois.readObject();
		ID = ois.readInt();
		NEXT_ID = Math.max(ID + 1, NEXT_ID); // Don't forget to change the next ID to avoid duplicate IDs.
		workingDepartment = new SimpleObjectProperty<>((StandardDepartment) ois.readObject());
		
		workingDays = FXCollections.observableArrayList();
		int wkdCount = ois.readInt();
		for(int i = 0; i < wkdCount; i++)
			workingDays.add((WorkDay) ois.readObject());
		
		checks = FXCollections.observableArrayList();
		int chkCount = ois.readInt();
		for(int i = 0; i < chkCount; i++)
			checks.add((EmployeeCheck) ois.readObject());
		
		lateDuration = new SimpleObjectProperty<>(MinutesDuration.ZERO);
		isPresent = new SimpleBooleanProperty(false);
		
		updateOvertime(null);
		updatePresence();
	}
	
	/**
	 * Get the presence property.
	 *
	 * @return The presence property.
	 */
	public SimpleBooleanProperty isPresentProperty()
	{
		return isPresent;
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
	 * Tell if the employee is in a valid state.
	 *
	 * @return True if the state is valid, false else.
	 */
	public boolean isValidState()
	{
		return isValidSchedule() && isValidMail() && !getLastName().equals("") && !getFirstName().equals("") && getWorkingDepartment() != null;
	}
	
	/**
	 * Get the category of the employee.
	 *
	 * @return The employee category.
	 */
	public String getCategory()
	{
		return this.getClass().getSimpleName();
	}
	
	/**
	 * Get the company of teh employee.
	 *
	 * @return The company.
	 */
	public Company getCompany()
	{
		return company;
	}
}
