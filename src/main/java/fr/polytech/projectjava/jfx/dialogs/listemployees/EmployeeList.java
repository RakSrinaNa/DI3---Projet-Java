package fr.polytech.projectjava.jfx.dialogs.listemployees;

import fr.polytech.projectjava.company.staff.Employee;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class EmployeeList extends TableView<Employee>
{
	public EmployeeList(ObservableList<Employee> employees, ListEmployeesDialogController controller)
	{
		super();
		setEditable(true);
		
		TableColumn<Employee, Number> columnEmployeeID = new TableColumn<>("ID");
		columnEmployeeID.setCellValueFactory(value -> new SimpleIntegerProperty(value.getValue().getID()));
		
		TableColumn<Employee, String> columnEmployeeName = new TableColumn<>("Name");
		columnEmployeeName.setCellValueFactory(value -> value.getValue().fullNameProperty());
		
		TableColumn<Employee, String> columnEmployeeDepartment = new TableColumn<>("Working department");
		columnEmployeeDepartment.setCellValueFactory(value -> value.getValue().workingDepartmentProperty().get().nameProperty());
		
		getColumns().addAll(columnEmployeeID, columnEmployeeName, columnEmployeeDepartment);
		
		this.setRowFactory(tv ->
		{
			TableRow<Employee> row = new TableRow<>();
			row.setOnMouseClicked(controller::employeeClick);
			return row ;
		});
		
		setSortPolicy(p -> false);
		setItems(employees);
		
		Platform.runLater(() -> {
			setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		});
	}
}
