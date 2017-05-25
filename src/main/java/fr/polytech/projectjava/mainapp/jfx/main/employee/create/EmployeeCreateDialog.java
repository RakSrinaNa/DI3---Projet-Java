package fr.polytech.projectjava.mainapp.jfx.main.employee.create;

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
public class EmployeeCreateDialog extends Stage
{
	private final EmployeeCreateDialogController controller;
	
	/**
	 * Constructor.
	 */
	public EmployeeCreateDialog()
	{
		super();
		controller = new EmployeeCreateDialogController(this);
		setTitle("Create employee");
		setScene(new Scene(buildStage()));
		sizeToScene();
	}
	
	/**
	 * Build the content.
	 *
	 * @return The root element.
	 */
	private Parent buildStage()
	{
		VBox root = new VBox();
		
		root.getChildren().addAll();
		
		return root;
	}
}
