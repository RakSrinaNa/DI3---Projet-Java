package fr.polytech.projectjava.checkingSimulation;

import fr.polytech.projectjava.company.checking.CheckInOut;
import java.io.Serializable;
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
public class CheckInfos implements Serializable
{
	private transient static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private static final long serialVersionUID = -3180459411345585955L;
	private final Employee employee;
	private final CheckInOut.CheckType checkType;
	private final LocalDate date;
	private final LocalTime time;
	
	public CheckInfos(Employee employee, CheckInOut.CheckType checkType, LocalDate date, LocalTime time)
	{
		this.employee = employee;
		this.checkType = checkType;
		this.date = date;
		this.time = time;
	}
	
	public String getForSocket()
	{
		return employee.getID() + ";" + checkType.toString() + ";" + dateFormat.format(LocalDateTime.of(date, time));
	}
}
