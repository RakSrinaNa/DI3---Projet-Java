package fr.polytech.projectjava.mainapp.jfx.employee;

import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.jfx.MainController;
import fr.polytech.projectjava.utils.jfx.RefreshableListCell;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * Represent the employee tab in the main window.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 12/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-12
 */
public class EmployeeTab extends Tab
{
	private EmployeeList employeesList;
	private ComboBox<StandardDepartment> departmentFilter;
	
	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 */
	public EmployeeTab(MainController controller)
	{
		setText("Employees");
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
		departmentFilter = new ComboBox<>();
		//Display departments as a string and not hashcode
		Callback<ListView<StandardDepartment>, ListCell<StandardDepartment>> standardDepartmentCellFactory = param -> new RefreshableListCell<>(StandardDepartment::nameProperty);
		departmentFilter.setButtonCell(standardDepartmentCellFactory.call(null));
		departmentFilter.setCellFactory(standardDepartmentCellFactory);
		departmentFilter.setMaxWidth(Double.MAX_VALUE);
		departmentFilter.setOnKeyPressed(evt -> {
			if(evt.getCode() == KeyCode.SPACE && evt.isControlDown())
			{
				((ComboBox)evt.getSource()).getSelectionModel().clearSelection();
				evt.consume();
			}
		});
		departmentFilter.setOnMouseClicked(evt -> {
			if(evt.getButton() == MouseButton.PRIMARY && evt.isControlDown())
			{
				((ComboBox)evt.getSource()).getSelectionModel().clearSelection();
				evt.consume();
			}
		});

		Button addEmployeeButton = new Button("Add employee");
		addEmployeeButton.setOnAction(controller::addEmployee);
		addEmployeeButton.setMaxWidth(Double.MAX_VALUE);

		Button removeEmployeeButton = new Button("Remove employee");
		removeEmployeeButton.setOnAction(evt -> controller.removeEmployee(evt, getList().getSelectionModel().getSelectedItem()));
		removeEmployeeButton.setMaxWidth(Double.MAX_VALUE);
		
		controls.getChildren().addAll(departmentFilter, addEmployeeButton, removeEmployeeButton);
		HBox.setHgrow(departmentFilter, Priority.SOMETIMES);
		HBox.setHgrow(addEmployeeButton, Priority.ALWAYS);
		HBox.setHgrow(removeEmployeeButton, Priority.ALWAYS);

		employeesList = new EmployeeList(controller, departmentFilter.getSelectionModel().selectedItemProperty());
		employeesList.setMaxHeight(Double.MAX_VALUE);
		
		root.getChildren().addAll(employeesList, controls);
		VBox.setVgrow(employeesList, Priority.ALWAYS);
		return root;
	}
	
	/**
	 * Get the department filter combobox.
	 *
	 * @return The department combobox.
	 */
	public ComboBox<StandardDepartment> getDepartmentFilter()
	{
		return departmentFilter;
	}
	
	/**
	 * Get the employee list.
	 *
	 * @return The employee list.
	 */
	public EmployeeList getList()
	{
		return employeesList;
	}
}
