package fr.polytech.projectjava.mainapp;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Boss;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
import fr.polytech.projectjava.mainapp.company.staff.checking.CheckInOut;
import fr.polytech.projectjava.mainapp.jfx.main.MainApplication;
import fr.polytech.projectjava.utils.Configuration;
import fr.polytech.projectjava.utils.Log;
import javafx.application.Application;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static fr.polytech.projectjava.mainapp.company.staff.checking.CheckInOut.CheckType.IN;
import static fr.polytech.projectjava.mainapp.company.staff.checking.CheckInOut.CheckType.OUT;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-23
 */
public class Main
{
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	/**
	 * Temporary main method.
	 *
	 * @param args Program's arguments.
	 */
	public static void main(String[] args)
	{
		buildCompany();
		Application.launch(MainApplication.class, args);
	}
	
	/**
	 * Build a company that will be serialized for testing.
	 */
	private static void buildCompany()
	{
		Company comp = new Company("TheCompany", new Boss("Robert", "LeBoss"));
		comp.addDepartment(new StandardDepartment(comp, "IT Dpt", new Manager(comp, "Victor", "AManager")));
		comp.addDepartment(new StandardDepartment(comp, "MC Dpt", new Manager(comp, "Maxence", "AManager")));
		comp.getDepartment(0).ifPresent(dpt -> dpt.addEmployee(new Employee(comp, "A", "AEmployee")));
		comp.getDepartment(0).ifPresent(dpt -> dpt.addEmployee(new Employee(comp, "B", "AEmployee")));
		comp.getDepartment(0).ifPresent(dpt -> dpt.addEmployee(new Employee(comp, "C", "AEmployee")));
		comp.getDepartment(1).ifPresent(dpt -> dpt.addEmployee(new Employee(comp, "D", "AEmployee")));
		comp.getDepartment(1).ifPresent(dpt -> dpt.addEmployee(new Employee(comp, "E", "AEmployee")));
		comp.getDepartment(1).ifPresent(dpt -> dpt.addEmployee(new Employee(comp, "F", "AEmployee")));
		comp.getEmployee(1).ifPresent(emp -> {
			try
			{
				emp.addCheckInOut(new CheckInOut(IN, dateFormat.parse("23/05/2017 13:00:00")));
				emp.addCheckInOut(new CheckInOut(OUT, dateFormat.parse("23/05/2017 15:00:00")));
			}
			catch(ParseException e)
			{
				Log.warning("Failed to parse date while building company", e);
			}
		});
		
		File file = new File(".", Configuration.getString("mainSaveFile"));
		
		if(file.exists())
			file.delete();
		
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)))
		{
			oos.writeObject(comp);
		}
		catch(Exception e)
		{
			Log.warning("Failed to save create company");
		}
	}
}
