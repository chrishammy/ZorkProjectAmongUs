/**
 * Class Game - the main class of the "Zork" game.
 *
 * Author:  Michael Kolling
 * Version: 1.1
 * Date:    March 2000
 * 
 * Modified by: Kevin Good
 * Date:        October 2019
 * 
 *  This class is the main class of the "Zork" application. Zork is a very
 *  simple, text based adventure game.  Users can walk around some scenery.
 *  That's all. It should really be extended to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  routine.
 * 
 *  This main class creates and initializes all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates the
 *  commands that the parser returns.
 */
import java.util.*;
class Game 
{
    private Parser parser;
    private Room currentRoom;
        

    /**
     * Create the game and initialize its internal map.
     */
  Room cafeteria, medbay, reactor, security, electrical, storage, admin, communications, shield, o2, nav, weapons, hallway1, hallway2, hallway3, invRoom, voidRoom;
      Item card, screwdriver, headphones, deodorant, doritos, whopper, emergencyButton;
      ArrayList<Room> rooms = new ArrayList<Room>();
      ArrayList<Room> tempRooms = new ArrayList<Room>();
      ArrayList<Item> availableObject = new ArrayList<Item>();
      ArrayList<Item> inventory = new ArrayList<Item>();
      ArrayList<Item> allObjects = new ArrayList<Item>();
  Baka baka1, baka2, baka3, baka4, baka5, baka6;
  String red, blue, green, yellow, orange, pink;
  ArrayList<Baka> aliveBakas = new ArrayList<Baka>();
  ArrayList<String> availableColors = new ArrayList<String>();
  ArrayList<Baka> allBakas=new ArrayList<Baka>();
  Task medscan, uploadData, organizeStorage, checkComms, stabalizeReactor, griddyShields, cardSwipe, emptyChute, fixWires;
  ArrayList<Task> shortTasks = new ArrayList<Task>();
  ArrayList<Task> copyShortTasks = new ArrayList<Task>();
  ArrayList<Task> longTasks = new ArrayList<Task>();
  ArrayList<Task> copyLongTasks = new ArrayList<Task>();
  ArrayList<Task> yourTasks = new ArrayList<Task>();
  int remainingMoves;
      int availableInventory=0;
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        
        //sets your remaining moves to a random number between 20-30
        remainingMoves=(int)(Math.random()*10+20);
        
        // create the rooms
        cafeteria = new Room("Cafeteria");
        medbay = new Room("Medbay");
        reactor = new Room("Reactor");
        security = new Room("Security");
        electrical = new Room("Electrical");
        storage = new Room("Storage");
        admin = new Room("Admin");
        communications = new Room("Communications");
        shield = new Room("Shield");
        o2 = new Room("O2");
        nav = new Room("Navigation");
        weapons = new Room("Weapons");
        hallway1 = new Room("Hallway1");
        hallway2 = new Room("Hallway2");
        hallway3 = new Room("Hallway3");
        invRoom = new Room("Inventory");
        voidRoom = new Room("Void");
        
        // initialise room exits
        cafeteria.setExits(null, weapons, admin, medbay);
        medbay.setExits(null, cafeteria, hallway1, null);
        reactor.setExits(null, hallway1, null, null);
        security.setExits(null, null, null, hallway1);
        electrical.setExits(hallway2, null, null, null);
        storage.setExits(admin, communications, null, hallway2);
        admin.setExits(cafeteria, null, storage, null);
        communications.setExits(null, shield, null, storage); 
        shield.setExits(hallway3, null, null, communications);
        o2.setExits(null, hallway3, null, null);
        nav.setExits(null, null, null, hallway3);
        weapons.setExits(null, null, hallway3, cafeteria);
        hallway1.setExits(medbay, security, hallway2, reactor);
        hallway2.setExits(hallway1, storage, electrical, null);
        hallway3.setExits(weapons, nav, shield, o2);

        //adds rooms to the arrayList of rooms
        rooms.add(cafeteria);
        rooms.add(medbay);
        rooms.add(reactor);
        rooms.add(security);
        rooms.add(electrical);
        rooms.add(storage);
        rooms.add(admin);
        rooms.add(communications);
        rooms.add(shield);
        rooms.add(o2);
        rooms.add(nav);
        rooms.add(weapons);
        rooms.add(hallway1);
        rooms.add(hallway2);
        rooms.add(hallway3);


        //declares and initializes objects
        card = new Item("Keycard", medbay);
        screwdriver = new Item("Screwdriver", security);
        headphones = new Item("Headphones");
        deodorant = new Item("Deodorant");
        doritos = new Item("Doritos");
        whopper = new Item("Whopper");
        emergencyButton = new Item("Button", cafeteria, 5);

        //Randomizes the locations of the trash
        tempRooms = rooms;
        headphones.setRoom(tempRooms.remove((int)   (Math.random()*tempRooms.size())));
      deodorant.setRoom(tempRooms.remove((int)(Math.random()*tempRooms.size())));
      doritos.setRoom(tempRooms.remove((int)(Math.random()*tempRooms.size())));
      whopper.setRoom(tempRooms.remove((int)(Math.random()*tempRooms.size())));

      //declares available object arrayList and adds all objects to it. This is for the grab and drop methods.
    availableObject.add(card);
    availableObject.add(screwdriver);
    availableObject.add(headphones);
    availableObject.add(deodorant);
    availableObject.add(doritos);
    availableObject.add(whopper);
    availableObject.add(emergencyButton);
  //initializes all colors and adds to available colors list
      red="Red";
      blue="Blue";
      green="Green";
      yellow="Yellow";
      orange="Orange";
      pink="Pink";
      availableColors.add(red);
      availableColors.add(blue);
      availableColors.add(green);
      availableColors.add(yellow);
      availableColors.add(orange);
      availableColors.add(pink);

    //list of true or false for isImpoter so a repeat player can't guess the imposter based off of player #
    ArrayList<Boolean> sussyList = new ArrayList<Boolean>();
    sussyList.add(true);
    sussyList.add(false);
    sussyList.add(false);
    sussyList.add(false);
    sussyList.add(false);
    //initializes all bakas and their colors
      //baka1 is user, hard coded to be not sus
    baka1 = new Baka(availableColors.remove((int)(Math.random()*availableColors.size())), false, cafeteria, true);
      //rest of bakas get randomized sus
    baka2 = new Baka(availableColors.remove((int)(Math.random()*availableColors.size())), sussyList.remove((int)Math.random()*sussyList.size()), cafeteria);
    baka3 = new Baka(availableColors.remove((int)(Math.random()*availableColors.size())), sussyList.remove((int)Math.random()*sussyList.size()), cafeteria);
    baka4 = new Baka(availableColors.remove((int)(Math.random()*availableColors.size())), sussyList.remove((int)Math.random()*sussyList.size()), cafeteria);
    baka5 = new Baka(availableColors.remove((int)(Math.random()*availableColors.size())), sussyList.remove((int)Math.random()*sussyList.size()), cafeteria);
    baka6 = new Baka(availableColors.remove((int)(Math.random()*availableColors.size())), sussyList.remove((int)Math.random()*sussyList.size()), cafeteria);

    //adds all initialized bakas to the aliveBakas list
    aliveBakas.add(baka1);
    aliveBakas.add(baka2);
    aliveBakas.add(baka3);
    aliveBakas.add(baka4);
    aliveBakas.add(baka5);
    aliveBakas.add(baka6);

    //adds all the bakas to allBakas list
      for(int j=0;j<aliveBakas.size();j++)
          allBakas.add(aliveBakas.get(j));

    //initializes all tasks
    medscan= new Task("Scan", "Scan yourself in medbay", "scan", medbay);
    uploadData= new Task("Upload", "Upload data in weapons", "upload", weapons);
    organizeStorage= new Task("Organize", "Organize the storage room", "organize", storage);
    checkComms= new Task("Check", "Check up on the communications room", "check", communications);
    stabalizeReactor= new Task("Stabilize", "Stabalize the reactor", "stabalize", reactor);
    griddyShields= new Task("Griddy", "Hit the griddy in shields", "griddy", shield);
    cardSwipe=new Task("Swipe", "Swipe the keycard in admin", "swipe", admin, card);
    emptyChute=new Task("Empty", "Empty the trash items scattered around into the\nchute in O2 (items are the doritos, whopper, deodorant,\nand headphones).", "empty", o2, doritos, whopper, deodorant, headphones);
    fixWires= new Task("Fix", "Fix the wires in electrical", "fix", electrical, screwdriver);

    //adds all tasks to the appropriate list
      shortTasks.add(medscan);
      shortTasks.add(uploadData);
      shortTasks.add(organizeStorage);
      shortTasks.add(stabalizeReactor);
      shortTasks.add(griddyShields);
      longTasks.add(cardSwipe);
      longTasks.add(emptyChute);
      longTasks.add(fixWires);

    //copies short and long lists
      for(int i=0;i<shortTasks.size();i++)
        copyShortTasks.add(shortTasks.get(i));
      for(int i=0;i<longTasks.size();i++)
        copyLongTasks.add(longTasks.get(i));

      //sets the users tasks by removing 1 tasks from copyLong and 3 from copyShort, randomized
      for(int i=0;yourTasks.size()<3;i++)
        {
          int random = (int)(Math.random()*copyShortTasks.size());
          yourTasks.add(copyShortTasks.remove(random));
        }
      for(int i=0;yourTasks.size()<4;i++)
        {
          int random = (int)(Math.random()*copyLongTasks.size());
          yourTasks.add(copyLongTasks.remove(random));
        }
      
      //sets the allObject list for the 'objects' method to a copy of availableObjects
      for(int j=0;j<availableObject.size();j++)
        allObjects.add(availableObject.get(j));
      
        currentRoom = cafeteria;  // start game in cafeteria
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished)
        {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Goodbye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("BEEP BEEP BEEP\nAMONG US\nCOMPLETE ALL TASKS BEFORE GETTING KILLED BY IMPOSTER\n");
        System.out.println("To view your tasks, type 'tasks'.\nTo view the map, type 'map'.\n");
        System.out.println("Type 'help' if you need help and/or to see more commands\nto help you in your gameplay.");
        System.out.println();
        System.out.println(currentRoom.longDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * If this command ends the game, true is returned, otherwise false is
     * returned.
     */
    private boolean processCommand(Command command) 
    {
        if(command.isUnknown())
        {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help"))
            printHelp();
        else if (commandWord.equals("go"))
            goRoom(command);
        else if(commandWord.equals("grab"))
            grab(command);
        else if(commandWord.equals("inventory"))
          inventory(command);
        else if(commandWord.equals("map"))
          map(command);
        else if(commandWord.equals("drop"))
          drop(command);
        else if(commandWord.equals("players"))
          players(command);
        else if(commandWord.equals("objects"))
          objects(command);
        else if(commandWord.equals("scan"))
          scan(command);
        else if(commandWord.equals("upload"))
          upload(command);
        else if(commandWord.equals("organize"))
          organize(command);
        else if(commandWord.equals("check"))
          check(command);
        else if(commandWord.equals("stabalize"))
          stabalize(command);
        else if(commandWord.equals("griddy"))
          griddy(command);
        else if(commandWord.equals("swipe"))
          swipe(command);
        else if(commandWord.equals("empty"))
          empty(command);
        else if(commandWord.equals("fix"))
          fix(command);
        else if(commandWord.equals("tasks"))
          tasks(command);
        else if (commandWord.equals("quit"))
        {
            if(command.hasSecondWord())
                System.out.println("Quit what?");
            else
                return true;  // signal that we want to quit
        }
        //pick up method here?
        return false;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander, perhaps you drool.");
        System.out.println("You stroll around Pickerington North High School.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
  Room previousRoom=null;
  
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        //back method
        
        if(direction.equals("back") && previousRoom!=null)
        {
          Room tempRoom = currentRoom;
          currentRoom = previousRoom;
          previousRoom = tempRoom;
          System.out.println(currentRoom.longDescription());

          //moves other bakas each time you move
          for(int i=0;i<aliveBakas.size();i++)
            {
              if(aliveBakas.get(i).getRoom().equals(medbay))
              {
                double chance=Math.random();
                if(chance<=0.5)
                  aliveBakas.get(i).setRoom(cafeteria);
                else if(chance>0.5)
                  aliveBakas.get(i).setRoom(hallway1);
              }
              else if(aliveBakas.get(i).getRoom().equals(cafeteria))
              {
                double chance=Math.random();
                if(chance<=0.33)
                  aliveBakas.get(i).setRoom(weapons);
                else if(chance>0.33 && chance<=0.66)
                  aliveBakas.get(i).setRoom(admin);
                else if(chance>0.66)
                  aliveBakas.get(i).setRoom(medbay);
              }
              else if(aliveBakas.get(i).getRoom().equals(weapons))
              {
                double chance=Math.random();
                if(chance<=0.5)
                  aliveBakas.get(i).setRoom(hallway3);
                else if(chance>0.5)
                  aliveBakas.get(i).setRoom(cafeteria);
              }
              else if(aliveBakas.get(i).getRoom().equals(hallway3))
              {
                double chance=Math.random();
                if(chance<=0.25)
                  aliveBakas.get(i).setRoom(weapons);
                else if(chance>0.25 && chance<=0.5)
                  aliveBakas.get(i).setRoom(nav);
                else if(chance>0.5 && chance<=0.75)
                  aliveBakas.get(i).setRoom(shield);
                else if(chance>0.75)
                  aliveBakas.get(i).setRoom(o2);
              }
              else if(aliveBakas.get(i).getRoom().equals(nav))
              {
                aliveBakas.get(i).setRoom(hallway3);
              }
              else if(aliveBakas.get(i).getRoom().equals(o2))
              {
                aliveBakas.get(i).setRoom(hallway3);
              }
              else if(aliveBakas.get(i).getRoom().equals(reactor))
              {
                aliveBakas.get(i).setRoom(hallway1);
              }
              else if(aliveBakas.get(i).getRoom().equals(security))
              {
                aliveBakas.get(i).setRoom(hallway1);
              }
              else if(aliveBakas.get(i).getRoom().equals(hallway1))
              {
                double chance=Math.random();
                if(chance<=0.25)
                  aliveBakas.get(i).setRoom(medbay);
                else if(chance>0.25 && chance<=0.5)
                  aliveBakas.get(i).setRoom(security);
                else if(chance>0.5 && chance<=0.75)
                  aliveBakas.get(i).setRoom(hallway2);
                else if(chance>0.75)
                  aliveBakas.get(i).setRoom(reactor);
              }
              else if(aliveBakas.get(i).getRoom().equals(admin))
              {
                double chance=Math.random();
                if(chance<=0.5)
                  aliveBakas.get(i).setRoom(cafeteria);
                else if(chance>0.5)
                  aliveBakas.get(i).setRoom(storage);
              }
              else if(aliveBakas.get(i).getRoom().equals(hallway2))
              {
                double chance=Math.random();
                if(chance<=0.33)
                  aliveBakas.get(i).setRoom(hallway1);
                else if(chance>0.33 && chance<=0.66)
                  aliveBakas.get(i).setRoom(storage);
                else if(chance>0.66)
                  aliveBakas.get(i).setRoom(electrical);
              }
              else if(aliveBakas.get(i).getRoom().equals(electrical))
              {
                aliveBakas.get(i).setRoom(hallway2);
              }
              else if(aliveBakas.get(i).getRoom().equals(storage))
              {
                double chance=Math.random();
                if(chance<=0.33)
                  aliveBakas.get(i).setRoom(admin);
                else if(chance>0.33 && chance<=0.66)
                  aliveBakas.get(i).setRoom(communications);
                else if(chance>0.66)
                  aliveBakas.get(i).setRoom(hallway2);
              }
              else if(aliveBakas.get(i).getRoom().equals(communications))
              {
                double chance=Math.random();
                if(chance<=0.5)
                  aliveBakas.get(i).setRoom(shield);
                else if(chance>0.5)
                  aliveBakas.get(i).setRoom(storage);
              }
              else if(aliveBakas.get(i).getRoom().equals(shield))
              {
                double chance=Math.random();
                if(chance<=0.5)
                  aliveBakas.get(i).setRoom(hallway3);
                else if(chance>0.5)
                  aliveBakas.get(i).setRoom(communications);
              }
            }
          
          String temp = "";
          
          for(int i=0;i<availableObject.size();i++)
            {
              if(availableObject.get(i).getRoom()==currentRoom)
              {
                temp+=("There is a(n) " + availableObject.get(i).getName() + " in this room.\n");
              }
          
            }
          System.out.println(temp + "\n");

          //everytime you move, remainingMoves dec by 1
          remainingMoves--;

          //if you're out of moves, call endGame
          if(remainingMoves==0)
            endGame();
          
          return;
        }
      else if(direction.equals("back")&&previousRoom==null)
      {
        System.out.println("You can't go back silly baka!");
        return;
      }

          
        // Try to leave current room.
        Room nextRoom = currentRoom.nextRoom(direction);
        
      
    
       // Room previousRoom = 
        if (nextRoom == null)
            System.out.println("There is no door!");
        else 
        {
            previousRoom = currentRoom;
            currentRoom = nextRoom;
            System.out.println(currentRoom.longDescription());
          //moves other bakas each time you move
          for(int i=0;i<aliveBakas.size();i++)
            {
              if(aliveBakas.get(i).getRoom().equals(medbay))
              {
                double chance=Math.random();
                if(chance<=0.5)
                  aliveBakas.get(i).setRoom(cafeteria);
                else if(chance>0.5)
                  aliveBakas.get(i).setRoom(hallway1);
              }
              else if(aliveBakas.get(i).getRoom().equals(cafeteria))
              {
                double chance=Math.random();
                if(chance<=0.33)
                  aliveBakas.get(i).setRoom(weapons);
                else if(chance>0.33 && chance<=0.66)
                  aliveBakas.get(i).setRoom(admin);
                else if(chance>0.66)
                  aliveBakas.get(i).setRoom(medbay);
              }
              else if(aliveBakas.get(i).getRoom().equals(weapons))
              {
                double chance=Math.random();
                if(chance<=0.5)
                  aliveBakas.get(i).setRoom(hallway3);
                else if(chance>0.5)
                  aliveBakas.get(i).setRoom(cafeteria);
              }
              else if(aliveBakas.get(i).getRoom().equals(hallway3))
              {
                double chance=Math.random();
                if(chance<=0.25)
                  aliveBakas.get(i).setRoom(weapons);
                else if(chance>0.25 && chance<=0.5)
                  aliveBakas.get(i).setRoom(nav);
                else if(chance>0.5 && chance<=0.75)
                  aliveBakas.get(i).setRoom(shield);
                else if(chance>0.75)
                  aliveBakas.get(i).setRoom(o2);
              }
              else if(aliveBakas.get(i).getRoom().equals(nav))
              {
                aliveBakas.get(i).setRoom(hallway3);
              }
              else if(aliveBakas.get(i).getRoom().equals(o2))
              {
                aliveBakas.get(i).setRoom(hallway3);
              }
              else if(aliveBakas.get(i).getRoom().equals(reactor))
              {
                aliveBakas.get(i).setRoom(hallway1);
              }
              else if(aliveBakas.get(i).getRoom().equals(security))
              {
                aliveBakas.get(i).setRoom(hallway1);
              }
              else if(aliveBakas.get(i).getRoom().equals(hallway1))
              {
                double chance=Math.random();
                if(chance<=0.25)
                  aliveBakas.get(i).setRoom(medbay);
                else if(chance>0.25 && chance<=0.5)
                  aliveBakas.get(i).setRoom(security);
                else if(chance>0.5 && chance<=0.75)
                  aliveBakas.get(i).setRoom(hallway2);
                else if(chance>0.75)
                  aliveBakas.get(i).setRoom(reactor);
              }
              else if(aliveBakas.get(i).getRoom().equals(admin))
              {
                double chance=Math.random();
                if(chance<=0.5)
                  aliveBakas.get(i).setRoom(cafeteria);
                else if(chance>0.5)
                  aliveBakas.get(i).setRoom(storage);
              }
              else if(aliveBakas.get(i).getRoom().equals(hallway2))
              {
                double chance=Math.random();
                if(chance<=0.33)
                  aliveBakas.get(i).setRoom(hallway1);
                else if(chance>0.33 && chance<=0.66)
                  aliveBakas.get(i).setRoom(storage);
                else if(chance>0.66)
                  aliveBakas.get(i).setRoom(electrical);
              }
              else if(aliveBakas.get(i).getRoom().equals(electrical))
              {
                aliveBakas.get(i).setRoom(hallway2);
              }
              else if(aliveBakas.get(i).getRoom().equals(storage))
              {
                double chance=Math.random();
                if(chance<=0.33)
                  aliveBakas.get(i).setRoom(admin);
                else if(chance>0.33 && chance<=0.66)
                  aliveBakas.get(i).setRoom(communications);
                else if(chance>0.66)
                  aliveBakas.get(i).setRoom(hallway2);
              }
              else if(aliveBakas.get(i).getRoom().equals(communications))
              {
                double chance=Math.random();
                if(chance<=0.5)
                  aliveBakas.get(i).setRoom(shield);
                else if(chance>0.5)
                  aliveBakas.get(i).setRoom(storage);
              }
              else if(aliveBakas.get(i).getRoom().equals(shield))
              {
                double chance=Math.random();
                if(chance<=0.5)
                  aliveBakas.get(i).setRoom(hallway3);
                else if(chance>0.5)
                  aliveBakas.get(i).setRoom(communications);
              }
            }
          String temp = "";
          for(int i=0;i<availableObject.size();i++)
            {
              if(availableObject.get(i).getRoom()==currentRoom)
                temp+=("There is a(n) " + availableObject.get(i).getName() + " in this room.\n");
              
        }
          System.out.println(temp);
          //everytime you move, remainingMoves dec by 1
          remainingMoves--;

          //if you're out of moves, call endGame
          if(remainingMoves==0)
            endGame();
    }
    }
  
  //grab method 
  private void grab(Command command)
  {
    if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to pick up...
            System.out.println("Grab what?");
            return;
        }
    String object = command.getSecondWord();
    //booleans for which parameter it pings
    boolean succesfulGrab=false;
    String pingObjectName="";
    Item succesfulObject=null;
    
  for(int i=0;i<availableObject.size();i++)
  {
    pingObjectName="";
    
    //succesful grab
if(availableObject.get(i).getName().toLowerCase().equals(object) && availableObject.get(i).getWeight()==1 && availableInventory<=3 && availableObject.get(i).getRoom().equals(currentRoom))
     {
       //this is what stays in for loops, make sure to change all return to break
        pingObjectName=availableObject.get(i).getName();
        succesfulGrab=true;
       succesfulObject=availableObject.get(i);
       break;
     }
    }

    
    //succesful print
    if(succesfulGrab)
    {
      System.out.println("You picked up a(n) " + pingObjectName + "\n");
      int pingIndex=-1;
      for(int i=0;i<availableObject.size();i++)
        {
          if(availableObject.get(i).equals(succesfulObject))
            pingIndex=i;
        }
      availableObject.get(pingIndex).setRoom(invRoom);
      inventory.add(availableObject.remove(pingIndex));
      availableInventory++;
    }
    else
    {
      System.out.println("You cannot do this. Check:\n1) Spelling\n2) What room the object is in\n3) If the object can be picked up\n4) How much inventory space you have left\n5) If the object has already been used/Is in your inventory.\n");
     }
  
     
 }

  //drop method
  private void drop(Command command)
  {
    if(!command.hasSecondWord())
        {
            // if there is no second word, we don't know what to drop...
            System.out.println("Grab what?");
            return;
        }
    String object = command.getSecondWord();
    boolean foundObject=false;
    int succesfulIndex=-1;
    
    //searches through your inv to see if there is an object to match the user input
    for(int i=0;i<inventory.size();i++)
      {
        if(inventory.get(i).getName().toLowerCase().equals(object))
        {
          succesfulIndex=i;
          foundObject=true;
          break;
        }
      }

    //if the object was found in your inv
    if(foundObject)
    {
      System.out.println("You dropped a(n) " + inventory.get(succesfulIndex).getName() + "\n");
      inventory.get(succesfulIndex).setRoom(currentRoom);
      availableObject.add(inventory.remove(succesfulIndex));
      availableInventory--;
    }
    else
    {
      System.out.println("Could not find object in your inventory. Please check\nspelling and whether you currently have this object or not.\n");
    }
  
  }

  //display inventory method
  private void inventory(Command command)
  {
    String inventoryPrintString="";
    for(int i=0;i<inventory.size();i++)
      {
        inventoryPrintString+=(i+1) + ") " + inventory.get(i).getName();
        inventoryPrintString+="\n";
      }
    if(inventory.size()==0)
    {
      inventoryPrintString+="There are no items in your inventory baka.\n";
    }
    System.out.println(inventoryPrintString);
  }

  private void objects(Command command)
  {
    String printString="";
    String canBePickedUpString="";
    for(int i=-1;i<allObjects.size();i++)
      {
        if(i<0)
        {
          printString+="\n   Name  |   Room  | Can Pick Up?\n\n";
        }
        else
        {
          if(allObjects.get(i).getWeight()==1)
            canBePickedUpString="Yes";
          else
            canBePickedUpString="No";
        
          printString+=(i+1) + ") " + allObjects.get(i).getName() + " - " + allObjects.get(i).getRoom().shortDescription() + " - " + canBePickedUpString + "\n";
        }
      }
    System.out.println(printString);
  
  }

  //map method
  private void map(Command command)
  {
    //the map string itself
    String minimap="\n         Med. — — — — Caf. — — — —   Weapon\n	      |		        |	           |\nReac. — Hall1 — Secur.  |       O2 — Hall3 — Nav\n          |             |              |\n          |           Admin            |\n          |             |              |\n        Hall2 — — — — Stor. — Com. — Shield\n          |\n       Electrical";

//declares our color red and a reset color that will restore the default color after printing
final String RESET = "\u001B[0m";
final String RED = "\u001B[31m";
int roomIndex=-1;
int endIndex=-1;
    //if statements that change the color of the minimap based on which room you are in, then print that room colored
    if(currentRoom.equals(medbay))//1
    {
      roomIndex=minimap.indexOf("Med.");
      endIndex=minimap.indexOf("Med.") + 4;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(cafeteria))//2
    {
      roomIndex=minimap.indexOf("Caf.");
      endIndex=minimap.indexOf("Caf.") + 4;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(weapons))//3
    {
      roomIndex=minimap.indexOf("Weapon");
      endIndex=minimap.indexOf("Weapon") + 6;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(reactor))//4
    {
      roomIndex=minimap.indexOf("Reac.");
      endIndex=minimap.indexOf("Reac.") + 5;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(hallway1))//5
    {
      roomIndex=minimap.indexOf("Hall1");
      endIndex=minimap.indexOf("Hall1") + 5;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(security))//6
    {
      roomIndex=minimap.indexOf("Secur.");
      endIndex=minimap.indexOf("Secur.") + 6;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(o2))//7
    {
      roomIndex=minimap.indexOf("O2");
      endIndex=minimap.indexOf("O2") + 2;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(hallway3))//8
    {
      roomIndex=minimap.indexOf("Hall3");
      endIndex=minimap.indexOf("Hall3") + 5;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(nav))//9
    {
      roomIndex=minimap.indexOf("Nav");
      endIndex=minimap.indexOf("Nav") + 3;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(admin))//10
    {
      roomIndex=minimap.indexOf("Admin");
      endIndex=minimap.indexOf("Admin") + 5;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(hallway2))//11
    {
      roomIndex=minimap.indexOf("Hall2");
      endIndex=minimap.indexOf("Hall2") + 5;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(storage))//12
    {
      roomIndex=minimap.indexOf("Stor.");
      endIndex=minimap.indexOf("Stor.") + 5;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(communications))//13
    {
      roomIndex=minimap.indexOf("Com.");
      endIndex=minimap.indexOf("Com.") + 4;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(shield))//14
    {
      roomIndex=minimap.indexOf("Shield");
      endIndex=minimap.indexOf("Shield") + 6;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
    if(currentRoom.equals(electrical))//15
    {
      roomIndex=minimap.indexOf("Electrical");
      endIndex=minimap.indexOf("Electrical") + 10;
      System.out.println(minimap.substring(0,roomIndex) + RED + minimap.substring(roomIndex,endIndex) + RESET + minimap.substring(endIndex) + "\n");
    }
  }

  private void players(Command command)
  {
    String printer="";
    String aliveWord="";
    for(int i=-1;i<allBakas.size();i++)
      {
        
        if(i==-1)
          printer+="\nColor  |  Alive?  |  Room\n";
        else
        {
          if(allBakas.get(i).getIsAlive())
          aliveWord="Yes";
        else
          aliveWord="No";
        
          printer+=(i+1) + ") " + allBakas.get(i).getColor() + " | " + aliveWord + " | " + allBakas.get(i).getRoom().shortDescription() + "\n";
        }
      }
    System.out.println(printer);
    
  }

  //medscan method
  private void scan(Command command)
  {
    boolean validTask=false;
    int succesfulIndex=-1;
    //checks if the task is valid to do
    for(int i=0;i<yourTasks.size();i++)
      {
        if(yourTasks.get(i).equals(medscan) && !yourTasks.get(i).getComplete() && yourTasks.get(i).getTaskRoom().equals(currentRoom))
        {
          validTask=true;
          succesfulIndex=i;
          break;
        }
      }
    
    if(validTask)
    {
      yourTasks.get(succesfulIndex).setComplete(true);
      System.out.println("Scan is complete!\n");
    }
    else
      System.out.println("Cannot complete task.\nPlease check what room you need to be in to complete the\ntask, if the task is already complete, and/or your\nspelling.\n");
  }

  //upload method
  private void upload(Command command)
  {
     boolean validTask=false;
    int succesfulIndex=-1;
    //checks if the task is valid to do
    for(int i=0;i<yourTasks.size();i++)
      {
        if(yourTasks.get(i).equals(uploadData) && !yourTasks.get(i).getComplete() && yourTasks.get(i).getTaskRoom().equals(currentRoom))
        {
          validTask=true;
          succesfulIndex=i;
          break;
        }
      }
    
    if(validTask)
    {
      yourTasks.get(succesfulIndex).setComplete(true);
      System.out.println("Upload is complete!\n");
      checkAllTasks();
    }
    else
      System.out.println("Cannot complete task.\nPlease check what room you need to be in to complete the\ntask, if the task is already complete, and/or your\nspelling.\n");
  }

  private void organize(Command command)
  {
    boolean validTask=false;
    int succesfulIndex=-1;
    //checks if the task is valid to do
    for(int i=0;i<yourTasks.size();i++)
      {
        if(yourTasks.get(i).equals(organizeStorage) && !yourTasks.get(i).getComplete() && yourTasks.get(i).getTaskRoom().equals(currentRoom))
        {
          validTask=true;
          succesfulIndex=i;
          break;
        }
      }
    
    if(validTask)
    {
      yourTasks.get(succesfulIndex).setComplete(true);
      System.out.println("Organizing is complete!\n");
      checkAllTasks();
    }
    else
      System.out.println("Cannot complete task.\nPlease check what room you need to be in to complete the\ntask, if the task is already complete, and/or your\nspelling.\n");
  }

  //check coms method
  private void check(Command command)
  {
    boolean validTask=false;
    int succesfulIndex=-1;
    //checks if the task is valid to do
    for(int i=0;i<yourTasks.size();i++)
      {
        if(yourTasks.get(i).equals(checkComms) && !yourTasks.get(i).getComplete() && yourTasks.get(i).getTaskRoom().equals(currentRoom))
        {
          validTask=true;
          succesfulIndex=i;
          break;
        }
      }
    
    if(validTask)
    {
      yourTasks.get(succesfulIndex).setComplete(true);
      System.out.println("Checked communications!\n");
      checkAllTasks();
    }
    else
      System.out.println("Cannot complete task.\nPlease check what room you need to be in to complete the\ntask, if the task is already complete, and/or your\nspelling.\n");
  }

  //stabalize reactor method
  private void stabalize(Command command)
  {
    boolean validTask=false;
    int succesfulIndex=-1;
    //checks if the task is valid to do
    for(int i=0;i<yourTasks.size();i++)
      {
        if(yourTasks.get(i).equals(stabalizeReactor) && !yourTasks.get(i).getComplete() && yourTasks.get(i).getTaskRoom().equals(currentRoom))
        {
          validTask=true;
          succesfulIndex=i;
          break;
        }
      }
    
    if(validTask)
    {
      yourTasks.get(succesfulIndex).setComplete(true);
      System.out.println("Reactor stabalized!\n");
      checkAllTasks();
    }
    else
      System.out.println("Cannot complete task.\nPlease check what room you need to be in to complete the\ntask, if the task is already complete, and/or your\nspelling.\n");
  }

  //griddy in shields method
  private void griddy(Command command)
  {
    boolean validTask=false;
    int succesfulIndex=-1;
    //checks if the task is valid to do
    for(int i=0;i<yourTasks.size();i++)
      {
        if(yourTasks.get(i).equals(griddyShields) && !yourTasks.get(i).getComplete() && yourTasks.get(i).getTaskRoom().equals(currentRoom))
        {
          validTask=true;
          succesfulIndex=i;
          break;
        }
      }
    
    if(validTask)
    {
      yourTasks.get(succesfulIndex).setComplete(true);
      System.out.println("You hit the griddy!\n");
      checkAllTasks();
    }
    else
      System.out.println("Cannot complete task.\nPlease check what room you need to be in to complete the\ntask, if the task is already complete, and/or your\nspelling.\n");
  }

  //swipe keycard method
  private void swipe(Command command)
  {
    boolean validTask=false;
    int succesfulIndex=-1;
    int invIndex=-1;
    //checks if the task is valid to do
    for(int i=0;i<yourTasks.size();i++)
      {
        if(yourTasks.get(i).equals(cardSwipe) && !yourTasks.get(i).getComplete() && yourTasks.get(i).getTaskRoom().equals(currentRoom))
        {
          if(inventory.size()>0)
          {
            for(int j=0;j<inventory.size();j++)
              {
                if(inventory.get(j).equals(card))
                {
                  validTask=true;
                  succesfulIndex=i;
                  invIndex=j;
                  break;
                }
              }
          }
        }
      }

    
    if(validTask)
    {
      yourTasks.get(succesfulIndex).setComplete(true);

      //parses through all objects list to remove the used object
      int allObjectsIndex=-1;
      for(int i=0;i<allObjects.size();i++)
        {
          if(allObjects.get(i).equals(card))
            allObjectsIndex=i;
        }
      allObjects.remove(allObjectsIndex);

      //removes the used object from your inventory and sets its room to the void so it can't be seen or used again
      inventory.get(invIndex).setRoom(voidRoom);
      inventory.remove(invIndex);
      
      System.out.println("Succesfully swiped kecard!\n");
      checkAllTasks();
    }
    else
      System.out.println("Cannot complete task. Please check what room you need to be in to complete the\ntask, if the task is already complete, your\nspelling and/or if you have the necessary items.\n");
  }

  //empty garbage method
  private void empty(Command command)
    {
    boolean validTask=false;
    int succesfulIndexDeodorant=-1;
    int succesfulIndexHeadphones=-1;
    int succesfulIndexDoritos=-1;
    int succesfulIndexWhopper=-1;
    int successCounter=0;
    String deodorantString="";
    String headphonesString="";
    String doritosString="";
    String whopperString="";
    int succesfulTaskIndex=-1;
    //checks if the task is valid to do
    for(int i=0;i<yourTasks.size();i++)
      {
        if(yourTasks.get(i).equals(emptyChute) && !yourTasks.get(i).getComplete() && yourTasks.get(i).getTaskRoom().equals(currentRoom))
        {
          succesfulTaskIndex=i;
          if(inventory.size()>0)
          {
            
            for(int j=0;j<inventory.size();j++)
              {
                if(inventory.get(j).equals(deodorant))
                {
                  validTask=true;
                  succesfulIndexDeodorant=j;

                  deodorantString+="Threw away deodorant.\n";
                  //parses through all objects list to remove                   the used object
                  int allObjectsIndex=-1;
                  for(int h=0;h<allObjects.size();h++)
                  {
                  if(allObjects.get(h).equals(deodorant))
                  allObjectsIndex=h;
                  }
                  allObjects.remove(allObjectsIndex);

                  //removes the used object from your                            inventory and sets its room to the void so                     it can't be seen or used again
                        
    inventory.get(succesfulIndexDeodorant).setRoom(voidRoom);
      inventory.remove(succesfulIndexDeodorant);
      
              //sets isThrownAway to true 
              deodorant.setIsThrownAway(true);

                  j--;
                }
                else if(inventory.get(j).equals(headphones))
                {
                  validTask=true;
                  succesfulIndexHeadphones=j;
                  headphonesString+="Threw away headphones.\n";
              //parses through all objects list to remove the           used object
              int allObjectsIndex=-1;
              for(int h=0;h<allObjects.size();h++)
              {
              if(allObjects.get(h).equals(headphones))
              allObjectsIndex=h;
              }
              allObjects.remove(allObjectsIndex);

              //removes the used object from your inventory                and sets its room to the void so it can't be                   seen or used again
              
    inventory.get(succesfulIndexHeadphones).setRoom(voidRoom);
      inventory.remove(succesfulIndexHeadphones); 

            //sets isThrownAway to true 
            headphones.setIsThrownAway(true);

            j--;
                }
          else if(inventory.get(j).equals(doritos))
            {
                  validTask=true;
                  succesfulIndexDoritos=j;
                  doritosString+="Threw away doritos.\n";
              //parses through all objects list to remove the                 used object
              int allObjectsIndex=-1;
            for(int h=0;h<allObjects.size();h++)
            {
              if(allObjects.get(h).equals(doritos))
              allObjectsIndex=h;
            }
            allObjects.remove(allObjectsIndex);

            //removes the used object from your inventory and              sets its room to the void so it can't be seen or               used again
      inventory.get(succesfulIndexDoritos).setRoom(voidRoom);
      inventory.remove(succesfulIndexDoritos); 

              //sets isThrownAway to true 
              doritos.setIsThrownAway(true);

            j--;
            }
      else if(inventory.get(j).equals(whopper))
          {
            validTask=true;
            succesfulIndexWhopper=j;
            whopperString+="Threw away whopper.\n";
            //parses through all objects list to remove the               used object
            int allObjectsIndex=-1;
            for(int h=0;h<allObjects.size();h++)
            {
              if(allObjects.get(h).equals(whopper))
              allObjectsIndex=h;
            }
            allObjects.remove(allObjectsIndex);

          //removes the used object from your inventory and             sets its room to the void so it can't be seen or              used again
      inventory.get(succesfulIndexWhopper).setRoom(voidRoom);
      inventory.remove(succesfulIndexWhopper); 

          //sets isThrownAway to true 
            whopper.setIsThrownAway(true);

          j--;
                }
              }
            
          }
        }
      }
  
    
    if(validTask)
    {
    
        //if all 4 items are thrown away, then set task to           completed
        if(deodorant.getIsThrownAway() &&   headphones.getIsThrownAway() && doritos.getIsThrownAway() && whopper.getIsThrownAway())
        {
          yourTasks.get(succesfulTaskIndex).setComplete(true);
          System.out.println("Succesfully threw away all items\n");
          checkAllTasks();
        }
        else 
          System.out.println(deodorantString + headphonesString + doritosString + whopperString);
        }
    else
      System.out.println("Cannot complete task. Please check what room you need to be in to complete the\ntask, if the task is already complete, your spelling,\nand/or if you have the neccesary items\n");
    }

//fix wires method
  private void fix(Command command)
  {
    boolean validTask=false;
    int succesfulIndex=-1;
    int invIndex=-1;
    //checks if the task is valid to do
    for(int i=0;i<yourTasks.size();i++)
      {
        if(yourTasks.get(i).equals(fixWires) && !yourTasks.get(i).getComplete() && yourTasks.get(i).getTaskRoom().equals(currentRoom))
        {
          if(inventory.size()>0)
          {
            for(int j=0;j<inventory.size();j++)
              {
                if(inventory.get(j).equals(screwdriver))
                {
                  validTask=true;
                  succesfulIndex=i;
                  invIndex=j;
                  break;
                }
              }
          }
        }
      }
  
    
    if(validTask)
    {
      yourTasks.get(succesfulIndex).setComplete(true);

      //parses through all objects list to remove the used object
      int allObjectsIndex=-1;
      for(int i=0;i<allObjects.size();i++)
        {
          if(allObjects.get(i).equals(screwdriver))
            allObjectsIndex=i;
        }
      allObjects.remove(allObjectsIndex);

      //removes the used object from your inventory and sets its room to the void so it can't be seen or used again
      inventory.get(invIndex).setRoom(voidRoom);
      inventory.remove(invIndex);
      
      System.out.println("Succesfully fixed wires!\n");
      checkAllTasks();
    }
    else
      System.out.println("Cannot complete task. Please check what room you need to be in to complete the\ntask, if the task is already complete, your spelling\nand/or if you have the necessary items.\n");
  }

  //method to display all of your tasks
  private void tasks(Command command)
  {
    String taskPrinter="\n";
    for(int i=0;i<yourTasks.size();i++)
      {
        int completionStatus=0;

        if(yourTasks.get(i).equals(cardSwipe) || yourTasks.get(i).equals(fixWires))
        {
          //updates completion status for print statement
        if(yourTasks.get(i).getComplete())
          completionStatus=1;
    
        taskPrinter+=(i+1) + ") " + yourTasks.get(i).getTaskName() + " - " + yourTasks.get(i).getDescription() + " - Keyword: " + yourTasks.get(i).getKeyword() + "\n      Completion Status: " + completionStatus + "/1 \n      Necesarry Item(s): " + yourTasks.get(i).getTaskItem1().getName() + "\n";
        }
        else if(yourTasks.get(i).equals(emptyChute))
        {
          int voidCounter=0;
          //checks the void room that completed objects get sent to, sees how many items are there in order to see how many items you've thrown away
          if(deodorant.getRoom().equals(voidRoom))
            voidCounter++;
          if(headphones.getRoom().equals(voidRoom))
            voidCounter++;
          if(doritos.getRoom().equals(voidRoom))
            voidCounter++;
          if(whopper.getRoom().equals(voidRoom))
            voidCounter++;
          
          //updates completion status for print statement
          completionStatus=voidCounter;
        
        taskPrinter+=(i+1) + ") " + yourTasks.get(i).getTaskName() + " - " + yourTasks.get(i).getDescription() + " - Keyword: " + yourTasks.get(i).getKeyword() + "\n      Completion Status: " + completionStatus + "/4 \n      Necesarry Item(s): " + yourTasks.get(i).getTaskItem1().getName() + ", " + yourTasks.get(i).getTaskItem2().getName() + ", " + yourTasks.get(i).getTaskItem3().getName() + ", \n        " + "             " + "    " + yourTasks.get(i).getTaskItem4().getName() + "\n";
        }
        else
        {
        //updates completion status for print statement
        if(yourTasks.get(i).getComplete())
          completionStatus=1;
    
        taskPrinter+=(i+1) + ") " + yourTasks.get(i).getTaskName() + " - " + yourTasks.get(i).getDescription() + " - Keyword: " + yourTasks.get(i).getKeyword() + "\n      Completion Status: " + completionStatus + "/1\n";
      }
  }
  System.out.println(taskPrinter);
}

  private void checkAllTasks()
  {
    boolean allComplete=false;
    
    for(int i=0;i<yourTasks.size();i++)
      {
        if(!yourTasks.get(i).getComplete())
        {
          allComplete=false;
          break;
        }
        else
          allComplete=true;
      }

    if(allComplete)
    {
      endGame();
    }
  }

  
  private void endGame()
  {
    if(remainingMoves==0)
    {
      System.out.println("You were killed. Game over!");
      System.exit(0);
    }
    else
    {
      System.out.println("Congratulations! You win!");
      System.exit(0);
    }
  }

  /*unused emergencyMeeting code
  private void emergencyMeeting()
  {
    //moves you to caf
    currentRoom=cafeteria;

    Baka votedOutBaka;
    int bakaRandomPicker=-1;
    
    System.out.println("\nBEEP BEEP BEEP\nEMERGENCY MEETING\n");

    //sets the random int to a random index in aliveBakas
    bakaRandomPicker=(int)(Math.random()*aliveBakas.size());
    
    
  }
  */
}
