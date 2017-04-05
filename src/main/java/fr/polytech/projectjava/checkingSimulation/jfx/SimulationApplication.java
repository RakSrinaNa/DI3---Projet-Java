package fr.polytech.projectjava.checkingSimulation.jfx;

import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.utils.ApplicationBase;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.function.Consumer;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class SimulationApplication extends ApplicationBase
{
	@Override
	public Parent createContent(Stage stage)
	{
		VBox root = new VBox();
		
		HBox inputs = new HBox();
		
		NumberField IDfield = new NumberField();
		ComboBox<CheckInOut.CheckType> typeField = new ComboBox<>();
		typeField.getItems().addAll(CheckInOut.CheckType.values());
		typeField.getSelectionModel().selectFirst();
		DatePicker dateField = new DatePicker();
		TimePicker timePicker = new TimePicker();
		
		inputs.getChildren().addAll(IDfield, typeField, dateField, timePicker);
		
		Button sendButton = new Button("Send");
		sendButton.setMaxWidth(Double.MAX_VALUE);
		
		root.getChildren().addAll(inputs, sendButton);
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
		return null;
	}
}
