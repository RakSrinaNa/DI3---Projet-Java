package fr.polytech.projectjava.checkingSimulation.jfx;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.sql.Time;
import java.time.LocalTime;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class TimePicker extends HBox
{
	private final NumberField hoursField;
	private final NumberField minutesField;
	private final NumberField secondsField;
	
	public TimePicker()
	{
		super();
		Label separator1 = new Label(":");
		Label separator2 = new Label(":");
		hoursField = new NumberField(0, number -> number >= 0 && number <= 23);
		minutesField = new NumberField(0, number -> number >= 0 && number <= 59);
		secondsField = new NumberField(0, number -> number >= 0 && number <= 59);
		
		getChildren().addAll(hoursField, separator1, minutesField, separator2, secondsField);
	}
	
	public LocalTime getTime()
	{
		return Time.valueOf("" + hoursField.getInt() + ":" + minutesField.getInt() + ":" + secondsField.getInt()).toLocalTime();
	}
}
