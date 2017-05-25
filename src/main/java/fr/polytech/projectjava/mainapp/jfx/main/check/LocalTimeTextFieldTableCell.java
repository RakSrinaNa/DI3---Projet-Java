package fr.polytech.projectjava.mainapp.jfx.main.check;

import fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck;
import fr.polytech.projectjava.utils.Log;
import fr.polytech.projectjava.utils.jfx.RoundedLocalTimeProperty;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import java.time.LocalTime;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-25
 */
public class LocalTimeTextFieldTableCell extends TextFieldTableCell<EmployeeCheck, LocalTime>
{
	/**
	 * Constructor.
	 */
	public LocalTimeTextFieldTableCell()
	{
		super(new StringConverter<LocalTime>()
		{
			@Override
			public String toString(LocalTime object)
			{
				return object == null ? "" : object.toString();
			}
			
			@Override
			public LocalTime fromString(String string)
			{
				try
				{
					String[] parts = string.split(":");
					return LocalTime.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
				}
				catch(Exception e)
				{
					Log.warning("Time entered is malformatted");
				}
				return null;
			}
		});
	}
	
	@Override
	public void updateItem(LocalTime item, boolean empty)
	{
		item = RoundedLocalTimeProperty.roundTime(item);
		super.updateItem(item, empty);
		if(getTableRow().getItem() != null && !((EmployeeCheck)getTableRow().getItem()).isValidState())
			getTableRow().setStyle("-fx-background-color: #FF0000;");
		else
			getTableRow().setStyle(null);
	}
}
