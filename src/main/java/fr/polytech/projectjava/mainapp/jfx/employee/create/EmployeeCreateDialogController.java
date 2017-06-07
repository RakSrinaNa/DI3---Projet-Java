package fr.polytech.projectjava.mainapp.jfx.employee.create;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
/**
 * Controller for the employee creation dialog.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class EmployeeCreateDialogController
{
	private final EmployeeCreateDialog view;
	private final Company company;

	/**
	 * Constructor.
	 *
	 * @param view The view.
	 * @param company The company where to add the employee.
	 */
	public EmployeeCreateDialogController(EmployeeCreateDialog view, Company company)
	{
		this.view = view;
		this.company = company;
	}

	/**
	 * Called when the employee need to be created.
	 *
	 * @param actionEvent The click event.
	 */
	public void valid(ActionEvent actionEvent)
	{
		if(!view.getFirstName().equals("") && !view.getLastName().equals("")) //If all the mandatory elements are given
		{
			Employee employee;
			if(view.getManagerCheck())
				employee = new Manager(company, view.getLastName(), view.getFirstName());
			else
				employee = new Employee(company, view.getLastName(), view.getFirstName());
			view.setResult(employee);
			view.close();
		}
		else
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Employee cannot be created");
			alert.setHeaderText("Employee cannot be created");
			alert.setContentText("You need to give a first and last name");
			alert.showAndWait();
		}
	}
}
