import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

import FalconChatClient.IncomingReader;

public class StarRaptorClient {

	private String myInitials;
	private int myId;
	private String myName;
	private Map<Integer,Transmittable> objectsOnScreen;
	
	private Socket 		mySocket;
	private Scanner 		mySocketScanner;
	private PrintWriter 	mySocketWriter;

	
	public StarRaptorClient(Map<Integer,Transmittable> objMap, String name) 
	{
		objectsOnScreen = objMap;
		myName = name;
		setupConnection();	
	}
	
	public void setupConnection()
	{
		System.out.println("Awaiting connection to server.");
		try
			{
				mySocket = new Socket(Constants.IP_ADDRESS,5000); // communicating with the server via channel 5000.
				mySocketScanner = new Scanner(mySocket.getInputStream());
				mySocketWriter = new PrintWriter(mySocket.getOutputStream());
				Thread readerThread = new Thread(new IncomingReader()); // this class gets written later in this file.
				readerThread.start();
				
				mySocketWriter.println(myName); // add my name to the things to send to the server
				mySocketWriter.flush();         // ...and send it.
				// Note: the server is expecting you to immediately send your name.
				System.out.println("Connected.");
			}
			catch (IOException e)
			{
				System.out.println("I couldn't connect.");
				e.printStackTrace();
			}
		
		}
	}
	public void parseMessage(String message)
	{
		int theId;
		String[] messageSequence = message.split(Constants.MJR_DIVIDER);
		switch (Integer.parseInt(messageSequence[0]))
		{
			case Constants.PREFIX_NEW_OBJECT:
				int type = Integer.parseInt(messageSequence[1]);
				theId = Integer.parseInt(messageSequence[2]);
				Transmittable newbie;
				if (type == Constants.TYPE_RAPTOR)
				{	ClientRaptor raptor = new ClientRaptor();
					raptor.buildFromDescription(messageSequence[3]);
					if (theId == myId)
						raptor.setPlayer(true);
					newbie = raptor;
					
				}
				
				objectsOnScreen.put(theId, newbie);
			break;
			case Constants.PREFIX_UPDATE_OBJECT:
				theId = Integer.parseInt(messageSequence[1]);
				Transmittable objectInQuestion = objectsOnScreen.get(theId);
				objectInQuestion.updateFromDescription(messageSequence[2]);
			break;
			case Constants.PREFIX_REMOVE_OBJECT:
				theId = Integer.parseInt(messageSequence[2]);
				objectsOnScreen.put(theId,null);
			break;
		
		}
		// Note: we probably need to find a way to tell the panel to repaint.
	}
	
	
}
