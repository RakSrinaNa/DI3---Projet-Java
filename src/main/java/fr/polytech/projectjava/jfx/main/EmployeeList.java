package fr.polytech.projectjava.jfx.main;

import fr.polytech.projectjava.company.departments.StandardDepartment;
import fr.polytech.projectjava.company.staff.Employee;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import java.time.Duration;
import java.util.function.Predicate;

/**
 * Represent a list of employees.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class EmployeeList extends TableView<Employee>
{
	private SimpleObjectProperty<Predicate<? super Employee>> filterRule;
	
	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 * @param departmentFilterProperty The standard department to filter.
	 */
	public EmployeeList(MainController controller, ReadOnlyObjectProperty<StandardDepartment> departmentFilterProperty)
	{
		super();
		filterRule = new SimpleObjectProperty<>(employee -> true);
		departmentFilterProperty.addListener(((observable, oldValue, newValue) -> {
			if(newValue == null)
				filterRule.set(employee -> true);
			else
				filterRule.set(employee -> employee.getWorkingDepartment().equals(newValue));
		}));
		setEditable(true);
		
		TableColumn<Employee, Number> columnEmployeeID = new TableColumn<>("ID");
		columnEmployeeID.setCellValueFactory(value -> new SimpleIntegerProperty(value.getValue().getID()));
		columnEmployeeID.setSortable(true);
		
		TableColumn<Employee, String> columnEmployeeName = new TableColumn<>("Name");
		columnEmployeeName.setCellValueFactory(value -> value.getValue().fullNameProperty());
		
		TableColumn<Employee, String> columnEmployeeDepartment = new TableColumn<>("Working department");
		columnEmployeeDepartment.setCellValueFactory(value -> value.getValue().workingDepartmentProperty().get().nameProperty());
		
		TableColumn<Employee, Duration> columnTime = new TableColumn<>("Overtime");
		columnTime.setCellValueFactory(value -> value.getValue().lateDurationProperty());
		
		TableColumn<Employee, Boolean> columnPresence = new TableColumn<>("Presence");
		columnPresence.setCellValueFactory(value -> value.getValue().isPresentProperty());
		
		//noinspection unchecked
		getColumns().addAll(columnEmployeeID, columnEmployeeName, columnEmployeeDepartment, columnTime, columnPresence);
		
		this.setRowFactory(tv -> {
			TableRow<Employee> row = new TableRow<>();
			row.setOnMouseClicked(controller::employeeClick);
			return row;
		});
		
		setSortPolicy(p -> true);
		
		Platform.runLater(() -> {
			setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		});
	}
	
	/**
	 * Set the list of this table.
	 *
	 * @param list The list to set.
	 */
	public void setList(ObservableList<Employee> list)
	{
		FilteredList<Employee> filteredItems = new FilteredList<>(list);
		filteredItems.predicateProperty().bind(filterRule);
		SortedList<Employee> sortedItems = new SortedList<>(filteredItems);
		setItems(sortedItems);
		sortedItems.comparatorProperty().bind(comparatorProperty());
	}
}
