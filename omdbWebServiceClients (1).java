/*
Name: Harguntas Singh Benipal
Teacher Name: Mr. Bains
Course : ICS4U0-A
Date: November 27, 2022
Description: This program has a menu system that asks the client if they want to search for a movie in the IMDB database, search through the existing file or exit. If number 1, it will store this information entered
in the "movielist2.txt" file and in a JTable. If number 2, it will then ask the user how they want to search for the movie and if it is in the file, it will show it in another JTable. If number 3, it will exit the program.
*/

/*Disclaimer: I downloaded the json.simple package which allowed me to use the JSON Objects and parse the json response. 
  Here is the link to downloading the package: https://jar-download.com/artifacts/com.github.cliftonlabs/json-simple/2.1.2/source-code. 
  After you download it, make sure that the jar file inside the zip and the zip are both in the same location as the .java file. Then go to the "Edit" button at the top, click "preferences", 
  click "Add" on the extra Classpath option at the bottom. Select the jar file that was inside the zip. Then click "apply" and then "ok" at the buttom.
*/

import java.io.*; //Allows you to read file
import java.net.*; //Allows to read and write to a Url Connection
import java.net.URL; //Allows to read and write to a Url Connection
import java.net.MalformedURLException; //Allows to read and write to a Url Connection
import java.net.HttpURLConnection; //Allows to read and write to a Url Connection
import java.io.InputStream; //Helps in reading the json response
import java.util.*; //Imports scanner so that you can read file and ask client
import org.json.simple.*; //I downloaded the json.simple package that allowed me to parse the string respone into a JSON Object which allowed me to dissect it
import org.json.simple.parser.JSONParser; //Allows me to use the JSON Parser to convert from string to JSON Object
import javax.swing.*; //Imports file that allows you to use swing elements like JOptionPane and JTable
import javax.swing.table.*; //Allows you to use the Deafault Table Model which is useful for updating the JTable
import javax.swing.BorderFactory;  //Allows you to give a border around the JTable
import javax.swing.border.TitledBorder;  //Allows you to give a border around the JTable
import java.awt.Color; //Allows you to color the JTable

public class omdbWebServiceClients //Name of program
{ 
          
       public static void main (String[] args) throws org.json.simple.parser.ParseException, FileNotFoundException, UnsupportedEncodingException //Main Method (Throws the parse expection as it is a checked exception)
       {
        
       Scanner console = new Scanner(System.in); //Allows me to interact with user 
         
       //Created the colors for the Jtables
       Color color1=new Color(150,250,250); 
       Color color2=new Color(250,200,100);
       Color color3=new Color(250,150,150);
     
       //Created the first JTable that will show all the movies on the Imdb database
       JFrame frameImdbDatabase=new JFrame();   
       DefaultTableModel tableModelImdbDatabase = new DefaultTableModel(); //Created the defaultTablemodel
       JPanel panelImdbDatabase = new JPanel();
       panelImdbDatabase.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "IMDB Database", TitledBorder.CENTER,TitledBorder.TOP)); //Named the panel
       JTable jtImdbDatabase=new JTable(tableModelImdbDatabase);  
       tableModelImdbDatabase.addColumn("Number"); //Added columns
       tableModelImdbDatabase.addColumn("Name");
       tableModelImdbDatabase.addColumn("Year");
       jtImdbDatabase.setBounds(50,60,300,400);   //Set the size of the JTable
       JScrollPane spImdbDatabase=new JScrollPane(jtImdbDatabase);    //Added the scrollpane
       panelImdbDatabase.add(spImdbDatabase);         
       frameImdbDatabase.add(panelImdbDatabase);
       frameImdbDatabase.setSize(500,500);    //Set the size of the frame
       frameImdbDatabase.setVisible(true);   
       jtImdbDatabase.setBackground(color3); //Added the color
       frameImdbDatabase.setLocation(600,0); //Set the location on the screen
       jtImdbDatabase.repaint(); 
     
       //Created the second JTable that will show all the movies that the user choose from the database (The movies from the file)
       JFrame frameMovieList=new JFrame();   
       DefaultTableModel tableModelMovieList = new DefaultTableModel(); //Created the defaultTablemodel
       JPanel panelMovieList = new JPanel();
       panelMovieList.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Movie List", TitledBorder.CENTER,TitledBorder.TOP));  //Named the panel
       JTable jtMovieList=new JTable(tableModelMovieList);  
       tableModelMovieList.addColumn("Name");//Added columns
       tableModelMovieList.addColumn("Year");
       tableModelMovieList.addColumn("Imdb ID");
       jtMovieList.setBounds(50,60,300,400);   //Set the size of the JTable
       JScrollPane spMovieList=new JScrollPane(jtMovieList);     //Added the scrollpane
       panelMovieList.add(spMovieList);         
       frameMovieList.add(panelMovieList);
       frameMovieList.setSize(500,500);    //Set the size of the frame
       frameMovieList.setVisible(true);   
       jtMovieList.setBackground(color1); //Added the color
       frameMovieList.setLocation(100,300);  //Set the location on the screen
       jtMovieList.repaint();  
     
        //Created the third JTable that will show all the movies that the user choose from the file (Searched for from the file)
       JFrame frameUsersChoice=new JFrame();   
       DefaultTableModel tableModelUsersChoice = new DefaultTableModel(); //Created the defaultTablemodel
       JPanel panelUsersChoice = new JPanel();
       panelUsersChoice.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Users Choice", TitledBorder.CENTER,TitledBorder.TOP)); //Named the panel
       JTable jtUsersChoice=new JTable(tableModelUsersChoice);  
       tableModelUsersChoice.addColumn("Name"); //Added columns
       tableModelUsersChoice.addColumn("Year");
       tableModelUsersChoice.addColumn("Imdb ID");
       jtUsersChoice.setBounds(50,60,300,400);    //Set the size of the JTable
       JScrollPane spUsersChoice=new JScrollPane(jtUsersChoice);    //Added the scrollpane
       panelUsersChoice.add(spUsersChoice);         
       frameUsersChoice.add(panelUsersChoice);
       frameUsersChoice.setSize(500,500);    //Set the size of the frame
       frameUsersChoice.setVisible(true);   
       jtUsersChoice.setBackground(color2); //Added the color
       frameUsersChoice.setLocation(1100,300); //Set the location on the screen
       jtUsersChoice.repaint();  
  
     PrintStream out = new PrintStream("imdbApiList.txt"); //Created print stream to file
     out.println("Title\t\t\t\t\t\t\t\tYear\t\t\t\t\t\t\t\timdbID\t\t\t\t\t\t\tType\t\t\t\t\tPoster"); //Created headers on file
     
    do //do while loop 
    {
      //ask user what to do
      String askUserOptions = JOptionPane.showInputDialog("\nWhat would you like to do:\n\n1. Enter a movie\n2. Search for a movie\n3. Exit");
      char askWhatToDo = askUserOptions.charAt(0);  //Takes the number that was typed and converts into char so it can be used in switch statement
 
      switch (askWhatToDo) //askWhatToDo used in switch statement
      {
        //first case
         case '1':
          
           String movieName = JOptionPane.showInputDialog("What is the movie name you want to search for"); //Asks user for movie name
           movieName = URLEncoder.encode(movieName, "UTF-8");
           String search_URL = "http://www.omdbapi.com/?s="+movieName+"&apikey=7da00300"; //Puts the movie name into the url 
           String jsonResponse= sendGetRequest(search_URL); //Gets the jsonResponse from IMDB from the sendGetRequest method. The search_URL value is passed as a parameter
          
           JSONParser parser = new JSONParser(); //Creates a JSONParser 
   
           JSONObject json = (JSONObject) parser.parse(jsonResponse.toString()); //Parse the string response to a JSONObject
           JSONArray jsonArray = (JSONArray) json.get("Search"); //Get the contents inside the "Search" key and convert those into a array
    
           for (int i=0; i<jsonArray.size(); i++) //Go through the entire array
           {
   
             JSONObject titleData = (JSONObject) jsonArray.get(i); //Get each line/element of the array (Each individual movie)

             tableModelImdbDatabase.addRow(new Object[]{(i+1), titleData.get("Title"), titleData.get("Year"), titleData.get("imdbID")}); //Get the movie name, year and id of each movie and output to the database JTable
           }
          
           int movieNumber = 0;
           if (jsonArray.size() > 1) 
           {
             movieNumber = Integer.parseInt(JOptionPane.showInputDialog("Which movie would you like to select? (Enter the movie number Ex: 1, 2, 3, etc.)")); //If more than 1 movie, ask which movie they want to select
            
             if((movieNumber)>jsonArray.size()) 
             {
               JOptionPane.showMessageDialog(null,"You did not enter within the range"); //If the user doesn't enter in the range of numbers
             }
             
           }
 
           tableModelImdbDatabase.setRowCount(0); //After the movie is selected, the rows are deleted
        
           jtImdbDatabase.repaint();  //Repaint the Jtable
          
           JSONObject titleData = (JSONObject) jsonArray.get(movieNumber-1); //Create another JSONObject that holds the data of the specific movie user wanted
  
           String title = (String) titleData.get("Title"); //Get the title of the movie
          
           out.println(title.replace(" ","_")+"\t\t\t\t\t\t\t\t"+titleData.get("Year")+"\t\t\t\t\t\t\t\t"+titleData.get("imdbID")+"\t\t\t\t\t\t\t"+titleData.get("Type")+"\t\t\t\t\t"+titleData.get("Poster")); //Print the info to the file
           tableModelMovieList.addRow(new Object[]{title, titleData.get("Year"), titleData.get("imdbID")}); //Send the data to the 2nd Jtable that stores the user's entered movies
        
           break;
        
           
        case '2':
          Scanner input = new Scanner(new File("imdbApiList.txt")); //Allows you to read file
          
          //Declare variables
          String movieNameLooking ="";
          int movieYearLooking=0;
          String movieIdLooking="";
           
          int howToSearch = Integer.parseInt(JOptionPane.showInputDialog("How would you like to search for the movie (1 = Movie Name, 2 = Year, 3 = Id)")); //Ask how user wants to search through file
  
         //ask user info depending on way of searching
          if (howToSearch == 1) 
          {
            movieNameLooking = JOptionPane.showInputDialog("Enter the movie name you are looking for:");//movie name
          }
   
          else if (howToSearch == 2) 
          {
            movieYearLooking = Integer.parseInt(JOptionPane.showInputDialog("Enter the year you are looking for: "));//movie year
          }
   
          else if (howToSearch == 3) 
          {
            movieIdLooking = JOptionPane.showInputDialog("Enter the id you are looking for: ");//movie id
          }
   
          else 
          {
            JOptionPane.showMessageDialog(null, "You did enter any of the options.");//If none are entered
            break;
          }
    
          //Declare variables
          String movieNameInput = "";
          int movieYearInput = 0;
          String movieIdInput = "";
    
          input.nextLine(); //Throws away first line
    
          int x=0;
          while(input.hasNextLine()) //goes through file
          {
            String line = input.nextLine(); //Tokenizes line
            Scanner scan = new Scanner(line); //Scanner created to read line
            
            //Gets info from line
            movieNameInput = scan.next();
            movieYearInput = scan.nextInt();
            movieIdInput = scan.next();
        
            if (movieNameInput.replace("_"," ").equalsIgnoreCase(movieNameLooking)||movieYearInput == movieYearLooking||movieIdInput.equalsIgnoreCase(movieIdLooking)) //If any of the info matches
            {
              tableModelUsersChoice.addRow(new Object[]{movieNameInput.replace("_"," "), movieYearInput, movieIdInput}); //Output info to 3rd JTable that holds data for the specific movies from the file
              x++; //adds 1 to x if it goes into this if statement
            }
        
          }
          
          if (x == 0) //If it didn't go into the if statement above, that meant that there were no matches
          {
            JOptionPane.showMessageDialog(null, "The movie that you are looking for does not exist in the database."); //Outputs statment
          }
          break;
    
        case '3':
          JOptionPane.showMessageDialog(null, "Thank you for using the program!");
          System.exit(0);  //Exits the program if user chooses to
       } //End of switch statement
        
       }while(true);  //Forever do while loop
      
    }//End of main
       
     
            
       public static String sendGetRequest(String search_Url) //sendGetRequest method. Sends a get request to the IMDB website and gets the response
       {
         StringBuffer response = new StringBuffer(); // Creates a  string that can be modified
      
         try
         {
           URL url = new URL(search_Url); //Makes the url address
           HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Connects to http and establishs a connection
           connection.setRequestMethod("GET"); //Sends get request to IMDB
           connection.setRequestProperty("Accept", "*/*"); //Handles the http request
           connection.setRequestProperty("Conent-Type", "application/json; charset=UTF-8"); //Gets the json response
     
           InputStream stream = connection.getInputStream(); //Creates a inputStream and gets the json response
           InputStreamReader reader = new InputStreamReader(stream); //Creates the inputStream reader 
           BufferedReader buffer = new BufferedReader(reader); //Creates a buffered reader that reads the json response
     
           String line;
           while((line = buffer.readLine()) !=null)  //The buffered reader goes through the response until the response has no lines left
           {
             response.append(line); //Puts the response lines into the String buffer
           }
           buffer.close(); //closes the buffer
           connection.disconnect(); //disconnects from the connection
     
         } catch (MalformedURLException e) //catches the MalformedURLException 
         {
           e.printStackTrace();

         } catch (IOException e)  //catches the IOException 
         {
           e.printStackTrace();
         }
      
         return response.toString(); //Changes the string buffer response to a string and returns it to the main method
       } //End of method
    
    
} //End of program