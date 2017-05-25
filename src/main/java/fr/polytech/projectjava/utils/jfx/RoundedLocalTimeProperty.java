package fr.polytech.projectjava.utils.jfx;

import fr.polytech.projectjava.mainapp.company.staff.Employee;
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
	
	private final Employee employee;
	
	/**
	 * Constructor.
	 *
	 * @param employee The employee where the check time come from.
	 */
	public RoundedLocalTimeProperty(Employee employee)
	{
		this(employee, null);
	}
	
	/**
	 * Constructor.
	 *
	 * @param employee  The employee where the check time come from.
	 * @param localTime The initial value.
	 */
	public RoundedLocalTimeProperty(Employee employee, LocalTime localTime)
	{
		super(localTime);
		this.employee = employee;
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
		return LocalTime.of(time.getHour(), 15 * (time.getMinute() / 15) + ((time.getMinute() % 15) <= 7 ? 0 : 15));
	}
	
	@Override
	public void set(LocalTime newValue)
	{
		super.set(newValue);
		if(employee != null)
		{
			employee.updateOvertime(null);
			employee.updatePresence();
		}
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
