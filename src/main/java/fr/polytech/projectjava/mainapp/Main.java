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
import java.util.concurrent.ThreadLocalRandom;
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
	private static final String[] LAST_NAMES = {"Areki", "Sakulcha", "Kipolo", "Gifipo", "Nanouk", "Gradebuk", "Molipo", "Nougat", "Azerty", "Jurino", "Monohy", "Calana", "Dabre", "Olicard", "Justi", "Bujugre", "Savre", "Grebe"};
	private static final String[] FIRST_NAMES = {"Robert", "David", "Pedro", "Johann", "Victor", "Maxence", "Alexis", "Alexandre", "Carl", "Bob", "Valentin", "Diana", "Marie", "Tha", "Joop", "Alice", "Ashley", "Valentine"};
	
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
		Company comp = new Company("Polytech'Tours", new Boss("Neron", "Emmanuel"));
		
		StandardDepartment it = new StandardDepartment(comp, "IT Department", new Manager(comp, "Polino", "Robert"));
		StandardDepartment mc = new StandardDepartment(comp, "MC Department", new Manager(comp, "Balako", "David"));
		StandardDepartment el = new StandardDepartment(comp, "EL Department", new Manager(comp, "Carali", "Diana"));
		StandardDepartment am = new StandardDepartment(comp, "AM Department", new Manager(comp, "Vsauce", "Mickael"));
		comp.addDepartment(it);
		comp.addDepartment(mc);
		comp.addDepartment(el);
		comp.addDepartment(am);
		
		generateEmployees(it);
		generateEmployees(mc);
		generateEmployees(el);
		generateEmployees(am);
		
		File file = new File(".", Configuration.getString("mainSaveFile"));
		
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)))
		{
			oos.writeObject(comp);
		}
		catch(Exception e)
		{
			Log.warning("Failed to save create company");
		}
	}
	
	/**
	 * Generates employee into a department.
	 *
	 * @param department The department to add the employees to.
	 */
	private static void generateEmployees(StandardDepartment department)
	{
		int employeeCount = ThreadLocalRandom.current().nextInt(10);
		for(int i = 0; i < employeeCount; i++)
		{
			String last = LAST_NAMES[ThreadLocalRandom.current().nextInt(LAST_NAMES.length)];
			String first = FIRST_NAMES[ThreadLocalRandom.current().nextInt(FIRST_NAMES.length)];
			Employee employee;
			if(ThreadLocalRandom.current().nextDouble() < 0.15)
				employee = new Manager(department.getCompany(), last, first);
			else
				employee = new Employee(department.getCompany(), last, first);
			if(ThreadLocalRandom.current().nextDouble() < 0.1)
			{
				LocalDate yesterday = LocalDate.now().minusDays(1);
				employee.addCheckInOut(IN, yesterday, LocalTime.of(8, ThreadLocalRandom.current().nextInt(60)));
				employee.addCheckInOut(OUT, yesterday, LocalTime.of(17, ThreadLocalRandom.current().nextInt(60)));
				employee.addCheckInOut(IN, LocalDate.now(), LocalTime.of(8, ThreadLocalRandom.current().nextInt(60)));
			}
			if(ThreadLocalRandom.current().nextDouble() < 0.2)
				employee.setMail(employee.getFirstName() + "." + employee.getLastName() + "@mail.fr");
			department.addEmployee(employee);
		}
	}
}
