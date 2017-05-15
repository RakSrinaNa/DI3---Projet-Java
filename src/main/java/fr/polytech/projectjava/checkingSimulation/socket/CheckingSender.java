package fr.polytech.projectjava.checkingSimulation.socket;

import fr.polytech.projectjava.checkingSimulation.CheckInfos;
import fr.polytech.projectjava.utils.Configuration;
import fr.polytech.projectjava.utils.UDPClientBuilder;
import javafx.util.Pair;
import java.io.IOException;
import java.net.DatagramPacket;
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
public class CheckingSender extends UDPClientBuilder
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
		super();
		setTimeout(10000);
		this.datas = datas;
	}
	
	@Override
	protected void processData() throws Exception
	{
		int packetSize = Configuration.getInt("socketPacketSize");
		
		connect(new InetSocketAddress(Configuration.getString("serverAddress"), Configuration.getInt("serverPort")));
		
		while(datas.hasNext())
		{
			sendPacket("CHECK".getBytes());
			
			Pair<DatagramPacket, byte[]> response = receivePacket(packetSize);
			if(!new String(response.getValue()).equals("OK"))
				return;
			
			sendPacket(datas.next().getForSocket().getBytes());
			
			response = receivePacket(packetSize);
			if(!new String(response.getValue()).equals("OK"))
				return;
			
			datas.remove();
		}
		
		sendPacket("END".getBytes());
	}
}
