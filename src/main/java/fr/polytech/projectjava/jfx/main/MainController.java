package fr.polytech.projectjava.jfx.main;

import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.staff.Employee;
import fr.polytech.projectjava.jfx.dialogs.createcompany.CompanyCreateDialog;
import fr.polytech.projectjava.jfx.dialogs.employee.EmployeeDialog;
import fr.polytech.projectjava.socket.CheckingServer;
import fr.polytech.projectjava.utils.Configuration;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import java.io.*;
import java.util.Optional;

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
		socketReceiver.stop();
		saveDatas();
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
	 * Add a check.
	 *
	 * @param employeeID The employee ID.
	 * @param check      The check to add.
	 *
	 * @return True if the check was added, false else.
	 */
	public boolean addChecking(int employeeID, CheckInOut check)
	{
		Optional<Employee> employee = getCompany().getEmployee(employeeID);
		if(employee.isPresent())
		{
			employee.get().addCheckInOut(check);
			return true;
		}
		return false;
	}
	
	/**
	 * Handle a click on an employee in the list.
	 *
	 * @param event The click event.
	 */
	public void employeeClick(MouseEvent event)
	{
		if(event.getClickCount() >= 2 && event.getSource() instanceof TableRow)
		{
			TableRow source = (TableRow) event.getSource();
			if(source.getItem() instanceof Employee)
			{
				Employee employee = (Employee) source.getItem();
				if(event.getButton() == MouseButton.PRIMARY  && event.isAltDown())
				{
					((TableRow) event.getSource()).getScene();
					EmployeeDialog dialog = new EmployeeDialog(employee);
					dialog.initOwner(((TableRow) event.getSource()).getScene().getWindow());
					dialog.initModality(Modality.APPLICATION_MODAL);
					dialog.showAndWait();
				}
			}
		}
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
	
	private Company company;
	
	/**
	 * Builds a new company.
	 *
	 * @return The created company.
	 */
	private Company buildNewCompany()
	{
		CompanyCreateDialog dialog = new CompanyCreateDialog();
		dialog.initOwner(parent.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
		return dialog.getResult();
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
			Company company = null;
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f)))
			{
				company = (Company) ois.readObject();
			}
			catch(IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			return Optional.ofNullable(company);
		}
		return Optional.empty();
	}
	
	/**
	 * Save the current company.
	 */
	public void saveDatas()
	{
		if(company != null)
		{
			try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(Configuration.getString("mainSaveFile")))))
			{
				oos.writeObject(company);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
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
		if(company == null)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("No company loaded");
			alert.setHeaderText("No company loaded");
			alert.setContentText("A company need to be loaded or create in order to work.");
			alert.showAndWait();
			parent.getStage().close();
			return false;
		}
		parent.getMainTab().getCompanyNameTextProperty().bind(company.nameProperty());
		parent.getMainTab().getBossNameTextProperty().bind(company.getBoss().fullNameProperty());
		parent.getEmployeeTab().getList().setList(company.getEmployees());
		parent.getEmployeeTab().getDepartmentFilter().setItems(company.getDepartements());
		return true;
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
}
