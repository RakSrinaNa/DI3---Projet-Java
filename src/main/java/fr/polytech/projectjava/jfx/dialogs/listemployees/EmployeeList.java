package fr.polytech.projectjava.jfx.dialogs.listemployees;

import fr.polytech.projectjava.checkingSimulation.CheckInfos;
import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.staff.Employee;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class EmployeeList extends TableView<Employee>
{
	public EmployeeList(ObservableList<Employee> employees)
	{
		super();
		setEditable(true);
		
		TableColumn<Employee, Number> columnEmployeeID = new TableColumn<>("ID");
		columnEmployeeID.setCellValueFactory(value -> value.getValue().IDProperty());
		
		TableColumn<Employee, String> columnEmployeeName = new TableColumn<>("Name");
		columnEmployeeName.setCellValueFactory(value -> value.getValue().firstNameProperty().concat(" ").concat(value.getValue().lastNameProperty()));
		
		TableColumn<Employee, String> columnCheckType = new TableColumn<>("Working department");
		columnCheckType.setCellValueFactory(value -> value.getValue().workingDepartmentProperty().get().nameProperty());
		
		TableColumn<CheckInfos, String> columnDate = new TableColumn<>("Date");
		columnDate.setCellValueFactory(value -> value.getValue().dateProperty());
		
		columnEmployee.getColumns().addAll(columnEmployeeID, columnEmployeeName);
		getColumns().addAll(columnEmployee, columnCheckType, columnDate);
		
		setSortPolicy(p -> false);
		setItems(checkings);
		
		Platform.runLater(() -> {
			setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		});
	}
}
