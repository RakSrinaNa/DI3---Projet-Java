package fr.polytech.projectjava.mainapp.jfx;

import fr.polytech.projectjava.mainapp.jfx.check.CheckTab;
import fr.polytech.projectjava.mainapp.jfx.company.CompanyTab;
import fr.polytech.projectjava.mainapp.jfx.department.DepartmentTab;
import fr.polytech.projectjava.mainapp.jfx.employee.EmployeeTab;
import fr.polytech.projectjava.utils.Log;
import fr.polytech.projectjava.utils.jfx.ApplicationBase;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	private CompanyTab companyTab;
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
	public Scene buildScene(Stage stage)
	{
		return new Scene(createContent(stage), 800, 400);
	}
	
	@Override
	public String getFrameTitle()
	{
		return "CompanyManagement";
	}
	
	@Override
	public Consumer<Stage> getStageHandler()
	{
		return stage -> stage.setOnCloseRequest(controller::close);
	}
	
	@Override
	public Consumer<Stage> getOnStageDisplayed() throws Exception
	{
		return stage -> {
			if(!controller.loadCompany())
			{
				getStage().close();
				Platform.exit();
				System.exit(2);
			}
			Log.info("Main application displayed");
		};
	}
	
	@Override
	public Parent createContent(Stage stage)
	{
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		
		companyTab = new CompanyTab(controller);
		employeeTab = new EmployeeTab(controller);
		departmentTab = new DepartmentTab(controller);
		checkTab = new CheckTab(controller);
		
		tabPane.getTabs().addAll(companyTab, employeeTab, departmentTab, checkTab);
		
		return tabPane;
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
	 * Get the employee tab.
	 *
	 * @return The employee tab.
	 */
	public EmployeeTab getEmployeeTab()
	{
		return employeeTab;
	}
	
	/**
	 * Get the main tab.
	 *
	 * @return The main tab.
	 */
	public CompanyTab getCompanyTab()
	{
		return companyTab;
	}
}
