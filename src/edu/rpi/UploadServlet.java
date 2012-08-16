package edu.rpi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.mail.internet.InternetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import oracle.jdbc.OracleCallableStatement;

import com.sciquest.po.PurchaseOrder;
import com.sciquest.po.PurchaseOrderMessage;

import edu.rpi.util.SendMail;
import edu.rpi.util.StreamToString;

/**
 * @author burzyc/glosej
 * 
 */
public class UploadServlet extends HttpServlet 
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(FAMISConnectionHelper.class);
    public static final String version = "12.8";   
    private FAMISConnectionHelper connAutoCommit;

    // This get Method resolves when you try to open the URL of the service
    // from a web browser.  It's a simple way to test if your Servlet is up
    // and running, and what the J2EE server environment variables look like.
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

        String resourcesFolder = getServletContext().getRealPath("/WEB-INF/resources");
        String url = getServletContext().getInitParameter("url");
        String userName = getServletContext().getInitParameter("username");
        String password = getServletContext().getInitParameter("password");

        connAutoCommit = new FAMISConnectionHelper(
                "oracle.jdbc.OracleDriver",
                url, userName, password, true);

        String resultConnectionTest = testDBConnection();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<head>");
        out.println("<title>" + this.getClass().getName() + "</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Hello! You have reached version " + version + " of "
                + this.getClass().getName() + "</h1>");
        out.println("<p>You are using JDBC url " + url);
        out.println("<p>You are using userName " + userName);
        out.println("<p>Result from SELECT SYSDATE FROM DUAL: <span style=\"color: red;\"> " + resultConnectionTest + "</span>");
        out.println("<p>The Resource Directory Path is " + resourcesFolder);
        out.println("</body>");
        out.println("</html>");
    }


    // The Post method is what SciQuest is sending XML data to.
    // It receives the XML as a "request" messages, and issues a 
    // "response" XML message indicating one of 3 possible outcomes:
    //    - 200 - success
    //    - 400 - bad request (something was wrong w/ the data they sent us)
    //    - 500 - server error (there was something wrong internally) 
    public void doPost(HttpServletRequest req, 
            HttpServletResponse res)
    throws ServletException, IOException
    {
        String poNumber = null;
        String db_response = null;
        String responseMessage = null;
        try {
            InputStream input;
            input = req.getInputStream();

            // InputStreams can be read only once... so we read the InputStream
            // into a String. The very next line copies that String into a Source
            // object type. The InputStream is not needed again.
            String poXMLasString = StreamToString.convertStreamToString(input);
            Source poXmlAsSource = new StreamSource(new java.io.StringReader(poXMLasString));
            // Attempt to parse the inbound XML just to get the PO Number.
            // If parsing fails, that's okay, continue processing so we can insert
            // the raw XML into our logging table. 
            PurchaseOrderMessage pom = null;
            try {
                pom = POImport.unmarshalPurchaseOrderMsg(poXmlAsSource);
                poNumber = getPoNumber(pom);
            } catch (JAXBException e) {
                logger.warn("problem Unmarshalling XML");
                logger.warn(e);
                e.printStackTrace();
                poNumber = "unknown";
                pom = null;
            }

            // Get CMMS Database connection settings from the web.xml file. 
            String url = getServletContext().getInitParameter("url");
            String userName = getServletContext().getInitParameter("username");
            String password = getServletContext().getInitParameter("password");
            connAutoCommit = new FAMISConnectionHelper(
                    "oracle.jdbc.OracleDriver",
                    url, userName, password, true);

            // insert the PO file into the DB as a Clob
            db_response = insert_XML_to_DB(poNumber, poXMLasString, "PO");

            // If the result from inserting raw XML into Database was 200 (success),
            // AND the PurchaseOrderMessage is not NULL, then continue to parse 
            // the XML and create a FAMIS PO.
            if (db_response.equals("200")) {
                if (pom != null) {

                    FAMISConnectionHelper connManualCommit = new FAMISConnectionHelper(
                            "oracle.jdbc.OracleDriver",
                            url, userName, password, false);
                    POImport poi = new POImport(connManualCommit);
                    // Reuse the PurchaseOrderMessage from above, because we know
                    // it's not null.
                    String[] result = poi.parse(pom);                    
                    logger.debug("Result from POImport.parse: " + result[0] + " - " + result[1]);
                    // The result code is now the result from the parsing routing.            
                    db_response = result[0];
                    responseMessage = result[1];
                } else {
                    db_response = "400"; //bad xml object
                    responseMessage = "Bad XML object";
                }
            } else {
                responseMessage = "internal server error: could not log request";
                // Since we can't log the XML to the DB, we'll write it to file
                // and then send a notification email to the programmers.
                try {
                    logger.debug("Writing po.xml to disk...");                    
                    writeFileToDisk(poXMLasString);
                } catch (IOException e) {
                    logger.error("ERROR :: Was not able to write po.xml to disk.");
                }
                try {
                    InternetAddress[] toAddresses = {new InternetAddress("glosej@rpi.edu"),
                            new InternetAddress("burzyc@rpi.edu"),
                            new InternetAddress("lazorr@rpi.edu")};
                    SendMail.sendMessage("CmmsSciquest@rpi.edu", 
                            toAddresses, 
                            "Error in UploadServlet", 
                            "UploadServlet was not able to log the incoming XML from SciQuest to the CMMS database. Please investigate.\n" +
                    "Common causes could be incorrect username, password or url for CMMS database.");
                } catch (Exception e) {
                    logger.error("Wasn't able to send emails... sorry.");
                }
            }
        } catch (Exception e) {
            logger.error("Unhandled Exception");
            logger.error(e);
            e.printStackTrace();           
            db_response = "500"; // some sort of internal Exception
            responseMessage = "internal server error - unhandled exception";
        }
        Send_Response_Message(poNumber, db_response, responseMessage, res);
    }


    public String getPoNumber(PurchaseOrderMessage purchaseOrderMsg) {
        String poNumber = "";
        logger.debug("attempting to get the po number");
        try {
            for (Object ob : purchaseOrderMsg.getExtendedPurchaseOrderOrPurchaseOrderOrResponseMessage()) {
                if (ob.getClass().equals(PurchaseOrder.class)) {
                    PurchaseOrder po = (PurchaseOrder) ob; 
                    poNumber = po.getPOHeader().getPONumber();
                    logger.debug("poNumber:"+poNumber);                    
                }
            }
        } catch (Exception e) {
            logger.error("Exception in getPoNumber");
            e.printStackTrace();
            poNumber = "unknown";
        }
        return poNumber;
    }

    public String insert_XML_to_DB(String poNumber, String xmlIn, String type) {
        String PACKAGE_NAME = "CMMSSCI.PREQ_EXPORT_PO_IMPORT_PKG.";
        String INSERT_PO_XML = "{ call " + PACKAGE_NAME + "p_insert_po_xml(?,?,?) }";
        //CallableStatement cstmt = null;
        // We use OracleCallableStatement here because it gives us access to the 
        // method "setStringForClob()" which allows for Strings greater than 32KB to 
        // be passed into Oracle as Clobs without having to break them up, or create
        // a Clob object in Java. 
        OracleCallableStatement cstmt = null;
        try {
            cstmt = (OracleCallableStatement) connAutoCommit.getConn().prepareCall(INSERT_PO_XML);
            cstmt.setString(1, poNumber);
            cstmt.setString(2, type);
            cstmt.setStringForClob(3, xmlIn);
            cstmt.execute();
        } catch (Exception e) {
            logger.error("Error in insert_po_to_db");
            logger.error("Could not log XML to database");
            logger.error(e);
            e.printStackTrace();
            // If there is any exception, SEND A MESSAGE TO SCIQUEST - 500 MSG
            return "500";
        }
        return "200";
    }

    public boolean Send_Response_Message(String poNumberIn, String response, HttpServletResponse res) throws IOException {
        return Send_Response_Message(poNumberIn, response, "", res);
    }

    // Send the XML response back to SciQuest after receiving the PO
    //
    public boolean Send_Response_Message(String poNumberIn, String response, String message, HttpServletResponse res) 
    throws IOException
    {
        String resourcesFolder = getServletContext().getRealPath("/WEB-INF/resources");
        File outfileXML;
        String success_file = resourcesFolder + "/200.xml";
        String bad_request_file = resourcesFolder + "/400.xml";
        String server_err_file =  resourcesFolder + "/500.xml";

        OutputStream outrespXML = res.getOutputStream();

        // Decide which file to send - if not a 200 or 400 then default to a 500!!
        if (response == "200")  
            outfileXML =  new File( success_file );
        else if (response == "400") {
            outfileXML =  new File( bad_request_file );
            //            if (!message.equalsIgnoreCase("")) {
            //                byte[] buffer = new byte[(int) outfileXML.length()];
            //                FileInputStream f = new FileInputStream(outfileXML);
            //                f.read(buffer);
            //                String file400 = new String(buffer);
            //                file400.replace("bad request", message);
            //                outfileXML 
            //            }
        }
        else 
            outfileXML =  new File ( server_err_file );  // this is a 500 error 

        //             
        // Send the file
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(outfileXML));
        byte[] buf = new byte[4 * 1024]; // 4K buffer seems to be the standard
        int bytesRead; 
        while ((bytesRead = in.read(buf)) != -1) { 
            outrespXML.write (buf, 0, bytesRead); 
        }

        in.close();
        outrespXML.flush();
        outrespXML.close();

        //BufferedInputStream XMLresponse = new BufferedInputStream(new FileInputStream(outfileXML));
        byte[] buffer = new byte[(int) outfileXML.length()];
        FileInputStream f = new FileInputStream(outfileXML);
        f.read(buffer);
        String XMLresponseAsString = new String(buffer);

        // Log the response we sent to SciQuest in PO_XML table.
        insert_XML_to_DB(poNumberIn, XMLresponseAsString, "RESPONSE");

        return true;
    }// End of Send_Response_Message()

    public String testDBConnection() {
        String sysdate; 
        try {
            ResultSet rs = connAutoCommit.getConn().createStatement()
            .executeQuery("select sysdate from dual");
            rs.next();
            sysdate = (String) rs.getString(1);

        } catch (SQLException e) {            
            e.printStackTrace();
            return "CONNECTION FAILED";
        }
        return sysdate;

    }

    public void writeFileToDisk(String xmlStringIn) throws IOException {

        String resourcesFolder = getServletContext().getRealPath("/WEB-INF/resources");

        File outputFile = 
            new File( resourcesFolder + "/po.xml");
        FileWriter out = new FileWriter(outputFile);
        out.write(xmlStringIn);

        out.close();
    }

}
