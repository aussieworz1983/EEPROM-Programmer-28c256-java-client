
/**
 * Write a description of class ConnSettings here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public  class ConnSettings
{
    private static ConnSettings INSTANCE;
    private String deviceName = "COM13" ;
    private int baudRate ;
    private byte[] theBytes = new byte[32768];
    
    // instance variables - replace the example below with your own
  
    public static ConnSettings getInstance()
    {
        // initialise instance variables
        if(INSTANCE ==null){
            INSTANCE =new ConnSettings();
            
        
        }
        return INSTANCE;
        
    }
    public String getDeviceName(){
    
        
        return deviceName;
    }
    public void setDeviceName(String device){
    deviceName=device;
        
        
    }
    public int getBaudRate(){
    return baudRate;
    }
    public void setBaudRate(int baud){
        baudRate = baud;
       
    }
    public void setTheBytes(byte[] bytes){
       theBytes = bytes;
    }
    public byte[] getTheBytes(){
    
    return theBytes;
    }
   
}
