package fr.polytech.projectjava.mainapp.jfx.department.create;

import fr.polytech.projectjava.mainapp.company.Company;
import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A dialog displaying an employee.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class StandardDepartmentCreateDialog extends Stage
{
	private StandardDepartment result;
	private final StandardDepartmentCreateDialogController controller;
	private TextArea nameText;
	private ComboBox<Manager> managerSelection;

	/**
	 * Constructor.
	 *
	 * @param company The company where to add the standard department.
	 */
	public StandardDepartmentCreateDialog(Company company)
	{
		super();
		controller = new StandardDepartmentCreateDialogController(this, company);
		setTitle("Create employee");
		setScene(new Scene(buildStage(company.getManagers())));
		sizeToScene();
	}

	/**
	 * Build the content.
	 *
	 * @param managers The managers of the company.
	 * @return The root element.
	 */
	private Parent buildStage(ObservableList<Manager> managers)
	{
		VBox root = new VBox(3);

		Button validButton = new Button("OK");
		validButton.setMaxWidth(Double.MAX_VALUE);
		validButton.setMaxHeight(Double.MAX_VALUE);
		validButton.setOnAction(controller::valid);
		validButton.setOnKeyPressed(evt -> {
			if(evt.getCode() == KeyCode.TAB)
			{
				nameText.requestFocus();
				evt.consume();
			}
		});

		Label nameLabel = new Label("Name: ");
		nameText = new TextArea();
		nameText.setMaxWidth(Double.MAX_VALUE);
		nameText.setPrefRowCount(1);
		nameText.setOnKeyPressed(evt -> {
			if(evt.getCode() == KeyCode.TAB)
			{
				managerSelection.requestFocus();
				evt.consume();
			}
		});
		HBox nameBox = new HBox();
		nameBox.getChildren().addAll(nameLabel, nameText);
		HBox.setHgrow(nameLabel, Priority.SOMETIMES);
		HBox.setHgrow(nameText, Priority.ALWAYS);

		managerSelection = new ComboBox<>(managers);
		managerSelection.setMaxWidth(Double.MAX_VALUE);
		managerSelection.setOnKeyPressed(evt -> {
			if(evt.getCode() == KeyCode.TAB)
			{
				validButton.requestFocus();
				evt.consume();
			}
			else if(evt.getCode() == KeyCode.SPACE && evt.isControlDown())
			{
				((ComboBox) evt.getSource()).getSelectionModel().clearSelection();
				evt.consume();
			}
		});
		managerSelection.setOnMouseClicked(evt -> {
			if(evt.getButton() == MouseButton.PRIMARY && evt.isControlDown())
			{
				((ComboBox) evt.getSource()).getSelectionModel().clearSelection();
				evt.consume();
			}
		});

		root.getChildren().addAll(nameBox, managerSelection, validButton);
		VBox.setVgrow(nameBox, Priority.NEVER);
		VBox.setVgrow(managerSelection, Priority.SOMETIMES);
		VBox.setVgrow(validButton, Priority.ALWAYS);

		return root;
	}

	/**
	 * Get the entered name.
	 *
	 * @return The name.
	 */
	public String getName()
	{
		return nameText.getText();
	}

	/**
	 * Get the selected manager.
	 *
	 * @return The selected manager.
	 */
	public Manager getManagerSelection()
	{
		return managerSelection.getSelectionModel().getSelectedItem();
	}

	/**
	 * Get the built standard department.
	 *
	 * @return The standard department.
	 */
	public StandardDepartment getResult()
	{
		return result;
	}

	/**
	 * Set the built standard department.
	 *
	 * @param department The built standard department.
	 */
	void setResult(StandardDepartment department)
	{
		result = department;
	}
}
