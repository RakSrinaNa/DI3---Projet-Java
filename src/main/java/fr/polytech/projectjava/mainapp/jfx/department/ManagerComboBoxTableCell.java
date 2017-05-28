package fr.polytech.projectjava.mainapp.jfx.department;

import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
import fr.polytech.projectjava.mainapp.company.staff.Person;
import fr.polytech.projectjava.utils.jfx.ObjectComboBoxTableCell;
import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-25
 */
public class ManagerComboBoxTableCell extends ObjectComboBoxTableCell<StandardDepartment, Manager>
{
	/**
	 * Constructor.
	 *
	 * @param items The managers of the company.
	 */
	public ManagerComboBoxTableCell(ObservableList<Manager> items)
	{
		super(null, items, Person::lastNameProperty);
		InvalidationListener nameChangedListener = observable -> updateItem(getItem(), isEmpty()); //Used to update the displayed value when the employee name is modified
		itemProperty().addListener((observable, oldValue, newValue) -> {
			if(oldValue != null)
				oldValue.fullNameProperty().removeListener(nameChangedListener);
			if(newValue != null)
				newValue.fullNameProperty().addListener(nameChangedListener);
		});
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
