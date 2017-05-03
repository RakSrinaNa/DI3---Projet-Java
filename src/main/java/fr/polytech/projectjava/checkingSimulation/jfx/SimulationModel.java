package fr.polytech.projectjava.checkingSimulation.jfx;

import fr.polytech.projectjava.checkingSimulation.CheckInfos;
import fr.polytech.projectjava.checkingSimulation.Employee;
import fr.polytech.projectjava.utils.Configuration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public class SimulationModel
{
	private final ObservableList<Employee> employees;
	private final ObservableList<CheckInfos> checkings;
	
	public SimulationModel()
	{
		employees = FXCollections.emptyObservableList();
		checkings = FXCollections.emptyObservableList();
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
	
	public void addChecking(CheckInfos checkInfos)
	{
		checkings.add(checkInfos);
	}
	
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
	
	public ObservableList<CheckInfos> getCheckings()
	{
		return checkings;
	}
	
	public ObservableList<Employee> getEmployeeList()
	{
		return employees;
	}
}
