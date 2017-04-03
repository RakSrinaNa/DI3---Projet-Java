package fr.polytech.projectjava.company.checking;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
	private static final long serialVersionUID = 5823365881731658606L;
	
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
	 * Get the difference between this date and the given time. This doesn't take the day into account.
	 *
	 * @param time The time to compare to.
	 * @param unit The unit of the time to return.
	 *
	 * @return The difference in milliseconds.
	 */
	public long getTimeDifference(Time time, TimeUnit unit)
	{
		Calendar thisCalendar = Calendar.getInstance();
		thisCalendar.setTime(this);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time.getTime());
		calendar.set(Calendar.YEAR, thisCalendar.get(Calendar.YEAR));
		calendar.set(Calendar.MONTH, thisCalendar.get(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, thisCalendar.get(Calendar.DAY_OF_MONTH));
		
		return unit.convert(getTime() - calendar.getTimeInMillis(), TimeUnit.MILLISECONDS);
	}
}
