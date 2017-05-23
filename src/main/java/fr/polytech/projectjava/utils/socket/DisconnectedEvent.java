package fr.polytech.projectjava.utils.socket;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-23
 */
public class DisconnectedEvent
{
	private final boolean errored;
	
	public boolean isErrored()
	{
		return errored;
	}
	
	public DisconnectedEvent(boolean errored)
	{
		this.errored = errored;
	}
}
