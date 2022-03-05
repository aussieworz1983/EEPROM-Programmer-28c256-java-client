import com.fazecast.jSerialComm.*;
import javax.swing.SwingWorker;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
/**
 * Write a description of class WriteWorker here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WriteWorker extends SwingWorker<String, Object>
{
    // instance variables - replace the example below with your own
    int arrayIndex=0;
    JTextArea textArea;
     byte[] byteStart = new byte[2];
      byte[] byteArray;
      boolean ready = true;
     String value ="";
    WriteWorker(JTextArea textarea)
    {
        // initialise instance variables
         textArea = textarea;
    }
   
    @Override
    public String doInBackground(){
    
    return WriteEEprom();
    }
    @Override
    protected void done(){
    try{
        //update ui teaxtarea1
        textArea.setText(get());
    }catch(Exception ignore){
    }
    }
    
    
    
    public String WriteEEprom(){
        
        String theText = "";
        ConnSettings conS = ConnSettings.getInstance( );                       
        String device = conS.getDeviceName();
        int baud =conS.getBaudRate(); 
        byteArray = conS.getTheBytes();  
        
        byteStart[0]=0;
        byteStart[1]=1;
        SerialPort comPort = SerialPort.getCommPort(device);
         
        comPort.setBaudRate(baud);
                    
       if(comPort.isOpen()==false){
           comPort.openPort();
        }
        
        
        comPort.addDataListener(new SerialPortDataListener() {
            
           @Override
           public int getListeningEvents() { 
               return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
              }
              
           @Override
            public void serialEvent(SerialPortEvent event)
           {
              //gated clause
              if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                  return;
              int i=0;
              byte[] newData = new byte[comPort.bytesAvailable()];                                         
              int numRead = comPort.readBytes(newData, newData.length); 
              String code = "";   
              
               for(byte data : newData){                                                
                  String hex = Integer.toHexString(data & 0xFF); 
                  String  lastStr = code;                        
                  String newStr = hex;
                  code = lastStr + newStr;                                                                                                                          
                 }   
                 
               if(code.equals("6f6bda")){
                  //ok is recieved from arduino
                  //System.out.println("ok msg recieved ");
                  Start(comPort);
                           
                 }
                           
               if(code.equals("11")){
                   //arduino exit msg 11
                  System.out.println("ready to write");
                  value="writing";
                  WriteFile( comPort);
                                                     
                 }
                        
                        
               }
                    
                       
          });
  
       return value;        
    }
    
    public void Start(SerialPort comPort){
        
        if(comPort.isOpen()==false){
           comPort.openPort();
        }
    
       comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);  
       
       try
       {             
         comPort.writeBytes(byteStart, byteStart.length);           
       } catch (Exception e) {
         e.printStackTrace();
       }
      // comPort.closePort();                      
    }
    public void WriteFile(SerialPort comPort){
        
        if(comPort.isOpen()==false){
          comPort.openPort();
        }
    
       comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);      
        try
       {
          byte[] byteArr64 = new byte[64];
          int times=0;
         for(int num = 0;num<512;num++){
             
            for(int i = 0;i<byteArr64.length;i++){                   
                     byte theBit = byteArray[arrayIndex];
                     byteArr64[i] = theBit;                       
                     arrayIndex++;                                                                                                                          
              }       
          //send 64 bytes at a time       
          comPort.writeBytes(byteArr64, byteArr64.length);  
          Thread.sleep(1000);          
          
        }
        
        value="finished";
        
        comPort.closePort();  
          
        } catch (Exception e) {
            e.printStackTrace();
        }
       comPort.closePort();     
    }
    
}
