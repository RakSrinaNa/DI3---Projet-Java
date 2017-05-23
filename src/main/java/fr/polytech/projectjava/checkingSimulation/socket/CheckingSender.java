package fr.polytech.projectjava.checkingSimulation.socket;

import fr.polytech.projectjava.checkingSimulation.CheckInfos;
import fr.polytech.projectjava.utils.Configuration;
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
		super(new InetSocketAddress(Configuration.getString("serverAddress"), Configuration.getInt("serverPort")));
		setTimeout(5000);
		this.datas = datas;
	}
	
	@Override
	protected boolean processData() throws Exception
	{
		int packetSize = Configuration.getInt("socketPacketSize");
		
		while(datas.hasNext())
		{
			sendPacket("CHECK".getBytes());
			
			byte[] response = receivePacket(packetSize);
			if(response == null || !new String(response).equals("OK"))
				return false;
			
			sendPacket(datas.next().getForSocket().getBytes());
			
			response = receivePacket(packetSize);
			if(response == null || !new String(response).equals("OK"))
				return false;
			
			datas.remove();
		}
		
		sendPacket("END".getBytes());
		return true;
	}
}
