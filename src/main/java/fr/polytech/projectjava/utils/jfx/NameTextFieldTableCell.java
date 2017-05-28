package fr.polytech.projectjava.utils.jfx;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-25
 */
public class NameTextFieldTableCell<T> extends TextFieldTableCell<T, String>
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
