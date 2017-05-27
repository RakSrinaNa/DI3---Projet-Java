package fr.polytech.projectjava.checkingsimulation.jfx;

import fr.polytech.projectjava.checkingsimulation.CheckInfos;
import fr.polytech.projectjava.checkingsimulation.Employee;
import fr.polytech.projectjava.checkingsimulation.jfx.components.CheckList;
import fr.polytech.projectjava.utils.Log;
import fr.polytech.projectjava.utils.jfx.ApplicationBase;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

/**
 * The checking application.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class SimulationApplication extends ApplicationBase
{
	private final static int MILLISECONDS_QUARTER = 900000;
	private final SimpleStringProperty currentTime = new SimpleStringProperty("");
	private final SimpleStringProperty roundedTimeString = new SimpleStringProperty("");
	private SimulationController controller;
	private LocalTime roundedTime = LocalTime.now();
	private Timeline currentTimeTimeline;
	private ComboBox<Employee> employeeField;
	private CheckList checkList;
	
	@Override
	public void preInit() throws Exception
	{
		super.preInit();
		controller = new SimulationController(this);
	}
	
	@Override
	public String getFrameTitle()
	{
		return "CheckingSimulation";
	}
	
	@Override
	public Consumer<Stage> getStageHandler()
	{
		return stage -> {
			stage.setOnCloseRequest(event -> {
				Log.info("Closing simulation...");
				if(controller.close(event))
					currentTimeTimeline.stop();
			});
			controller.loadDatas();
			controller.refreshEmployees(null);
		};
	}
	
	@Override
	public Consumer<Stage> getOnStageDisplayed() throws Exception
	{
		return stage -> {
			startTimeUpdated(); // Start the timeline to update the clock
			Log.info("Simulation displayed");
		};
	}
	
	@Override
	public Parent createContent(Stage stage)
	{
		VBox root = new VBox();
		
		StackPane times = new StackPane();
		Label currentTime = new Label();
		currentTime.textProperty().bind(this.currentTime);
		
		Label roundedTime = new Label();
		roundedTime.textProperty().bind(this.roundedTimeString);
		
		times.getChildren().addAll(currentTime, roundedTime);
		StackPane.setAlignment(currentTime, Pos.CENTER_LEFT);
		StackPane.setAlignment(roundedTime, Pos.CENTER_RIGHT);
		
		employeeField = new ComboBox<>();
		employeeField.setItems(FXCollections.observableArrayList());
		Callback<ListView<Employee>, ListCell<Employee>> employeeCellFactory = new Callback<ListView<Employee>, ListCell<Employee>>() /// Display the employee infos instead of it's hashcode.
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
		
		checkList = new CheckList();
		checkList.setMaxWidth(Double.MAX_VALUE);
		checkList.setMaxHeight(Double.MAX_VALUE);
		
		Button sendButton = new Button("Check I/O");
		sendButton.setMaxWidth(Double.MAX_VALUE);
		sendButton.setOnAction(evt -> controller.sendInfos(evt, employeeField.getSelectionModel().getSelectedItem(), this.roundedTime));
		
		Button refreshButton = new Button("Refresh employees");
		refreshButton.setMaxWidth(Double.MAX_VALUE);
		refreshButton.setOnAction(controller::refreshEmployees);
		
		Button sendPendingButton = new Button("Send pending checks");
		sendPendingButton.setMaxWidth(Double.MAX_VALUE);
		sendPendingButton.setOnAction(controller::sendPending);
		
		HBox employeeBox = new HBox();
		employeeBox.getChildren().addAll(employeeField, sendButton);
		HBox.setHgrow(employeeField, Priority.SOMETIMES);
		HBox.setHgrow(sendButton, Priority.ALWAYS);
		
		root.getChildren().addAll(times, checkList, employeeBox, refreshButton, sendPendingButton);
		
		VBox.setVgrow(checkList, Priority.ALWAYS);
		return root;
	}
	
	/**
	 * Start tje timeline updating the current time.
	 */
	private void startTimeUpdated()
	{
		if(currentTimeTimeline == null)
		{
			currentTimeTimeline = new Timeline(new KeyFrame(Duration.seconds(0), actionEvent -> {
				Calendar calendar = Calendar.getInstance(); //Get the current time
				int hours = calendar.get(Calendar.HOUR_OF_DAY);
				int minutes = calendar.get(Calendar.MINUTE);
				int seconds = calendar.get(Calendar.SECOND);
				currentTime.set((hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds); //Set the clock
				
				/* Round time to the closest quarter */
				Date time = calendar.getTime();
				long quarterMillis = time.getTime() % MILLISECONDS_QUARTER;
				time.setTime(time.getTime() - quarterMillis);
				if(quarterMillis >= MILLISECONDS_QUARTER / 2)
					time.setTime(time.getTime() + MILLISECONDS_QUARTER);
				calendar.setTime(time);
				
				hours = calendar.get(Calendar.HOUR_OF_DAY);
				minutes = calendar.get(Calendar.MINUTE);
				roundedTime = LocalTime.of(hours, minutes);
				roundedTimeString.set((hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes); //Set the rounded clock
			}), new KeyFrame(Duration.seconds(1))); //Update every second
			currentTimeTimeline.setCycleCount(Animation.INDEFINITE);
			currentTimeTimeline.play();
			Log.info("Clock updater started");
		}
	}
	
	/**
	 * Get the checkings to be sent.
	 *
	 * @return The checkings.
	 */
	public ObservableList<CheckInfos> getCheckings()
	{
		return checkList.getItems();
	}
	
	/**
	 * Get the employee list.
	 *
	 * @return The employee list.
	 */
	public ObservableList<Employee> getEmployees()
	{
		return employeeField.getItems();
	}
}
