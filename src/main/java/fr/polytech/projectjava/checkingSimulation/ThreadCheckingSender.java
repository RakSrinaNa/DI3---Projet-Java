package fr.polytech.projectjava.checkingSimulation;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class ThreadCheckingSender extends Thread
{
	public ThreadCheckingSender()
	{
		setName("ThreadCheckingReceiver");
		setDaemon(true);
	}
}
