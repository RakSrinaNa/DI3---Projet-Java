package fr.polytech.projectjava.checkingsimulation.jfx;

import fr.polytech.projectjava.checkingsimulation.CheckInfos;
import fr.polytech.projectjava.checkingsimulation.Employee;
import fr.polytech.projectjava.checkingsimulation.socket.CheckingSender;
import fr.polytech.projectjava.checkingsimulation.socket.EmployeeGetter;
import fr.polytech.projectjava.mainapp.company.staff.checking.CheckInOut;
import fr.polytech.projectjava.utils.Configuration;
import fr.polytech.projectjava.utils.Log;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The controller for the checking application.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-25
 */
public class SimulationController
{
	private final SimulationApplication parent;
	
	/**
	 * Constructor.
	 *
	 * @param simulationApplication The simulation application.
	 */
	public SimulationController(SimulationApplication simulationApplication)
	{
		this.parent = simulationApplication;
	}
	
	/**
	 * Called when the check I/O button is clicked.
	 *
	 * @param evt         The event that happened.
	 * @param employee    The employee concerned with the check.
	 * @param roundedTime The rounded time of the event.
	 */
	public void sendInfos(ActionEvent evt, Employee employee, LocalTime roundedTime)
	{
		if(!(evt.getSource() instanceof Button))
			return;
		Button source = (Button) evt.getSource();
		if(employee == null)
		{
			source.setText("Please select an employee - Check I/O");
			return;
		}
		CheckInfos checkInfos = new CheckInfos(employee, employee.isInside() ? CheckInOut.CheckType.OUT : CheckInOut.CheckType.IN, LocalDate.now(), roundedTime);
		employee.setInside(!employee.isInside());
		
		getCheckings().add(checkInfos);
		
		sendPending(evt);
		
		source.setText("Check I/O");
	}
	
	/**
	 * Get the list of the checkings.
	 *
	 * @return The checking list.
	 */
	public ObservableList<CheckInfos> getCheckings()
	{
		return parent.getCheckings();
	}
	
	/**
	 * Send pending checks to the main application.
	 *
	 * @param actionEvent The click event.
	 */
	public void sendPending(ActionEvent actionEvent)
	{
		try
		{
			if(getCheckings().size() > 0)
				new Thread(new CheckingSender(getCheckings().iterator())).start();
		}
		catch(IOException e)
		{
			Log.warning("Error sending checks", e);
		}
	}
	
	/**
	 * Refresh the employee list with the server.
	 *
	 * @param evt The click event.
	 */
	public void refreshEmployees(ActionEvent evt)
	{
		try
		{
			getEmployeeList().clear();
			EmployeeGetter employeeGetter = new EmployeeGetter(getEmployeeList());
			new Thread(employeeGetter).start();
			if(evt != null && evt.getSource() instanceof Button)
			{
				Button button = (Button) evt.getSource();
				button.setDisable(true);
				employeeGetter.addFinishedListener(event -> button.setDisable(false));
			}
		}
		catch(IOException e)
		{
			Log.warning("Server not reachable");
		}
	}
	
	/**
	 * Get the list of the employees.
	 *
	 * @return The employee list.
	 */
	public ObservableList<Employee> getEmployeeList()
	{
		return parent.getEmployees();
	}
	
	/**
	 * Called when the window have to close.
	 * Save the model.
	 *
	 * @param windowEvent The event closing the window.
	 *
	 * @return True if everything went fine, false else.
	 */
	@SuppressWarnings("SameReturnValue")
	public boolean close(WindowEvent windowEvent)
	{
		saveDatas();
		return true;
	}
	
	/**
	 * Save the model into a file.
	 */
	public void saveDatas()
	{
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(Configuration.getString("simulationSaveFile")))))
		{
			oos.writeInt(getCheckings().size());
			for(CheckInfos infos : getCheckings())
				oos.writeObject(infos);
		}
		catch(IOException e)
		{
			Log.error("Failed to save the simulation", e);
		}
	}
	
	/**
	 * Load the previously saved data.
	 */
	public void loadDatas()
	{
		File f = new File(Configuration.getString("simulationSaveFile"));
		if(f.exists() && f.isFile())
		{
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f)))
			{
				int count = ois.readInt();
				for(int i = 0; i < count; i++)
					getCheckings().add((CheckInfos) ois.readObject());
			}
			catch(IOException | ClassNotFoundException e)
			{
				Log.warning("Failed to load the simulation state", e);
			}
		}
	}
	
	/**
	 * Add a checking to the model.
	 *
	 * @param checkInfos The checking to add.
	 */
	public void addChecking(CheckInfos checkInfos)
	{
		getCheckings().add(checkInfos);
	}
}
