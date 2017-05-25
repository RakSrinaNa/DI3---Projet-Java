package fr.polytech.projectjava.mainapp.jfx.main.department;

import fr.polytech.projectjava.mainapp.company.departments.StandardDepartment;
import fr.polytech.projectjava.mainapp.company.staff.Manager;
import fr.polytech.projectjava.mainapp.jfx.main.MainController;
import fr.polytech.projectjava.utils.jfx.SortedTableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;

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
		
		int colCount = 2;
		int padding = 2;
		
		setEditable(false);
		
		TableColumn<StandardDepartment, String> columnName = new TableColumn<>("Name");
		columnName.setCellValueFactory(value -> value.getValue().nameProperty());
		columnName.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<StandardDepartment, Manager> columnManager = new TableColumn<>("Manager");
		columnManager.setCellValueFactory(value -> value.getValue().leaderProperty());
		columnManager.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		//noinspection unchecked
		getColumns().addAll(columnName, columnManager);
		
		this.setRowFactory(tv -> {
			TableRow<StandardDepartment> row = new TableRow<>();
			row.setOnMouseClicked(controller::departmentClick);
			return row;
		});
	}
}
