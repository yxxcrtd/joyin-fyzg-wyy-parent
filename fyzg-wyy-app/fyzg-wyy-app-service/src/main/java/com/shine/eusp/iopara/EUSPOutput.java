/**
 * EUSPOutput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.shine.eusp.iopara;

public class EUSPOutput  implements java.io.Serializable {
    private int retCode;

    private String retMessage;

    private String retValue;

    private int rowsetCount;

    private EUSPRowSet[] rowsets;

    public EUSPOutput() {
    }

    public EUSPOutput(
           int retCode,
           String retMessage,
           String retValue,
           int rowsetCount,
           EUSPRowSet[] rowsets) {
           this.retCode = retCode;
           this.retMessage = retMessage;
           this.retValue = retValue;
           this.rowsetCount = rowsetCount;
           this.rowsets = rowsets;
    }


    /**
     * Gets the retCode value for this EUSPOutput.
     * 
     * @return retCode
     */
    public int getRetCode() {
        return retCode;
    }


    /**
     * Sets the retCode value for this EUSPOutput.
     * 
     * @param retCode
     */
    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }


    /**
     * Gets the retMessage value for this EUSPOutput.
     * 
     * @return retMessage
     */
    public String getRetMessage() {
        return retMessage;
    }


    /**
     * Sets the retMessage value for this EUSPOutput.
     * 
     * @param retMessage
     */
    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }


    /**
     * Gets the retValue value for this EUSPOutput.
     * 
     * @return retValue
     */
    public String getRetValue() {
        return retValue;
    }


    /**
     * Sets the retValue value for this EUSPOutput.
     * 
     * @param retValue
     */
    public void setRetValue(String retValue) {
        this.retValue = retValue;
    }


    /**
     * Gets the rowsetCount value for this EUSPOutput.
     * 
     * @return rowsetCount
     */
    public int getRowsetCount() {
        return rowsetCount;
    }


    /**
     * Sets the rowsetCount value for this EUSPOutput.
     * 
     * @param rowsetCount
     */
    public void setRowsetCount(int rowsetCount) {
        this.rowsetCount = rowsetCount;
    }


    /**
     * Gets the rowsets value for this EUSPOutput.
     * 
     * @return rowsets
     */
    public EUSPRowSet[] getRowsets() {
        return rowsets;
    }


    /**
     * Sets the rowsets value for this EUSPOutput.
     * 
     * @param rowsets
     */
    public void setRowsets(EUSPRowSet[] rowsets) {
        this.rowsets = rowsets;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof EUSPOutput)) return false;
        EUSPOutput other = (EUSPOutput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.retCode == other.getRetCode() &&
            ((this.retMessage==null && other.getRetMessage()==null) || 
             (this.retMessage!=null &&
              this.retMessage.equals(other.getRetMessage()))) &&
            ((this.retValue==null && other.getRetValue()==null) || 
             (this.retValue!=null &&
              this.retValue.equals(other.getRetValue()))) &&
            this.rowsetCount == other.getRowsetCount() &&
            ((this.rowsets==null && other.getRowsets()==null) || 
             (this.rowsets!=null &&
              java.util.Arrays.equals(this.rowsets, other.getRowsets())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getRetCode();
        if (getRetMessage() != null) {
            _hashCode += getRetMessage().hashCode();
        }
        if (getRetValue() != null) {
            _hashCode += getRetValue().hashCode();
        }
        _hashCode += getRowsetCount();
        if (getRowsets() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRowsets());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getRowsets(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(EUSPOutput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:iopara.eusp.shine.com", "EUSPOutput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("retValue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "retValue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.xmlsoap.org/soap/encoding/", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rowsetCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rowsetCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rowsets");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rowsets"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn:iopara.eusp.shine.com", "EUSPRowSet"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
