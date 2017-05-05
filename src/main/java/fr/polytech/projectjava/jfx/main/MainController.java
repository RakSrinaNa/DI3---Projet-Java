package fr.polytech.projectjava.jfx.main;

import fr.polytech.projectjava.ThreadCheckingReceiver;
import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.staff.Employee;
import fr.polytech.projectjava.jfx.dialogs.listemployees.ListEmployeesDialog;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import java.net.SocketException;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class MainController
{
	private final MainModel model;
	private final MainApplication view;
	private ThreadCheckingReceiver socketReceiver;
	
	public MainController(MainApplication mainApplication)
	{
		view = mainApplication;
		model = new MainModel(mainApplication);
		try
		{
			socketReceiver = new ThreadCheckingReceiver(this);
		}
		catch(SocketException e)
		{
			e.printStackTrace();
		}
	}
	
	public void close(WindowEvent windowEvent)
	{
		socketReceiver.stop();
		model.saveDatas();
	}
	
	public Collection<Employee> listEmployees()
	{
		return model.getCompany().getEmployees();
	}
	
	public boolean addChecking(int employeeID, CheckInOut check)
	{
		Optional<Employee> employee = model.getCompany().getEmployee(employeeID);
		if(employee.isPresent())
		{
			employee.get().addCheckInOut(check);
			return true;
		}
		return false;
	}
	
	public void displayEmployees(ActionEvent actionEvent)
	{
		ListEmployeesDialog dialog = new ListEmployeesDialog(model.getCompany());
		dialog.initOwner(view.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}
	
	public void loadCompany()
	{
		if(model.loadCompany())
			new Thread(socketReceiver).start();
	}
	
	public Optional<Employee> getEmployeeByID(int ID)
	{
		return model.getCompany().getEmployee(ID);
	}
}
