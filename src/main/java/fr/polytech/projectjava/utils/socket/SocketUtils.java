package fr.polytech.projectjava.utils.socket;

import fr.polytech.projectjava.utils.Log;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

/**
 * TCP Utils.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 16/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-16
 */
public abstract class SocketUtils
{
	protected final Socket socket;
	private boolean log = true;
	
	/**
	 * Constructor.
	 *
	 * @param socket The socket.
	 */
	SocketUtils(Socket socket)
	{
		this.socket = socket;
	}
	
	/**
	 * Receive a packet of a size of maximum 256.
	 *
	 * @return The read packet, null if failed.
	 *
	 * @throws IllegalStateException If the socket isn't connected.
	 */
	protected byte[] receivePacket() throws IllegalStateException
	{
		return receivePacket(256);
	}
	
	/**
	 * Receive a packet.
	 *
	 * @param size The maximum size to read.
	 *
	 * @return The read packet, null if failed.
	 *
	 * @throws IllegalStateException If the socket isn't connected.
	 */
	protected byte[] receivePacket(int size) throws IllegalStateException
	{
		if(!socket.isConnected())
			throw new IllegalStateException("Socket is not connected");
		byte buffer[] = new byte[size];
		try
		{
			int read = socket.getInputStream().read(buffer);
			if(read <= 0)
				return null;
			buffer = Arrays.copyOf(buffer, read);
			if(log)
				Log.info("Received data: " + Arrays.toString(buffer));
			return buffer;
		}
		catch(Exception e)
		{
			Log.info("Failed to received data " + e.getMessage());
		}
		return null;
	}
	
	/**
	 * Send a packet.
	 *
	 * @param data The data to send.
	 *
	 * @throws IOException           If an I/O error occurs.
	 * @throws IllegalStateException If the socket isn't connected.
	 */
	protected void sendPacket(byte[] data) throws IOException, IllegalStateException
	{
		if(!socket.isConnected())
			throw new IllegalStateException("Socket is not connected");
		socket.getOutputStream().write(data);
		socket.getOutputStream().flush();
		if(log)
			Log.info("Sent data: " + Arrays.toString(data));
	}
	
	/**
	 * Disconnect the socket.
	 *
	 * @throws IOException If an I/O error occurs when closing this socket.
	 */
	void disconnect() throws IOException
	{
		Log.info("Closing socket");
		if(!socket.isClosed())
			socket.close();
	}
	
	/**
	 * Defines if these methods should log what happens.
	 *
	 * @param log The state of the logging.
	 */
	protected void setLog(boolean log)
	{
		this.log = log;
	}
	
	/**
	 * Set the socket timeout.
	 *
	 * @param duration The timeout.
	 *
	 * @throws SocketException If there is an error in the underlying protocol, such as a TCP error.
	 */
	protected void setTimeout(int duration) throws SocketException
	{
		socket.setSoTimeout(duration);
	}
}
