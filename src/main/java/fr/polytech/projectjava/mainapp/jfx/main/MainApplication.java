package fr.polytech.projectjava.mainapp.jfx.main;

import fr.polytech.projectjava.utils.Log;
import fr.polytech.projectjava.utils.jfx.ApplicationBase;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import java.net.BindException;
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
	private DepartmentTab departmentTab;
	private CheckTab checkTab;
	
	@Override
	public void preInit() throws Exception
	{
		try
		{
			this.controller = new MainController(this);
		}
		catch(BindException e)
		{
			Log.error("Main app is already running or another app is using this port");
			System.exit(2);
		}
	}
	
	@Override
	public Parent createContent(Stage stage)
	{
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		
		mainTab = new MainTab(controller);
		employeeTab = new EmployeeTab(controller);
		departmentTab = new DepartmentTab(controller);
		checkTab = new CheckTab(controller);
		
		tabPane.getTabs().addAll(mainTab, employeeTab, departmentTab, checkTab);
		
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
	
	/**
	 * Get the department tab.
	 *
	 * @return The department tab.
	 */
	public DepartmentTab getDepartmentTab()
	{
		return departmentTab;
	}
	
	/**
	 * Get the check tab.
	 *
	 * @return The check tab.
	 */
	public CheckTab getCheckTab()
	{
		return checkTab;
	}
	
	@Override
	public Consumer<Stage> getStageHandler()
	{
		return stage -> stage.setOnCloseRequest(controller::close);
	}
}
