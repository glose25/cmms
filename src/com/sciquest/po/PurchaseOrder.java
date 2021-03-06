//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.10.28 at 10:22:16 AM EDT 
//


package com.sciquest.po;

import java.util.ArrayList;
import java.util.List;
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
    "poHeader",
    "poLine"
})
@XmlRootElement(name = "PurchaseOrder")
public class PurchaseOrder {

    @XmlElement(name = "POHeader", required = true)
    protected POHeader poHeader;
    @XmlElement(name = "POLine", required = true)
    protected List<POLine> poLine;

    /**
     * Gets the value of the poHeader property.
     * 
     * @return
     *     possible object is
     *     {@link POHeader }
     *     
     */
    public POHeader getPOHeader() {
        return poHeader;
    }

    /**
     * Sets the value of the poHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link POHeader }
     *     
     */
    public void setPOHeader(POHeader value) {
        this.poHeader = value;
    }

    /**
     * Gets the value of the poLine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the poLine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPOLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POLine }
     * 
     * 
     */
    public List<POLine> getPOLine() {
        if (poLine == null) {
            poLine = new ArrayList<POLine>();
        }
        return this.poLine;
    }

}
