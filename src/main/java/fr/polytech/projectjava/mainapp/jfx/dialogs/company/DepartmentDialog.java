package fr.polytech.projectjava.mainapp.jfx.dialogs.company;

import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A dialog displaying a department.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class DepartmentDialog extends Stage
{
	private final DepartmentDialogController controller;
	
	/**
	 * Constructor.
	 *
	 * @param department The department to display.
	 */
	public DepartmentDialog(StandardDepartment department)
	{
		super();
		controller = new DepartmentDialogController(this);
		setTitle("StandardDepartment - " + department.getName());
		setScene(new Scene(buildStage(department)));
		sizeToScene();
	}
	
	/**
	 * Build the content.
	 *
	 * @param department The department to display.
	 *
	 * @return The root element.
	 */
	private Parent buildStage(StandardDepartment department)
	{
		VBox root = new VBox();
		
		root.getChildren().addAll();
		
		return root;
	}
}
