package fr.polytech.projectjava.jfx.dialogs.listemployees;

import fr.polytech.projectjava.company.Company;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class ListEmployeesDialog extends Stage
{
	private final ListEmployeesDialogController controller;
	private EmployeeList employeeList;
	
	public ListEmployeesDialog(Company company)
	{
		super();
		controller = new ListEmployeesDialogController(this);
		setTitle("Employees");
		setScene(new Scene(buildStage()));
		sizeToScene();
	}
	
	private Parent buildStage()
	{
		VBox root = new VBox();
		
		employeeList = new EmployeeList();
		
		root.getChildren().addAll(employeeList);
		VBox.setVgrow(employeeList, Priority.ALWAYS);
		
		return root;
	}
}
