package fr.polytech.projectjava.mainapp.jfx.main.department;

import fr.polytech.projectjava.mainapp.jfx.main.MainController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Represent the department tab in the main window.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 12/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-12
 */
public class DepartmentTab extends Tab
{
	private DepartmentList departmentsList;
	
	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 */
	public DepartmentTab(MainController controller)
	{
		setText("Departments");
		setContent(buildContent(controller));
	}
	
	/**
	 * Build the tab content.
	 *
	 * @param controller The main controller.
	 *
	 * @return The root node.
	 */
	private Node buildContent(MainController controller)
	{
		VBox root = new VBox();
		
		HBox controls = new HBox();
		
		Button addDepartmentButton = new Button("Add department");
		addDepartmentButton.setOnAction(controller::addDepartment);
		addDepartmentButton.setMaxWidth(Double.MAX_VALUE);
		
		Button removeDepartmentButton = new Button("Remove department");
		removeDepartmentButton.setOnAction(evt -> controller.removeDepartment(evt, departmentsList));
		removeDepartmentButton.setMaxWidth(Double.MAX_VALUE);
		
		controls.getChildren().addAll(addDepartmentButton, removeDepartmentButton);
		HBox.setHgrow(addDepartmentButton, Priority.ALWAYS);
		HBox.setHgrow(removeDepartmentButton, Priority.ALWAYS);
		
		departmentsList = new DepartmentList(controller);
		departmentsList.setMaxHeight(Double.MAX_VALUE);
		
		root.getChildren().addAll(departmentsList, controls);
		VBox.setVgrow(departmentsList, Priority.ALWAYS);
		return root;
	}
	
	/**
	 * Get the department list.
	 *
	 * @return The department list.
	 */
	public DepartmentList getList()
	{
		return departmentsList;
	}
}
