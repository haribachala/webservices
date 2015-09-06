package com.java.webservices;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Hari on 04-09-2015.
 */
public class DemoSoapApiSimulator {

    public static  final String localName= "TimeRequest";
    public  static  final String  localNameSpace ="http://webservices/mysoap/";
    public  static final String prefix ="ms";

            private ByteArrayInputStream inputStream;
            private ByteArrayOutputStream outputStream;

     public static void main(String[]  args){
         new DemoSoapApiSimulator().request();
     }

    private void  request(){
        try {
            //  create soap message
            SOAPMessage soapMessage = create_soap_message();
            SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
            SOAPHeader soapHeader= envelope.getHeader();

            // Add element toHeader
            Name lookup_name = create_qname(soapMessage);
            soapHeader.addHeaderElement(lookup_name).addTextNode("time_request");

            outputStream = new ByteArrayOutputStream();
            soapMessage.writeTo(outputStream);
            trace("The request is:", soapMessage);

              SOAPMessage response= processRequest();
            extract_content_and_print(response);
        }catch (SOAPException se){
            System.err.println("se"+se);

        }catch (Exception io){
            System.out.println("io"+io);
        }



    }
    private  void  extract_content_and_print(SOAPMessage soapMessage){
        try {
            SOAPBody soapBody = soapMessage.getSOAPBody();
            Name name = create_qname(soapMessage);
            Iterator it = soapBody.getChildElements(name);
            Node node = (Node)it.next();
            String value = (node==null) ? "Error" :  node.getValue();
            System.out.println("Value:" +value);
        }catch (SOAPException se){
            System.err.println("se"+se);
        }
    }
    private SOAPMessage processRequest()
    {
        process_incoming_soap_message();
        coardinateStreams();
        return create_soap_message(inputStream);


    }
    private void  coardinateStreams(){
        inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        outputStream.reset();

    }
    private void process_incoming_soap_message(){
        try {
            coardinateStreams();
            SOAPMessage message = create_soap_message(inputStream);
            Name look_up = create_qname(message);
            SOAPHeader header = message.getSOAPHeader();
            Iterator iterator= header.getChildElements(look_up);
           Node node= (Node) iterator.next();
            String value= (node.getValue()==null) ? "SOAP header Error" : node.getValue();
            if(value.toLowerCase().contains("time_request")){
                SOAPBody soapBody = message.getSOAPBody();
                soapBody.addBodyElement(look_up).addTextNode(new Date().toString());
                message.saveChanges();
                message.writeTo(outputStream);
                trace("The Received SOAP meg" , message);

            }
        }catch(SOAPException se){
            System.err.println("se"+se);

        }catch (IOException io){
            System.err.println("io"+io);
        }
    }
    private SOAPMessage create_soap_message(InputStream inputStream){
        SOAPMessage message=null;
        try {
            MessageFactory messageFactory = MessageFactory.newInstance();
           message= messageFactory.createMessage(null, inputStream);

        }catch (SOAPException se){
            System.err.println("se"+se);
        }catch(IOException io){
            System.err.println("io"+io);
        }
        return  message;
    }
    private SOAPMessage create_soap_message(){
        SOAPMessage message = null;
        try {

            MessageFactory messageFactory = MessageFactory.newInstance();
           message= messageFactory.createMessage();
        }
        catch (SOAPException e){
            System.err.println("e"+e);

        }
        return message;
    }
    private Name create_qname(SOAPMessage message){
        Name qname=null;
        try{
            SOAPEnvelope envelope =message.getSOAPPart().getEnvelope();
            qname=envelope.createName(localName, prefix, localNameSpace);
        }catch (SOAPException e){
            System.err.println("e"+e);
        }
        return qname;
    }
    private void  trace(String requestResponse, SOAPMessage msg){
        try{
            System.out.println("\n");
            System.out.println(requestResponse);
            msg.writeTo(System.out);
        }catch (SOAPException se){
            System.err.println("se"+se);

        }catch (IOException io){
            System.err.println("io:"+io);
        }

    }
}
