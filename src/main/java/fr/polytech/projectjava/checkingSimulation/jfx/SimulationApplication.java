package fr.polytech.projectjava.checkingSimulation.jfx;

import fr.polytech.projectjava.checkingSimulation.Employee;
import fr.polytech.projectjava.checkingSimulation.jfx.components.CheckList;
import fr.polytech.projectjava.checkingSimulation.jfx.components.TimePicker;
import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.utils.ApplicationBase;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class SimulationApplication extends ApplicationBase
{
	private final static int MILLISECONDS_QUARTER = 900000;
	private SimulationController controller;
	private CheckList checkList;
	private SimpleStringProperty currentTime = new SimpleStringProperty("");
	private SimpleStringProperty roundedTime = new SimpleStringProperty("");
	private Timeline currentTimeTimeline;
	
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
		
		StackPane times = new StackPane();
		Label currentTime = new Label();
		currentTime.textProperty().bind(this.currentTime);
		
		Label roundedTime = new Label();
		roundedTime.textProperty().bind(this.roundedTime);
		
		times.getChildren().addAll(currentTime, roundedTime);
		StackPane.setAlignment(currentTime, Pos.CENTER_LEFT);
		StackPane.setAlignment(roundedTime, Pos.CENTER_RIGHT);
		
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
		
		Button sendButton = new Button("Check I/O");
		sendButton.setMaxWidth(Double.MAX_VALUE);
		sendButton.setOnAction(evt -> controller.sendInfos(evt, employeeField.getSelectionModel().getSelectedItem(), typeField.getSelectionModel().getSelectedItem(), dateField.getValue(), timePicker.getTime()));
		
		Button refreshButton = new Button("Refresh employees");
		refreshButton.setMaxWidth(Double.MAX_VALUE);
		refreshButton.setOnAction(evt -> controller.refreshEmployees());
		
		root.getChildren().addAll(times, inputs, checkList, sendButton, refreshButton);
		
		VBox.setVgrow(checkList, Priority.ALWAYS);
		return root;
	}
	
	private void startTimeUpdated()
	{
		currentTimeTimeline = new Timeline(new KeyFrame(Duration.seconds(0), actionEvent -> {
			Calendar calendar = Calendar.getInstance();
			int hours = calendar.get(Calendar.HOUR_OF_DAY);
			int minutes = calendar.get(Calendar.MINUTE);
			int seconds = calendar.get(Calendar.SECOND);
			currentTime.set((hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds);
			
			Date time = calendar.getTime();
			long quarterMillis = time.getTime() % MILLISECONDS_QUARTER;
			time.setTime(time.getTime() - quarterMillis);
			if(quarterMillis >= MILLISECONDS_QUARTER / 2)
				time.setTime(time.getTime() + MILLISECONDS_QUARTER);
			calendar.setTime(time);
			
			hours = calendar.get(Calendar.HOUR_OF_DAY);
			minutes = calendar.get(Calendar.MINUTE);
			roundedTime.set((hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes);
		}), new KeyFrame(Duration.seconds(1)));
		currentTimeTimeline.setCycleCount(Animation.INDEFINITE);
		currentTimeTimeline.play();
	}
	
	@Override
	public String getFrameTitle()
	{
		return "CheckingSimulation";
	}
	
	@Override
	public Consumer<Stage> getOnStageDisplayed() throws Exception
	{
		return stage -> {
			startTimeUpdated();
		};
	}
	
	@Override
	public Consumer<Stage> getStageHandler()
	{
		return stage -> stage.setOnCloseRequest(event -> {
			if(controller.close(event))
				currentTimeTimeline.stop();
		});
	}
}
