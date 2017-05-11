package fr.polytech.projectjava.jfx.main;

import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.jfx.dialogs.createcompany.CompanyCreateDialog;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import java.io.*;
import java.util.Optional;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class MainModel
{
	private static final String FILENAME = "tCompany.pjv";
	
	private final MainApplication view;
	private Company company;
	
	public MainModel(MainApplication mainApplication)
	{
		this.view = mainApplication;
	}
	
	private Company buildNewCompany()
	{
		CompanyCreateDialog dialog = new CompanyCreateDialog();
		dialog.initOwner(view.getStage());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.showAndWait();
		return dialog.getResult();
	}
	
	private Optional<Company> loadLastCompany()
	{
		File f = new File(FILENAME);
		if(f.exists() && f.isFile())
		{
			Company company = null;
			try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f)))
			{
				company = (Company) ois.readObject();
			}
			catch(IOException | ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			return Optional.ofNullable(company);
		}
		return Optional.empty();
	}
	
	public void saveDatas()
	{
		if(company != null)
		{
			try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(FILENAME))))
			{
				oos.writeObject(company);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public boolean loadCompany()
	{
		Optional<Company> companyOptional = loadLastCompany();
		company = companyOptional.orElseGet(this::buildNewCompany);
		if(company == null)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("No company loaded");
			alert.setHeaderText("No company loaded");
			alert.setContentText("A company need to be loaded or create in order to work.");
			alert.showAndWait();
			view.getStage().close();
			return false;
		}
		view.getCompanyNameTextProperty().bind(company.nameProperty());
		return true;
	}
	
	public Company getCompany()
	{
		return company;
	}
}
