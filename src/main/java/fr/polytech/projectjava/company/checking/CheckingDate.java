package fr.polytech.projectjava.company.checking;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Represent the date the employee checked at.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class CheckingDate extends Date
{
	/**
	 * Constructor.
	 *
	 * @param date The date of the event.
	 */
	public CheckingDate(Date date)
	{
		super(date.getTime());
	}
	
	/**
	 * Get the difference in milliseconds between this date and the given time. This doesn't take the day into account.
	 *
	 * @param time The time to compare to.
	 *
	 * @return The difference in milliseconds.
	 */
	public long getTimeDifferenceAsMilliseconds(Time time)
	{
		Calendar thisCalendar = Calendar.getInstance();
		thisCalendar.setTime(this);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time.getTime());
		calendar.set(Calendar.YEAR, thisCalendar.get(Calendar.YEAR));
		calendar.set(Calendar.MONTH, thisCalendar.get(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, thisCalendar.get(Calendar.DAY_OF_MONTH));
		
		return getTime() - calendar.getTimeInMillis();
	}
}
