package fr.polytech.projectjava.utils.socket;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-23
 */
public class DisconnectedEvent
{
	private final boolean result;
	
	/**
	 * Constructor.
	 *
	 * @param result The result of the processData function.
	 */
	public DisconnectedEvent(boolean result)
	{
		this.result = result;
	}
	
	/**
	 * Get the result of the disconnect.
	 *
	 * @return True if everything went fine, false else.
	 */
	public boolean isResult()
	{
		return result;
	}
}
