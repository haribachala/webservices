package com.java.webservices;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Created by Hari on 02-09-2015.
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface TimeServer {
    @WebMethod
    String getTimeAsString();
    Long   getTimeAsElapsed();


}
