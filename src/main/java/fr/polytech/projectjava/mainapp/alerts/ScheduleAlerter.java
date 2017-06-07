package fr.polytech.projectjava.mainapp.alerts;

import fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck;
import fr.polytech.projectjava.mainapp.jfx.MainController;
import java.time.LocalDate;

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
			EmployeeCheck check = employee.getCheckForDate(LocalDate.now());
			if(Math.abs(check.getArrivalOffset().getMinutes()) >= 30)
				check.notifyManagerArrival();
			if(Math.abs(check.getDepartureOffset().getMinutes()) >= 30)
				check.notifyManagerDeparture();
		}));
	}
}
