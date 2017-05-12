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
	
	public ListEmployeesDialog(Company company)
	{
		super();
		controller = new ListEmployeesDialogController(this);
		setTitle("Employees");
		setScene(new Scene(buildStage(company)));
		sizeToScene();
	}
	
	private Parent buildStage(Company company)
	{
		VBox root = new VBox();
		
		EmployeeList employeeList = new EmployeeList(company.getEmployees(), controller);
		
		root.getChildren().addAll(employeeList);
		VBox.setVgrow(employeeList, Priority.ALWAYS);
		
		return root;
	}
}
