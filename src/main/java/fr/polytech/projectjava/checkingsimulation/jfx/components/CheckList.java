package fr.polytech.projectjava.checkingsimulation.jfx.components;

import fr.polytech.projectjava.checkingsimulation.CheckInfos;
import fr.polytech.projectjava.mainapp.company.staff.checking.CheckInOut;
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
		
		int colCount = 4;
		int padding = 2;
		
		setEditable(false);
		
		TableColumn columnEmployee = new TableColumn("Employee");
		
		TableColumn<CheckInfos, Number> columnEmployeeID = new TableColumn<>("ID");
		columnEmployeeID.setCellValueFactory(value -> value.getValue().getEmployee().IDProperty());
		columnEmployeeID.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<CheckInfos, String> columnEmployeeName = new TableColumn<>("Name");
		columnEmployeeName.setCellValueFactory(value -> value.getValue().getEmployee().firstnameProperty().concat(" ").concat(value.getValue().getEmployee().lastnameProperty()));
		columnEmployeeName.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<CheckInfos, CheckInOut.CheckType> columnCheckType = new TableColumn<>("In/Out");
		columnCheckType.setCellValueFactory(value -> value.getValue().checkTypeProperty());
		columnCheckType.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
		TableColumn<CheckInfos, String> columnDate = new TableColumn<>("Date");
		columnDate.setCellValueFactory(value -> value.getValue().dateProperty());
		columnDate.prefWidthProperty().bind(widthProperty().subtract(padding).divide(colCount));
		
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
