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
    "tshConfig",
    "tshActualCharge"
})
@XmlRootElement(name = "TaxShippingHandling")
public class TaxShippingHandling {

    @XmlElement(name = "TSHConfig", required = true)
    protected TSHConfig tshConfig;
    @XmlElement(name = "TSHActualCharge")
    protected TSHActualCharge tshActualCharge;

    /**
     * Gets the value of the tshConfig property.
     * 
     * @return
     *     possible object is
     *     {@link TSHConfig }
     *     
     */
    public TSHConfig getTSHConfig() {
        return tshConfig;
    }

    /**
     * Sets the value of the tshConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSHConfig }
     *     
     */
    public void setTSHConfig(TSHConfig value) {
        this.tshConfig = value;
    }

    /**
     * Gets the value of the tshActualCharge property.
     * 
     * @return
     *     possible object is
     *     {@link TSHActualCharge }
     *     
     */
    public TSHActualCharge getTSHActualCharge() {
        return tshActualCharge;
    }

    /**
     * Sets the value of the tshActualCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSHActualCharge }
     *     
     */
    public void setTSHActualCharge(TSHActualCharge value) {
        this.tshActualCharge = value;
    }

}
