package fr.polytech.projectjava.checkingSimulation;

import fr.polytech.projectjava.company.checking.CheckInOut;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 25/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-25
 */
public class CheckInfos implements Serializable
{
	private transient static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
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
	
	public LocalDateTime getCheckDate()
	{
		return LocalDateTime.of(date, time);
	}
	
	public CheckInOut.CheckType getCheckType()
	{
		return checkType;
	}
	
	public Employee getEmployee()
	{
		return employee;
	}
	
	public String getForSocket()
	{
		return getEmployee().getID() + ";" + getCheckType().toString() + ";" + getFormattedCheckDate();
	}
	
	public String getFormattedCheckDate()
	{
		return getCheckDate().format(dateFormat);
	}
}
