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
    "poClause"
})
@XmlRootElement(name = "POClauses")
public class POClauses {

    @XmlElement(name = "POClause")
    protected List<POClause> poClause;

    /**
     * Gets the value of the poClause property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the poClause property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPOClause().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POClause }
     * 
     * 
     */
    public List<POClause> getPOClause() {
        if (poClause == null) {
            poClause = new ArrayList<POClause>();
        }
        return this.poClause;
    }

}