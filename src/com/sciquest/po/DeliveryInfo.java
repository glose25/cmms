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
    "expedite",
    "shippingMethod",
    "requestedDeliveryDate"
})
@XmlRootElement(name = "DeliveryInfo")
public class DeliveryInfo {

    @XmlElement(name = "Expedite")
    protected Expedite expedite;
    @XmlElement(name = "ShippingMethod")
    protected ShippingMethod shippingMethod;
    @XmlElement(name = "RequestedDeliveryDate")
    protected String requestedDeliveryDate;

    /**
     * Gets the value of the expedite property.
     * 
     * @return
     *     possible object is
     *     {@link Expedite }
     *     
     */
    public Expedite getExpedite() {
        return expedite;
    }

    /**
     * Sets the value of the expedite property.
     * 
     * @param value
     *     allowed object is
     *     {@link Expedite }
     *     
     */
    public void setExpedite(Expedite value) {
        this.expedite = value;
    }

    /**
     * Gets the value of the shippingMethod property.
     * 
     * @return
     *     possible object is
     *     {@link ShippingMethod }
     *     
     */
    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    /**
     * Sets the value of the shippingMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShippingMethod }
     *     
     */
    public void setShippingMethod(ShippingMethod value) {
        this.shippingMethod = value;
    }

    /**
     * Gets the value of the requestedDeliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestedDeliveryDate() {
        return requestedDeliveryDate;
    }

    /**
     * Sets the value of the requestedDeliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestedDeliveryDate(String value) {
        this.requestedDeliveryDate = value;
    }

}
