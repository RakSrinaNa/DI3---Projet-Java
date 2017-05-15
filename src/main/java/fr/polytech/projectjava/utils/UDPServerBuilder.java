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
public abstract class UDPServerBuilder extends DatagramSocketBase
{
	/**
	 * Constructor.
	 *
	 * @param address The address the server is listening on.
	 *
	 * @throws SocketException {@link java.net.DatagramSocket#DatagramSocket(SocketAddress)} If the socket could not be opened, or the socket could not bind to the specified local port.
	 */
	public UDPServerBuilder(InetSocketAddress address) throws SocketException
	{
		super(address);
	}
}
