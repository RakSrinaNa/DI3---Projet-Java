package fr.polytech.projectjava.checkingSimulation.jfx.components;

import fr.polytech.projectjava.checkingSimulation.CheckInfos;
import fr.polytech.projectjava.company.checking.CheckInOut;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Represent a list of checkings.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public class CheckList extends TableView<CheckInfos>
{
	/**
	 * Constructor.
	 */
	public CheckList()
	{
		super();
		setEditable(false);
		
		TableColumn columnEmployee = new TableColumn("Employee");
		
		TableColumn<CheckInfos, Number> columnEmployeeID = new TableColumn<>("ID");
		columnEmployeeID.setCellValueFactory(value -> value.getValue().getEmployee().IDProperty());
		
		TableColumn<CheckInfos, String> columnEmployeeName = new TableColumn<>("Name");
		columnEmployeeName.setCellValueFactory(value -> value.getValue().getEmployee().firstnameProperty().concat(" ").concat(value.getValue().getEmployee().lastnameProperty()));
		
		TableColumn<CheckInfos, CheckInOut.CheckType> columnCheckType = new TableColumn<>("In/Out");
		columnCheckType.setCellValueFactory(value -> value.getValue().checkTypeProperty());
		
		TableColumn<CheckInfos, String> columnDate = new TableColumn<>("Date");
		columnDate.setCellValueFactory(value -> value.getValue().dateProperty());
		
		//noinspection unchecked
		columnEmployee.getColumns().addAll(columnEmployeeID, columnEmployeeName);
		//noinspection unchecked
		getColumns().addAll(columnEmployee, columnCheckType, columnDate);
		
		setSortPolicy(p -> false);
		setItems(FXCollections.observableArrayList());
		
		Platform.runLater(() -> {
			setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		});
	}
}
