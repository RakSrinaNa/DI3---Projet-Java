package fr.polytech.projectjava.utils.jfx;

import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Property to modify a StringProperty depending on a date.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 06/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-06
 */
public class SimpleLocalDateTimeProperty extends SimpleStringProperty
{
	private final DateTimeFormatter formatter;
	private LocalDateTime date;
	
	/**
	 * Constructor.
	 *
	 * @param date      The default date to set.
	 * @param formatter The formatter to use with the date.
	 */
	public SimpleLocalDateTimeProperty(LocalDateTime date, DateTimeFormatter formatter)
	{
		super(formatter.format(date));
		this.date = date;
		this.formatter = formatter;
	}
	
	/**
	 * Set the date of the property.
	 *
	 * @param newValue The new date to set.
	 */
	public void set(LocalDateTime newValue)
	{
		super.set(formatter.format(newValue));
		date = newValue;
	}
	
	/**
	 * Get the date of the property.
	 *
	 * @return The date.
	 */
	public LocalDateTime getDate()
	{
		return date;
	}
}
