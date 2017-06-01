package fr.polytech.projectjava.mainapp.jfx;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
import fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck;
import fr.polytech.projectjava.mainapp.jfx.check.CheckList;
import fr.polytech.projectjava.mainapp.jfx.check.create.CheckCreateDialog;
import fr.polytech.projectjava.mainapp.jfx.company.create.CompanyCreateDialog;
import fr.polytech.projectjava.mainapp.jfx.department.create.StandardDepartmentCreateDialog;
import fr.polytech.projectjava.mainapp.jfx.employee.create.EmployeeCreateDialog;
import fr.polytech.projectjava.mainapp.socket.CheckingServer;
import fr.polytech.projectjava.utils.Configuration;
import fr.polytech.projectjava.utils.Log;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * Controller for the main window.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class MainController
{
	private final MainApplication parent;
	private final CheckingServer socketReceiver;
	private Company company;
	
	/**
	 * Constructor.
	 *
	 * @param mainApplication The main window.
	 *
	 * @throws IOException If the socket failed to be opened.
	 */
	public MainController(MainApplication mainApplication) throws IOException
	{
		parent = mainApplication;
		socketReceiver = new CheckingServer(this);
		new Thread(socketReceiver).start();
	}
	
	/**
	 * Used when the application closes. Stop the socket server and save datas.
	 *
	 * @param windowEvent The even of the close request.
	 */
	public void close(WindowEvent windowEvent)
	{
		Log.info("Closing main app");
		socketReceiver.stop();
		saveDatas();
		Log.info("Main app closed");
	}
	
	/**
	 * Save the current company.
	 */
	public void saveDatas()
	{
		if(company != null)
		{
			Log.info("Saving loaded company");
			try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(Configuration.getString("mainSaveFile")))))
			{
				oos.writeObject(company);
				Log.info("Company saved");
			}
			catch(IOException e)
			{
				Log.error("Failed to save company", e);
			}
		}
	}
	
	/**
	 * List the employees of the company.
	 *
	 * @return The employees.
	 */
	public ObservableList<Employee> listEmployees()
	{
		return getCompany().getEmployees();
	}
	
	/**
	 * Export the employees as CSV.
	 *
	 * @param event The click event.
	 */
	public void exportCSV(ActionEvent event)
	{
		try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File(".", System.currentTimeMillis() + ".csv"))))
		{
			getCompany().getEmployees().forEach(emp -> pw.println(emp.asCSV(";")));
			Log.info("Employees exported as CSV");
		}
		catch(FileNotFoundException e)
		{
			Log.error("Couldn't export employees", e);
		}
	}
	
	/**
	 * Import employees from CSV.
	 *
	 * @param event The click event.
	 */
	public void importCSV(ActionEvent event)
	{
		askFile(new File(".")).ifPresent(file -> {
			try
			{
				for(String employee : Files.readAllLines(Paths.get(file.toURI())))
				{
					Queue<String> parts = new LinkedList<>();
					parts.addAll(Arrays.asList(employee.split(";")));
					try
					{
						switch(parts.poll())
						{
							case "Manager":
								Manager.fromCSV(getCompany(), parts);
								break;
							case "Employee":
							default:
								Employee.fromCSV(getCompany(), parts);
						}
					}
					catch(Exception e)
					{
						Log.error("Couldn't parse CSV employee: " + employee, e);
					}
				}
			}
			catch(IOException e)
			{
				Log.warning("Error reading CSV file", e);
			}
		});
	}
	
	/**
	 * Ask a file to the user.
	 *
	 * @param defaultFile The default directory to start with.
	 *
	 * @return The chosen file.
	 */
	public static Optional<File> askFile(File defaultFile)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select file");
		if(defaultFile != null && defaultFile.exists())
			fileChooser.setInitialDirectory(defaultFile);
		return Optional.ofNullable(fileChooser.showOpenDialog(new Stage()));
	}
	
	/**
	 * Get the current loaded company.
	 *
	 * @return The loaded company.
	 */
	public Company getCompany()
	{
		return company;
	}
	
	/**
	 * Add a check.
	 *
	 * @param employeeID The employee ID.
	 * @param checkType  The check type.
	 * @param date       The date and time when it happened.
	 *
	 * @return True if the check was added, false else.
	 */
	public boolean addChecking(int employeeID, EmployeeCheck.CheckType checkType, LocalDateTime date)
	{
		Optional<Employee> employee = getCompany().getEmployee(employeeID);
		if(employee.isPresent())
		{
			employee.get().addCheckInOut(checkType, date.toLocalDate(), date.toLocalTime());
			return true;
		}
		return false;
	}
	
	/**
	 * Get an employee by its ID.
	 *
	 * @param ID The employee ID.
	 *
	 * @return An optional of the employee.
	 */
	public Optional<Employee> getEmployeeByID(int ID)
	{
		return getCompany().getEmployee(ID);
	}
	
	/**
	 * Builds a new company.
	 *
	 * @return The created company.
	 */
	private Company buildNewCompany()
	{
		CompanyCreateDialog dialog = new CompanyCreateDialog();
		dialog.initOwner(parent.getStage());
		dialog.getScene().getStylesheets().add("jfx/base.css");
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
		return dialog.getResult();
	}
	
	/**
	 * Load a company into the view.
	 *
	 * @return True if a company was loaded, false else.
	 */
	public boolean loadCompany()
	{
		Optional<Company> companyOptional = loadLastCompany();
		company = companyOptional.orElseGet(this::buildNewCompany);
		if(company == null) // If no companies could be loaded
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("No company loaded");
			alert.setHeaderText("No company loaded");
			alert.setContentText("A company need to be loaded or created for the application to work.");
			alert.showAndWait();
			return false;
		}
		SimpleStringProperty employeeCount = new SimpleStringProperty("" + getCompany().getEmployees().size());
		SimpleStringProperty departmentCount = new SimpleStringProperty("" + getCompany().getDepartements().size());
		company.getEmployees().addListener((InvalidationListener) observable -> employeeCount.set("" + company.getEmployees().size()));
		company.getDepartements().addListener((InvalidationListener) observable -> departmentCount.set("" + company.getDepartements().size()));
		
		/* Bind values to the UI */
		parent.getCompanyTab().getCompanyNameTextProperty().bind(company.nameProperty());
		parent.getCompanyTab().getBossNameTextProperty().bind(company.getBoss().fullNameProperty());
		parent.getCompanyTab().getEmployeeCountTextProperty().bind(employeeCount);
		parent.getCompanyTab().getDepartmentCountTextProperty().bind(departmentCount);
		parent.getEmployeeTab().getList().setList(company.getEmployees());
		parent.getEmployeeTab().getDepartmentFilter().setItems(company.getDepartements());
		parent.getDepartmentTab().getList().setList(company.getDepartements());
		parent.getCheckTab().getList().setList(company.getChecks());
		parent.getCheckTab().getDepartmentFilter().setItems(company.getDepartements());
		parent.getCheckTab().getEmployeeFilter().setItems(company.getEmployees());
		return true;
	}
	
	/**
	 * Load the last company.
	 *
	 * @return The loaded company.
	 */
	private Optional<Company> loadLastCompany()
	{
		File f = new File(Configuration.getString("mainSaveFile"));
		if(f.exists() && f.isFile())
		{
			Log.info("Loading last company...");
			Company company = null;
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f)))
			{
				company = (Company) ois.readObject();
				Log.info("Company loaded");
			}
			catch(IOException | ClassNotFoundException | ClassCastException e)
			{
				Log.warning("Failed to load last company", e);
			}
			return Optional.ofNullable(company);
		}
		return Optional.empty();
	}
	
	/**
	 * Bring the popup when an employee want to be added.
	 *
	 * @param event The click event.
	 */
	public void addEmployee(ActionEvent event)
	{
		EmployeeCreateDialog dialog = new EmployeeCreateDialog(getCompany());
		dialog.initOwner(((Button) event.getSource()).getScene().getWindow());
		dialog.getScene().getStylesheets().add("jfx/base.css");
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
		Employee result = dialog.getResult();
		if(result != null) //If an employee was created
			getCompany().addEmployee(result);
	}
	
	/**
	 * Bring the popup when a company want to be added.
	 *
	 * @param event The click event.
	 */
	public void addDepartment(ActionEvent event)
	{
		StandardDepartmentCreateDialog dialog = new StandardDepartmentCreateDialog(getCompany());
		dialog.initOwner(((Button) event.getSource()).getScene().getWindow());
		dialog.getScene().getStylesheets().add("jfx/base.css");
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
		StandardDepartment result = dialog.getResult();
		if(result != null) //If a standard department was created
			getCompany().addDepartment(result);
	}
	
	/**
	 * Remove a department from the list.
	 * This is done only if the department is empty.
	 *
	 * @param evt        The click event.
	 * @param department The department to remove.
	 */
	public void removeDepartment(ActionEvent evt, StandardDepartment department)
	{
		if(department != null) //If a department is selected
		{
			if(department.getEmployees().size() == 0)
				company.removeDepartment(department);
			else
			{
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Department not empty");
				alert.setHeaderText("Department not empty");
				alert.setContentText("The department must have no employee before being deleted");
				alert.showAndWait();
			}
		}
	}
	
	/**
	 * Bring the popup when a check want to be added.
	 *
	 * @param event The click event.
	 */
	public void addCheck(ActionEvent event)
	{
		CheckCreateDialog dialog = new CheckCreateDialog(company.getEmployees());
		dialog.initOwner(((Button) event.getSource()).getScene().getWindow());
		dialog.getScene().getStylesheets().add("jfx/base.css");
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
		EmployeeCheck result = dialog.getResult();
		if(result != null) //If a check was created
			result.getEmployee().addCheck(result);
	}
	
	/**
	 * Remove a check from the list.
	 *
	 * @param evt       The click event.
	 * @param checkList The check list.
	 */
	public void removeCheck(ActionEvent evt, CheckList checkList)
	{
		if(checkList.getSelectionModel().getSelectedItem() != null) //If a check is selected
		{
			EmployeeCheck check = checkList.getSelectionModel().getSelectedItem();
			Log.info("Removing check " + check);
			check.getEmployee().removeCheck(check);
		}
	}
	
	/**
	 * Called when the manager of a department is modified.
	 *
	 * @param event The change event.
	 */
	public void managerChanged(TableColumn.CellEditEvent<StandardDepartment, Manager> event)
	{
		event.getRowValue().setLeader(event.getNewValue()); //Update the manager of the company
	}
	
	/**
	 * Called when an employee change its department.
	 *
	 * @param event The change event.
	 */
	public void employeeDepartmentChanged(TableColumn.CellEditEvent<Employee, StandardDepartment> event)
	{
		if(event.getOldValue() == null || !event.getOldValue().equals(event.getNewValue()))
		{
			if(event.getOldValue() != null)
				event.getOldValue().removeEmployee(event.getRowValue());
			if(event.getNewValue() != null)
				event.getNewValue().addEmployee(event.getRowValue());
		}
	}
	
	/**
	 * Promote an employee to a manager status.
	 *
	 * @param event    The click event.
	 * @param employee The employee to promote.
	 */
	public void promoteEmployee(ActionEvent event, Employee employee)
	{
		if(employee instanceof Manager)
			return;
		Manager manager = new Manager(employee);
	}
	
	/**
	 * Remove an employee from the company.
	 *
	 * @param event    The click event.
	 * @param employee The employee to remove.
	 */
	public void removeEmployee(ActionEvent event, Employee employee)
	{
		if(employee != null)
			employee.getCompany().removeEmployee(employee);
	}
}
