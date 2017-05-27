package fr.polytech.projectjava.mainapp.company.staff.checking;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Represent a check-in.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-27
 */
public class CheckInOut implements Serializable
{
	private static final long serialVersionUID = 2956226284028169226L;
	private final static int MILLISECONDS_QUARTER = 900000;
	private final LocalDate checkDate;
	private final LocalTime checkTime;
	private final CheckType checkType;
	
	/**
	 * Enumeration of the different types of checks possible.
	 */
	public enum CheckType
	{
		IN, OUT
	}
	
	/**
	 * Construct a checking at the current date.
	 *
	 * @param checkType The check type.
	 */
	public CheckInOut(CheckType checkType)
	{
		this(checkType, new Date());
	}
	
	/**
	 * Create a checking with a date and a type.
	 *
	 * @param checkType The check type.
	 * @param date      The date it happened.
	 */
	public CheckInOut(CheckType checkType, Date date)
	{
		this.checkType = checkType;
		checkDate = new java.sql.Date(date.getTime()).toLocalDate();
		checkTime = new java.sql.Time(date.getTime()).toLocalTime();
	}
	
	/**
	 * Create a checking with a date, time and a type.
	 *
	 * @param type The check type.
	 * @param date The date it happened.
	 * @param time The time it happened.
	 */
	public CheckInOut(CheckType type, LocalDate date, LocalTime time)
	{
		this(type, Date.from(LocalDateTime.of(date, time).atZone(ZoneId.systemDefault()).toInstant()));
	}
	
	@Override
	public String toString()
	{
		return checkTime.toString();
	}
	
	/**
	 * Get the type of the checking.
	 *
	 * @return Its type.
	 */
	public CheckType getCheckType()
	{
		return checkType;
	}
	
	/**
	 * Get the day of the check.
	 *
	 * @return The day.
	 */
	public LocalDate getDay()
	{
		return checkDate;
	}
	
	/**
	 * Get the time of the check.
	 *
	 * @return The time.
	 */
	public LocalTime getTime()
	{
		return checkTime;
	}
}
