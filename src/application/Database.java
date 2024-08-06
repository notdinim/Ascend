// DATABASE FOR UNTITLED GAME

// IMPORTS
package application;

// IMPORTS
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class Database extends Main{

	// FIELDS
	
	// TEMP STAGE
	private Stage primaryStage;
	
	public String PROTOTYPE = "ID, KEY, CLASS, COINS, WEAPON, LOADPOINT"; // IMPLEMENT X AND Y POS INSTEAD OF LOADPOINT

	// HANDLERS
	private boolean state, idFound;
	private static String entry;
	
	// FILE NECESSITIES
	private static File database;
	
	private static FileReader in;
	private static BufferedReader br;
	
	private FileWriter out;
	private BufferedWriter bw;
	
	private static FileWriter out2;
	private static BufferedWriter bw2;
	
	// PLAYER VALUES LIST
	static ArrayList <String> userValues;
	private static String oldData = "";
	
	// MENU SCREEN CLASS
	private MenuScreen menu;
	
	// CONTRUCTOR
	public Database(Stage primaryStage)
	{
		// INITIALIZE PARENT CLASS
		super();
		
		// SETTING THE STAGE
		this.primaryStage = primaryStage;
		
		// INITIATE STATE TO TRUE
		state = true;
		idFound = false;
		// SET ENTRY TO NULL
		entry = "";
		
		// INITIALIZING DATABASE FILE
		database = new File("database.txt");
		
		// TRY STATEMENT
		try
		{
			// OPENING FILE OBJECT STREAMS
			in = new FileReader(database);
			br = new BufferedReader(in);
			
			out = new FileWriter(database, true);
			bw = new BufferedWriter(out);
		}
		
		// CATCH STATEMENT
		catch (Exception e)
		{
			e.printStackTrace();
		}	
		
		// INITIATE BASE VALUES LIST
		userValues = new ArrayList<String>();
	}
	
	// METHODS
	public boolean getState()
	{
		// RETURN STATE
		return state;
	}
	
	public boolean getIdFound()
	{
		// RETURN ID FOUND
		return idFound;
	}
	
	// METHOD TO CREATE NEW ACCOUNT
	public void createNewAccount(String id, String key)
	{
		// CLEARING ARRAYLISTS
		userValues.clear();
		
		// ARRAYLIST TO HANDLE NEW ACCOUNT CREATIION
		ArrayList<String> temp = new ArrayList<String>();
		
		// REOPEN FILE STREAMS
		try
		{
			// OPENING FILE OBJECT STREAMS
			in = new FileReader(database);
			br = new BufferedReader(in);
			out = new FileWriter(database, true);
			bw = new BufferedWriter(out);		
		}
		
		// CATCH STATEMENT
		catch (Exception e)
		{
			e.printStackTrace();
		}	
		
		// MAKING SAVE STATE VARIABLE TRUE
		state = true;
		
		// SETTING ENTRY TO NULL
		entry = "";
		
		// WHILE LOOP TO HANDLE STATES
		while (state)
		{		
			// CHECKING IF ONLY LETTERS AND NUMBERS ARE ENTERED IN USERNAME INPUT
			for (int i = 0; i < id.length(); i++)
			{
				if (!Character.isLetter(id.charAt(i)) && !Character.isDigit(id.charAt(i)))
				{
					// CHANGING STATE TO FALSE IF ID ISNT A LETTER OR DIGIT
					state = false;
					
					// BREAKING FOR LOOP
					break;
				}
				else
					// SETTING STATE TO TRUE
					state = true;
			}

			// BREAKING LOOP IF STATE IS FALSE AFTER ID CHECK
			if (!state)
			{
				break;
			}
			
			// CHECKING IF ONLY LETTERS AND NUMBERS ARE ENTERED IN PASSWORD INPUT
			for (int i = 0; i < key.length(); i++)
			{
				if (!Character.isLetter(key.charAt(i)) && !Character.isDigit(key.charAt(i)))
				{
					// CHANGING STATE TO FALSE IF KEY ISNT A CHARACTER OR DIGIT
					state = false;
					
					// BREAKING FOR LOOP
					break;
				}
				else
					// SETTING STATE TO TRUE
					state = true;
			}

			// CHECKING IF ID OR KEY ARE EMPTY OR CONTAIN WHITESPACE
			if (id.isBlank() || key.isBlank())
			{
				// SETTING STATE TO FALSE
				state = false;
				break;
			}
			
			// TRY STATEMENT
			try 
			{				
				// READING DATABASE FILE TO LOOK FOR DUPLICATE IDS
				while ((entry = br.readLine()) != null)
				{	
					// SPLITTING DATA
					String[] splitData = entry.split(", ");
					// STORING ID IN ARRAY LIST
					temp.add(splitData[0]);
				}
				
				// SET MARK TO 0
				br.mark(0);
				
				// CHECK AGAINST FILES TO MAKE SURE NO DUPLICATE USERNAME
				for (int i = 0; i < temp.size(); i++)
				{
					// CHECKING IF ID IS FOUND
					if (temp.get(i).equals(id))
					{
						// CHANGING STATE TO FALSE BECAUSE DUPLICATE ID FOUND
						state = false;
						
						Alert alert = new Alert(AlertType.ERROR);
						alert.setContentText("USERNAME ALREADY TAKEN");
						alert.setTitle("Account Manager");
						alert.setHeaderText(null);
						alert.getButtonTypes().clear();
						alert.getButtonTypes().addAll(ButtonType.OK);
						alert.showAndWait();
						break;
					}
				}
	
				// RESETTING BUFFERED READER TO MARK
				br.reset();
			}
			
			// CATCH STATEMENT
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			// CLEARING TEMP LIST OF DATA
			temp.clear();
			
			// BREAK AT THE END OF WHILE LOOP
			break;
		}

		// CLOSING BUFFERED READER AND FILE READER
		try {
			br.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// CHECKING IF AT THE END OF CHECK, STATE IS TRUE
		if (state)
		{
			// TEMP STRING VALUE
			String tempSave = "";

			// ADDING VALUES TO LIST AND MAKING TEMP SAVE VALUE
			tempSave += id + ", " + key + ", " + null + ", " + 0 + ", " + null + ", " + null;
			userValues.add(id);
			userValues.add(key);
			userValues.add(null);
			userValues.add(Integer.toString(0));
			userValues.add(null);
			userValues.add(null);
			
			// SETTING MAIN VALUE HOLDER IN MAIN TO VALUE IN THIS CLASS
			UntitledHandler.boolMenuScreen = true;
			Main.plrData = userValues;
			
			// TRY CATCH
			try
			{
				// WRITING TO DATABASE FILE
				bw.newLine();
				bw.write(tempSave);

				// CLOSING BUFFERED WRITER AND FILE WRITER TO FLUSH DATA INTO FILE
				bw.close();
				out.close();
			}

			// CATCH STATEMENT
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			// DISPLAYING SUCCESS CREATING ACCONT MESSAGE
			newAccountMsg();
		}
	}
	
	// METHOD TO DISPLAY ACCOUNT CREATION ALERT
	public void newAccountMsg()
	{
		// SUCCESS ACCOUNT CREATED MESSAGE
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setContentText("ACCOUNT CREATED");
		alert.setTitle("Account Manager");
		alert.setHeaderText(null);
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(ButtonType.OK);
		alert.showAndWait();

		// LOOPING THROUGH SIZE OF LIST AND ADD VALUES TO OLD DATA STRING
		for (int i = 0; i < userValues.size(); i++)
		{
			if (i != userValues.size() - 1)
			{
				oldData += userValues.get(i) + ", ";
			}
			else
			{
				oldData += userValues.get(i);
			}
		}
		
		// INITIALIZE MENU SCREEN CLASS
		menu = new MenuScreen();
		UntitledHandler.boolMenuScreen = true;
		menu.init(primaryStage, true);
	}
	
	// METHOD TO LOAD PLAYER VALUES
	public void loadAccount(String id, String key)
	{
		// CLEARING ARRAYLISTS
		userValues.clear();
		
		// ARRAYLIST TO HANDLE ACCOUNT LOAD
		ArrayList<String> tempID = new ArrayList<String>();
		ArrayList<String> tempKEY = new ArrayList<String>();
		ArrayList<String> tempCLASS = new ArrayList<String>();
		ArrayList<String> tempCOINS = new ArrayList<String>();
		ArrayList<String> tempWEAPON = new ArrayList<String>();
		ArrayList<String> tempLOADPOINT = new ArrayList<String>();
		
		// REOPEN FILE STREAMS
		try
		{
			// OPENING FILE OBJECT STREAMS
			in = new FileReader(database);
			br = new BufferedReader(in);
			out = new FileWriter(database, true);
			bw = new BufferedWriter(out);		
		}
		
		// CATCH STATEMENT
		catch (Exception e)
		{
			e.printStackTrace();
		}	
		
		// BOOLEAN HANDLER
		idFound = false;
		
		// TRY STATEMENT
		try 
		{				
			// READING DATABASE FILE TO LOOK FOR DUPLICATE IDS
			while ((entry = br.readLine()) != null)
			{	
				// SPLITTING DATA TO STORE USERNAME AND PASSWORD IN ID AND KEY LISTS
				String[] splitData = entry.split(", ");
				
				// ADDING DATA TO LISTS
				tempID.add(splitData[0]);
				tempKEY.add(splitData[1]);
				tempCLASS.add(splitData[2]);
				tempCOINS.add(splitData[3]);
				tempWEAPON.add(splitData[4]);
				tempLOADPOINT.add(splitData[5]);
			}

			// SET MARK TO 0
			br.mark(0);

			// RESETTING BUFFERED READER TO MARK
			br.reset();

			// CHECK AGAINST FILES TO MAKE SURE NO DUPLICATE USERNAME
			for (int i = 0; i < tempID.size(); i++)
			{
				// CHECKING IF ID AND KEY HAVE BEEN FOUND
				if (tempID.get(i).equals(id) && tempKEY.get(i).equals(key))
				{
					// CHANGING STATE TO FALSE BECAUSE DUPLICATE ID FOUND
					idFound = true;
					
					// ADDING CORRESPONDING VALUES TO LIST
					userValues.add(id);
					userValues.add(key);
					userValues.add(tempCLASS.get(i));
					userValues.add(tempCOINS.get(i));
					userValues.add(tempWEAPON.get(i));
					userValues.add(tempLOADPOINT.get(i));
					
					// SETTING MAIN VALUE HOLDER IN MAIN TO VALUE IN THIS CLASS
					Main.plrData = userValues;
				}
			}

			// CHECKING IF ID AND KEY CORRESPOND
			if (idFound)
			{
				// RETURNING SUCCESS LOGIN MESSAGE TO USER
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("LOGIN SUCCESSFUL");
				alert.setTitle("Account Manager");
				alert.setHeaderText(null);
				alert.getButtonTypes().clear();
				alert.getButtonTypes().addAll(ButtonType.OK);
				alert.showAndWait();
				
				// LOOPING THROUGH SIZE OF LIST AND ADD VALUES TO OLD DATA STRING
				for (int i = 0; i < userValues.size(); i++)
				{
					if (i != userValues.size() - 1)
					{
						oldData += userValues.get(i) + ", ";
					}
					else
					{
						oldData += userValues.get(i);
					}
				}
				
				// INITIALIZE MENU SCREEN CLASS
				UntitledHandler.boolMenuScreen = true;
				menu = new MenuScreen();	
				menu.init(primaryStage, false);
			}
			else
			{
				// RETURNING FAILED LOGIN MESSAGE TO USER
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("NO ACCOUNT FOUND");
				alert.setTitle("Account Manager");
				alert.setHeaderText(null);
				alert.getButtonTypes().clear();
				alert.getButtonTypes().addAll(ButtonType.OK);
				alert.showAndWait();
			}
		}

		// CATCH STATEMENT
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	// METHOD TO SAVE PLAYER VALUES
	public static void save()
	{
		// LOCAL STRING NEW DATA VARIABLE TO STORE VALUES FOR SAVE
		String newData = "";

		// ARRAYLIST TO SAVE ALL VALUES FOR OVERIDING DATA
		ArrayList<String> tempData = new ArrayList<String>();

		// REOPEN FILE STREAMS
		try
		{
			// OPENING FILE OBJECT STREAMS
			in = new FileReader(database);
			br = new BufferedReader(in);	
		}

		// CATCH STATEMENT
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		// LOOPING THROUGH SIZE OF LIST AND ADD VALUES TO TEMP STRING
		for (int i = 0; i < Main.plrData.size(); i++)
		{
			if (i != Main.plrData.size() - 1)
			{
				newData += Main.plrData.get(i) + ", ";
			}
			else
			{
				newData += Main.plrData.get(i);
			}
		}
		
		// TRY STATEMENT
		try 
		{				
			// READING DATABASE FILE TO STORE IN LIST
			while ((entry = br.readLine()) != null)
			{	
				// REMOVING OLD VALUES FROM FILE AND ADDING THE REST
				if (!entry.contains(oldData))
				{
					tempData.add(entry);
				}
			}
			
			// STORING VALUES FROM LIST INTO ARRAY OF SAME SIZE - USING THIS FOR SORTING ALGORITHM
			String[] arr = new String[tempData.size() + 1];

			for (int i = 0; i < tempData.size(); i++)
			{
				arr[i] = tempData.get(i);
			}
			arr[arr.length - 1] = newData;
			
			// SELECTION SORT
	        for (int i = 0; i < arr.length - 1; i++) {
	            int minIndex = i;
	            for (int j = i + 1; j < arr.length; j++) {
	                int k = 0;
	                while (k < arr[j].length() && k < arr[minIndex].length() && arr[j].charAt(k) == arr[minIndex].charAt(k)) {
	                    k++;
	                }
	                if (k < arr[j].length() && k < arr[minIndex].length() && arr[j].charAt(k) < arr[minIndex].charAt(k)) {
	                    minIndex = j;
	                } else if (k == arr[minIndex].length() && k < arr[j].length()) {
	                    minIndex = j;
	                }
	            }
	            String temp = arr[minIndex];
	            arr[minIndex] = arr[i];
	            arr[i] = temp;
			}
	        
	        // CLEARING ARRAYLIST
	        tempData.clear();
	        
	        // ADDING VALUES BACK TO PLAYER DATA LIST
	        for (int j = 0; j < arr.length; j++)
	        {
	        	tempData.add(arr[j]);
	        }
	        
			// SET MARK TO 0
			br.mark(0);

			// RESETTING BUFFERED READER TO MARK
			br.reset();

			// CLOSING BUFFERED READER
			br.close();
			in.close();
			
			// OPENING FILE WRITER OBJECT STREAMS
			out2 = new FileWriter(database);
			bw2 = new BufferedWriter(out2);	
			
			// WRITING NEW SAVED DATA TO FILE
			for (int i = 0; i < tempData.size(); i++)
			{
				// OVERIDING CURRENT DATABASE AND WRITING NEW ONE WITH UPDATED VALUES
				if (i != 0)
				{
					bw2.newLine();
				}
				
				bw2.write(tempData.get(i));
			}
			
			// CLOSING BUFFERED WRITER AND FILE WRITER TO FLUSH DATA INTO FILE
			bw2.close();
			out2.close();
		}

		// CATCH STATEMENT
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
