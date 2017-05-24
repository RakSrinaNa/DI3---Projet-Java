package fr.polytech.projectjava.utils.jfx;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import java.time.LocalTime;

/**
 * Fields to input a date.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 24/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-24
 */
public class TimePicker extends HBox
{
	private  boolean changed = false;
	private final NumberField hoursField;
	private final NumberField minutesField;
	
	
	/**
	 * Constructor.
	 */
	public TimePicker()
	{
		super();
		setMaxWidth(Double.MAX_VALUE);
		
		Label separator1 = new Label("H");
		Label separator2 = new Label("M");
		hoursField = new NumberField(0, number -> {
			changed = true;
			return number >= 0 && number <= 23;
		});
		hoursField.setMaxWidth(Double.MAX_VALUE);
		minutesField = new NumberField(0, number -> {
			changed = true;
			return number >= 0 && number <= 59;
		});
		minutesField.setMaxWidth(Double.MAX_VALUE);
		
		getChildren().addAll(hoursField, separator1, minutesField, separator2);
		HBox.setHgrow(hoursField, Priority.SOMETIMES);
		HBox.setHgrow(minutesField, Priority.SOMETIMES);
	}
	
	
	
	/**
	 * Get the time value.
	 *
	 * @return The time.
	 */
	public LocalTime getTime()
	{
		return changed ? LocalTime.of(hoursField.getInt(), minutesField.getInt()) : null;
	}
}
