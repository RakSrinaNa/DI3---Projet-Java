package fr.polytech.projectjava.checkingSimulation;

import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.utils.SimpleLocalDateTimeProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private Employee employee;
	private SimpleObjectProperty<CheckInOut.CheckType> checkType;
	private SimpleLocalDateTimeProperty date;
	
	public CheckInfos(Employee employee, CheckInOut.CheckType checkType, LocalDate date, LocalTime time)
	{
		this.employee = employee;
		this.checkType = new SimpleObjectProperty<>(checkType);
		this.date = new SimpleLocalDateTimeProperty(LocalDateTime.of(date, time), dateFormat);
	}
	
	public SimpleLocalDateTimeProperty dateProperty()
	{
		return date;
	}
	
	public LocalDateTime getCheckDate()
	{
		return dateProperty().getDate();
	}
	
	public CheckInOut.CheckType getCheckType()
	{
		return checkTypeProperty().get();
	}
	
	public SimpleObjectProperty<CheckInOut.CheckType> checkTypeProperty()
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
	
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.writeObject(getEmployee());
		oos.writeObject(getCheckType());
		oos.writeObject(getCheckDate());
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
	{
		employee = (Employee) ois.readObject();
		checkType = new SimpleObjectProperty<>((CheckInOut.CheckType) ois.readObject());
		date = new SimpleLocalDateTimeProperty((LocalDateTime) ois.readObject(), dateFormat);
	}
}
