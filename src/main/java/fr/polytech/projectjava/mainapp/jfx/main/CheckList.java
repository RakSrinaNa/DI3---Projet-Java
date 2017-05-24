package fr.polytech.projectjava.mainapp.jfx.main;

import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.checking.CheckInOut;
import fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck;
import fr.polytech.projectjava.utils.jfx.SortedTableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import java.time.LocalDate;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 24/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-24
 */
public class CheckList extends SortedTableView<EmployeeCheck>
{
	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 */
	public CheckList(MainController controller)
	{
		super();
		
		int colCount = 4;
		int padding = 2;
		
		setEditable(false);
		
		TableColumn<EmployeeCheck, Employee> columnEmployee = new TableColumn<>("Employee");
		columnEmployee.setCellValueFactory(value -> value.getValue().employeeProperty());
		columnEmployee.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<EmployeeCheck, LocalDate> columnDate = new TableColumn<>("Date");
		columnDate.setCellValueFactory(value -> value.getValue().dateProperty());
		columnDate.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<EmployeeCheck, CheckInOut> columnArrival = new TableColumn<>("Arrival");
		columnArrival.setCellValueFactory(value -> value.getValue().checkInProperty());
		columnArrival.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<EmployeeCheck, CheckInOut> columnDeparture = new TableColumn<>("Departure");
		columnDeparture.setCellValueFactory(value -> value.getValue().checkOutProperty());
		columnDeparture.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		//noinspection unchecked
		getColumns().addAll(columnEmployee, columnDate, columnArrival, columnDeparture);
		
		this.setRowFactory(tv -> {
			TableRow<EmployeeCheck> row = new TableRow<>();
			row.setOnMouseClicked(controller::checkClick);
			return row;
		});
	}
}
