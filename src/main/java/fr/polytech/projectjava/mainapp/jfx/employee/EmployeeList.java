package fr.polytech.projectjava.mainapp.jfx.employee;

import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.jfx.MainController;
import fr.polytech.projectjava.utils.jfx.MinutesDuration;
import fr.polytech.projectjava.utils.jfx.SortedTableView;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import java.time.LocalTime;
import java.util.function.Predicate;

/**
 * Represent a list of employees.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class EmployeeList extends SortedTableView<Employee>
{
	private final SimpleObjectProperty<Predicate<? super Employee>> filterRule;
	
	/**
	 * Constructor.
	 *
	 * @param controller               The main controller.
	 * @param departmentFilterProperty The standard department to filter.
	 */
	public EmployeeList(MainController controller, ReadOnlyObjectProperty<StandardDepartment> departmentFilterProperty)
	{
		super();
		
		int colCount = 7;
		int padding = 2;
		
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
		columnEmployeeID.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<Employee, String> columnEmployeeName = new TableColumn<>("Name");
		columnEmployeeName.setCellValueFactory(value -> value.getValue().fullNameProperty());
		columnEmployeeName.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<Employee, String> columnEmployeeDepartment = new TableColumn<>("Working department");
		columnEmployeeDepartment.setCellValueFactory(value -> value.getValue().workingDepartmentProperty().get().nameProperty());
		columnEmployeeDepartment.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<Employee, MinutesDuration> columnTime = new TableColumn<>("Overtime");
		columnTime.setCellValueFactory(value -> value.getValue().lateDurationProperty());
		columnTime.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<Employee, Boolean> columnPresence = new TableColumn<>("Presence");
		columnPresence.setCellValueFactory(value -> value.getValue().isPresentProperty());
		columnPresence.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<Employee, LocalTime> columnArrival = new TableColumn<>("Arrival");
		columnArrival.setCellValueFactory(value -> value.getValue().arrivalTimeProperty());
		columnArrival.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<Employee, LocalTime> columnDeparture = new TableColumn<>("Departure");
		columnDeparture.setCellValueFactory(value -> value.getValue().departureTimeProperty());
		columnDeparture.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		//noinspection unchecked
		getColumns().addAll(columnEmployeeID, columnEmployeeName, columnEmployeeDepartment, columnTime, columnPresence);
		
		this.setRowFactory(tv -> {
			TableRow<Employee> row = new TableRow<>();
			row.setOnMouseClicked(controller::employeeClick);
			return row;
		});
	}
	
	/**
	 * Set the list of this table.
	 *
	 * @param list The list to set.
	 */
	@Override
	public void setList(ObservableList<Employee> list)
	{
		FilteredList<Employee> filteredItems = new FilteredList<>(list);
		filteredItems.predicateProperty().bind(filterRule);
		super.setList(filteredItems);
	}
}
