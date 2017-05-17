package fr.polytech.projectjava.company.checking;

import javafx.beans.property.SimpleObjectProperty;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * Represent a day check.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 17/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-17
 */
public class EmployeeCheck implements Serializable
{
	private static final long serialVersionUID = 2289845323375640933L;
	private SimpleObjectProperty<LocalDate> date;
	private SimpleObjectProperty<CheckInOut> checkIn = new SimpleObjectProperty<>();
	private SimpleObjectProperty<CheckInOut> checkOut = new SimpleObjectProperty<>();
	
	/**
	 * Constructor.
	 *
	 * @param date The date of the check.
	 */
	public EmployeeCheck(LocalDate date)
	{
		this.date = new SimpleObjectProperty<>(date);
	}
	
	/**
	 * Constructor that set the day of the check to the one of the checkinout.
	 *
	 * @param checkInOut The check to take the date from and add.
	 */
	public EmployeeCheck(CheckInOut checkInOut)
	{
		this.date = new SimpleObjectProperty<>(checkInOut.getDay());
		setInOut(checkInOut);
	}
	
	/**
	 * Tell if the check in out is part of this day check.
	 *
	 * @param checkInOut The checkinout to test.
	 *
	 * @return True if same day, false else.
	 */
	public boolean isDateOf(CheckInOut checkInOut)
	{
		return getDate().isEqual(checkInOut.getDay());
	}
	
	/**
	 * Tells if a day is in progress (one check is present).
	 *
	 * @return True if one check is present, false else.
	 */
	public boolean isInProgress()
	{
		return (checkIn.get() != null) ^ (checkOut.get() != null);
	}
	
	/**
	 * Get the time worked for this day.
	 *
	 * @return The time worked.
	 */
	public Duration getWorkedTime()
	{
		if(checkIn.get() == null || checkOut.get() == null)
			return Duration.ZERO;
		return Duration.of(checkOut.get().getTime().toSecondOfDay() - checkIn.get().getTime().toSecondOfDay(), SECONDS);
	}
	
	/**
	 * Get the date of the check.
	 *
	 * @return The date.
	 */
	public LocalDate getDate()
	{
		return dateProperty().get();
	}
	
	/**
	 * Get the dat property.
	 *
	 * @return The day property.
	 */
	private SimpleObjectProperty<LocalDate> dateProperty()
	{
		return date;
	}
	
	/**
	 * Set a check in/out to the day.
	 *
	 * @param checkInOut The check to set.
	 *
	 * @throws IllegalArgumentException If the check isn't the correct type or the date isn't the same.
	 */
	public void setInOut(CheckInOut checkInOut) throws IllegalArgumentException
	{
		switch(checkInOut.getCheckType())
		{
			case IN:
				setIn(checkInOut);
				break;
			case OUT:
				setOut(checkInOut);
				break;
		}
	}
	
	/**
	 * Set the in check.
	 *
	 * @param check The check to set.
	 *
	 * @throws IllegalArgumentException If the check isn't the correct type or the date isn't the same.
	 */
	public void setIn(CheckInOut check) throws IllegalArgumentException
	{
		if(check.getCheckType() != CheckInOut.CheckType.IN)
			throw new IllegalArgumentException("The check must be IN");
		if(!check.getDay().isEqual(getDate()))
			throw new IllegalArgumentException("The checks are not from the same day");
		checkIn.set(check);
	}
	
	/**
	 * Set the out check.
	 *
	 * @param check The check to set.
	 *
	 * @throws IllegalArgumentException If the check isn't the correct type or the date isn't the same.
	 */
	public void setOut(CheckInOut check) throws IllegalArgumentException
	{
		if(check.getCheckType() != CheckInOut.CheckType.OUT)
			throw new IllegalArgumentException("The check must be OUT");
		if(!check.getDay().isEqual(getDate()))
			throw new IllegalArgumentException("The checks are not from the same day");
		checkOut.set(check);
	}
	
	/**
	 * Serialize the object.
	 *
	 * @param oos The object stream.
	 *
	 * @throws IOException If the serialization failed.
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.writeObject(getDate());
		oos.writeInt((checkIn.get() != null ? 1 : 0) << 2 + (checkOut.get() != null ? 1 : 0));
		if(checkIn.get() != null)
			oos.writeObject(checkIn.get());
		if(checkOut.get() != null)
			oos.writeObject(checkOut.get());
	}
	
	/**
	 * Deserialize an object.
	 *
	 * @param ois The object stream.
	 *
	 * @throws IOException            If the deserialization failed.
	 * @throws ClassNotFoundException If the file doesn't represent the correct class.
	 */
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
	{
		date = new SimpleObjectProperty<>((LocalDate) ois.readObject());
		int infos = ois.readInt();
		if((infos & 0x02) == 0x02)
			checkIn = new SimpleObjectProperty<>((CheckInOut) ois.readObject());
		if((infos & 0x01) == 0x01)
			checkOut = new SimpleObjectProperty<>((CheckInOut) ois.readObject());
	}
}
