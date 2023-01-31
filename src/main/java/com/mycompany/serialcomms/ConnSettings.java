/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.serialcomms;

/**
 *
 * @author aussie
 */
public class ConnSettings {
    private static ConnSettings INSTANCE;
    private String deviceName = "COM13" ;
    private int baudRate ;
    private byte[] theBytes = new byte[32768];
    //connection settings object is a singleton design
    //instance variables - replace the example below with your own
  
    public static ConnSettings getInstance()
    {
        // initialise instance variables
        if(INSTANCE ==null){
            INSTANCE =new ConnSettings();
            
        
        }
        return INSTANCE;
        
    }
    //getters and etters
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
