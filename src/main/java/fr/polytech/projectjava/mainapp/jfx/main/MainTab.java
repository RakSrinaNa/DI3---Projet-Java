package fr.polytech.projectjava.mainapp.jfx.main;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * Represent the main tab in the main window.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 12/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-12
 */
public class MainTab extends Tab
{
	private Text companyNameArea;
	private Text bossNameArea;
	private Text employeeCountArea;
	private Text departmentCountArea;
	
	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 */
	public MainTab(MainController controller)
	{
		super();
		setText("General information");
		setContent(buildContent(controller));
	}
	
	/**
	 * Build the tab content.
	 *
	 * @param controller The main controller.
	 *
	 * @return The root note.
	 */
	private Node buildContent(MainController controller)
	{
		VBox root = new VBox(10);
		root.setPadding(new Insets(20, 0, 20, 0));
		
		companyNameArea = new Text("");
		companyNameArea.setStyle("-fx-font-size: 50;");
		TextFlow companyNameBox = new TextFlow(companyNameArea);
		companyNameBox.setTextAlignment(TextAlignment.CENTER);
		companyNameBox.setMaxWidth(Double.MAX_VALUE);
		
		Text bossNameLabel = new Text("Boss: ");
		bossNameArea = new Text("");
		TextFlow bossNameBox = new TextFlow(bossNameLabel, bossNameArea);
		bossNameBox.setTextAlignment(TextAlignment.CENTER);
		bossNameBox.setMaxWidth(Double.MAX_VALUE);
		
		Text employeeCountLabel = new Text("Employee count: ");
		employeeCountArea = new Text("");
		TextFlow employeeCountBox = new TextFlow(employeeCountLabel, employeeCountArea);
		employeeCountBox.setTextAlignment(TextAlignment.CENTER);
		employeeCountBox.setMaxWidth(Double.MAX_VALUE);
		
		Text departmentCountLabel = new Text("Department count: ");
		departmentCountArea = new Text("");
		TextFlow departmentCountBox = new TextFlow(departmentCountLabel, departmentCountArea);
		departmentCountBox.setTextAlignment(TextAlignment.CENTER);
		departmentCountBox.setMaxWidth(Double.MAX_VALUE);
		
		root.getChildren().addAll(companyNameBox, bossNameBox, employeeCountBox, departmentCountBox);
		return root;
	}
	
	/**
	 * Get the boss name property.
	 *
	 * @return The boss name property.
	 */
	public StringProperty getBossNameTextProperty()
	{
		return bossNameArea.textProperty();
	}
	
	/**
	 * Get the company name property.
	 *
	 * @return The company name property.
	 */
	public StringProperty getCompanyNameTextProperty()
	{
		return companyNameArea.textProperty();
	}
	
	/**
	 * Get the employee count property.
	 *
	 * @return The employee count property.
	 */
	public StringProperty getEmployeeCountTextProperty()
	{
		return employeeCountArea.textProperty();
	}
	
	/**
	 * Get the department count property.
	 *
	 * @return The department count property.
	 */
	public StringProperty getDepartmentCountTextProperty()
	{
		return departmentCountArea.textProperty();
	}
}
