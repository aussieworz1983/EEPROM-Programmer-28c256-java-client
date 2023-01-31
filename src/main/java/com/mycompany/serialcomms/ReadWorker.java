/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.serialcomms;

import java.util.List;
import java.util.ArrayList;
import java.io.*;
import com.fazecast.jSerialComm.*;
import javax.swing.*;

/**
 *
 * @author aussie
 */
public class ReadWorker extends SwingWorker<String, Object> {

    ConnSettings conS = ConnSettings.getInstance();
    String done = "";
     String output = "";
    int romSize = 32768;
    byte[] byteArr = new byte[romSize];

    JTextArea textArea;

    ReadWorker(JTextArea textarea) {
        textArea = textarea;
    }

    @Override
    public String doInBackground() {
        return ReadEEprom();
    }

    @Override
    protected void done() {
        try {
            //update ui teaxtarea1
              textArea.setText(get());
        } catch (Exception ignore) {

        }
    }

    public String ReadEEprom() {

        String device = conS.getDeviceName();
        int baud = conS.getBaudRate();
        SerialPort comPort = SerialPort.getCommPort(device);

        comPort.setNumDataBits(8);
        comPort.setBaudRate(baud);

        if (comPort.isOpen() == false) {
            comPort.openPort();
        }

        comPort.addDataListener(new SerialPortDataListener() {

            int i = 0;
            int byteCnt = 0;
            int lineCnt = 15;

            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                boolean reading = false;
                String recByte = "";
                byte bit0 = 1;
                byte bit1 = 1;
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
                    return;//guard clause
                }
                byte[] recData = new byte[comPort.bytesAvailable()];
                int dataRead = comPort.readBytes(recData, recData.length);

                for (byte data : recData) {
                    String hex = Integer.toHexString(data & 0xFF);
                    String lastByte = recByte;
                    recByte = "";
                    String newByte = hex;
                    recByte = lastByte + newByte;

                    if (i > 5) {
                        byteArr[byteCnt] = data;
                        if (byteCnt == lineCnt) {
                            lineCnt += 16;
                        }

                        byteCnt++;
                        
                    }
                    i++;

                }
                //TODO FIX THIS Code

                if (byteCnt+1 >= romSize) {
                   
                    //theText=totalText.toString();
                    //writeFile=true;
                   
                   
                    
                    
                    int j = 0;
                    int line = 15;
                    int row = 31;
                    for (var eachByte : byteArr) {
                        String hex = Integer.toHexString(eachByte & 0xFF);
                        output = output+hex+" ";
                        
                        if(j==line){
                          output = output+" - " ;
                          line+=16;
                           if(j==row){
                            output = output+" \n";
                           row+=32;
                           }
                        }
                       
                        
                        j++;
                    }
                     
                    WriteBytesToFile(byteArr, "outputFile.bin");
                    done = output;

                }
                if (reading != true) {

                    //before function call write or read nano sketch
                    //11 to nano sends back 00
                    //10 to nano read all
                    //01 to nano write operation
                    //after function call read on nano sketch
                    //nano sends 11 on completion of read
                    if (recByte.equals("11")) {
                        //close port read finished
                        System.out.println("read finished");
                        bit0 = 0;
                        bit1 = 0;
                        Reply(comPort, bit0, bit1);
                        comPort.closePort();
                        reading=false;
                    } else if (recByte.equals("00")) {
                        //activate read operation

                        bit0 = 1;
                        bit1 = 0;
                        //textArea.setText("reading "+romSize+"bytes from nano");

                        Reply(comPort, bit0, bit1);
                        System.out.println("reading");

                        reading = true;
                    } else {
                        //strart read send 11
                        bit0 = 1;
                        bit1 = 1;
                        Reply(comPort, bit0, bit1);

                    }

                }

            }

        });
       
         while(done.equals("")){
         try{
                Thread.sleep(10000);
               
            }catch(Exception e){
            
            }
        }
        return done;
    }

    public void Reply(SerialPort comPort, byte bit0, byte bit1) {
        //send two bytes to nano
        if (comPort.isOpen() == false) {
            comPort.openPort();
        }

        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        try {
            byte[] byteArray = new byte[2];
            byteArray[0] = bit0;
            byteArray[1] = bit1;

            comPort.writeBytes(byteArray, byteArray.length);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // comPort.closePort();                      
    }

    public void WriteBytesToFile(byte[] bytes, String filename) {
        System.out.println("writing file");
        try (FileOutputStream out = new FileOutputStream(filename)) {
            out.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("file written");
    }
}
