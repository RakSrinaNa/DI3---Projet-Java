package fr.polytech.projectjava.jfx.main;

import fr.polytech.projectjava.ThreadCheckingReceiver;
import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.staff.Employee;
import fr.polytech.projectjava.jfx.dialogs.employee.EmployeeDialog;
import javafx.collections.ObservableList;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import java.net.SocketException;
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
	private ThreadCheckingReceiver socketReceiver;
	
	public MainController(MainApplication mainApplication)
	{
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
	
	public ObservableList<Employee> listEmployees()
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
	
	public void loadCompany()
	{
		if(model.loadCompany())
			new Thread(socketReceiver).start();
	}
	
	public void employeeClick(MouseEvent event)
	{
		if(event.getSource() instanceof TableRow)
		{
			TableRow source = (TableRow) event.getSource();
			if(source.getItem() instanceof Employee)
			{
				Employee employee = (Employee) source.getItem();
				if(event.getButton() == MouseButton.PRIMARY)
				{
					((TableRow) event.getSource()).getScene();
					EmployeeDialog dialog = new EmployeeDialog(employee);
					dialog.initOwner(((TableRow) event.getSource()).getScene().getWindow());
					dialog.initModality(Modality.APPLICATION_MODAL);
					dialog.showAndWait();
				}
			}
		}
	}
	
	public Optional<Employee> getEmployeeByID(int ID)
	{
		return model.getCompany().getEmployee(ID);
	}
}
