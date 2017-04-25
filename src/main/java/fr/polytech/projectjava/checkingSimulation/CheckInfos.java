package fr.polytech.projectjava.checkingSimulation;

import fr.polytech.projectjava.company.checking.CheckInOut;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-25
 */
public class CheckInfos
{
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private final int employeeID;
	private final CheckInOut.CheckType checkType;
	private final LocalDate date;
	private final LocalTime time;
	
	public CheckInfos(int employeeID, CheckInOut.CheckType checkType, LocalDate date, LocalTime time)
	{
		this.employeeID = employeeID;
		this.checkType = checkType;
		this.date = date;
		this.time = time;
	}
	
	public String getForSocket()
	{
		return employeeID + ";" + checkType.toString() + ";" + dateFormat.format(LocalDateTime.of(date, time));
	}
}
