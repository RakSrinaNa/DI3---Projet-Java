package fr.polytech.projectjava.jfx.main;

import fr.polytech.projectjava.utils.ApplicationBase;
import javafx.beans.property.StringProperty;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
	private Label companyNameArea;
	private Label bossNameArea;
	
	@Override
	public void preInit() throws Exception
	{
		this.controller = new MainController(this);
	}
	
	@Override
	public Parent createContent(Stage stage)
	{
		VBox root = new VBox();
		
		Label companyNameLabel = new Label("Company name: ");
		companyNameArea = new Label("");
		HBox companyNameBox = new HBox();
		companyNameBox.getChildren().addAll(companyNameLabel, companyNameArea);
		
		Label bossNameLabel = new Label("Boss: ");
		bossNameArea = new Label("");
		HBox bossNameBox = new HBox();
		bossNameBox.getChildren().addAll(bossNameLabel, bossNameArea);
		
		Button listEmployees = new Button("List employees");
		listEmployees.setMaxWidth(Double.MAX_VALUE);
		listEmployees.setOnAction(controller::displayEmployees);
		
		root.getChildren().addAll(companyNameBox, bossNameBox, listEmployees);
		
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
