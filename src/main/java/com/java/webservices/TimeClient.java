package com.java.webservices;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * Created by Hari on 03-09-2015.
 */
public class TimeClient {

   public static void main(String[] args)throws  Exception{
       URL url = new URL("http://127.0.0.1:9876/ts?wsdl");
       QName qName = new QName("http://webservices.java.com/", "TimeServerImpService" );

       Service service = Service.create(url, qName);

       TimeServer timeServer= service.getPort(TimeServer.class);
       System.out.println(timeServer.getTimeAsString());
       System.out.println(timeServer.getTimeAsElapsed());



   }
}
