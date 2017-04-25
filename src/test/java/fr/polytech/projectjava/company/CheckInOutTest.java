package fr.polytech.projectjava.company;

import fr.polytech.projectjava.company.checking.CheckInOut;
import org.junit.Test;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.assertEquals;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-27
 */
public class CheckInOutTest
{
	@Test
	public void getCheckDate() throws Exception
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 10);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		CheckInOut check1 = new CheckInOut(CheckInOut.CheckType.IN, calendar.getTime());
		assertEquals(new Time(calendar.getTimeInMillis()).toLocalTime(), check1.getTime());
		
		calendar.set(Calendar.HOUR_OF_DAY, 10);
		calendar.set(Calendar.MINUTE, 5);
		CheckInOut check2 = new CheckInOut(CheckInOut.CheckType.IN, calendar.getTime());
		calendar.set(Calendar.MINUTE, 0);
		assertEquals(new Time(calendar.getTimeInMillis()).toLocalTime(), check2.getTime());
		
		calendar.set(Calendar.HOUR_OF_DAY, 10);
		calendar.set(Calendar.MINUTE, 10);
		CheckInOut check3 = new CheckInOut(CheckInOut.CheckType.IN, calendar.getTime());
		calendar.set(Calendar.MINUTE, 15);
		assertEquals(new Time(calendar.getTimeInMillis()).toLocalTime(), check3.getTime());
		
		calendar.set(Calendar.HOUR_OF_DAY, 10);
		calendar.set(Calendar.MINUTE, 55);
		CheckInOut check4 = new CheckInOut(CheckInOut.CheckType.IN, calendar.getTime());
		calendar.set(Calendar.HOUR_OF_DAY, 11);
		calendar.set(Calendar.MINUTE, 0);
		assertEquals(new Time(calendar.getTimeInMillis()).toLocalTime(), check4.getTime());
	}
	
	@Test
	public void getCheckType() throws Exception
	{
		CheckInOut check1 = new CheckInOut(CheckInOut.CheckType.IN);
		CheckInOut check2 = new CheckInOut(CheckInOut.CheckType.IN, new Date());
		CheckInOut check3 = new CheckInOut(CheckInOut.CheckType.OUT, new Date());
		
		assertEquals(CheckInOut.CheckType.IN, check1.getCheckType());
		assertEquals(CheckInOut.CheckType.IN, check2.getCheckType());
		assertEquals(CheckInOut.CheckType.OUT, check3.getCheckType());
	}
}