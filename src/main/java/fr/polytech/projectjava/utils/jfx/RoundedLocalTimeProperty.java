package fr.polytech.projectjava.utils.jfx;

import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalTime;

/**
 * Property for a rounded time property.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-25
 */
public class RoundedLocalTimeProperty extends SimpleObjectProperty<LocalTime>
{
	
	/**
	 * Constructor.
	 */
	public RoundedLocalTimeProperty()
	{
		this(null);
	}
	
	/**
	 * Constructor.
	 *
	 * @param localTime The initial value.
	 */
	public RoundedLocalTimeProperty(LocalTime localTime)
	{
		super(localTime);
	}
	
	@Override
	public LocalTime get()
	{
		return roundTime(super.get());
	}
	
	/**
	 * Round a time to the closest quarter of time.
	 *
	 * @param time The time to round.
	 *
	 * @return The rounded time.
	 */
	public static LocalTime roundTime(LocalTime time)
	{
		if(time == null)
			return null;
		int mins = 15 * (time.getMinute() / 15) + ((time.getMinute() % 15) <= 7 ? 0 : 15); //Compute the rounded minutes
		return LocalTime.of(time.getHour() + mins / 60, mins % 60);
	}
	
	/**
	 * Get the not rounded time.
	 *
	 * @return The real time.
	 */
	public LocalTime getRealTime()
	{
		return super.get();
	}
}
