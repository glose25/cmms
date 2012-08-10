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
                System.out.println(e.getErrorCode()); 
                System.out.println(e.getLocalizedMessage());
            } else {
                result[0] = "400";
                result[1] = "Some element error";
                e.printStackTrace();
            }                            
        } catch (Exception e) {
            System.out.println("Some other Exception");
            result[0] = "500";
            result[1] = "server exception";
            e.printStackTrace();
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
        System.out.println("requester:" + requester);

        String buyer = po.getPOHeader().getBuyerInfo().getEmail();
        System.out.println("buyer:"+buyer);

        String vendorNo = po.getPOHeader().getSupplier().getSupplierNumber();
        System.out.println("vendorNo:"+vendorNo);

        String poNumber = po.getPOHeader().getPONumber();
        System.out.println("poNumber:"+poNumber);

        String woNumber = "";
        String commodity = "";
        String preqNo = "";

        for (CustomFieldValueSet customFieldSet : po.getPOHeader().getCustomFieldValueSet()) {
            if (customFieldSet.getName().equalsIgnoreCase("FAMIS Work Order No.")) {
                for (CustomFieldValue customField : customFieldSet.getCustomFieldValue()) {
                    woNumber = customField.getValue();
                    System.out.println("woNumber:"+woNumber);
                }
            }
            if (customFieldSet.getName().equalsIgnoreCase("Commodity Code")) {
                for (CustomFieldValue customField : customFieldSet.getCustomFieldValue()) {
                    commodity = customField.getValue();
                    System.out.println("commodity:"+commodity);
                }
            }
            if (customFieldSet.getName().equalsIgnoreCase(
            "External Requisition Number")) {
                for (CustomFieldValue customField : customFieldSet.getCustomFieldValue()) {
                    preqNo = customField.getValue();
                    System.out.println("preqNo:"+preqNo);
                }
            }
        }

        System.out.println(poNumber);

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
        System.out.println("SUCCESS: inserted into TFAMIS_PO");

        for (POLine poLine : po.getPOLine()) {
            String lineNo = poLine.getLinenumber();
            System.out.println("lineNo:"+lineNo);

            String description = poLine.getItem().getDescription();
            System.out.println("description:"+description);

            String partNumber = poLine.getItem().getCatalogNumber();
            System.out.println("partNumber:"+partNumber);

            String price = poLine.getLineCharges().getUnitPrice().getMoney().getvalue();
            System.out.println("price:"+price);

            String quantity = poLine.getQuantity();
            System.out.println("quantity:"+quantity);

            String unitOfMeasure = "";

            for (ProductUnitOfMeasure puom : poLine.getItem().getProductUnitOfMeasure()) {
                if (puom.getType().equalsIgnoreCase("system")) {
                    unitOfMeasure = puom.getMeasurement().getMeasurementUnit();
                    System.out.println("unitOfMeasure:"+unitOfMeasure);
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
            System.out.println("SUCCESS: inserted into TFAMIS_POLINE");

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
            System.out.println("BEGIN: Create PO in FAMIS....");
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
        System.out.println("END: Created PO in FAMIS....");

    }

}
