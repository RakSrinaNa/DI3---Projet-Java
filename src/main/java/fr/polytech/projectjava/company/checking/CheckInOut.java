package fr.polytech.projectjava.company.checking;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
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
		
		long quarterMillis = date.getTime() % MILLISECONDS_QUARTER;
		date.setTime(date.getTime() - quarterMillis);
		if(quarterMillis >= MILLISECONDS_QUARTER / 2)
			date.setTime(date.getTime() + MILLISECONDS_QUARTER);
		
		checkDate = new java.sql.Date(date.getTime()).toLocalDate();
		checkTime = new java.sql.Time(date.getTime()).toLocalTime();
	}
	
	@Override
	public String toString()
	{
		return "Check " + checkType + " at " + checkDate;
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
	
	public LocalDate getDay()
	{
		return checkDate;
	}
	
	public LocalTime getTime()
	{
		return checkTime;
	}
}
