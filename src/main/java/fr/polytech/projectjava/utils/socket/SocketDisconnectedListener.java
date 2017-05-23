package fr.polytech.projectjava.utils.socket;

/**
 * Interface to listen on the socket disconnection.
 *
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 23/05/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-05-23
 */
public interface SocketDisconnectedListener
{
	void onSocketDisconnected(DisconnectedEvent evt);
}
