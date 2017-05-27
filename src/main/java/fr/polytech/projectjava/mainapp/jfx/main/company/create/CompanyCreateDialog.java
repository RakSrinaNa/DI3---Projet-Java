package fr.polytech.projectjava.mainapp.jfx.main.company.create;

import fr.polytech.projectjava.mainapp.company.Company;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Dialog window to create a company.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class CompanyCreateDialog extends Stage
{
	private final CompanyCreateDialogController controller;
	private Company result;
	private TextField companyNameInput;
	private TextField bossFirstNameInput;
	private TextField bossLastNameInput;
	
	/**
	 * Constructor.
	 */
	public CompanyCreateDialog()
	{
		super();
		controller = new CompanyCreateDialogController(this);
		result = null;
		setTitle("Company creation");
		setScene(new Scene(buildStage()));
		sizeToScene();
	}
	
	/**
	 * Build the window elements.
	 *
	 * @return The root element.
	 */
	private Parent buildStage()
	{
		VBox root = new VBox(2);
		
		Label companyNameLabel = new Label("Company name:");
		companyNameInput = new TextField();
		companyNameInput.setMaxWidth(Double.MAX_VALUE);
		
		HBox companyNameBox = new HBox();
		companyNameBox.getChildren().addAll(companyNameLabel, companyNameInput);
		HBox.setHgrow(companyNameInput, Priority.ALWAYS);
		
		Label bossFirstNameLabel = new Label("Boss first name:");
		bossFirstNameInput = new TextField();
		bossFirstNameInput.setMaxWidth(Double.MAX_VALUE);
		
		HBox bossFirstNameBox = new HBox();
		bossFirstNameBox.getChildren().addAll(bossFirstNameLabel, bossFirstNameInput);
		HBox.setHgrow(bossFirstNameInput, Priority.ALWAYS);
		
		Label bossLastNameLabel = new Label("Boss last name:");
		bossLastNameInput = new TextField();
		bossLastNameInput.setMaxWidth(Double.MAX_VALUE);
		
		HBox bossLastNameBox = new HBox();
		bossLastNameBox.getChildren().addAll(bossLastNameLabel, bossLastNameInput);
		HBox.setHgrow(bossLastNameInput, Priority.ALWAYS);
		
		Button valid = new Button("OK");
		valid.setMaxWidth(Double.MAX_VALUE);
		valid.setOnAction(controller::valid);
		
		root.getChildren().addAll(companyNameBox, bossFirstNameBox, bossLastNameBox, valid);
		return root;
	}
	
	/**
	 * Get the first name of the boss.
	 *
	 * @return The boss' first name.
	 */
	public String getBossFirstName()
	{
		return bossFirstNameInput.getText();
	}
	
	/**
	 * Get the last name of the boss.
	 *
	 * @return The boss' last name.
	 */
	public String getBossLastName()
	{
		return bossLastNameInput.getText();
	}
	
	/**
	 * Get the company name.
	 *
	 * @return The company name.
	 */
	public String getCompanyName()
	{
		return companyNameInput.getText();
	}
	
	/**
	 * Get the built company.
	 *
	 * @return The company.
	 */
	public Company getResult()
	{
		return result;
	}
	
	/**
	 * Set the built company.
	 *
	 * @param company The built company.
	 */
	public void setResult(Company company)
	{
		result = company;
	}
}
