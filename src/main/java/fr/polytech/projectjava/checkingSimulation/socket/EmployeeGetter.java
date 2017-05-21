package fr.polytech.projectjava.checkingSimulation.socket;

import fr.polytech.projectjava.checkingSimulation.Employee;
import fr.polytech.projectjava.utils.Configuration;
import fr.polytech.projectjava.utils.SocketBase;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Get the employees from the main application.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class EmployeeGetter extends SocketBase
{
	private static final Object lock = new Object();
	private final ObservableList<Employee> datas;
	
	/**
	 * Constructor.
	 *
	 * @param datas The list to add the employees to.
	 *
	 * @throws IOException If an I/O error occurs when creating the socket.
	 */
	public EmployeeGetter(ObservableList<Employee> datas) throws IOException
	{
		super(new InetSocketAddress(Configuration.getString("serverAddress"), Configuration.getInt("serverPort")));
		setTimeout(10000);
		this.datas = datas;
	}
	
	@Override
	protected void processData() throws Exception
	{
		synchronized(lock)
		{
			int packetSize = Configuration.getInt("socketPacketSize");
			
			sendPacket("EMPLOYEE".getBytes());
			
			byte[] response;
			while((response = receivePacket(packetSize)) != null && !new String(response).equals("ERROR") && !new String(response).equals("DONE"))
			{
				datas.add(Employee.parse(new String(response)));
				sendPacket("OK".getBytes());
			}
			
			sendPacket("END".getBytes());
		}
	}
}
