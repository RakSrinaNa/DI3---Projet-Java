package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.departments.StandardDepartment;
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
public class Employee extends Person
{
	protected final static LocalTime DEFAULT_ARRIVAL_TIME = Time.valueOf("08:30:00").toLocalTime();
	protected final static LocalTime DEFAULT_DEPARTURE_TIME = Time.valueOf("17:30:00").toLocalTime();
	private static final long serialVersionUID = -8611138931676775765L;
	private final int ID;
	private final ArrayList<CheckInOut> checks = new ArrayList<>();
	private final ArrayList<DayOfWeek> workingDays = new ArrayList<>();
	protected static int NEXT_ID = 0;
	private StandardDepartment workingDepartment;
	private LocalTime arrivalTime;
	private LocalTime departureTime;
	
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
		this.arrivalTime = arrivalTime;
		this.departureTime = departureTIme;
	}
	
	@Override
	public String toString()
	{
		return super.toString() + "\nID: \t" + getID() + "\nDpt: \t" + getWorkingDepartment();
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
				total -= arrivalTime.until(departureTime, MINUTES);
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
		if(arrivalTime.isAfter(departureTime))
			throw new IllegalArgumentException("Arrival time can't be after the departure time.");
		this.arrivalTime = arrivalTime;
	}
	
	/**
	 * Get the list of checking the employee did.
	 *
	 * @return A list of the checking.
	 */
	public ArrayList<CheckInOut> getChecks()
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
		if(arrivalTime.isAfter(this.departureTime))
			throw new IllegalArgumentException("Arrival time can't be after the departure time.");
		this.departureTime = departureTime;
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
		return workingDepartment;
	}
	
	/**
	 * Set the working department for this employee.
	 *
	 * @param workingDepartment The department to affect him to.
	 */
	public void setWorkingDepartment(StandardDepartment workingDepartment)
	{
		this.workingDepartment = workingDepartment;
	}
}
