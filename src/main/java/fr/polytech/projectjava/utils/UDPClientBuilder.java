package fr.polytech.projectjava.utils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public abstract class UDPClientBuilder extends DatagramSocketBase
{
	/**
	 * Constructor.
	 *
	 * @throws SocketException {@link java.net.DatagramSocket#DatagramSocket()} If the socket could not be opened, or the socket could not bind to the specified local port.
	 */
	public UDPClientBuilder() throws SocketException
	{
		super(null);
	}
	
	/**
	 * Constructor.
	 *
	 * @param address Bind this client to this address.
	 * @throws SocketException {@link java.net.DatagramSocket#DatagramSocket(SocketAddress)} If the socket could not be opened, or the socket could not bind to the specified local port.
	 */
	public UDPClientBuilder(InetSocketAddress address) throws SocketException
	{
		super(address);
	}
}
