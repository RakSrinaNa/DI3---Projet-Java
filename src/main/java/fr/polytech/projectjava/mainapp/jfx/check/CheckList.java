package fr.polytech.projectjava.mainapp.jfx.check;

import com.sun.javafx.scene.control.skin.TableHeaderRow;
import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck;
import fr.polytech.projectjava.mainapp.jfx.MainController;
import fr.polytech.projectjava.utils.jfx.SortedTableView;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Predicate;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 24/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-24
 */
public class CheckList extends SortedTableView<EmployeeCheck>
{
	private final SimpleObjectProperty<Predicate<EmployeeCheck>> filterRule;

	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 * @param departmentFilterProperty The selection of the department.
	 * @param employeeFilterProperty The selection of the employee.
	 * @param startDateProperty The selection of the starting date.
	 * @param endDateProperty The selection of the ending date.
	 * @param presenceProperty The selection of the presence filter.
	 */
	public CheckList(MainController controller, ReadOnlyObjectProperty<StandardDepartment> departmentFilterProperty, ReadOnlyObjectProperty<Employee> employeeFilterProperty, ObjectProperty<LocalDate> startDateProperty, ObjectProperty<LocalDate> endDateProperty, BooleanProperty presenceProperty)
	{
		super();

		filterRule = new SimpleObjectProperty<>(check -> true);
		final SimpleObjectProperty<Predicate<EmployeeCheck>> startDateFilter = new SimpleObjectProperty<>(check -> true);
		final SimpleObjectProperty<Predicate<EmployeeCheck>> endDateFilter = new SimpleObjectProperty<>(check -> true);
		final SimpleObjectProperty<Predicate<EmployeeCheck>> departmentFilter = new SimpleObjectProperty<>(check -> true);
		final SimpleObjectProperty<Predicate<EmployeeCheck>> employeeFilter = new SimpleObjectProperty<>(check -> true);
		final SimpleObjectProperty<Predicate<EmployeeCheck>> presenceFilter = new SimpleObjectProperty<>(check -> true);

		InvalidationListener refreshFilters = observable -> filterRule.set(startDateFilter.get().and(endDateFilter.get()).and(departmentFilter.get()).and(employeeFilter.get()).and(presenceFilter.get())); //Refresh the global filter when one of the sub rules change
		startDateFilter.addListener(refreshFilters);
		endDateFilter.addListener(refreshFilters);
		departmentFilter.addListener(refreshFilters);
		employeeFilter.addListener(refreshFilters);
		presenceFilter.addListener(refreshFilters);

		departmentFilterProperty.addListener(((observable, oldValue, newValue) -> {
			if(newValue == null)
				departmentFilter.set(check -> true);
			else
				departmentFilter.set(check -> check.getEmployee().getWorkingDepartment().equals(newValue));
		}));

		employeeFilterProperty.addListener(((observable, oldValue, newValue) -> {
			if(newValue == null)
				employeeFilter.set(check -> true);
			else
				employeeFilter.set(check -> check.getEmployee().equals(newValue));
		}));

		startDateProperty.addListener(((observable, oldValue, newValue) -> {
			if(newValue == null)
				startDateFilter.set(check -> true);
			else
				startDateFilter.set(check -> check.getDate().isAfter(newValue) || check.getDate().equals(newValue));
		}));

		endDateProperty.addListener(((observable, oldValue, newValue) -> {
			if(newValue == null)
				endDateFilter.set(check -> true);
			else
				endDateFilter.set(check -> check.getDate().isBefore(newValue) || check.getDate().equals(newValue));
		}));

		presenceProperty.addListener(((observable, oldValue, newValue) -> {
			if(!newValue)
				endDateFilter.set(check -> true);
			else
				endDateFilter.set(EmployeeCheck::isInProgress);
		}));

		int colCount = 4;
		int padding = 2;

		setEditable(true);

		TableColumn<EmployeeCheck, Employee> columnEmployee = new TableColumn<>("Employee");
		columnEmployee.setCellValueFactory(value -> value.getValue().employeeProperty());
		columnEmployee.setCellFactory(list -> new EmployeeTableCell());
		columnEmployee.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));

		TableColumn<EmployeeCheck, LocalDate> columnDate = new TableColumn<>("Date");
		columnDate.setCellValueFactory(value -> value.getValue().dateProperty());
		columnDate.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));

		TableColumn<EmployeeCheck, LocalTime> columnArrival = new TableColumn<>("Arrival");
		columnArrival.setEditable(true);
		columnArrival.setCellFactory(list -> new CheckLocalTimeTextFieldTableCell());
		columnArrival.setCellValueFactory(value -> value.getValue().checkInProperty());
		columnArrival.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));

		TableColumn<EmployeeCheck, LocalTime> columnDeparture = new TableColumn<>("Departure");
		columnDeparture.setEditable(true);
		columnDeparture.setCellFactory(list -> new CheckLocalTimeTextFieldTableCell());
		columnDeparture.setCellValueFactory(value -> value.getValue().checkOutProperty());
		columnDeparture.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));

		//noinspection unchecked
		getColumns().addAll(columnEmployee, columnDate, columnArrival, columnDeparture);
		skinProperty().addListener((obs, oldSkin, newSkin) -> {
			final TableHeaderRow header = (TableHeaderRow) lookup("TableHeaderRow");
			header.reorderingProperty().addListener((o, oldVal, newVal) -> header.setReordering(false));
		});
	}

	/**
	 * Set the list of this table.
	 *
	 * @param list The list to set.
	 */
	@Override
	public void setList(ObservableList<EmployeeCheck> list)
	{
		FilteredList<EmployeeCheck> filteredItems = new FilteredList<>(list);
		filteredItems.predicateProperty().bind(filterRule);
		super.setList(filteredItems);
	}
}
