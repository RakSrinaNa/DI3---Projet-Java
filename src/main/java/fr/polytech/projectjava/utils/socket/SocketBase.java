package fr.polytech.projectjava.utils.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

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
	private final ArrayList<SocketDisconnectedListener> disconnectListeners;
	
	/**
	 * Constructor.
				*
	 * @param address The address to connect to.
	 *
	 * @throws IOException If an I/O error occurs when creating the socket.
			*/
	public SocketBase(InetSocketAddress address) throws IOException
		{
			this(new Socket(address.getAddress(), address.getPort()));
		}
		
		/**
		 * Constructor.
		 *
		 * @param socket TThe socket.
		 */
	protected SocketBase(Socket socket)
		{
			super(socket);
			disconnectListeners = new ArrayList<>();
	}
	
	@Override
	public void run()
	{
		boolean error = false;
		try
		{
			error = processData();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			error = true;
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
		boolean finalError = error;
		disconnectListeners.forEach(listener -> listener.onSocketDisconnected(new DisconnectedEvent(finalError)));
	}
	
	/**
	 * Function called when the runnable is started.
	 * Disconnect is called after this method.
	 *
	 * @throws Exception If an error occurred.
	 */
	protected abstract boolean processData() throws Exception;
	
	public void addFinishedListener(SocketDisconnectedListener listener)
	{
		disconnectListeners.add(listener);
	}
}
