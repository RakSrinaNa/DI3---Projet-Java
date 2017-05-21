package fr.polytech.projectjava.jfx.main;

import fr.polytech.projectjava.utils.ApplicationBase;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
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
	private MainTab mainTab;
	private EmployeeTab employeeTab;
	
	@Override
	public void preInit() throws Exception
	{
		this.controller = new MainController(this);
	}
	
	@Override
	public Parent createContent(Stage stage)
	{
		TabPane tabPane = new TabPane();
		mainTab = new MainTab(controller);
		employeeTab = new EmployeeTab(controller);
		
		tabPane.getTabs().addAll(mainTab, employeeTab);
		
		return tabPane;
	}
	
	@Override
	public String getFrameTitle()
	{
		return "CompanyManagement";
	}
	
	/**
	 * Get the main tab.
	 *
	 * @return The main tab.
	 */
	public MainTab getMainTab()
	{
		return mainTab;
	}
	
	/**
	 * Get the employee tab.
	 *
	 * @return The employee tab.
	 */
	public EmployeeTab getEmployeeTab()
	{
		return employeeTab;
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
}
