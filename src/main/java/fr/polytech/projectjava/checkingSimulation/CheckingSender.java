package fr.polytech.projectjava.checkingSimulation;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class CheckingSender
{
	private static final String HOST = "127.0.0.1";
	private static final int PORT_NUMBER = 9842;
	
	public static void sendInfos(CheckInfos infos) throws IOException
	{
		Socket socket = new Socket(HOST, PORT_NUMBER);
		sendInfos(infos, socket.getOutputStream());
		socket.close();
	}
	
	private static void sendInfos(CheckInfos infos, OutputStream outputStream) throws IOException
	{
		for(char c : infos.getForSocket().toCharArray())
			outputStream.write((byte) c);
	}
}
