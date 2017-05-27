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
	private boolean stop = false;
	
	/**
	 * Constructor.
	 *
	 * @param address The address to listen to.
	 *
	 * @throws IOException If an I/O error occurs when opening the socket.
	 */
	public ServerSocketBase(InetSocketAddress address) throws IOException
	{
		socket = new ServerSocket(address.getPort(), 0, address.getAddress());
	}
	
	/**
	 * Constructor.
	 *
	 * @param port The port to listen to.
	 *
	 * @throws IOException If an I/O error occurs when opening the socket.
	 */
	public ServerSocketBase(int port) throws IOException
	{
		socket = new ServerSocket(port);
	}
	
	@Override
	public void run()
	{
		while(!stop)
		{
			try
			{
				buildClient(this.socket.accept());
				Log.info("Server accepted client");
			}
			catch(SocketTimeoutException ignored)
			{
			}
			catch(Exception e)
			{
				Log.warning("Error in server socket", e);
			}
		}
		try
		{
			socket.close();
		}
		catch(IOException e)
		{
			Log.warning("Error closing server socket", e);
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
			Log.info("Stopping server... This may take up to " + (socket.getSoTimeout() / 1000) + "s");
		}
		catch(IOException e)
		{
			Log.error("", e);
		}
		stop = true;
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
