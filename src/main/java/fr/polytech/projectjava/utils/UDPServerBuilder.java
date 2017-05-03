package fr.polytech.projectjava.utils;

import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 03/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-03
 */
public abstract class UDPServerBuilder extends DatagramSocketBase
{
	public UDPServerBuilder(InetSocketAddress address) throws SocketException
	{
		super(address);
	}
}
