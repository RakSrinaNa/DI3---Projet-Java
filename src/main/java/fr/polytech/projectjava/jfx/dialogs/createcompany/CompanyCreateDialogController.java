package fr.polytech.projectjava.jfx.dialogs.createcompany;

import fr.polytech.projectjava.company.Company;
import fr.polytech.projectjava.company.staff.Boss;
import javafx.event.ActionEvent;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/04/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-04-27
 */
public class CompanyCreateDialogController
{
	private final CompanyCreateDialog view;
	
	public CompanyCreateDialogController(CompanyCreateDialog view)
	{
		this.view = view;
	}
	
	public void valid(ActionEvent actionEvent)
	{
		if(!view.getCompanyName().equals("") && !view.getBossLastName().equals("") && !view.getBossFirstName().equals(""))
		{
			view.setResult(new Company(view.getCompanyName(), new Boss(view.getBossLastName(), view.getBossFirstName())));
			view.close();
		}
	}
}
