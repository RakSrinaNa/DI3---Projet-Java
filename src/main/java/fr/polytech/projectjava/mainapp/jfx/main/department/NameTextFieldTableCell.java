package fr.polytech.projectjava.mainapp.jfx.main.department;

import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-25
 */
public class NameTextFieldTableCell extends TextFieldTableCell<StandardDepartment, String>
{
	/**
	 * Constructor.
	 */
	public NameTextFieldTableCell()
	{
		super(new DefaultStringConverter());
	}
	
	@Override
	public void updateItem(String item, boolean empty)
	{
		super.updateItem(item, empty);
		if(!empty && item != null && item.equals(""))
			getTableRow().setStyle("-fx-background-color: #FF0000;");
		else
			getTableRow().setStyle(null);
	}
}
