﻿package com.nkcs.server;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JOptionPane;

public class Server {
    
    ServerSocket ss = null;
    private String getnameString=null;
    boolean started = false;
    List<Client> clients = new ArrayList<Client>();
    List<Info> infos = new ArrayList<Info>();
    
    public List<Client> getClient(){
        return clients;
    }
    
    class Info{
        private String info_name = null;
        public Info(){
            
        }
        public void setName(String name){
            info_name = name;
        }
        public String getName(){
            return info_name;
        }
    }
    
    
    public static void main(String[] args) {
        String inputport = JOptionPane.showInputDialog("请输入该服务器的端口号:");
        int port = Integer.parseInt(inputport);
        new Server().start(port);
    }
 
    public void start(int port) {
        try {
           ss = new ServerSocket(port);
           System.out.println("服务器启动");
           started = true;
        } catch (BindException e) {
              System.out.println(" 端口已经被占用");
              System.exit(0);
           }
          catch (IOException e) {
             e.printStackTrace();
          }

      try {
         while (started) {
             Socket s = ss.accept();
             Client c = new Client (s);
             System.out.println("A client is connected");
             new Thread(c).start();
             clients.add(c);
             
             
         }
      } catch (IOException e) {
            e.printStackTrace();
         }
         finally {
            try {
               ss.close();
            } catch (IOException e) {
                  e.printStackTrace();
               }
         }
   }


  class Client implements Runnable {
     private String chatKey="SLEEKNETGEOCK4stsjeS";
     private Socket s = null;
     private DataInputStream dis = null;
     private DataOutputStream dos = null;
     private boolean bConnected = false;
     private String sendmsg=null;
     Client (Socket s) {
        this.s = s;
        try {
          dis = new DataInputStream (s.getInputStream());
          dos = new DataOutputStream (s.getOutputStream());
          bConnected = true;
        } catch(IOException e) {
              e.printStackTrace();
           }
     }
     
     public void send (String str) {
         
         try {
             System.out.println(s);
             dos.writeUTF(str+"");
             dos.flush();
         } catch(IOException e) {
             clients.remove(this);
             System.out.println("对方已经退出了");
         }
     }
     public void run() {
         try {
            while (bConnected) {
                String str = dis.readUTF();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String date = "  ["+df.format(new Date())+"]";
                if(str.startsWith(chatKey+"online:")){
                    Info info = new Info();
                    getnameString = str.substring(27);
                    
                    info.setName(getnameString);
                    infos.add(info);
                    for (int i=0; i<clients.size(); i++) {
                      Client c = clients.get(i);
                      c.send(getnameString+" on line."+date);
                    }
                    System.out.println(getnameString+" on line."+date);
                }
                else if(str.startsWith(chatKey+"offline:")){
                    getnameString = str.substring(28);
                    clients.remove(this);
                    for (int i=0; i<clients.size(); i++) {
                          Client c = clients.get(i);
                          c.send(getnameString+" off line."+date);
                        }
                    System.out.println(getnameString+" off line."+date);
                }
                else{
                    int charend = str.indexOf("end;");
                    String chatString = str.substring(charend+4);
                    String chatName = str.substring(25, charend);
                    
                    sendmsg=chatName+date+"\n"+chatString; 
                    for (int i=0; i<clients.size(); i++) {
                        Client c = clients.get(i);
                        c.send(sendmsg);
                      }
                    System.out.println(sendmsg);
                }
             }
         } catch (SocketException e) {
             System.out.println("client is closed!");
             clients.remove(this);
         } catch (EOFException e) {
               System.out.println("client is closed!");
               clients.remove(this);
            }
            catch (IOException e) {
               e.printStackTrace();
            }
           finally {
             try {
               if (dis != null) dis.close();
               if (dos != null) dos.close();
               if (s != null) s.close();
             } catch (IOException e) {
                   e.printStackTrace();
               }
           }
     }
  }

  
}