package fr.polytech.projectjava.jfx.dialogs.createcompany;

import fr.polytech.projectjava.company.Company;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class CompanyCreateDialog extends Stage
{
	private Company result;
	private TextField companyNameInput;
	private TextField bossFirstNameInput;
	private TextField bossLastNameInput;
	private CompanyCreateDialogController controller;
	
	public CompanyCreateDialog()
	{
		super();
		controller = new CompanyCreateDialogController(this);
		result = null;
		setTitle("Company creation");
		setScene(new Scene(buildStage()));
		sizeToScene();
	}
	
	private Parent buildStage()
	{
		VBox root = new VBox(2);
		
		Label companyNameLabel = new Label("Company name:");
		companyNameInput = new TextField();
		
		HBox companyNameBox = new HBox();
		companyNameBox.getChildren().addAll(companyNameLabel, companyNameInput);
		
		Label bossFirstNameLabel = new Label("Boss first name:");
		bossFirstNameInput = new TextField();
		
		HBox bossFirstNameBox = new HBox();
		bossFirstNameBox.getChildren().addAll(bossFirstNameLabel, bossFirstNameInput);
		
		Label bossLastNameLabel = new Label("Boss last name:");
		bossLastNameInput = new TextField();
		
		HBox bossLastNameBox = new HBox();
		bossLastNameBox.getChildren().addAll(bossLastNameLabel, bossLastNameInput);
		
		Button valid = new Button("OK");
		valid.setMaxWidth(Double.MAX_VALUE);
		valid.setOnAction(controller::valid);
		
		root.getChildren().addAll(companyNameBox, bossFirstNameBox, bossLastNameBox, valid);
		return root;
	}
	
	public String getBossFirstName()
	{
		return bossFirstNameInput.getText();
	}
	
	public String getBossLastName()
	{
		return bossLastNameInput.getText();
	}
	
	public String getCompanyName()
	{
		return companyNameInput.getText();
	}
	
	public Company getResult()
	{
		return result;
	}
	
	public void setResult(Company company)
	{
		result = company;
	}
}
