package fr.polytech.projectjava.mainapp.alerts;

import fr.polytech.projectjava.mainapp.jfx.MainController;
import java.time.LocalDate;

/**
 * Runnable to send monthly reports.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 06/06/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-06-06
 */
public class ReportAlerter implements Runnable
{
	private final MainController controller;
	
	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 */
	public ReportAlerter(MainController controller)
	{
		this.controller = controller;
	}
	
	@Override
	public void run()
	{
		if(LocalDate.now().getDayOfMonth() == 1) // If we're the fist of the month (so a new month started).
		{
			//TODO Monthly report
		}
	}
}
