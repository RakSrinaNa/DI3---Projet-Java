package fr.polytech.projectjava.checkingSimulation;

import fr.polytech.projectjava.utils.Configuration;
import fr.polytech.projectjava.utils.UDPClientBuilder;
import javafx.util.Pair;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.util.Iterator;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class CheckingSender extends UDPClientBuilder
{
	private final Iterator<CheckInfos> datas;
	
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
