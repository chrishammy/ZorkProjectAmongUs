public class Baka
{
  //instance var
  private String color;
  private boolean isImposter;
  private Room bakaRoom;
  private boolean isUser;
  private boolean isAlive;
  
  //constructor
  public Baka(String col, boolean isSus, Room r, boolean bakaBoy)
  {
    color=col;
    isImposter=isSus;
    bakaRoom=r;
    isUser = bakaBoy;
    isAlive = true;
  }

  public Baka(String c, boolean i, Room ro)
  {
    color = c;
    isImposter = i;
    bakaRoom = ro;
    isUser = false;
    isAlive=true;
  }

  //setters and getters
  public void setColor(String newColor)
  {
    color = newColor;
  }

  public void setImposter(boolean changeImposter)
  {
    isImposter = changeImposter;
  }

  public void setRoom(Room newRoom)
  {
    bakaRoom = newRoom;
  }

  public void setUser(boolean changeUser)
  {
    isUser = changeUser;
  }
  public void setIsAlive(boolean boo)
  {
    isAlive=boo;
  }

  //getters

  public String getColor()
  {
    return color;
  }

  public boolean getImposter()
  {
    return isImposter;
  }

  public Room getRoom()
  {
    return bakaRoom;
  }

  public boolean getUser()
  {
    return isUser;
  }
  public boolean getIsAlive()
  {
    return isAlive;
  }
  
  
}