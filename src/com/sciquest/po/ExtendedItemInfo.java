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
    "generalItemInfo",
    "internalItemInfo",
    "externalItemInfo",
    "form"
})
@XmlRootElement(name = "ExtendedItemInfo")
public class ExtendedItemInfo {

    @XmlElement(name = "GeneralItemInfo")
    protected GeneralItemInfo generalItemInfo;
    @XmlElement(name = "InternalItemInfo")
    protected InternalItemInfo internalItemInfo;
    @XmlElement(name = "ExternalItemInfo")
    protected ExternalItemInfo externalItemInfo;
    @XmlElement(name = "Form")
    protected Form form;

    /**
     * Gets the value of the generalItemInfo property.
     * 
     * @return
     *     possible object is
     *     {@link GeneralItemInfo }
     *     
     */
    public GeneralItemInfo getGeneralItemInfo() {
        return generalItemInfo;
    }

    /**
     * Sets the value of the generalItemInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeneralItemInfo }
     *     
     */
    public void setGeneralItemInfo(GeneralItemInfo value) {
        this.generalItemInfo = value;
    }

    /**
     * Gets the value of the internalItemInfo property.
     * 
     * @return
     *     possible object is
     *     {@link InternalItemInfo }
     *     
     */
    public InternalItemInfo getInternalItemInfo() {
        return internalItemInfo;
    }

    /**
     * Sets the value of the internalItemInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternalItemInfo }
     *     
     */
    public void setInternalItemInfo(InternalItemInfo value) {
        this.internalItemInfo = value;
    }

    /**
     * Gets the value of the externalItemInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ExternalItemInfo }
     *     
     */
    public ExternalItemInfo getExternalItemInfo() {
        return externalItemInfo;
    }

    /**
     * Sets the value of the externalItemInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExternalItemInfo }
     *     
     */
    public void setExternalItemInfo(ExternalItemInfo value) {
        this.externalItemInfo = value;
    }

    /**
     * Gets the value of the form property.
     * 
     * @return
     *     possible object is
     *     {@link Form }
     *     
     */
    public Form getForm() {
        return form;
    }

    /**
     * Sets the value of the form property.
     * 
     * @param value
     *     allowed object is
     *     {@link Form }
     *     
     */
    public void setForm(Form value) {
        this.form = value;
    }

}
