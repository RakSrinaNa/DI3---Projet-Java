package fr.polytech.projectjava.checkingSimulation;

import fr.polytech.projectjava.utils.Configuration;
import fr.polytech.projectjava.utils.UDPClientBuilder;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class EmployeeGetter extends UDPClientBuilder
{
	private static final Object lock = new Object();
	private final ObservableList<Employee> datas;
	
	public EmployeeGetter(ObservableList<Employee> datas) throws IOException
	{
		super();
		setTimeout(10000);
		this.datas = datas;
	}
	
	@Override
	protected void processData() throws Exception
	{
		synchronized(lock)
		{
			int packetSize = Configuration.getInt("socketPacketSize");
			
			connect(new InetSocketAddress(Configuration.getString("serverAddress"), Configuration.getInt("serverPort")));
			sendPacket("EMPLOYEE".getBytes());
			
			Pair<DatagramPacket, byte[]> response;
			while((response = receivePacket(packetSize)) != null && !new String(response.getValue()).equals("ERROR") && !new String(response.getValue()).equals("DONE"))
			{
				datas.add(Employee.parse(new String(response.getValue())));
				
				sendPacket("OK".getBytes());
			}
			
			sendPacket("END".getBytes());
		}
	}
}
