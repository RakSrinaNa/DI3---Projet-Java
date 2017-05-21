package fr.polytech.projectjava.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Represent a TCP Client.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public abstract class SocketBase extends SocketUtils implements Runnable
{
	/**
	 * Constructor.
	 *
	 * @param address The address to connect to.
	 *
	 * @throws IOException If an I/O error occurs when creating the socket.
	 */
	public SocketBase(InetSocketAddress address) throws IOException
	{
		super(new Socket(address.getAddress(), address.getPort()));
	}
	
	/**
	 * Constructor.
	 *
	 * @param socket TThe socket.
	 */
	protected SocketBase(Socket socket)
	{
		super(socket);
	}
	
	@Override
	public void run()
	{
		try
		{
			processData();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				disconnect();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Function called when the runnable is started.
	 * Disconnect is called after this method.
	 *
	 * @throws Exception If an error occurred.
	 */
	protected abstract void processData() throws Exception;
}
