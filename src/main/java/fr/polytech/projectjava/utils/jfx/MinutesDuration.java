package fr.polytech.projectjava.utils.jfx;

import java.io.Serializable;

/**
 * JavaFX MinutesDuration with custom toString.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 24/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-24
 */
public class MinutesDuration implements Serializable
{
	private static final long serialVersionUID = 5819372112167246368L;
	public final static MinutesDuration ZERO = new MinutesDuration(0);
	private final long minutes;
	
	/**
	 * Constructor.
	 * Create a duration of 0.
	 */
	public MinutesDuration()
	{
		this(0);
	}
	
	/**
	 * Constructor.
	 *
	 * @param minutes The initial amount of minutes.
	 */
	public MinutesDuration(long minutes)
	{
		this.minutes = minutes;
	}
	
	@Override
	public String toString()
	{
		return getMinutes() + "m";
	}
	
	/**
	 * Create a new MinutesDuration with a number of seconds. The minutes will be rounded down.
	 *
	 * @param seconds The number of seconds.
	 *
	 * @return The constructed MinutesDuration.
	 */
	public static MinutesDuration seconds(long seconds)
	{
		return new MinutesDuration(seconds / 60);
	}
	
	/**
	 * Add a duration to this one.
	 *
	 * @param duration The duration to add.
	 *
	 * @return A new object of the two durations added.
	 */
	public MinutesDuration add(MinutesDuration duration)
	{
		return new MinutesDuration(getMinutes() + duration.getMinutes());
	}
	
	/**
	 * Substract a duration from this one.
	 *
	 * @param duration The duration to substract.
	 *
	 * @return A new object of the two durations substracted.
	 */
	public MinutesDuration substract(MinutesDuration duration)
	{
		return new MinutesDuration(getMinutes() - duration.getMinutes());
	}
	
	/**
	 * Get the minutes.
	 *
	 * @return The duration in minutes.
	 */
	public long getMinutes()
	{
		return minutes;
	}
}
