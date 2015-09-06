package com.java.webservices;

import java.util.Date;
import javax.jws.WebService;

/**
 *  The @WebService property endpointInterface links the
 *  SIB (this class) to the SEI (ch01.ts.TimeServer).
 *  Note that the method implementations are not annotated
 *  as @WebMethods.
 */
@WebService(endpointInterface = "com.java.webservices.TimeServer")
public class TimeServerImp  implements TimeServer {
    public String getTimeAsString() { return new Date().toString(); }
    public Long getTimeAsElapsed() { return new Date().getTime(); }
}
