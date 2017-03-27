package fr.polytech.projectjava;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-27
 */
public class InvalidArgumentExceptionTest
{
	@Test
	public void reasonTest() throws Exception
	{
		String reason = "A reason.";
		InvalidArgumentException exception = new InvalidArgumentException(reason);
		assertEquals(reason, exception.getMessage());
	}
}