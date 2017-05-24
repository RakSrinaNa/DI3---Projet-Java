package fr.polytech.projectjava.mainapp.jfx.main;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Represent the checks tab in the main window.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 12/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-12
 */
public class CheckTab extends Tab
{
	private CheckList checksList;
	
	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 */
	public CheckTab(MainController controller)
	{
		setText("Checks");
		setContent(buildContent(controller));
	}
	
	/**
	 * Build the tab content.
	 *
	 * @param controller The main controller.
	 *
	 * @return The root node.
	 */
	private Node buildContent(MainController controller)
	{
		VBox root = new VBox();
		
		HBox controls = new HBox();
		
		Button addCheckButton = new Button("Add check");
		addCheckButton.setOnAction(controller::addCheck);
		addCheckButton.setMaxWidth(Double.MAX_VALUE);
		
		Button removeCheckButton = new Button("Remove check");
		removeCheckButton.setOnAction(evt -> controller.removeCheck(evt, checksList));
		removeCheckButton.setMaxWidth(Double.MAX_VALUE);
		
		controls.getChildren().addAll(addCheckButton, removeCheckButton);
		HBox.setHgrow(addCheckButton, Priority.ALWAYS);
		HBox.setHgrow(removeCheckButton, Priority.ALWAYS);
		
		checksList = new CheckList(controller);
		checksList.setMaxHeight(Double.MAX_VALUE);
		
		root.getChildren().addAll(checksList, controls);
		VBox.setVgrow(checksList, Priority.ALWAYS);
		return root;
	}
	
	
	/**
	 * Get the check list.
	 *
	 * @return The check list.
	 */
	public CheckList getList()
	{
		return checksList;
	}
}
