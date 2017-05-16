package fr.polytech.projectjava.utils;

import javafx.util.Pair;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;

/**
 * Class to handle a DatagramSocket.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public abstract class DatagramSocketBase implements Runnable
{
	private DatagramSocket socket;
	
	/**
	 * Constructor.
	 *
	 * @param address The address to bind to, null if none.
	 *
	 * @throws SocketException {@link java.net.DatagramSocket#DatagramSocket()} If the socket could not be opened, or the socket could not bind to the specified local port.
	 */
	DatagramSocketBase(InetSocketAddress address) throws SocketException
	{
		socket = new DatagramSocket(address);
	}
	
	/**
	 * Used to connect the socket to an address.
	 *
	 * @param address The address to connect to.
	 *
	 * @throws IllegalStateException If the socket is already connected.
	 * @throws SocketException       {@link java.net.DatagramSocket#DatagramSocket()} If the socket could not be opened, or the socket could not bind to the specified local port.
	 */
	public void connect(InetSocketAddress address) throws IllegalStateException, SocketException
	{
		if(socket.isConnected())
		{
			if(!address.getAddress().equals(socket.getInetAddress()) || address.getPort() != socket.getPort())
				throw new IllegalStateException("Socket already connected to other socket");
		}
		else
			socket.connect(address);
	}
	
	/**
	 * Disconnect the socket.
	 */
	public void disconnect()
	{
		socket.disconnect();
	}
	
	/**
	 * Receive a packet of the given size.
	 *
	 * @param size The packet size.
	 *
	 * @return A pair of the packet received with its byte array. The bytes contains "ERROR" if an error occurred, or timed out.
	 *
	 * @throws IOException If an I/O error occurs.
	 */
	public Pair<DatagramPacket, byte[]> receivePacket(int size) throws IOException
	{
		return receivePacket(size, 0);
	}
	
	/**
	 * Receive a packet of the given size and offset.
	 *
	 * @param size   The packet size.
	 * @param offset The offset of the packet.
	 *
	 * @return A pair of the packet received with its byte array. The bytes contains "ERROR" if an error occurred, or timed out.
	 *
	 * @throws IOException If an I/O error occurs.
	 */
	public Pair<DatagramPacket, byte[]> receivePacket(int size, int offset) throws IOException
	{
		System.out.println("Waiting for packet.");
		byte buffer[] = new byte[size];
		DatagramPacket packet = new DatagramPacket(buffer, offset, size);
		try
		{
			socket.receive(packet);
			byte datas[] = Arrays.copyOf(buffer, packet.getLength());
			System.out.println("Received data: " + Arrays.toString(datas) + "(" + new String(datas) + ")");
			return new Pair<>(packet, datas);
		}
		catch(SocketTimeoutException ignored)
		{
		}
		return new Pair<>(packet, "ERROR".getBytes());
	}
	
	/**
	 * Send a packet to the connected address.
	 *
	 * @param data The bytes to send.
	 *
	 * @throws IOException           If an I/O error occurs.
	 * @throws IllegalStateException If the socket isn't connected.
	 */
	public void sendPacket(byte[] data) throws IOException, IllegalStateException
	{
		if(!socket.isConnected())
			throw new IllegalStateException("Socket is not connected");
		DatagramPacket packet = new DatagramPacket(data, 0, data.length);
		socket.send(packet);
		System.out.println("Sent data: " + Arrays.toString(data) + "(" + new String(data) + ")");
	}
	
	/**
	 * Send a packet to a given address.
	 *
	 * @param address The address to send the data to.
	 * @param data    The bytes to send.
	 *
	 * @throws IOException If an I/O error occurs.
	 */
	public void sendPacket(InetSocketAddress address, byte[] data) throws IOException
	{
		DatagramPacket packet = new DatagramPacket(data, 0, data.length, address);
		socket.send(packet);
		System.out.println("Sent data: " + Arrays.toString(data) + "(" + new String(data) + ")");
	}
	
	/**
	 * Reply to a packet.
	 *
	 * @param datagramPacket The packet to reply to.
	 * @param data           The bytes to send.
	 *
	 * @throws IOException If an I/O error occurs.
	 */
	public void replyPacket(DatagramPacket datagramPacket, byte[] data) throws IOException
	{
		sendPacket(new InetSocketAddress(datagramPacket.getAddress(), datagramPacket.getPort()), data);
	}
	
	@Override
	public void run()
	{
		try
		{
			processData();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			disconnect();
		}
	}
	
	/**
	 * Method called by the runnable before the socket is disconnected.
	 *
	 * @throws Exception If an error occurred.
	 */
	protected abstract void processData() throws Exception;
	
	/**
	 * Set the timeout of the socket.
	 *
	 * @param duration The timeout duration.
	 *
	 * @throws SocketException If there is an error in the underlying protocol, such as an UDP error
	 */
	public void setTimeout(int duration) throws SocketException
	{
		socket.setSoTimeout(duration);
	}
}
