import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class StarRaptorServer extends TimerTask
{
	private int nextAvailableID; // each chatterer/client will be assigned an id number. 
	// This is the number to be assigned next.

	private ServerSocket mySocket;
	private Map<Integer,Transmittable> objectsOnScreen;
	private Map<Integer,ServerRaptor> raptors;
	private Map<Integer,ServerBullet> bullets;
	private ArrayList<ServerRaptor> raptorsToAdd;
	private ArrayList<ServerBullet> bulletsToAdd;
	private ArrayList<ServerRaptor> raptorsToRemove;
	private ArrayList<ServerBullet> bulletsToRemove;
	private Date lastTime;
	
	public StarRaptorServer() {
		super();
		System.out.println("Initializing.");
		nextAvailableID = 0;
		Timer t = new Timer();
		lastTime = new Date();
		objectsOnScreen = new HashMap<Integer,Transmittable>();
		raptors = new HashMap<Integer,ServerRaptor>();
		bullets = new HashMap<Integer,ServerBullet>();
		raptorsToAdd = new ArrayList<ServerRaptor>();
		bulletsToAdd = new ArrayList<ServerBullet>();
		raptorsToRemove = new ArrayList<ServerRaptor>();
		bulletsToRemove = new ArrayList<ServerBullet>();
		t.scheduleAtFixedRate(this, 0, 20); 	// this is the TimerTask class whose run()
		// method will be called. 0 is the delay before
		// the method is called first; 20 is the delay
		// (in ms) between calls of run().
		setupNetworking();
		
	}
	
	public void setupNetworking()
	{
		try
		{
			mySocket = new ServerSocket(5000);
			while(true)
			{
				System.out.println("Waiting for Client");
				// Wait for a connection request from a client. Don't advance to the next line until you do.
				Socket clientSocket = mySocket.accept(); 
				
				// ask the socket for a writer that will allow us to send stuff to this client.
				PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
				
				// build a ClientReader that will start to constantly listen for message from this client.
				ClientReader cr = new ClientReader(clientSocket, pw); // note: this class is written later in this file.
				
				// build a ServerRaptor instance that will represent the person on the other end of this connection, one
				//    to whom we can send messages.
				ServerRaptor nextRaptor = new ServerRaptor(cr.getInitials(),nextAvailableID, pw); 
				nextRaptor.setxPos(Math.random()*Constants.SCREEN_WIDTH);
				nextRaptor.setyPos(Math.random()*Constants.SCREEN_HEIGHT);
				nextRaptor.setAngle(Math.random()*Math.PI*2);
				System.out.println("Added: "+nextRaptor.longDescription());
				// add the new raptor to the list of all raptors.
//				objectsOnScreen.put(nextAvailableID, nextRaptor);
				
				raptorsToAdd.add(nextRaptor);			
				
				
				
				nextAvailableID++;
			}
		}
		catch (IOException e) // in case there is a problem with the connection...
        {
            e.printStackTrace();
        }	
	}
	
	public void updateObjectsOnScreen()
	{
		while (!raptorsToAdd.isEmpty())
		{
			ServerRaptor raptor = raptorsToAdd.remove(0);
			objectsOnScreen.put(raptor.getId(),raptor);
			raptors.put(raptor.getId(), raptor);
			tellRaptorAll(raptor.getId());
			// tell everybody about this new raptor.
			broadcastAdd(raptor.getId(), raptor);
		}
		
		while (!raptorsToRemove.isEmpty())
		{
			int rId = raptorsToRemove.remove(0).getId();
			objectsOnScreen.remove(rId);
			raptors.remove(rId);
		}
		while (!bulletsToRemove.isEmpty())
		{
			int bId = bulletsToRemove.remove(0).getId();
			objectsOnScreen.remove(bId);
			bullets.remove(bId);
			broadcastRemove(bId);
		}
		while (!bulletsToAdd.isEmpty())
		{
			ServerBullet b = bulletsToAdd.remove(0);
			System.out.println("Adding bullet #"+b.getId()+": "+b.longDescription());
			objectsOnScreen.put(b.getId(), b);
			bullets.put(b.getId(), b);
			broadcastAdd(b.getId(),b);
		}
	}
	
	public void fireBullet(ServerRaptor raptor)
	{
		System.out.println("Firing. "+nextAvailableID);
		ServerBullet bullet = new ServerBullet();
		bullet.setxPos(raptor.getxPos()+Constants.BULLET_LAUNCH_OFFSET*Math.cos(raptor.getAngle()));
		bullet.setyPos(raptor.getyPos()+Constants.BULLET_LAUNCH_OFFSET*Math.sin(raptor.getAngle()));
		bullet.setVx(raptor.getVx()+ Constants.BULLET_SPEED * Math.cos(raptor.getAngle()));
		bullet.setVy(raptor.getVy()+ Constants.BULLET_SPEED * Math.sin(raptor.getAngle()));
		bullet.setId(nextAvailableID);
		bulletsToAdd.add(bullet);
		
		nextAvailableID ++;
		raptor.resetFireTime();
	}
	
	/**
	 * implementation of TimerTask's abstract run() method... this method will be called
	 * over and over again on a regular basis.
	 */
	public void run()
	{
		/*
		 Unlike the chat program, the run method in this program DOES do something - this is where we run the simulation of
		 all the objects on screen. We tell each object on screen to move by one step and then send out the information about
		 all their new locations.
		 */
		
		; // do nothing.
		Date now = new Date();
		double deltaT = (now.getTime() - lastTime.getTime())/1000.0;  // the difference in time since the last time we were here.
																	 // getTime is measured in milliseconds, so we divide by 
																	 // 1000.0 to get seconds.
		updateObjectsOnScreen();
//		System.out.println(objectsOnScreen.keySet());
//		// step everything.
		for (Integer id: objectsOnScreen.keySet())
		{
			
			((Steppable)(objectsOnScreen.get(id))).step(deltaT);
			broadcastChange(id, objectsOnScreen.get(id));
		}
		
		// possibly fire bullets....
		for (Integer id: raptors.keySet())
		{
			if ((raptors.get(id).isFiring()) && (raptors.get(id).canFire()))
			{
				fireBullet(raptors.get(id));
				
			}	
		}
		// expire bullets and detect collisions.
		for (Integer bulId: bullets.keySet())
		{
			if (! bullets.get(bulId).isAlive())
			{
				bulletsToRemove.add(bullets.get(bulId));
				broadcastRemove(bulId);
			}
			else
			{
				ServerBullet b = bullets.get(bulId);
				for (Integer rapId: raptors.keySet())
				{
					ServerRaptor r = raptors.get(rapId);
					double d2 = Math.pow(b.getxPos()-r.getxPos(), 2) +
							Math.pow(b.getyPos()-r.getyPos(), 2);
					if (d2<Constants.RAPTOR_BULLET_COLLISION_DISTANCE_SQUARED)
					{
						r.setHealth(r.getHealth()-Constants.HEALTH_LOSS_PER_HIT);
						bulletsToRemove.add(bullets.get(bulId));
						if (r.getHealth()<0)
						{
							r.setxPos(Math.random()*Constants.SCREEN_WIDTH);
							r.setyPos(Math.random()*Constants.SCREEN_HEIGHT);
							r.setHealth(1.0);
						}
					}
				}
				
			}
			
		}
		
		
		lastTime = now;
		
	}
	
	/**
	 * tell all the Raptors online about the <i>changes</i> in this particular object.
	 * @param id - the id number of the object
	 * @param obj - the object, itself.
	 */
	public void broadcastChange(int id, Transmittable obj)
	{
		String message = Constants.PREFIX_UPDATE_OBJECT+
						 Constants.MJR_DIVIDER+
						 id+
						 Constants.MJR_DIVIDER+
						 obj.shortDescription();
		if (id == 1)
			System.out.println(message);
		for (Integer key: raptors.keySet())
			raptors.get(key).sendMessage(message);
	}
	
	public void broadcastAdd(int id, Transmittable obj)
	{
		int type = -1;
		if (obj instanceof ServerRaptor)
			type = Constants.TYPE_RAPTOR;
		if (obj instanceof ServerBullet)
			type = Constants.TYPE_BULLET;
		String message = Constants.PREFIX_NEW_OBJECT+
						  Constants.MJR_DIVIDER+
						  type + 
						  Constants.MJR_DIVIDER+
						  id +
						  Constants.MJR_DIVIDER+
						  obj.longDescription();
		for (Integer key: raptors.keySet())
			raptors.get(key).sendMessage(message);
	}
	
	/**
	 * tell all the Raptors about the departure of the given object from the screen.
	 * @param id
	 */
	public void broadcastRemove(int id)
	{
		String message = Constants.PREFIX_REMOVE_OBJECT+Constants.MJR_DIVIDER+id;
		for (Integer key: raptors.keySet())
			raptors.get(key).sendMessage(message);
	}
	
	/**
	 * sends "ADD" messages about all objects on screen to one particular raptor. 
	 * Presumably, this would be used when a new raptor is created and needs to know
	 * about what else is on screen. Time consuming, so don't do this more often than
	 * necessary.
	 * @param id - which raptor are we telling about everything?
	 */
	public void tellRaptorAll(int id)
	{
		for (Integer key: objectsOnScreen.keySet())
		{
			int type = -1;
			if (objectsOnScreen.get(key) instanceof ServerRaptor)
				type = Constants.TYPE_RAPTOR;
			String message = Constants.PREFIX_NEW_OBJECT+
					  Constants.MJR_DIVIDER+
					  type + 
					  Constants.MJR_DIVIDER+
					  key +
					  Constants.MJR_DIVIDER+
					  objectsOnScreen.get(key).longDescription();
			raptors.get(id).sendMessage(message);
		}
	}
	
	
	
	
	
	public void handleMessage(String message, int raptorID)
	{
		String[] messageComponents = message.split(Constants.MNR_DIVIDER);
		
		// In this program, the clients only ever send one type of message - the state of the controls held by the player.
		boolean leftPressed   = Boolean.parseBoolean(messageComponents[0]);
		boolean rightPressed  = Boolean.parseBoolean(messageComponents[1]);
		boolean thrustPressed = Boolean.parseBoolean(messageComponents[2]);
		boolean firePressed   = Boolean.parseBoolean(messageComponents[3]);
		
		raptors.get(raptorID).setControlStates(leftPressed,rightPressed,thrustPressed,firePressed);
	}

	/**
	 * A client needs to be removed from the list of chatterers, and we'll tell all the others
	 * he/she has left.
	 * @param whichID
	 */
	public void disconnectClient(int whichID)
	{
		broadcastRemove(whichID);
		raptorsToRemove.add(raptors.get(whichID));
//		objectsOnScreen.remove(whichID);
		System.out.println("Client "+whichID+" disconnected.");
	}
	
	/**
	 * This is an "interior class." It is just like a normal class, except, only FalconChatServer
	 * knows about this class, and it has access to all the variables in the FalconChatServer
	 * instance that created the instance of this class.
	 * 
	 * In this case, ClientReader maintains a thread - a separate, simultaneous process - that
	 * constantly listens to the client to see whether a new message has come in and passes any
	 * that it receives up to FalconChatServer's handleMessage().
	 * @author harlan.howe
	 *
	 */
	private class ClientReader implements Runnable
	{
		private Socket mySocket;
		private PrintWriter myPrintWriter;
		private Scanner myScanner;
		
		private String myInitials;
		private int myID;
		
		public ClientReader(Socket s, PrintWriter pw)
		{
			mySocket = s;
			myPrintWriter = pw;
			try
			{
				myScanner = new Scanner(mySocket.getInputStream());
				myInitials = myScanner.nextLine(); // assumes the first thing sent by a new client is its name...
				
				myID = nextAvailableID; // (from the outer class, which we have access to.)
				
				myPrintWriter.println(myID); // send back the ID we assigned it. 
											// (A bit silly for this chat program, but might be handy later.)
				
				myPrintWriter.flush(); // actually sends the message.
				
				new Thread(this).start(); // begin running the thread - it looks for a run() method in this class.
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		public String getInitials() { return myInitials; }
	
		@Override
		/**
		 * our implementation of Runnable's abstract run() method. This keeps checking for
		 * messages from this client's socket, via the scanner.
		 */
		public void run()
		{
			try
			{
				while(true)
				{
					handleMessage(myScanner.nextLine(), myID); // wait for a message, and deal with it.
				}
			}
			catch(NoSuchElementException nse) // this client has dropped his/her connection.
			{
				disconnectClient(myID);
			}
		}
	}
}
