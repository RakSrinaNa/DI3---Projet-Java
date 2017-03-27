package fr.polytech.projectjava;

/**
 * Represent an exception when an argument is not valid.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 27/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-27
 */
public class InvalidArgumentException extends Exception
{
	/**
	 * Constructor.
	 *
	 * @param reason The reason of the exception.
	 */
	public InvalidArgumentException(String reason)
	{
		super(reason);
	}
}
