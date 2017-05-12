package fr.polytech.projectjava.jfx.dialogs.listemployees;

import fr.polytech.projectjava.company.staff.Employee;
import fr.polytech.projectjava.jfx.dialogs.employee.EmployeeDialog;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class ListEmployeesDialogController
{
	private final ListEmployeesDialog parent;
	
	public ListEmployeesDialogController(ListEmployeesDialog parent)
	{
		this.parent = parent;
	}
	
	public void employeeClick(MouseEvent event)
	{
		if(event.getSource() instanceof TableRow)
		{
			TableRow source = (TableRow) event.getSource();
			if(source.getItem() instanceof Employee)
			{
				Employee employee = (Employee) source.getItem();
				if(event.getButton() == MouseButton.PRIMARY)
				{
					((TableRow) event.getSource()).getScene();
					EmployeeDialog dialog = new EmployeeDialog(employee);
					dialog.initOwner(((TableRow) event.getSource()).getScene().getWindow());
					dialog.initModality(Modality.APPLICATION_MODAL);
					dialog.showAndWait();
				}
			}
		}
	}
}
