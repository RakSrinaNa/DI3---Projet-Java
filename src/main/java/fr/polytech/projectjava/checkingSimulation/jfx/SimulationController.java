package fr.polytech.projectjava.checkingSimulation.jfx;

import fr.polytech.projectjava.checkingSimulation.CheckInfos;
import fr.polytech.projectjava.checkingSimulation.Employee;
import fr.polytech.projectjava.checkingSimulation.socket.CheckingSender;
import fr.polytech.projectjava.checkingSimulation.socket.EmployeeGetter;
import fr.polytech.projectjava.company.checking.CheckInOut;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;
import java.io.IOException;
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
	private final SimulationModel model;
	
	/**
	 * Constructor.
	 */
	public SimulationController()
	{
		model = new SimulationModel();
		refreshEmployees();
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
			
			model.addChecking(checkInfos);
			
			new Thread(new CheckingSender(model.getCheckings().iterator())).start();
			
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
			model.getEmployeeList().clear();
			new Thread(new EmployeeGetter(model.getEmployeeList())).start();
		}
		catch(IOException e)
		{
			e.printStackTrace();
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
	public boolean close(WindowEvent windowEvent)
	{
		model.saveDatas();
		return true;
	}
	
	/**
	 * Get the model.
	 *
	 * @return The model.
	 */
	public SimulationModel getModel()
	{
		return model;
	}
}
