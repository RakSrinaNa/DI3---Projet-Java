package fr.polytech.projectjava.mainapp.company.staff;

import fr.polytech.projectjava.utils.jfx.RoundedLocalTimeProperty;
import java.time.LocalTime;

/**
 * Property for a rounded time property.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-25
 */
public class EmployeeRoundedLocalTimeProperty extends RoundedLocalTimeProperty
{
	
	private final Employee employee;
	
	/**
	 * Constructor.
	 *
	 * @param employee The employee where the check time come from.
	 */
	public EmployeeRoundedLocalTimeProperty(Employee employee)
	{
		this(employee, null);
	}
	
	/**
	 * Constructor.
	 *
	 * @param employee  The employee where the check time come from.
	 * @param localTime The initial value.
	 */
	public EmployeeRoundedLocalTimeProperty(Employee employee, LocalTime localTime)
	{
		super(localTime);
		this.employee = employee;
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
}
