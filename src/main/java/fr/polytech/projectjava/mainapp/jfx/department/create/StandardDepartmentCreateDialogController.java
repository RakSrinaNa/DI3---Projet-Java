package fr.polytech.projectjava.mainapp.jfx.department.create;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
/**
 * Controller for the standard department creation dialog.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class StandardDepartmentCreateDialogController
{
	private final StandardDepartmentCreateDialog view;
	private final Company company;

	/**
	 * Constructor.
	 *
	 * @param view The view.
	 * @param company The company where to add the standard department.
	 */
	public StandardDepartmentCreateDialogController(StandardDepartmentCreateDialog view, Company company)
	{
		this.view = view;
		this.company = company;
	}

	/**
	 * Called when the standard department need to be created.
	 *
	 * @param actionEvent The click event.
	 */
	public void valid(ActionEvent actionEvent)
	{
		if(!view.getName().equals("")) //If all the mandatory elements are given
		{
			StandardDepartment department = new StandardDepartment(company, view.getName(), view.getManagerSelection());

			view.setResult(department);
			view.close();
		}
		else
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Standard department cannot be created");
			alert.setHeaderText("Standard department cannot be created");
			alert.setContentText("You need to give a name");
			alert.showAndWait();
		}
	}
}
