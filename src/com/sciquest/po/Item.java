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
    "catalogNumber",
    "auxiliaryCatalogNumber",
    "description",
    "productUnitOfMeasure",
    "productSize",
    "unspsc",
    "commodityCode",
    "productReferenceNumber",
    "productType",
    "productClassification",
    "manufacturerInfo",
    "extendedItemInfo",
    "leadTimeDays",
    "casNumber",
    "radioNuclide",
    "barCode",
    "form"
})
@XmlRootElement(name = "Item")
public class Item {

    @XmlElement(name = "CatalogNumber")
    protected String catalogNumber;
    @XmlElement(name = "AuxiliaryCatalogNumber")
    protected String auxiliaryCatalogNumber;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "ProductUnitOfMeasure")
    protected List<ProductUnitOfMeasure> productUnitOfMeasure;
    @XmlElement(name = "ProductSize")
    protected List<ProductSize> productSize;
    @XmlElement(name = "UNSPSC")
    protected String unspsc;
    @XmlElement(name = "CommodityCode")
    protected String commodityCode;
    @XmlElement(name = "ProductReferenceNumber")
    protected String productReferenceNumber;
    @XmlElement(name = "ProductType", required = true)
    protected String productType;
    @XmlElement(name = "ProductClassification")
    protected List<ProductClassification> productClassification;
    @XmlElement(name = "ManufacturerInfo")
    protected ManufacturerInfo manufacturerInfo;
    @XmlElement(name = "ExtendedItemInfo")
    protected ExtendedItemInfo extendedItemInfo;
    @XmlElement(name = "LeadTimeDays")
    protected String leadTimeDays;
    @XmlElement(name = "CASNumber")
    protected String casNumber;
    @XmlElement(name = "RadioNuclide")
    protected String radioNuclide;
    @XmlElement(name = "BarCode")
    protected String barCode;
    @XmlElement(name = "Form")
    protected Form form;

    /**
     * Gets the value of the catalogNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCatalogNumber() {
        return catalogNumber;
    }

    /**
     * Sets the value of the catalogNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCatalogNumber(String value) {
        this.catalogNumber = value;
    }

    /**
     * Gets the value of the auxiliaryCatalogNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuxiliaryCatalogNumber() {
        return auxiliaryCatalogNumber;
    }

    /**
     * Sets the value of the auxiliaryCatalogNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuxiliaryCatalogNumber(String value) {
        this.auxiliaryCatalogNumber = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the productUnitOfMeasure property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productUnitOfMeasure property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductUnitOfMeasure().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductUnitOfMeasure }
     * 
     * 
     */
    public List<ProductUnitOfMeasure> getProductUnitOfMeasure() {
        if (productUnitOfMeasure == null) {
            productUnitOfMeasure = new ArrayList<ProductUnitOfMeasure>();
        }
        return this.productUnitOfMeasure;
    }

    /**
     * Gets the value of the productSize property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productSize property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductSize().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductSize }
     * 
     * 
     */
    public List<ProductSize> getProductSize() {
        if (productSize == null) {
            productSize = new ArrayList<ProductSize>();
        }
        return this.productSize;
    }

    /**
     * Gets the value of the unspsc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUNSPSC() {
        return unspsc;
    }

    /**
     * Sets the value of the unspsc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUNSPSC(String value) {
        this.unspsc = value;
    }

    /**
     * Gets the value of the commodityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * Sets the value of the commodityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommodityCode(String value) {
        this.commodityCode = value;
    }

    /**
     * Gets the value of the productReferenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductReferenceNumber() {
        return productReferenceNumber;
    }

    /**
     * Sets the value of the productReferenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductReferenceNumber(String value) {
        this.productReferenceNumber = value;
    }

    /**
     * Gets the value of the productType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductType() {
        return productType;
    }

    /**
     * Sets the value of the productType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductType(String value) {
        this.productType = value;
    }

    /**
     * Gets the value of the productClassification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the productClassification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProductClassification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProductClassification }
     * 
     * 
     */
    public List<ProductClassification> getProductClassification() {
        if (productClassification == null) {
            productClassification = new ArrayList<ProductClassification>();
        }
        return this.productClassification;
    }

    /**
     * Gets the value of the manufacturerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ManufacturerInfo }
     *     
     */
    public ManufacturerInfo getManufacturerInfo() {
        return manufacturerInfo;
    }

    /**
     * Sets the value of the manufacturerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ManufacturerInfo }
     *     
     */
    public void setManufacturerInfo(ManufacturerInfo value) {
        this.manufacturerInfo = value;
    }

    /**
     * Gets the value of the extendedItemInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ExtendedItemInfo }
     *     
     */
    public ExtendedItemInfo getExtendedItemInfo() {
        return extendedItemInfo;
    }

    /**
     * Sets the value of the extendedItemInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtendedItemInfo }
     *     
     */
    public void setExtendedItemInfo(ExtendedItemInfo value) {
        this.extendedItemInfo = value;
    }

    /**
     * Gets the value of the leadTimeDays property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLeadTimeDays() {
        return leadTimeDays;
    }

    /**
     * Sets the value of the leadTimeDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLeadTimeDays(String value) {
        this.leadTimeDays = value;
    }

    /**
     * Gets the value of the casNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCASNumber() {
        return casNumber;
    }

    /**
     * Sets the value of the casNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCASNumber(String value) {
        this.casNumber = value;
    }

    /**
     * Gets the value of the radioNuclide property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRadioNuclide() {
        return radioNuclide;
    }

    /**
     * Sets the value of the radioNuclide property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRadioNuclide(String value) {
        this.radioNuclide = value;
    }

    /**
     * Gets the value of the barCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBarCode() {
        return barCode;
    }

    /**
     * Sets the value of the barCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBarCode(String value) {
        this.barCode = value;
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
