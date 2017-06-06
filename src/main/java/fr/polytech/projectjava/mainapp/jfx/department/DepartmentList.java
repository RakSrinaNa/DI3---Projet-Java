package fr.polytech.projectjava.mainapp.jfx.department;

import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
import fr.polytech.projectjava.mainapp.jfx.MainController;
import fr.polytech.projectjava.utils.jfx.SortedTableView;
import fr.polytech.projectjava.utils.jfx.StringTextFieldTableCell;
import javafx.scene.control.TableColumn;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 24/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-24
 */
public class DepartmentList extends SortedTableView<StandardDepartment>
{
	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 */
	public DepartmentList(MainController controller)
	{
		super();
		
		int colCount = 3;
		int padding = 2;
		
		setEditable(true);

		TableColumn<StandardDepartment, String> columnName = new TableColumn<>("Name");
		columnName.setCellValueFactory(value -> value.getValue().nameProperty());
		columnName.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		columnName.setCellFactory(list -> new StringTextFieldTableCell<>(StandardDepartment::isValidState));
		columnName.setEditable(true);

		TableColumn<StandardDepartment, Number> columnCount = new TableColumn<>("Employee count");
		columnCount.setCellValueFactory(value -> value.getValue().memberCountProperty());
		columnCount.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<StandardDepartment, Manager> columnManager = new TableColumn<>("Manager");
		columnManager.setCellValueFactory(value -> value.getValue().leaderProperty());
		columnManager.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		columnManager.setCellFactory(list -> new ManagerComboBoxTableCell(controller.getCompany().getManagers()));
		columnManager.setEditable(true);
		columnManager.setOnEditCommit(controller::managerChanged);
		
		//noinspection unchecked
		getColumns().addAll(columnName, columnCount, columnManager);
	}
}
