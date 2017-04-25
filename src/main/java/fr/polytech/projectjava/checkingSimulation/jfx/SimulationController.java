package fr.polytech.projectjava.checkingSimulation.jfx;

import fr.polytech.projectjava.checkingSimulation.CheckInfos;
import fr.polytech.projectjava.checkingSimulation.CheckingSender;
import fr.polytech.projectjava.company.checking.CheckInOut;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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
	public static void sendInfos(ActionEvent evt, int employeeID, CheckInOut.CheckType checkType, LocalDate date, LocalTime time)
	{
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
			CheckingSender.sendInfos(new CheckInfos(employeeID, checkType, date, time));
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
}
