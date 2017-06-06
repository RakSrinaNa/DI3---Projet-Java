package fr.polytech.projectjava.mainapp.alerts;

import fr.polytech.projectjava.mainapp.jfx.MainController;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 06/06/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-06-06
 */
public class ScheduleAlerter implements Runnable
{
	private final MainController controller;
	
	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 */
	public ScheduleAlerter(MainController controller)
	{
		this.controller = controller;
	}
	
	@Override
	public void run()
	{
		controller.getCompany().getDepartements().forEach(department -> department.getEmployees().forEach(employee -> {
			//TODO Late check
		}));
	}
}
