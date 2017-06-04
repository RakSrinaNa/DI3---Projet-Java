package fr.polytech.projectjava.mainapp.jfx.check;

import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.Person;
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
 * Represent the checks tab in the main window.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 12/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-12
 */
public class CheckTab extends Tab
{
	private CheckList checksList;
	private ComboBox<StandardDepartment> departmentFilter;
	private ComboBox<Employee> employeeFilter;
	
	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 */
	public CheckTab(MainController controller)
	{
		setText("Checks");
		setContent(buildContent(controller));
	}

	/**
	 * Build the tab content.
	 *
	 * @param controller The main controller.
	 * @return The root node.
	 */
	private Node buildContent(MainController controller)
	{
		VBox root = new VBox();

		HBox controls = new HBox();
		
		CheckBox inProgressFilter = new CheckBox("In progress");

		departmentFilter = new ComboBox<>();
		//Display departments as string instead of their hashcode
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

		employeeFilter = new ComboBox<>();
		//Display employees as string instead of their hashcode
		Callback<ListView<Employee>, ListCell<Employee>> employeeCellFactory = param -> new RefreshableListCell<>(Person::fullNameProperty);
		employeeFilter.setButtonCell(employeeCellFactory.call(null));
		employeeFilter.setCellFactory(employeeCellFactory);
		employeeFilter.setMaxWidth(Double.MAX_VALUE);
		employeeFilter.setOnKeyPressed(evt -> {
			if(evt.getCode() == KeyCode.SPACE && evt.isControlDown())
			{
				((ComboBox)evt.getSource()).getSelectionModel().clearSelection();
				evt.consume();
			}
		});
		employeeFilter.setOnMouseClicked(evt -> {
			if(evt.getButton() == MouseButton.PRIMARY && evt.isControlDown())
			{
				((ComboBox)evt.getSource()).getSelectionModel().clearSelection();
				evt.consume();
			}
		});

		DatePicker startDate = new DatePicker();
		startDate.setMaxWidth(Double.MAX_VALUE);

		DatePicker endDate = new DatePicker();
		endDate.setMaxWidth(Double.MAX_VALUE);

		Button addCheckButton = new Button("Add check");
		addCheckButton.setOnAction(controller::addCheck);
		addCheckButton.setMaxWidth(Double.MAX_VALUE);

		Button removeCheckButton = new Button("Remove check");
		removeCheckButton.setOnAction(evt -> controller.removeCheck(evt, checksList));
		removeCheckButton.setMaxWidth(Double.MAX_VALUE);

		controls.getChildren().addAll(inProgressFilter, employeeFilter, startDate, endDate, departmentFilter, addCheckButton, removeCheckButton);
		HBox.setHgrow(employeeFilter, Priority.SOMETIMES);
		HBox.setHgrow(startDate, Priority.SOMETIMES);
		HBox.setHgrow(endDate, Priority.SOMETIMES);
		HBox.setHgrow(departmentFilter, Priority.SOMETIMES);
		HBox.setHgrow(addCheckButton, Priority.ALWAYS);
		HBox.setHgrow(removeCheckButton, Priority.ALWAYS);

		checksList = new CheckList(controller, departmentFilter.getSelectionModel().selectedItemProperty(), employeeFilter.getSelectionModel().selectedItemProperty(), startDate.valueProperty(), endDate.valueProperty(), inProgressFilter.selectedProperty());
		checksList.setMaxHeight(Double.MAX_VALUE);

		root.getChildren().addAll(checksList, controls);
		VBox.setVgrow(checksList, Priority.ALWAYS);
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
	 * Get the employee filter combobox.
	 *
	 * @return The employee combobox.
	 */
	public ComboBox<Employee> getEmployeeFilter()
	{
		return employeeFilter;
	}

	/**
	 * Get the check list.
	 *
	 * @return The check list.
	 */
	public CheckList getList()
	{
		return checksList;
	}
}
