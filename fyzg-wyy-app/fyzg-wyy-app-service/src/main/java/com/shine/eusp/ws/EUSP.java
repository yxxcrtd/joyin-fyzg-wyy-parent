/**
 * EUSP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shine.eusp.ws;

public interface EUSP extends java.rmi.Remote {
    public com.shine.eusp.iopara.EUSPOutput login(String loginuser, String md5Password) throws java.rmi.RemoteException;
    public void logout(String token) throws java.rmi.RemoteException;
    public com.shine.eusp.iopara.EUSPOutput callService(String token, String productId, String serviceName, String methodName, com.shine.eusp.iopara.EUSPParameter[] params) throws java.rmi.RemoteException;
}
