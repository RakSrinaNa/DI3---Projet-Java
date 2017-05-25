package fr.polytech.projectjava.mainapp.jfx.main.check.create;

import fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck;
import fr.polytech.projectjava.utils.Log;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

/**
 * Controller for the check dialog.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class CheckCreateDialogController
{
	private final CheckCreateDialog view;
	
	/**
	 * Constructor.
	 *
	 * @param view The parent view.
	 */
	public CheckCreateDialogController(CheckCreateDialog view)
	{
		this.view = view;
	}
	
	/**
	 * Verify if the check is a valid one.
	 * If it is the case, it builds it and closes the popup.
	 *
	 * @param actionEvent The click event.
	 */
	public void valid(ActionEvent actionEvent)
	{
		if(view.getEmployee() != null && view.getDate() != null)
		{
			if(view.getEmployee().hasCheckForDate(view.getDate()))
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Check cannot be added");
				alert.setHeaderText("Check cannot be added");
				alert.setContentText("This employee already have a check for this date, please edit it instead");
				alert.showAndWait();
				return;
			}
			try
			{
				EmployeeCheck check = new EmployeeCheck(view.getEmployee(), view.getDate());
				if(view.getInTime() != null)
					check.setIn(view.getInTime());
				if(view.getOutTime() != null)
					check.setOut(view.getOutTime());
				view.setResult(check);
				view.close();
			}
			catch(IllegalArgumentException e)
			{
				Log.warning("Error creating the check", e);
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Check cannot be added");
				alert.setHeaderText("Check cannot be added");
				alert.setContentText("This check isn't in a valid state");
				alert.showAndWait();
			}
		}
		else
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Check cannot be added");
			alert.setHeaderText("Check cannot be added");
			alert.setContentText("You need to at least select an employee and a date");
			alert.showAndWait();
		}
	}
}
