package fr.polytech.projectjava.company.checking;

import org.junit.Test;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.assertEquals;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class CheckingDateTest
{
	@Test
	public void getTimeDifferenceAsMilliseconds() throws Exception
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 20);
		calendar.set(Calendar.SECOND, 30);
		calendar.set(Calendar.MILLISECOND, 40);
		
		CheckingDate checkingDate = new CheckingDate(calendar.getTime());
		assertEquals(12030040, checkingDate.getTimeDifferenceAsMilliseconds(Time.valueOf("09:00:00")));
		assertEquals(-2369960, checkingDate.getTimeDifferenceAsMilliseconds(Time.valueOf("13:00:00")));
	}
}