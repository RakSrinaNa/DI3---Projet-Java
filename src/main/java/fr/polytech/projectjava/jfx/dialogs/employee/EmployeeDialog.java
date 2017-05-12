package fr.polytech.projectjava.jfx.dialogs.employee;

import fr.polytech.projectjava.company.staff.Employee;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class EmployeeDialog extends Stage
{
	private final EmployeeDialogController controller;
	
	public EmployeeDialog(Employee employee)
	{
		super();
		controller = new EmployeeDialogController(this);
		setTitle("Employee - " + employee.getFullName());
		setScene(new Scene(buildStage(employee)));
		sizeToScene();
	}
	
	private Parent buildStage(Employee employee)
	{
		VBox root = new VBox();
		
		root.getChildren().addAll();
		
		return root;
	}
}
