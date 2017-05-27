package fr.polytech.projectjava.mainapp.jfx.main.department;

import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
import javafx.collections.ObservableList;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-25
 */
public class ManagerComboBoxTableCell extends javafx.scene.control.cell.ComboBoxTableCell<StandardDepartment, Manager>
{
	/**
	 * Constructor.
	 */
	public ManagerComboBoxTableCell(ObservableList<Manager> items)
	{
		super(null, items);
	}
	
	@Override
	public void updateItem(Manager item, boolean empty)
	{
		super.updateItem(item, empty);
		if(!empty && item == null)
			getTableRow().setStyle("-fx-background-color: #FF0000;");
		else
			getTableRow().setStyle(null);
	}
}
