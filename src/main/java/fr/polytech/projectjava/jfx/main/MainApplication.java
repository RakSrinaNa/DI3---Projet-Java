package fr.polytech.projectjava.jfx.main;

import fr.polytech.projectjava.utils.ApplicationBase;
import javafx.beans.property.StringProperty;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import java.util.function.Consumer;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class MainApplication extends ApplicationBase
{
	private MainController controller;
	private Text companyNameArea;
	private Text bossNameArea;
	private EmployeeList employeeList;
	
	@Override
	public void preInit() throws Exception
	{
		this.controller = new MainController(this);
	}
	
	@Override
	public Parent createContent(Stage stage)
	{
		VBox root = new VBox();
		
		Text companyNameLabel = new Text("Company name: ");
		companyNameArea = new Text("");
		TextFlow companyNameBox = new TextFlow(companyNameLabel, companyNameArea);
		
		Text bossNameLabel = new Text("Boss: ");
		bossNameArea = new Text("");
		TextFlow bossNameBox = new TextFlow(bossNameLabel, bossNameArea);
		
		employeeList = new EmployeeList(controller);
		
		Button addEmployeeButton = new Button("Add employee");
		addEmployeeButton.setMaxWidth(Double.MAX_VALUE);
		
		HBox employeeControls = new HBox();
		employeeControls.getChildren().addAll(addEmployeeButton);
		HBox.setHgrow(addEmployeeButton, Priority.ALWAYS);
		
		root.getChildren().addAll(companyNameBox, bossNameBox, employeeList, employeeControls);
		
		return root;
	}
	
	public StringProperty getBossNameTextProperty()
	{
		return bossNameArea.textProperty();
	}
	
	public StringProperty getCompanyNameTextProperty()
	{
		return companyNameArea.textProperty();
	}
	
	public EmployeeList getEmployeesList()
	{
		return employeeList;
	}
	
	@Override
	public String getFrameTitle()
	{
		return "CompanyManagement";
	}
	
	@Override
	public Consumer<Stage> getOnStageDisplayed() throws Exception
	{
		return stage -> controller.loadCompany();
	}
	
	@Override
	public Consumer<Stage> getStageHandler()
	{
		return stage -> stage.setOnCloseRequest(controller::close);
	}
	
	public void setCompanyName(String name)
	{
		companyNameArea.setText(name);
	}
}
