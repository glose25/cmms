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
    "taxShippingHandling"
})
@XmlRootElement(name = "ShippingCharges")
public class ShippingCharges {

    @XmlElement(name = "TaxShippingHandling", required = true)
    protected TaxShippingHandling taxShippingHandling;

    /**
     * Gets the value of the taxShippingHandling property.
     * 
     * @return
     *     possible object is
     *     {@link TaxShippingHandling }
     *     
     */
    public TaxShippingHandling getTaxShippingHandling() {
        return taxShippingHandling;
    }

    /**
     * Sets the value of the taxShippingHandling property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxShippingHandling }
     *     
     */
    public void setTaxShippingHandling(TaxShippingHandling value) {
        this.taxShippingHandling = value;
    }

}
