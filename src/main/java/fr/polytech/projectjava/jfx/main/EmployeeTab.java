package fr.polytech.projectjava.jfx.main;

import fr.polytech.projectjava.company.departments.StandardDepartment;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
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
		HBox root = new HBox(10);
		
		VBox controls = new VBox();
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
		
		controls.getChildren().addAll(departmentFilter);
		
		employeesList = new EmployeeList(controller, departmentFilter.getSelectionModel().selectedItemProperty());
		employeesList.setMaxHeight(Double.MAX_VALUE);
		
		root.getChildren().addAll(employeesList, controls);
		HBox.setHgrow(employeesList, Priority.ALWAYS);
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
