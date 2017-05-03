package fr.polytech.projectjava.utils;

import javafx.util.Pair;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public abstract class DatagramSocketBase implements Runnable
{
	private DatagramSocket socket;
	
	DatagramSocketBase(InetSocketAddress address) throws SocketException
	{
		socket = new DatagramSocket(address);
	}
	
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
	
	public void disconnect()
	{
		socket.disconnect();
	}
	
	public Pair<DatagramPacket, byte[]> receivePacket(int size) throws IOException, IllegalStateException
	{
		return receivePacket(size, 0);
	}
	
	public Pair<DatagramPacket, byte[]> receivePacket(int size, int offset) throws IOException, IllegalStateException
	{
		byte buffer[] = new byte[size];
		DatagramPacket packet = new DatagramPacket(buffer, offset, size);
		try
		{
			socket.receive(packet);
			System.out.println("Received data: " + Arrays.toString(buffer));
		}
		catch(SocketTimeoutException e)
		{
			return null;
		}
		return new Pair<>(packet, buffer);
	}
	
	public void sendPacket(byte[] data) throws IOException, IllegalStateException
	{
		if(!socket.isConnected())
			throw new IllegalStateException("Socket is not connected");
		DatagramPacket packet = new DatagramPacket(data, 0, data.length);
		socket.send(packet);
		System.out.println("Sent data: " + Arrays.toString(data));
	}
	
	public void sendPacket(InetSocketAddress address, byte[] data) throws IOException
	{
		DatagramPacket packet = new DatagramPacket(data, 0, data.length, address);
		socket.send(packet);
		System.out.println("Sent data: " + Arrays.toString(data));
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
	
	protected abstract void processData() throws Exception;
	
	public void setTimeout(int duration) throws SocketException
	{
		socket.setSoTimeout(duration);
	}
}
