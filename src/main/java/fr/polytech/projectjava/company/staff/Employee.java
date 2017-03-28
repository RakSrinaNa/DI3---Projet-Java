package fr.polytech.projectjava.company.staff;

import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.departments.StandardDepartment;
import java.sql.Time;
import java.util.ArrayList;

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
	protected final static Time DEFAULT_ARRIVAL_TIME = Time.valueOf("08:30:00");
	protected final static Time DEFAULT_DEPARTURE_TIME = Time.valueOf("17:30:00");
	private final static int MILLISECONDS_IN_MINUTE = 60000;
	private final int ID;
	private final ArrayList<CheckInOut> checks = new ArrayList<>();
	protected static int NEXT_ID = 0;
	private StandardDepartment workingDepartment;
	private Time arrivalTime;
	private Time departureTime;
	
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
	public Employee(String lastName, String firstName, Time arrivalTime, Time departureTIme) throws IllegalArgumentException
	{
		super(lastName, firstName);
		if(arrivalTime.after(departureTIme))
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
	 * Get the arrival time of this employee.
	 *
	 * @return The arrival time.
	 */
	public Time getArrivalTime()
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
	public void setArrivalTime(Time arrivalTime) throws IllegalArgumentException
	{
		if(arrivalTime.after(departureTime))
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
	public Time getDepartureTime()
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
	public void setDepartureTime(Time departureTime) throws IllegalArgumentException
	{
		if(arrivalTime.after(this.departureTime))
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
	 * Get the number of minutes the employee done more.
	 *
	 * @return The number of minutes. If the number is negative it represents the number of minutes dues.
	 */
	public long getOverMinutes()
	{
		return checks.parallelStream().mapToLong(check ->
		{
			if(check.getCheckType() == CheckInOut.CheckType.IN)
				return -check.getCheckDate().getTimeDifferenceAsMilliseconds(getArrivalTime()) / MILLISECONDS_IN_MINUTE;
			return check.getCheckDate().getTimeDifferenceAsMilliseconds(getDepartureTime()) / MILLISECONDS_IN_MINUTE;
		}).sum();
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
