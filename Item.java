public class Item
  {
    //instance variables
    private String name;
    private Room starterRoom;
    private int weight;
    private boolean isThrownAway;

    
    //constructor for items with set location
    public Item(String itemName, Room itemRoom)
    {
      name = itemName;
      starterRoom = itemRoom;
      weight=1;
      isThrownAway=false;
   
    }
    //constructor for items with random location
    public Item(String itemName)
    {
      name=itemName;
      weight=1;
      isThrownAway=false;
    }
    //constructer for items that you can't pick up
    public Item(String itemName, Room itemRoom, int invSpace)
    {
      name = itemName;
      starterRoom = itemRoom;
      weight=invSpace;
      isThrownAway=false;
   
    }
 
    //setter methods
    public void setName(String newName)
    {
      name = newName;
    }
  
    public void setRoom(Room newRoom)
    {
      starterRoom=newRoom;
    }  

    public void setRandomRoom()
    {
      
    }

    public void setWeight(int newWeight)
    {
      weight = newWeight;
    }

    public void setIsThrownAway(boolean bool)
    {
      isThrownAway=bool;
    }
    
    //getter methods
    public String getName()
    {
      return name;
    }

    public Room getRoom()
    {
      return starterRoom;
    }

    public int getWeight()
    {
      return weight;
    }

    public boolean getIsThrownAway()
    {
      return isThrownAway;
    }
  }