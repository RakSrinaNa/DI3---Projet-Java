package fr.polytech.projectjava.checkingSimulation.jfx;

import fr.polytech.projectjava.checkingSimulation.CheckInfos;
import fr.polytech.projectjava.checkingSimulation.CheckingSender;
import fr.polytech.projectjava.checkingSimulation.Employee;
import fr.polytech.projectjava.checkingSimulation.EmployeeGetter;
import fr.polytech.projectjava.company.checking.CheckInOut;
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
		CheckInfos checkInfos = new CheckInfos(employee, checkType, date, time);
		try
		{
			if(date == null)
			{
				((Button) evt.getSource()).setText("Please enter a correct date - Send");
				return;
			}
			if(evt.getSource() instanceof Button)
			{
				((Button) evt.getSource()).setDisable(true);
				((Button) evt.getSource()).setText("Sending...");
			}
			
			model.addChecking(checkInfos);
			
			new Thread(new CheckingSender(model.getCheckings().iterator())).start();
			
			if(evt.getSource() instanceof Button)
				((Button) evt.getSource()).setText("Send");
		}
		catch(IOException e)
		{
			e.printStackTrace();
			if(evt.getSource() instanceof Button)
				((Button) evt.getSource()).setText("Sending failed, try again");
		}
		finally
		{
			if(evt.getSource() instanceof Button)
				((Button) evt.getSource()).setDisable(false);
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
	
	public void close(WindowEvent windowEvent)
	{
		model.saveDatas();
	}
	
	public SimulationModel getModel()
	{
		return model;
	}
}
