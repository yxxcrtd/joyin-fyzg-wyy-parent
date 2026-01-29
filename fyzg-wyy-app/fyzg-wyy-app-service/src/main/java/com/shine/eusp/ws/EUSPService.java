/**
 * EUSPService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shine.eusp.ws;

public interface EUSPService extends javax.xml.rpc.Service {
    public String getEUSPWsAddress();

    public EUSP getEUSPWs() throws javax.xml.rpc.ServiceException;

    public EUSP getEUSPWs(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
