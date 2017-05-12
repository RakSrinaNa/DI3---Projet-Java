package fr.polytech.projectjava.jfx.main;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 12/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-12
 */
public class MainTab extends Tab
{
	private Text companyNameArea;
	private Text bossNameArea;
	
	public MainTab(MainController controller)
	{
		super();
		setText("General information");
		setContent(buildContent(controller));
	}
	
	private Node buildContent(MainController controller)
	{
		VBox root = new VBox();
		
		Text companyNameLabel = new Text("Company name: ");
		companyNameArea = new Text("");
		TextFlow companyNameBox = new TextFlow(companyNameLabel, companyNameArea);
		
		Text bossNameLabel = new Text("Boss: ");
		bossNameArea = new Text("");
		TextFlow bossNameBox = new TextFlow(bossNameLabel, bossNameArea);
		
		root.getChildren().addAll(companyNameBox, bossNameBox);
		return root;
	}
	
	public StringProperty getBossNameTextProperty()
	{
		return bossNameArea.textProperty();
	}
	
	public StringProperty getCompanyNameTextProperty()
	{
		return companyNameArea.textProperty();
	}
}
