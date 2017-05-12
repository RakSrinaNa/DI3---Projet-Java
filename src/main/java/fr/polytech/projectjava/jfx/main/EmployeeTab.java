package fr.polytech.projectjava.jfx.main;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 12/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-12
 */
public class EmployeeTab extends Tab
{
	private EmployeeList employeesList;
	
	public EmployeeTab(MainController controller)
	{
		setText("Employees");
		setContent(buildContent(controller));
	}
	
	private Node buildContent(MainController controller)
	{
		VBox root = new VBox();
		
		employeesList = new EmployeeList(controller);
		
		root.getChildren().addAll(employeesList);
		return root;
	}
	
	public EmployeeList getList()
	{
		return employeesList;
	}
}
