package fr.polytech.projectjava.checkingSimulation.jfx;

import fr.polytech.projectjava.checkingSimulation.CheckInfos;
import fr.polytech.projectjava.checkingSimulation.Employee;
import fr.polytech.projectjava.checkingSimulation.socket.CheckingSender;
import fr.polytech.projectjava.checkingSimulation.socket.EmployeeGetter;
import fr.polytech.projectjava.company.checking.CheckInOut;
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
	 * @param evt      The event that happened.
	 * @param employee The employee concerned with the check.
	 */
	public void sendInfos(ActionEvent evt, Employee employee)
	{
		if(!(evt.getSource() instanceof Button))
			return;
		Button source = (Button) evt.getSource();
		if(employee == null)
		{
			source.setText("Please select an employee - Check I/O");
			return;
		}
		try
		{
			CheckInfos checkInfos = new CheckInfos(employee, employee.isInside() ? CheckInOut.CheckType.OUT : CheckInOut.CheckType.IN, LocalDate.now(), LocalTime.now());
			employee.setInside(!employee.isInside());
			
			source.setDisable(true);
			source.setText("Check I/O...");
			
			getCheckings().add(checkInfos);
			
			new Thread(new CheckingSender(getCheckings().iterator())).start();
			
			source.setText("Check I/O");
		}
		catch(IOException e)
		{
			e.printStackTrace();
			source.setText("Sending failed, check queued");
		}
		finally
		{
			source.setDisable(false);
		}
	}
	
	/**
	 * Refresh the employee list with the server.
	 */
	public void refreshEmployees()
	{
		try
		{
			getEmployeeList().clear();
			new Thread(new EmployeeGetter(getEmployeeList())).start();
		}
		catch(IOException e)
		{
			Log.warning("Server not reachable");
		}
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
				e.printStackTrace();
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
			e.printStackTrace();
		}
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
	 * Get the list of the employees.
	 *
	 * @return The employee list.
	 */
	public ObservableList<Employee> getEmployeeList()
	{
		return parent.getEmployees();
	}
}
