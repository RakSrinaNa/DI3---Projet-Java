package fr.polytech.projectjava;

import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.staff.Employee;
import fr.polytech.projectjava.jfx.main.MainController;
import fr.polytech.projectjava.utils.Configuration;
import fr.polytech.projectjava.utils.Log;
import fr.polytech.projectjava.utils.UDPServerBuilder;
import javafx.util.Pair;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Thread used to receive the data from the checking machine.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class ThreadCheckingReceiver extends UDPServerBuilder
{
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private final int PORT_NUMBER = 9842;
	private final MainController controller;
	private boolean stop = false;
	
	/**
	 * Constructor.
	 *
	 * @param controller The controller of the main application.
	 * @throws SocketException {@link java.net.DatagramSocket#DatagramSocket(SocketAddress)} If the socket could not be opened, or the socket could not bind to the specified local port.
	 */
	public ThreadCheckingReceiver(MainController controller) throws SocketException
	{
		super(new InetSocketAddress(Configuration.getString("serverAddress"), Configuration.getInt("serverPort")));
		this.controller = controller;
		setTimeout(20000);
	}
	
	/**
	 * Stop the server.
	 */
	public void stop()
	{
		stop = true;
	}
	
	@Override
	protected void processData() throws Exception
	{
		int packetSize = Configuration.getInt("socketPacketSize");
		
		while(!stop)
		{
			try
			{
				Pair<DatagramPacket, byte[]> response = receivePacket(packetSize);
				switch(new String(response.getValue()))
				{
					case "CHECK":
						replyPacket(response.getKey(), "OK".getBytes());
						processCheck(receivePacket(packetSize));
						break;
					case "EMPLOYEE":
						sendEmployees(packetSize, response.getKey());
						break;
					case "END":
					case "ERROR":
						break;
					default:
						Log.warning("Unknown socket command: " + new String(response.getValue()));
				}
			}
			catch(Exception ignored)
			{
			}
		}
	}
	
	/**
	 * Handle the reception of new datas.
	 *
	 * @param datagramPacketPair The data received.
	 * @throws IOException If the ACK couldn't be sent.
	 * @throws ParseException If the date couldn't be read.
	 */
	private void processCheck(Pair<DatagramPacket, byte[]> datagramPacketPair) throws IOException, ParseException
	{
		String response[] = new String(datagramPacketPair.getValue()).split(";");
		if(controller.addChecking(Integer.parseInt(response[0]), new CheckInOut(CheckInOut.CheckType.valueOf(response[1]), dateFormat.parse(response[2]))))
			replyPacket(datagramPacketPair.getKey(), "OK".getBytes());
	}
	
	/**
	 * Send an employee.
	 *
	 * @param packetSize The maximum packet size.
	 * @param datagramPacket The packet to reply to.
	 * @throws IOException If the data couldn't be sent.
	 */
	private void sendEmployees(int packetSize, DatagramPacket datagramPacket) throws IOException
	{
		for(Employee employee : controller.listEmployees())
		{
			replyPacket(datagramPacket, employeeToString(employee).getBytes());
			Pair<DatagramPacket, byte[]> response = receivePacket(packetSize);
			if(!new String(response.getValue()).equals("OK"))
				throw new IllegalStateException("Received not OK");
		}
		replyPacket(datagramPacket, "DONE".getBytes());
	}
	
	/**
	 * Transform an employee to a string ready to be sent.
	 *
	 * @param employee The employee to stringify.
	 * @return The string to send corresponding to the employee.
	 */
	private String employeeToString(Employee employee)
	{
		return employee.getID() + ";" + employee.getFirstName() + ";" + employee.getLastName();
	}
}
