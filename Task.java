public class Task
  {
    private String taskName;
    private String description;
    private String keyword;
    private boolean isCompleted;
    private Room taskRoom;
    private Item necessaryItem1;
    private Item necessaryItem2;
    private Item necessaryItem3;
    private Item necessaryItem4;

    public Task(String name, String des, String key, Room r, Item taskItem1, Item taskItem2, Item taskItem3, Item taskItem4)
    {
      taskName=name;
      description=des;
      keyword=key;
      isCompleted=false;
      taskRoom=r;
      necessaryItem1 = taskItem1;
      necessaryItem2 = taskItem2;
      necessaryItem3 = taskItem3;
      necessaryItem4 = taskItem4;
    }
    public Task(String name, String des, String key, Room r, Item taskItem1)
    {
      taskName=name;
      description=des;
      keyword=key;
      isCompleted=false;
      taskRoom=r;
      necessaryItem1 = taskItem1;
      necessaryItem2 = null;
      necessaryItem3 = null;
      necessaryItem4 = null;
    }
    public Task(String name, String des, String key, Room r)
    {
      taskName=name;
      description=des;
      keyword=key;
      isCompleted=false;
      taskRoom=r;
      necessaryItem1 = null;
      necessaryItem2 = null;
      necessaryItem3 = null;
      necessaryItem4 = null;
    }

    //setters
    public void setTaskName(String tn)
    {
      taskName = tn;
    }

    public void setDescription(String des)
    {
      description=des;
    }

    public void setKeyword(String key)
    {
      keyword=key;
    }
    public void setComplete(boolean se)
    {
      isCompleted = se;
    }

    public void setTaskRoom(Room TR)
    {
      taskRoom = TR;
    }

    public void setTaskItem(Item i)
    {
      necessaryItem1 = i;
    }

    //getters

    public String getTaskName()
    {
      return taskName;
    }
    public String getDescription()
    {
      return description;
    }
    public String getKeyword()
    {
      return keyword;
    }

    public boolean getComplete()
    {
      return isCompleted;
    }

    public Room getTaskRoom()
    {
      return taskRoom;
    }

    public Item getTaskItem1()
    {
      return necessaryItem1;
    }
    public Item getTaskItem2()
    {
      return necessaryItem2;
    }
    public Item getTaskItem3()
    {
      return necessaryItem3;
    }
    public Item getTaskItem4()
    {
      return necessaryItem4;
    }
  }