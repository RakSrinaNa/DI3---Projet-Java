package fr.polytech.projectjava.utils.socket;

import fr.polytech.projectjava.utils.Log;
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
	private final String name;
	
	/**
	 * Constructor.
	 *
	 * @param name    The name of the socket.
	 * @param address The address to connect to.
	 *
	 * @throws IOException If an I/O error occurs when creating the socket.
	 */
	public SocketBase(String name, InetSocketAddress address) throws IOException
	{
		this(name, new Socket(address.getAddress(), address.getPort()));
	}
	
	/**
	 * Constructor.
	 *
	 * @param name   The name of the socket.
	 * @param socket TThe socket.
	 */
	protected SocketBase(String name, Socket socket)
	{
		super(socket);
		this.name = name;
		disconnectListeners = new ArrayList<>();
	}
	
	@Override
	public void run()
	{
		Log.info("Starting client " + getName());
		boolean error;
		try
		{
			error = processData();
		}
		catch(Exception e)
		{
			Log.warning("Error during client " + getName(), e);
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
				Log.warning("Error disconnecting client " + getName(), e);
			}
		}
		boolean finalError = error;
		disconnectListeners.forEach(listener -> listener.onSocketDisconnected(new DisconnectedEvent(finalError)));
	}
	
	/**
	 * Disconnect the socket.
	 *
	 * @throws IOException If an I/O error occurs when closing this socket.
	 */
	void disconnect() throws IOException
	{
		Log.info("Closing client " + getName());
		if(!socket.isClosed())
			socket.close();
	}
	
	/**
	 * Function called when the runnable is started.
	 * Disconnect is called after this method.
	 *
	 * @return True if everything went fine, false else.
	 *
	 * @throws Exception If an error occurred.
	 */
	protected abstract boolean processData() throws Exception;
	
	/**
	 * Register a SocketDisconnectedListener/
	 *
	 * @param listener The listener to add.
	 */
	public void addFinishedListener(SocketDisconnectedListener listener)
	{
		disconnectListeners.add(listener);
	}
	
	/**
	 * Get the name of the socket.
	 *
	 * @return The socket name.
	 */
	public String getName()
	{
		return name;
	}
}
