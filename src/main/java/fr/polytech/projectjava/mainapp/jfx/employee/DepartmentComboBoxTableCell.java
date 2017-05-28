package fr.polytech.projectjava.mainapp.jfx.employee;

import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.utils.jfx.ObjectComboBoxTableCell;
import javafx.collections.ObservableList;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-25
 */
public class DepartmentComboBoxTableCell extends ObjectComboBoxTableCell<Employee, StandardDepartment>
{
	/**
	 * Constructor.
	 *
	 * @param items The managers of the company.
	 */
	public DepartmentComboBoxTableCell(ObservableList<StandardDepartment> items)
	{
		super(null, items, StandardDepartment::nameProperty);
	}
	
	@Override
	public void updateItem(StandardDepartment item, boolean empty)
	{
		super.updateItem(item, empty);
		if(!empty && getTableRow().getItem() != null && !((Employee) getTableRow().getItem()).isValidState())
			getTableRow().setStyle("-fx-background-color: #FF0000;");
		else
			getTableRow().setStyle(null);
	}
}
