package fr.polytech.projectjava.checkingSimulation.jfx;

import fr.polytech.projectjava.checkingSimulation.CheckInfos;
import fr.polytech.projectjava.checkingSimulation.Employee;
import fr.polytech.projectjava.utils.Configuration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;

/**
 * Model for the checking application.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public class SimulationModel
{
	private final ObservableList<Employee> employees;
	private final ObservableList<CheckInfos> checkings;
	
	/**
	 * Constructor.
	 */
	public SimulationModel()
	{
		employees = FXCollections.observableArrayList();
		checkings = FXCollections.observableArrayList();
		File f = new File(Configuration.getString("simulationSaveFile"));
		if(f.exists() && f.isFile())
		{
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f)))
			{
				int count = ois.readInt();
				for(int i = 0; i < count; i++)
					checkings.add((CheckInfos) ois.readObject());
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
		checkings.add(checkInfos);
	}
	
	/**
	 * Save the model into a file.
	 */
	public void saveDatas()
	{
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(Configuration.getString("simulationSaveFile")))))
		{
			oos.writeInt(checkings.size());
			for(CheckInfos infos : checkings)
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
		return checkings;
	}
	
	/**
	 * Get the list of the employees.
	 *
	 * @return The employee list.
	 */
	public ObservableList<Employee> getEmployeeList()
	{
		return employees;
	}
}
