package fr.polytech.projectjava.jfx.main;

import fr.polytech.projectjava.ThreadCheckingReceiver;
import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.staff.Employee;
import fr.polytech.projectjava.jfx.dialogs.listemployees.ListEmployeesDialog;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class MainController
{
	private final MainModel model;
	private final MainApplication view;
	private ThreadCheckingReceiver socketReceiver;
	
	public MainController(MainApplication mainApplication)
	{
		view = mainApplication;
		model = new MainModel(mainApplication);
		socketReceiver = new ThreadCheckingReceiver(this);
	}
	
	public void close(WindowEvent windowEvent)
	{
		socketReceiver.interrupt();
		model.saveDatas();
	}
	
	public Collection<Employee> listEmployees()
	{
		return new ArrayList<>(); //TODO
	}
	
	public void addChecking(Pair<Integer, CheckInOut> integerCheckInOutPair)
	{
		//TODO
	}
	
	public void displayEmployees(ActionEvent actionEvent)
	{
		ListEmployeesDialog dialog = new ListEmployeesDialog(model.getCompany());
		dialog.initOwner(view.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
	}
	
	public void loadCompany()
	{
		if(model.loadCompany())
			socketReceiver.start();
	}
}
