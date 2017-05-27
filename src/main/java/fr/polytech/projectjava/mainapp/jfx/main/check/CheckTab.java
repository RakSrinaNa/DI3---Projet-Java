package fr.polytech.projectjava.mainapp.jfx.main.check;

import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.jfx.main.MainController;
import javafx.scene.Node;
import javafx.scene.control.*;
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
	 *
	 * @return The root node.
	 */
	private Node buildContent(MainController controller)
	{
		VBox root = new VBox();
		
		HBox controls = new HBox();
		
		departmentFilter = new ComboBox<>();
		Callback<ListView<StandardDepartment>, ListCell<StandardDepartment>> standardDepartmentCellFactory = new Callback<ListView<StandardDepartment>, ListCell<StandardDepartment>>()
		{
			@Override
			public ListCell<StandardDepartment> call(ListView<StandardDepartment> param)
			{
				return new ListCell<StandardDepartment>()
				{
					@Override
					protected void updateItem(StandardDepartment item, boolean empty)
					{
						super.updateItem(item, empty);
						if(item == null || empty)
							setText(null);
						else
							setText(item.getID() + ": " + item.getName());
					}
				};
			}
		};
		departmentFilter.setButtonCell(standardDepartmentCellFactory.call(null));
		departmentFilter.setCellFactory(standardDepartmentCellFactory);
		departmentFilter.setMaxWidth(Double.MAX_VALUE);
		
		employeeFilter = new ComboBox<>();
		Callback<ListView<Employee>, ListCell<Employee>> employeeCellFactory = new Callback<ListView<Employee>, ListCell<Employee>>()
		{
			@Override
			public ListCell<Employee> call(ListView<Employee> param)
			{
				return new ListCell<Employee>()
				{
					@Override
					protected void updateItem(Employee item, boolean empty)
					{
						super.updateItem(item, empty);
						if(item == null || empty)
							setText(null);
						else
							setText(item.getID() + ": " + item.getFullName());
					}
				};
			}
		};
		employeeFilter.setButtonCell(employeeCellFactory.call(null));
		employeeFilter.setCellFactory(employeeCellFactory);
		employeeFilter.setMaxWidth(Double.MAX_VALUE);
		
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
		
		controls.getChildren().addAll(employeeFilter, startDate, endDate, departmentFilter, addCheckButton, removeCheckButton);
		HBox.setHgrow(employeeFilter, Priority.SOMETIMES);
		HBox.setHgrow(startDate, Priority.SOMETIMES);
		HBox.setHgrow(endDate, Priority.SOMETIMES);
		HBox.setHgrow(departmentFilter, Priority.SOMETIMES);
		HBox.setHgrow(addCheckButton, Priority.ALWAYS);
		HBox.setHgrow(removeCheckButton, Priority.ALWAYS);
		
		checksList = new CheckList(controller, departmentFilter.getSelectionModel().selectedItemProperty(), employeeFilter.getSelectionModel().selectedItemProperty(), startDate.valueProperty(), endDate.valueProperty());
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
