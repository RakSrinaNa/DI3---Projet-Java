package fr.polytech.projectjava.mainapp.socket;

import fr.polytech.projectjava.mainapp.company.staff.Employee;
import fr.polytech.projectjava.mainapp.company.staff.checking.EmployeeCheck;
import fr.polytech.projectjava.utils.Configuration;
import fr.polytech.projectjava.utils.Log;
import fr.polytech.projectjava.utils.socket.SocketBase;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Represent a connection opened by a client.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 21/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-21
 */
public class CheckingClient extends SocketBase
{
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private final CheckingServer parent;
	private boolean stop = false;
	
	/**
	 * Constructor.
	 *
	 * @param socket         The socket opened.
	 * @param checkingServer The server that opened it.
	 *
	 * @throws SocketException If there is an error in the underlying protocol, such as a TCP error.
	 */
	protected CheckingClient(Socket socket, CheckingServer checkingServer) throws SocketException
	{
		super(socket);
		parent = checkingServer;
		setTimeout(Configuration.getInt("mainClientTimeout"));
	}
	
	@Override
	protected boolean processData() throws Exception
	{
		int packetSize = Configuration.getInt("socketPacketSize");
		while(!stop)
		{
			try
			{
				byte[] response = receivePacket(packetSize);
				if(response == null)
					stop();
				else
					switch(new String(response))
					{
						case "CHECK":
							sendPacket("OK".getBytes());
							processCheck(receivePacket(packetSize));
							break;
						case "EMPLOYEE":
							sendEmployees();
							break;
						default:
							Log.warning("Unknown socket command: " + new String(response));
						case "END":
						case "ERROR":
							stop();
					}
			}
			catch(Exception e)
			{
				Log.error("Server error - " + e.getMessage());
			}
		}
		return true;
	}
	
	/**
	 * Stop the client.
	 */
	public void stop()
	{
		stop = true;
	}
	
	/**
	 * Handle the reception of new datas.
	 *
	 * @param message The data received.
	 *
	 * @throws IOException              If the ACK couldn't be sent.
	 * @throws ParseException           If the date couldn't be read.
	 * @throws IllegalArgumentException If the message is null.
	 */
	private void processCheck(byte[] message) throws IOException, ParseException, IllegalArgumentException
	{
		if(message == null)
			throw new IllegalArgumentException("The response is null");
		String response[] = new String(message).split(";");
		if(parent.getController().addChecking(Integer.parseInt(response[0]), EmployeeCheck.CheckType.valueOf(response[1]), toLocalDateTime(dateFormat.parse(response[2]))))
			sendPacket("OK".getBytes());
	}
	
	/**
	 * Convert a Date to a LocalDateTime.
	 *
	 * @param date The date to convert.
	 *
	 * @return The LocalDateTime.
	 */
	private LocalDateTime toLocalDateTime(Date date)
	{
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}
	
	/**
	 * Send an employee.
	 *
	 * @throws IOException If the data couldn't be sent.
	 */
	private void sendEmployees() throws IOException
	{
		for(Employee employee : parent.getController().listEmployees())
		{
			sendPacket(employeeToString(employee).getBytes());
			byte[] response = receivePacket();
			if(response == null || !new String(response).equals("OK"))
				throw new IllegalStateException("Received not OK");
		}
		sendPacket("DONE".getBytes());
	}
	
	/**
	 * Transform an employee to a string ready to be sent.
	 *
	 * @param employee The employee to stringify.
	 *
	 * @return The string to send corresponding to the employee.
	 */
	private String employeeToString(Employee employee)
	{
		return employee.getID() + ";" + employee.getFirstName() + ";" + employee.getLastName();
	}
}
