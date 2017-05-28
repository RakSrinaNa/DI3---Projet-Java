package fr.polytech.projectjava.mainapp.jfx.employee;

import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
import fr.polytech.projectjava.mainapp.jfx.MainController;
import fr.polytech.projectjava.utils.jfx.MinutesDuration;
import fr.polytech.projectjava.utils.jfx.NameTextFieldTableCell;
import fr.polytech.projectjava.utils.jfx.SortedTableView;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;
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
	 * @param controller The main controller.
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

		TableColumn<Employee, Number> columnID = new TableColumn<>("ID");
		columnID.setCellValueFactory(value -> new SimpleIntegerProperty(value.getValue().getID()));
		columnID.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));

		TableColumn<Employee, String> columnFirstName = new TableColumn<>("First Name");
		columnFirstName.setEditable(true);
		columnFirstName.setCellFactory(list -> new NameTextFieldTableCell<>(Employee::isValidState));
		columnFirstName.setCellValueFactory(value -> value.getValue().firstNameProperty());
		columnFirstName.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));

		TableColumn<Employee, String> columnLastName = new TableColumn<>("Last Name");
		columnLastName.setEditable(true);
		columnLastName.setCellFactory(list -> new NameTextFieldTableCell<>(Employee::isValidState));
		columnLastName.setCellValueFactory(value -> value.getValue().lastNameProperty());
		columnLastName.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));

		TableColumn<Employee, StandardDepartment> columnDepartment = new TableColumn<>("Working department");
		columnDepartment.setEditable(true);
		columnDepartment.setCellFactory(list -> new DepartmentComboBoxTableCell(controller.getCompany().getDepartements()));
		columnDepartment.setCellValueFactory(value -> value.getValue().workingDepartmentProperty());
		columnDepartment.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		columnDepartment.setOnEditCommit(controller::employeeDepartmentChanged);

		TableColumn<Employee, MinutesDuration> columnTime = new TableColumn<>("Overtime");
		columnTime.setCellValueFactory(value -> value.getValue().lateDurationProperty());
		columnTime.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));

		TableColumn<Employee, Boolean> columnPresence = new TableColumn<>("Presence");
		columnPresence.setCellValueFactory(value -> value.getValue().isPresentProperty());
		columnPresence.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));

		TableColumn<Employee, String> columnCategory = new TableColumn<>("Is manager");
		columnCategory.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCategory()));
		columnCategory.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));

		setRowFactory(tv -> {
			TableRow<Employee> row = new TableRow<>();
			row.setOnMouseClicked(evt -> {
				if(evt.getButton() == MouseButton.SECONDARY && row.getItem() instanceof Manager)
				{
					ContextMenu contextMenu = new ContextMenu();
					MenuItem menuPromote = new MenuItem("Promote to manager");
					menuPromote.setOnAction(evt2 -> controller.promoteEmployee(evt2, row.getItem()));
					contextMenu.getItems().add(menuPromote);
					contextMenu.show(this, evt.getScreenX(), evt.getScreenY());
				}
			});
			return row;
		});

		//noinspection unchecked
		getColumns().

				addAll(columnID, columnFirstName, columnLastName, columnDepartment, columnTime, columnPresence, columnCategory);
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
