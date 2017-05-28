package fr.polytech.projectjava.utils.socket;

import fr.polytech.projectjava.utils.Log;
import java.io.IOException;
import java.net.*;

/**
 * Represent a TCP server.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public abstract class ServerSocketBase implements Runnable
{
	private final ServerSocket socket;
	private final String name;
	private boolean stop = false;
	
	/**
	 * Constructor.
	 *
	 * @param name    The name of the socket.
	 * @param address The address to listen to.
	 *
	 * @throws IOException If an I/O error occurs when opening the socket.
	 */
	public ServerSocketBase(String name, InetSocketAddress address) throws IOException
	{
		this.name = name;
		socket = new ServerSocket(address.getPort(), 0, address.getAddress());
	}
	
	/**
	 * Constructor.
	 *
	 * @param name The name of the socket.
	 * @param port The port to listen to.
	 *
	 * @throws IOException If an I/O error occurs when opening the socket.
	 */
	public ServerSocketBase(String name, int port) throws IOException
	{
		this.name = name;
		socket = new ServerSocket(port);
	}
	
	@Override
	public void run()
	{
		Log.info("Starting server " + getName());
		while(!stop)
		{
			try
			{
				buildClient(this.socket.accept());
				Log.info("Server " +getName() + " accepted client");
			}
			catch(SocketTimeoutException ignored)
			{
			}
			catch(Exception e)
			{
				Log.warning("Error in server " + getName(), e);
			}
		}
		try
		{
			Log.info("Closing server " + getName());
			socket.close();
		}
		catch(IOException e)
		{
			Log.warning("Error closing server " + getName(), e);
		}
	}
	
	/**
	 * Function called when a client connects.
	 *
	 * @param socket The socket created.
	 */
	protected abstract void buildClient(Socket socket);
	
	/**
	 * Stop the runnable.
	 */
	public void stop()
	{
		try
		{
			Log.info("Stopping server " + getName() + "... This may take up to " + (socket.getSoTimeout() / 1000) + "s");
		}
		catch(IOException e)
		{
			Log.error("", e);
		}
		stop = true;
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
	
	/**
	 * Set the server timeout.
	 *
	 * @param duration The timeout.
	 *
	 * @throws SocketException If there is an error in the underlying protocol, such as a TCP error.
	 */
	protected void setTimeout(int duration) throws SocketException
	{
		socket.setSoTimeout(duration);
	}
}
