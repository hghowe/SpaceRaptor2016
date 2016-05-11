import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class StarRaptorClient {

	private String myInitials;
	private int myId;
	private String myName;
	private String myColor;
	private Map<Integer,Transmittable> objectsOnScreen;
	private JPanel thePanel;
	
	private Socket 		mySocket;
	private Scanner 		mySocketScanner;
	private PrintWriter 	mySocketWriter;

	
	public StarRaptorClient(Map<Integer,Transmittable> objMap, String name, JPanel panel, String color) 
	{
		objectsOnScreen = objMap;
		myName = name;
		myColor = color;
		thePanel = panel;
		setupConnection();	
	}
	
	/**
	 * initiate the connection to the server.
	 */
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
			mySocketWriter.println(myColor); // add my color to the things to send to the server
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
	public void parseMessage(String message)
	{
		int theId;
		//System.out.println("Parsing message: "+message);
		String[] messageSequence = message.split(Constants.MJR_DIVIDER);
		switch (Integer.parseInt(messageSequence[0]))
		{
			case Constants.PREFIX_NEW_OBJECT:
				int type = Integer.parseInt(messageSequence[1]);
				theId = Integer.parseInt(messageSequence[2]);
				Transmittable newbie = null;
				if (type == Constants.TYPE_RAPTOR)
				{	ClientRaptor raptor = new ClientRaptor();
					raptor.buildFromDescription(messageSequence[3]);
					if (theId == myId)
						raptor.setPlayer(true);
					newbie = raptor;
				}
				else if (type == Constants.TYPE_BULLET)
				{
					ClientBullet bullet = new ClientBullet();
					bullet.buildFromDescription(messageSequence[3]);
					newbie = bullet;
				}
				else
				{
					System.out.println("Uh-oh. created a null object.");
				}
				objectsOnScreen.put(theId, newbie);
				System.out.println("There are now "+objectsOnScreen.size()+" objects on screen.");
			break;
			case Constants.PREFIX_UPDATE_OBJECT:
				theId = Integer.parseInt(messageSequence[1]);
				Transmittable objectInQuestion = objectsOnScreen.get(theId);
				objectInQuestion.updateFromDescription(messageSequence[2]);
				
			break;
			case Constants.PREFIX_REMOVE_OBJECT:
				theId = Integer.parseInt(messageSequence[1]);
				objectsOnScreen.remove(theId);
			break;
		
		}
		thePanel.repaint(); // we've changed something about the screen - we'd better repaint it!
	}
	
	/**
	 * tell the server that the state of the keyboard for this player has changed. Only need to send this when there is a change, 
	 * so don't overdo it!
	 * @param left
	 * @param right
	 * @param thrust
	 * @param fire
	 */
	public void sendKeyBoardStateString(boolean left, boolean right, boolean thrust, boolean fire)
	{
		mySocketWriter.println(left+Constants.MNR_DIVIDER +
							   right+Constants.MNR_DIVIDER+
							   thrust+Constants.MNR_DIVIDER+
							   fire);
		mySocketWriter.flush();
	}
	/**
	 * this class is in charge of hearing back from the server. It runs a thread that will keep looking for the server over
	 * and over again.
	 * @author harlan.howe
	 *
	 */
	public class IncomingReader implements Runnable
	{
		public void run()
		{
			try
			{
				// the first thing we receive from the server is this client's assigned ID.
				myId = Integer.parseInt(mySocketScanner.nextLine());
				System.out.println("I have been assigned ID# "+myId);
				while (true)
				{
					// wait for a message from the server and have the FalconChatClient act upon it.
					parseMessage(mySocketScanner.nextLine());
				}
			}
			catch (NoSuchElementException nsee)
			{
				JOptionPane.showConfirmDialog(null, "Lost connection.");
				System.exit(1);
			}
		}
	}
	
}
