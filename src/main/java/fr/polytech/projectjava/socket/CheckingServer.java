package fr.polytech.projectjava.socket;

import fr.polytech.projectjava.jfx.main.MainController;
import fr.polytech.projectjava.utils.Configuration;
import fr.polytech.projectjava.utils.Log;
import fr.polytech.projectjava.utils.ServerSocketBase;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * Server for the checking app.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 21/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-21
 */
public class CheckingServer extends ServerSocketBase
{
	private final MainController controller;
	
	/**
	 * Constructor.
	 *
	 * @param controller The main controller.
	 *
	 * @throws IOException If an I/O error occurs when opening the socket.
	 */
	public CheckingServer(MainController controller) throws IOException
	{
		super(new InetSocketAddress(Configuration.getString("serverAddress"), Configuration.getInt("serverPort")));
		this.controller = controller;
		setTimeout(20000);
	}
	
	@Override
	protected void buildClient(Socket socket)
	{
		try
		{
			new Thread(new CheckingClient(socket, this)).start();
		}
		catch(SocketException e)
		{
			Log.error("Error building server socket", e);
		}
	}
	
	/**
	 * Get the main controller.
	 *
	 * @return The controller.
	 */
	public MainController getController()
	{
		return controller;
	}
}
