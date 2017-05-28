package fr.polytech.projectjava.mainapp.jfx.check.create;

import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck;
import fr.polytech.projectjava.utils.jfx.TimePicker;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Dialog window to create a check.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class CheckCreateDialog extends Stage
{
	private final CheckCreateDialogController controller;
	private EmployeeCheck result;
	private ComboBox<Employee> employeeField;
	private TimePicker arrivalTime;
	private TimePicker departureTime;
	private DatePicker datePicker;
	
	/**
	 * Constructor.
	 *
	 * @param employees The employees of the company.
	 */
	public CheckCreateDialog(ObservableList<Employee> employees)
	{
		super();
		controller = new CheckCreateDialogController(this);
		result = null;
		setTitle("Check creation");
		setScene(new Scene(buildStage(employees)));
		sizeToScene();
	}
	
	/**
	 * Build the window elements.
	 *
	 * @param employees The employees of the company.
	 *
	 * @return The root element.
	 */
	private Parent buildStage(ObservableList<Employee> employees)
	{
		VBox root = new VBox(2);
		
		employeeField = new ComboBox<>();
		employeeField.setItems(employees);
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
		employeeField.setButtonCell(employeeCellFactory.call(null));
		employeeField.setCellFactory(employeeCellFactory);
		employeeField.setMaxWidth(Double.MAX_VALUE);
		
		datePicker = new DatePicker();
		datePicker.setMaxWidth(Double.MAX_VALUE);
		
		HBox arrivalBox = new HBox();
		Label arrivalLabel = new Label("Arrival: ");
		arrivalTime = new TimePicker();
		arrivalBox.getChildren().addAll(arrivalLabel, arrivalTime);
		HBox.setHgrow(arrivalTime, Priority.ALWAYS);
		
		HBox departureBox = new HBox();
		Label departureLabel = new Label("Departure: ");
		departureTime = new TimePicker();
		departureBox.getChildren().addAll(departureLabel, departureTime);
		HBox.setHgrow(departureTime, Priority.ALWAYS);
		
		Button valid = new Button("OK");
		valid.setMaxWidth(Double.MAX_VALUE);
		valid.setOnAction(controller::valid);
		
		root.getChildren().addAll(employeeField, datePicker, arrivalBox, departureBox, valid);
		return root;
	}
	
	/**
	 * Get the selected date.
	 *
	 * @return The selected date.
	 */
	public LocalDate getDate()
	{
		return datePicker.getValue();
	}
	
	/**
	 * Get the selected employee.
	 *
	 * @return The selected employee.
	 */
	public Employee getEmployee()
	{
		return employeeField.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * Get the selected arrival time.
	 *
	 * @return The selected arrival time.
	 */
	public LocalTime getInTime()
	{
		return arrivalTime.getTime();
	}
	
	/**
	 * Get the selected departure time.
	 *
	 * @return The selected departure time.
	 */
	public LocalTime getOutTime()
	{
		return departureTime.getTime();
	}
	
	/**
	 * Get the built check.
	 *
	 * @return The check.
	 */
	public EmployeeCheck getResult()
	{
		return result;
	}
	
	/**
	 * Set the built check.
	 *
	 * @param check The built check.
	 */
	void setResult(EmployeeCheck check)
	{
		result = check;
	}
}
