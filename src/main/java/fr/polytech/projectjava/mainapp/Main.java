package fr.polytech.projectjava.mainapp;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Boss;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
import fr.polytech.projectjava.mainapp.jfx.MainApplication;
import fr.polytech.projectjava.utils.Configuration;
import fr.polytech.projectjava.utils.Log;
import javafx.application.Application;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import static fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck.CheckType.IN;
import static fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck.CheckType.OUT;

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
	 * Main method.
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
			emp.addCheckInOut(IN, LocalDate.of(2017, 5, 23), LocalTime.of(13, 0));
			emp.addCheckInOut(OUT, LocalDate.of(2017, 5, 23), LocalTime.of(15, 0));
		});
		
		File file = new File(".", Configuration.getString("mainSaveFile"));
		
		if(file.exists())
			if(file.delete())
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
