//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.10.28 at 10:22:16 AM EDT 
//


package com.sciquest.po;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "status",
    "objectErrors"
})
@XmlRootElement(name = "ResponseMessage")
public class ResponseMessage {

    @XmlElement(name = "Status", required = true)
    protected Status status;
    @XmlElement(name = "ObjectErrors")
    protected ObjectErrors objectErrors;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the objectErrors property.
     * 
     * @return
     *     possible object is
     *     {@link ObjectErrors }
     *     
     */
    public ObjectErrors getObjectErrors() {
        return objectErrors;
    }

    /**
     * Sets the value of the objectErrors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObjectErrors }
     *     
     */
    public void setObjectErrors(ObjectErrors value) {
        this.objectErrors = value;
    }

}