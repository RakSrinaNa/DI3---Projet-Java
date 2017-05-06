package fr.polytech.projectjava.checkingSimulation.jfx;

import fr.polytech.projectjava.checkingSimulation.Employee;
import fr.polytech.projectjava.checkingSimulation.jfx.components.CheckList;
import fr.polytech.projectjava.checkingSimulation.jfx.components.TimePicker;
import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.utils.ApplicationBase;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.util.function.Consumer;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class SimulationApplication extends ApplicationBase
{
	private SimulationController controller;
	private CheckList checkList;
	
	@Override
	public void preInit() throws Exception
	{
		super.preInit();
		controller = new SimulationController();
	}
	
	@Override
	public Parent createContent(Stage stage)
	{
		VBox root = new VBox();
		
		HBox inputs = new HBox();
		
		ComboBox<Employee> employeeField = new ComboBox<>();
		employeeField.setItems(controller.getModel().getEmployeeList());
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
							setText(item.getID() + ": " + item.getName());
					}
				};
			}
		};
		employeeField.setButtonCell(employeeCellFactory.call(null));
		employeeField.setCellFactory(employeeCellFactory);
		
		ComboBox<CheckInOut.CheckType> typeField = new ComboBox<>();
		typeField.getItems().addAll(CheckInOut.CheckType.values());
		typeField.getSelectionModel().selectFirst();
		DatePicker dateField = new DatePicker();
		TimePicker timePicker = new TimePicker();
		
		inputs.getChildren().addAll(employeeField, typeField, dateField, timePicker);
		
		checkList = new CheckList(controller.getModel().getCheckings());
		checkList.setMaxWidth(Double.MAX_VALUE);
		checkList.setMaxHeight(Double.MAX_VALUE);
		
		Button sendButton = new Button("Send");
		sendButton.setMaxWidth(Double.MAX_VALUE);
		sendButton.setOnAction(evt -> controller.sendInfos(evt, employeeField.getSelectionModel().getSelectedItem(), typeField.getSelectionModel().getSelectedItem(), dateField.getValue(), timePicker.getTime()));
		
		Button refreshButton = new Button("Refresh employees");
		refreshButton.setMaxWidth(Double.MAX_VALUE);
		refreshButton.setOnAction(evt -> controller.refreshEmployees());
		
		root.getChildren().addAll(inputs, checkList, sendButton, refreshButton);
		
		VBox.setVgrow(checkList, Priority.ALWAYS);
		return root;
	}
	
	@Override
	public String getFrameTitle()
	{
		return "CheckingSimulation";
	}
	
	@Override
	public Consumer<Stage> getOnStageDisplayed() throws Exception
	{
		return null;
	}
	
	@Override
	public Consumer<Stage> getStageHandler()
	{
		return stage -> stage.setOnCloseRequest(controller::close);
	}
}
