package fr.polytech.projectjava.checkingsimulation.socket;

import fr.polytech.projectjava.checkingsimulation.Employee;
import fr.polytech.projectjava.utils.Configuration;
import fr.polytech.projectjava.utils.Log;
import fr.polytech.projectjava.utils.socket.SocketBase;
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
	protected boolean processData() throws Exception
	{
		int packetSize = Configuration.getInt("socketPacketSize");
		
		Log.info("Requesting employees...");
		sendPacket("EMPLOYEE".getBytes()); //Tell the server we want the list of the employees
		
		byte[] response;
		while((response = receivePacket(packetSize)) != null && !new String(response).equals("DONE")) //While the server isn't done and haven't failed
		{
			Employee employee = Employee.parse(new String(response)); //Parse  the employee
			if(!datas.contains(employee))
				datas.add(employee);
			Log.info("Received employee " + employee);
			sendPacket("OK".getBytes()); // Send acknowledgment
		}
		Log.info("Employees received");
		sendPacket("END".getBytes()); // Tell the server we're done
		return true;
	}
}
