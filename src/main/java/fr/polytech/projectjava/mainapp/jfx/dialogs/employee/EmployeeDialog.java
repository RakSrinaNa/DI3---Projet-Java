package fr.polytech.projectjava.mainapp.jfx.dialogs.employee;

import fr.polytech.projectjava.mainapp.company.staff.Employee;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A dialog displaying an employee.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class EmployeeDialog extends Stage
{
	private final EmployeeDialogController controller;
	
	/**
	 * Constructor.
	 *
	 * @param employee The employee to display.
	 */
	public EmployeeDialog(Employee employee)
	{
		super();
		controller = new EmployeeDialogController(this);
		setTitle("Employee - " + employee.getFullName());
		setScene(new Scene(buildStage(employee)));
		sizeToScene();
	}
	
	/**
	 * Build the content.
	 *
	 * @param employee The employee to display.
	 *
	 * @return The root element.
	 */
	private Parent buildStage(Employee employee)
	{
		VBox root = new VBox();
		
		root.getChildren().addAll();
		
		return root;
	}
}
