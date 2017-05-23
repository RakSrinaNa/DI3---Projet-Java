package fr.polytech.projectjava;

import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.departments.StandardDepartment;
import fr.polytech.projectjava.company.staff.Boss;
import fr.polytech.projectjava.company.staff.Employee;
import fr.polytech.projectjava.company.staff.Manager;
import fr.polytech.projectjava.jfx.main.MainApplication;
import fr.polytech.projectjava.utils.Configuration;
import javafx.application.Application;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static fr.polytech.projectjava.company.checking.CheckInOut.CheckType.IN;
import static fr.polytech.projectjava.company.checking.CheckInOut.CheckType.OUT;

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
		Company comp = new Company("TheCompany", new Boss("Robert", "LeBoss"));
		comp.addDepartment(new StandardDepartment(comp, "IT Dpt", new Manager("Victor", "AManager")));
		comp.addDepartment(new StandardDepartment(comp, "MC Dpt", new Manager("Maxence", "AManager")));
		comp.getDepartment(0).ifPresent(dpt -> dpt.addEmployee(new Employee("A", "AEmployee")));
		comp.getDepartment(0).ifPresent(dpt -> dpt.addEmployee(new Employee("B", "AEmployee")));
		comp.getDepartment(0).ifPresent(dpt -> dpt.addEmployee(new Employee("C", "AEmployee")));
		comp.getDepartment(1).ifPresent(dpt -> dpt.addEmployee(new Employee("D", "AEmployee")));
		comp.getDepartment(1).ifPresent(dpt -> dpt.addEmployee(new Employee("E", "AEmployee")));
		comp.getDepartment(1).ifPresent(dpt -> dpt.addEmployee(new Employee("F", "AEmployee")));
		comp.getEmployee(1).ifPresent(emp -> {
			try
			{
				emp.addCheckInOut(new CheckInOut(IN, dateFormat.parse("23/05/2017 13:00:00")));
				emp.addCheckInOut(new CheckInOut(OUT, dateFormat.parse("23/05/2017 15:00:00")));
			}
			catch(ParseException e)
			{
				e.printStackTrace();
			}
		});
		
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(".", Configuration.getString("mainSaveFile")))))
		{
			oos.writeObject(comp);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		Application.launch(MainApplication.class, args);
	}
}
