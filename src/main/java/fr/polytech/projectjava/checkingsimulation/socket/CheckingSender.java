package fr.polytech.projectjava.checkingsimulation.socket;

import fr.polytech.projectjava.checkingsimulation.CheckInfos;
import fr.polytech.projectjava.utils.Configuration;
import fr.polytech.projectjava.utils.Log;
import fr.polytech.projectjava.utils.socket.SocketBase;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Iterator;

/**
 * The socket client sending the checks to the server.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class CheckingSender extends SocketBase
{
	private final static Object LOCK = new Object();
	private final Iterator<CheckInfos> datas;
	
	/**
	 * Constructor.
	 *
	 * @param datas An iterator of the checkings to send.
	 *
	 * @throws IOException If an error occurred creating the client.
	 */
	public CheckingSender(Iterator<CheckInfos> datas) throws IOException
	{
		super("Checking client", new InetSocketAddress(Configuration.getString("serverAddress"), Configuration.getInt("serverPort")));
		setTimeout(Configuration.getInt("simulationCheckTimeout"));
		this.datas = datas;
	}
	
	@Override
	protected boolean processData() throws Exception
	{
		synchronized(LOCK)
		{
			int packetSize = Configuration.getInt("socketPacketSize");
			
			while(datas.hasNext())
			{
				sendPacket("CHECK".getBytes()); // Tell the server we went to send a check
				
				byte[] response = receivePacket(packetSize);
				if(response == null || !new String(response).equals("OK")) // If the server didn't agree
					return false;
				
				CheckInfos check = datas.next();
				Log.info("Sending " + check);
				sendPacket(check.getForSocket().getBytes()); // Send the check
				
				response = receivePacket(packetSize);
				if(response == null || !new String(response).equals("OK")) //Wait acknowledgement
					return false;
				
				datas.remove();
			}
			
			sendPacket("END".getBytes()); // Tell the server we're done
		}
		return true;
	}
}
