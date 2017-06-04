package fr.polytech.projectjava.mainapp.jfx.employee.create;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.staff.Employee;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A dialog to create an employee.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class EmployeeCreateDialog extends Stage
{
	private Employee result;
	private final EmployeeCreateDialogController controller;
	private TextArea firstNameText;
	private TextArea lastNameText;
	private CheckBox managerCheck;

	/**
	 * Constructor.
	 *
	 * @param company The company where to add the employee.
	 */
	public EmployeeCreateDialog(Company company)
	{
		super();
		controller = new EmployeeCreateDialogController(this, company);
		setTitle("Create employee");
		setScene(new Scene(buildStage()));
		sizeToScene();
	}

	/**
	 * Build the content.
	 *
	 * @return The root element.
	 */
	private Parent buildStage()
	{
		VBox root = new VBox(3);

		Button validButton = new Button("OK");
		validButton.setMaxWidth(Double.MAX_VALUE);
		validButton.setMaxHeight(Double.MAX_VALUE);
		validButton.setOnAction(controller::valid);
		validButton.setOnKeyPressed(evt -> {
			if(evt.getCode() == KeyCode.TAB)
			{
				firstNameText.requestFocus();
				evt.consume();
			}
		});

		Label firstNameLabel = new Label("First name: ");
		firstNameText = new TextArea();
		firstNameText.setMaxWidth(Double.MAX_VALUE);
		firstNameText.setPrefRowCount(1);
		firstNameText.setOnKeyPressed(evt -> {
			if(evt.getCode() == KeyCode.TAB)
			{
				lastNameText.requestFocus();
				evt.consume();
			}
		});
		HBox firstNameBox = new HBox();
		firstNameBox.getChildren().addAll(firstNameLabel, firstNameText);
		HBox.setHgrow(firstNameLabel, Priority.SOMETIMES);
		HBox.setHgrow(firstNameText, Priority.ALWAYS);

		Label lastNameLabel = new Label("Last name: ");
		lastNameText = new TextArea();
		lastNameText.setMaxWidth(Double.MAX_VALUE);
		lastNameText.setPrefRowCount(1);
		lastNameText.setOnKeyPressed(evt -> {
			if(evt.getCode() == KeyCode.TAB)
			{
				managerCheck.requestFocus();
				evt.consume();
			}
		});
		HBox lastNameBox = new HBox();
		lastNameBox.getChildren().addAll(lastNameLabel, lastNameText);
		HBox.setHgrow(lastNameLabel, Priority.SOMETIMES);
		HBox.setHgrow(lastNameText, Priority.ALWAYS);

		managerCheck = new CheckBox("Is manager?");
		managerCheck.setMaxWidth(Double.MAX_VALUE);
		managerCheck.setOnKeyPressed(evt -> {
			if(evt.getCode() == KeyCode.TAB)
			{
				validButton.requestFocus();
				evt.consume();
			}
		});

		root.getChildren().addAll(firstNameBox, lastNameBox, managerCheck, validButton);
		VBox.setVgrow(firstNameBox, Priority.NEVER);
		VBox.setVgrow(lastNameBox, Priority.NEVER);
		VBox.setVgrow(managerCheck, Priority.SOMETIMES);
		VBox.setVgrow(validButton, Priority.ALWAYS);

		return root;
	}

	/**
	 * Get the entered first name.
	 *
	 * @return The first name.
	 */
	public String getFirstName()
	{
		return firstNameText.getText();
	}

	/**
	 * Get the entered last name.
	 *
	 * @return The last name.
	 */
	public String getLastNameText()
	{
		return lastNameText.getText();
	}

	/**
	 * Get if the employee should be a manager.
	 *
	 * @return True if it is a manager, false else.
	 */
	public boolean getManagerCheck()
	{
		return managerCheck.isSelected();
	}

	/**
	 * Get the built employee.
	 *
	 * @return The employee.
	 */
	public Employee getResult()
	{
		return result;
	}

	/**
	 * Set the built employee.
	 *
	 * @param employee The built employee.
	 */
	void setResult(Employee employee)
	{
		result = employee;
	}
}
