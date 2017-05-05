package fr.polytech.projectjava.checkingSimulation.jfx.components;

import fr.polytech.projectjava.checkingSimulation.CheckInfos;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public class CheckList extends TableView<CheckInfos>
{
	public CheckList(ObservableList<CheckInfos> checkings)
	{
		super(checkings);
		setEditable(false);
		
		TableColumn columnEmployee = new TableColumn("Employee");
		
		TableColumn<CheckInfos, Number> columnEmployeeID = new TableColumn<>("ID");
		columnEmployeeID.setCellValueFactory(value -> new SimpleIntegerProperty(value.getValue().getEmployee().getID()));
		
		TableColumn<CheckInfos, String> columnEmployeeName = new TableColumn<>("Name");
		columnEmployeeName.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEmployee().getName()));
		
		TableColumn<CheckInfos, String> columnCheckType = new TableColumn<>("In/Out");
		columnCheckType.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getCheckType().toString()));
		
		TableColumn<CheckInfos, String> columnDate = new TableColumn<>("Date");
		columnDate.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getFormattedCheckDate()));
		
		columnEmployee.getColumns().addAll(columnEmployeeID, columnEmployeeName);
		getColumns().addAll(columnEmployee, columnCheckType, columnDate);
		
		setItems(checkings);
	}
}
