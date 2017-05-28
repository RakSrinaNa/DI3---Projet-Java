package fr.polytech.projectjava.mainapp.jfx.check;

import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.Person;
import fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck;
import fr.polytech.projectjava.utils.jfx.ObjectTableCell;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-25
 */
public class EmployeeTableCell extends ObjectTableCell<EmployeeCheck, Employee>
{
	/**
	 * Constructor.
	 */
	public EmployeeTableCell()
	{
		super(Person::fullNameProperty);
	}
}
