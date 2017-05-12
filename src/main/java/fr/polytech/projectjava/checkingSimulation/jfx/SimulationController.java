package fr.polytech.projectjava.checkingSimulation.jfx;

import fr.polytech.projectjava.checkingSimulation.CheckInfos;
import fr.polytech.projectjava.checkingSimulation.CheckingSender;
import fr.polytech.projectjava.checkingSimulation.Employee;
import fr.polytech.projectjava.checkingSimulation.EmployeeGetter;
import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.utils.Log;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-25
 */
public class SimulationController
{
	private final SimulationModel model;
	
	public SimulationController()
	{
		model = new SimulationModel();
		refreshEmployees();
	}
	
	public void sendInfos(ActionEvent evt, Employee employee, CheckInOut.CheckType checkType, LocalDate date, LocalTime time)
	{
		if(!(evt.getSource() instanceof Button))
			return;
		Button source = (Button) evt.getSource();
		if(employee == null)
		{
			source.setText("Please select an employee - Send");
			return;
		}
		try
		{
			if(date == null)
			{
				Log.info("Performing automatic check...");
				checkType = employee.isInside() ? CheckInOut.CheckType.OUT : CheckInOut.CheckType.IN;
				employee.setInside(!employee.isInside());
				date = LocalDate.now();
				time = LocalTime.now();
			}
			CheckInfos checkInfos = new CheckInfos(employee, checkType, date, time);
			
			source.setDisable(true);
			source.setText("Sending...");
			
			model.addChecking(checkInfos);
			
			new Thread(new CheckingSender(model.getCheckings().iterator())).start();
			
			source.setText("Send");
		}
		catch(IOException e)
		{
			e.printStackTrace();
			source.setText("Sending failed, try again");
		}
		finally
		{
			source.setDisable(false);
		}
	}
	
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
	
	public boolean close(WindowEvent windowEvent)
	{
		model.saveDatas();
		return true;
	}
	
	public SimulationModel getModel()
	{
		return model;
	}
}
