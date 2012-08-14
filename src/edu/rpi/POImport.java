/**
 * 
 */
package edu.rpi;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.SQLException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;

import org.apache.log4j.Logger;

import com.sciquest.po.CustomFieldValue;
import com.sciquest.po.CustomFieldValueSet;
import com.sciquest.po.POLine;
import com.sciquest.po.ProductUnitOfMeasure;
import com.sciquest.po.PurchaseOrder;
import com.sciquest.po.PurchaseOrderMessage;

/**
 * @author glosej/lazorr
 * 
 */
public class POImport {

    /**
     * 
     */
    private static Logger logger = Logger.getLogger(POImport.class);
    private static final String PACKAGE_NAME = "CMMSSCI.PREQ_EXPORT_PO_IMPORT_PKG.";
    private static final String INSERT_PO_DATA = "{ call " + PACKAGE_NAME + "p_insert_temp_po_data(?,?,?,?,?,?,?) }";
    private static final String INSERT_PO_LINE_DATA = "{ call " + PACKAGE_NAME + "p_insert_temp_po_line_data(?,?,?,?,?,?,?,?,?,?) }";
    private static final String CREATE_PO = "{ call " + PACKAGE_NAME + "p_create_po(?) }";
    private FAMISConnectionHelper conn;

    public POImport(FAMISConnectionHelper connIn) {
        conn = connIn;
    }

    /**
     * @param xmlIn
     *          a <code>InputStream</code> object type
     * @return
     *          a <code>PurchaseOrderMessage</code> representing the XML passed in.
     * @throws JAXBException
     */
    public static PurchaseOrderMessage unmarshalPurchaseOrderMsg(InputStream xmlIn) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance("com.sciquest.po");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PurchaseOrderMessage pomReturn = (PurchaseOrderMessage) unmarshaller.unmarshal(xmlIn);
        return pomReturn;                
    }

    /**
     * @param xmlIn
     *          a <code>Source</code> object type
     * @return
     *          a <code>PurchaseOrderMessage</code> representing the XML passed in.
     * @throws JAXBException
     */
    public static PurchaseOrderMessage unmarshalPurchaseOrderMsg(Source xmlIn) throws JAXBException {        
        JAXBContext context = JAXBContext.newInstance("com.sciquest.po");
        Unmarshaller unmarshaller = context.createUnmarshaller();
        PurchaseOrderMessage pomReturn = (PurchaseOrderMessage) unmarshaller.unmarshal(xmlIn);
        return pomReturn;        
    }

    /**
     * This method parses the PO XML and returns the result
     * 
     * @param pom   
     *          the <code>PurchaseOrderMessage</code> to parse
     * @return a String[] representing the result and result message
     */
    public String[] parse(PurchaseOrderMessage pom) {
        String[] result = {"200","success"};
        result[0] = "200";
        result[1] = "success";
        try {
            for (Object ob : pom.getExtendedPurchaseOrderOrPurchaseOrderOrResponseMessage()) {
                if (ob.getClass().equals(PurchaseOrder.class)) {
                    getElements((PurchaseOrder) ob);
                }
            }
        }catch (SQLException e) {
            if (e.getLocalizedMessage().toUpperCase().contains("_PO_PK") 
                    || e.getLocalizedMessage().toUpperCase().contains("ALREADY EXISTS IN CMMS")) {                
                result[0] = "400";
                result[1] = "PO Already Processed";
                // no need to print the full stack trace, just the localized msg
                logger.error(e.getErrorCode()); 
                logger.error(e.getLocalizedMessage());
            } else {
                result[0] = "400";
                result[1] = "Some element error";
                e.printStackTrace();
                logger.error(e);
            }                            
        } catch (Exception e) {
            logger.error("Some other Exception");
            result[0] = "500";
            result[1] = "server exception";
            e.printStackTrace();
            logger.error(e);
        }        
        return result;

    }

    /**
     * This method extracts all the values from only those elements we want to pull
     * into FAMIS.
     * 
     * @param po  the PurchaseOrder element to parse
     * @throws SQLException
     */
    public void getElements(PurchaseOrder po) throws SQLException {
        String requester = po.getPOHeader().getRequestor().getUserProfile().getUsername();
        logger.debug("requester:" + requester);

        String buyer = po.getPOHeader().getBuyerInfo().getEmail();
        logger.debug("buyer:"+buyer);

        String vendorNo = po.getPOHeader().getSupplier().getSupplierNumber();
        logger.debug("vendorNo:"+vendorNo);

        String poNumber = po.getPOHeader().getPONumber();
        logger.debug("poNumber:"+poNumber);

        String woNumber = "";
        String commodity = "";
        String preqNo = "";

        for (CustomFieldValueSet customFieldSet : po.getPOHeader().getCustomFieldValueSet()) {
            if (customFieldSet.getName().equalsIgnoreCase("FAMIS Work Order No.")) {
                for (CustomFieldValue customField : customFieldSet.getCustomFieldValue()) {
                    woNumber = customField.getValue();
                    logger.debug("woNumber:"+woNumber);
                }
            }
            if (customFieldSet.getName().equalsIgnoreCase("Commodity Code")) {
                for (CustomFieldValue customField : customFieldSet.getCustomFieldValue()) {
                    commodity = customField.getValue();
                    logger.debug("commodity:"+commodity);
                }
            }
            if (customFieldSet.getName().equalsIgnoreCase(
            "External Requisition Number")) {
                for (CustomFieldValue customField : customFieldSet.getCustomFieldValue()) {
                    preqNo = customField.getValue();
                    logger.debug("preqNo:"+preqNo);
                }
            }
        }

        logger.debug(poNumber);

        CallableStatement cstmt = null;
        try {
            cstmt = conn.getConn().prepareCall(INSERT_PO_DATA);
            cstmt.setString(1, poNumber);
            cstmt.setString(2, preqNo);
            cstmt.setString(3, buyer.replace("@rpi.edu", ""));
            cstmt.setString(4, requester);
            cstmt.setString(5, vendorNo);
            cstmt.setString(6, null); // was manufacturer
            cstmt.setString(7, woNumber);
            cstmt.execute();
        } catch (SQLException e) {
            conn.rollback();
            throw e;            
        }
        logger.debug("SUCCESS: inserted into TFAMIS_PO");

        for (POLine poLine : po.getPOLine()) {
            String lineNo = poLine.getLinenumber();
            logger.debug("lineNo:"+lineNo);

            String description = poLine.getItem().getDescription();
            logger.debug("description:"+description);

            String partNumber = poLine.getItem().getCatalogNumber();
            logger.debug("partNumber:"+partNumber);

            String price = poLine.getLineCharges().getUnitPrice().getMoney().getvalue();
            logger.debug("price:"+price);

            String quantity = poLine.getQuantity();
            logger.debug("quantity:"+quantity);

            String unitOfMeasure = "";

            for (ProductUnitOfMeasure puom : poLine.getItem().getProductUnitOfMeasure()) {
                if (puom.getType().equalsIgnoreCase("system")) {
                    unitOfMeasure = puom.getMeasurement().getMeasurementUnit();
                    logger.debug("unitOfMeasure:"+unitOfMeasure);
                }
            }            

            cstmt = null;
            try {
                cstmt = conn.getConn().prepareCall(INSERT_PO_LINE_DATA);
                cstmt.setString(1, poNumber);
                cstmt.setString(2, lineNo);
                cstmt.setString(3, preqNo);
                cstmt.setString(4, partNumber);
                cstmt.setString(5, quantity);
                cstmt.setString(6, price);
                cstmt.setString(7, unitOfMeasure);
                cstmt.setString(8, description);
                cstmt.setString(9, woNumber);
                cstmt.setString(10, commodity);
                cstmt.execute();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
            logger.debug("SUCCESS: inserted into TFAMIS_POLINE");

        }
        // We decided NOT to commit at this point and wait until the PO
        // has been successfully created in the FAMIS tables.  If that create
        // fails, we will rollback the entire transaction.  This means there will 
        // be no records of the PO in the temp tables, but the logs will contain
        // data elements received.
        // conn.commit();

        // Now, we're ready to call the procedure that actually creates a FAMIS PO
        // from the data in the temp tables. 
        cstmt = null;
        try {
            logger.debug("BEGIN: Create PO in FAMIS....");
            cstmt = conn.getConn().prepareCall(CREATE_PO);
            cstmt.setString(1, poNumber);
            cstmt.execute();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
        // Perform a commit here because the FAMIS Purchase Order has been 
        // successfully created with no exceptions.
        conn.commit();
        logger.debug("END: Created PO in FAMIS....");

    }

}
