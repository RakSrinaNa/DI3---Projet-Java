package fr.polytech.projectjava.company;

import org.junit.Test;
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
		calendar.set(Calendar.HOUR, 10);
		calendar.set(Calendar.MINUTE, 0);
		
		CheckInOut check1 = new CheckInOut(CheckInOut.CheckType.IN, calendar.getTime());
		assertEquals(calendar.getTime().getTime(), check1.getCheckDate().getTime(), 0);
		
		calendar.set(Calendar.HOUR, 10);
		calendar.set(Calendar.MINUTE, 5);
		CheckInOut check2 = new CheckInOut(CheckInOut.CheckType.IN, calendar.getTime());
		calendar.set(Calendar.MINUTE, 0);
		assertEquals(calendar.getTime().getTime(), check2.getCheckDate().getTime(), 0);
		
		calendar.set(Calendar.HOUR, 10);
		calendar.set(Calendar.MINUTE, 10);
		CheckInOut check3 = new CheckInOut(CheckInOut.CheckType.IN, calendar.getTime());
		calendar.set(Calendar.MINUTE, 15);
		assertEquals(calendar.getTime().getTime(), check3.getCheckDate().getTime(), 0);
		
		calendar.set(Calendar.HOUR, 10);
		calendar.set(Calendar.MINUTE, 55);
		CheckInOut check4 = new CheckInOut(CheckInOut.CheckType.IN, calendar.getTime());
		calendar.set(Calendar.HOUR, 11);
		calendar.set(Calendar.MINUTE, 0);
		assertEquals(calendar.getTime().getTime(), check4.getCheckDate().getTime(), 0);
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