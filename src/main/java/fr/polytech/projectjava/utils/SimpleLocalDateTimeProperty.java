package fr.polytech.projectjava.utils;

import javafx.beans.property.SimpleStringProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 06/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-06
 */
public class SimpleLocalDateTimeProperty extends SimpleStringProperty
{
	private final DateTimeFormatter formatter;
	private LocalDateTime date;
	
	public SimpleLocalDateTimeProperty(LocalDateTime date, DateTimeFormatter formatter)
	{
		super(formatter.format(date));
		this.date = date;
		this.formatter = formatter;
	}
	
	@Override
	@Deprecated
	public void set(String newValue)
	{
		super.set(newValue);
	}
	
	public void set(LocalDateTime newValue)
	{
		super.set(formatter.format(newValue));
		date = newValue;
	}
	
	public LocalDateTime getDate()
	{
		return date;
	}
}
