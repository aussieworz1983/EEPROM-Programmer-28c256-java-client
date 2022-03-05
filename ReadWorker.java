import com.fazecast.jSerialComm.*;
import javax.swing.SwingWorker;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
/**
 * Write a description of class ReadWorker here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

public class ReadWorker extends SwingWorker<String, Object>
{
    byte bit0 = 1;
    int romSize =  32768;
    byte bit1 = 1;
    boolean writeFile =false;
    byte[] byteArr = new byte[romSize];
    boolean reading = false;
    int num=0;
    int cnt = 0;
    int lineNum = 15;
    List<String> totalText = new ArrayList<String>();
    String theText ="";
    
    JTextArea textArea;
    // instance variables - replace the example below with your own
    ReadWorker(JTextArea textarea){
     textArea = textarea;
    }
    
    @Override
    public String doInBackground(){
    return ReadEEprom();
    }
    @Override
    protected void done(){
    try{
        //update ui teaxtarea1
          textArea.setText(get());
    }catch(Exception ignore){
    }
    }
    
    
    public String ReadEEprom(){
        
         
         
                    ConnSettings conS = ConnSettings.getInstance( );                       
                    String device = conS.getDeviceName();
                    int baud =conS.getBaudRate(); 
                    
                    SerialPort comPort = SerialPort.getCommPort(device);
                    comPort.setNumDataBits(8);  
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
                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                        return;
                        int i=0;
                        byte[] newData = new byte[comPort.bytesAvailable()];
                        
                                          
                        int numRead = comPort.readBytes(newData, newData.length); 
                      
                        
                        String code = "";
                        List<String> theData = new ArrayList<String>();
                     
                          for(byte data : newData){  
                        
                        
                         String hex = Integer.toHexString(data & 0xFF); 
                         String  lastStr = code;
                         code="";
                         String newStr = hex;
                         code = lastStr + newStr;
                         theData.add(hex);  
                          if(cnt>5){
                             byteArr[num]=data;
                             totalText.add(hex);
                             if(num==lineNum){
                                totalText.add("\n");
                                lineNum+=16;
                                }
                              num++;
                            }
                         
                         i++;
                         cnt++;
                        }           
                      
                            if(num+1 >= romSize){
                             comPort.closePort();
                               // byte[] filteredByteArray = Arrays.copyOfRange(byteArr, 6, byteArr.length);
                                
                              
                                theText=totalText.toString();
                                writeFile=true;
                                WriteBytesToFile(byteArr,"outputFile.bin");  
                                
                             
                           }
                       
                      if(reading!=true){
                        if(code.equals("11")){
                            //arduino exit msg 11
                            System.out.println("arduino exit msg recieved ");
                            comPort.closePort();
                           
                        }else if(code.equals("00")){
                           //arduino welcome msg 00
                           //send read all 10
                            bit0=1;bit1=0;
                            Reply(comPort,bit0,bit1);
                              System.out.println("welcome from arduino");   
                              System.out.println("calling read on ard");
                              reading=true;
                        }else{
                            bit0=1;bit1=1;
                            Reply(comPort,bit0,bit1);
                        }
                        
                        //parse for msg and reply
                        //welcome message java 11
                    
                       }
                    }
                    });
                    
                    
        
        
   
       
        while(theText.equals("")){
         try{
                Thread.sleep(10000);
               
            }catch(Exception e){
            
            }
        }
        
        return theText;
       
        
        
    }
    
    public void Reply(SerialPort comPort,byte bit0,byte bit1){
        if(comPort.isOpen()==false){
           comPort.openPort();
        }
    
       comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);      
       try
       {
           byte[] byteArray = new byte[2];
           byteArray[0]=bit0;
           byteArray[1]=bit1;   
           
           comPort.writeBytes(byteArray, byteArray.length); 
          
        } catch (Exception e) { e.printStackTrace(); }
      // comPort.closePort();                      
    }
    public void WriteBytesToFile(byte[] bytes,String filename){
          System.out.println("writing file");
       try (FileOutputStream out = new FileOutputStream(filename)){
        out.write(bytes);
    }

    catch (IOException e) {
        e.printStackTrace();
    }   
      System.out.println("file written");
    }
    
}

