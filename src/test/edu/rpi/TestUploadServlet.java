/**
 * 
 */
package test.edu.rpi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.rpi.util.KeyboardReader;
import edu.rpi.util.StreamToString;

public class TestUploadServlet {

    private String destinationUrl;

    public static String GetXMLDocument(String filename)
    {
        String xmlLines = "";
        StringBuffer sb = new StringBuffer();

        try {

            File inputFile = new File(filename);

            BufferedReader in=new BufferedReader(new FileReader(inputFile));
            String line=null;

            while ((line=in.readLine())!=null)
            {
                sb.append(line);
            }
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        xmlLines = sb.toString();
        return xmlLines;
    }


    public static void main(String args[]) {
        String xmldataOut = "";
        String POfileToLoad = KeyboardReader.getPromptedString("PO test file path and name\n");
        //System.out.println("Example Servlet URL: https://testfixx.rpi.edu:5555/servlet/UploadServlet\n");
        System.out.println("Example Servlet URL: https://cmmscitest.rpi.edu:5557/iacs-cmms/UploadServlet\n");
        String servletURL = KeyboardReader.getPromptedString("URL of Servlet to Test\n");
        xmldataOut = GetXMLDocument(POfileToLoad);
        TestUploadServlet testUploadServlet = new TestUploadServlet(servletURL);
        //        System.out.println("XML LENGTH : " + xmldataOut.length());
        //        System.out.println(xmldataOut);
        // the .transmit method actually transmit the Preq to SciQuest.        
        testUploadServlet.transmit(xmldataOut);
        //testUploadServlet.pingServlet();
    }

    public TestUploadServlet(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }


    public void pingServlet() {
        try {
            URL url = new URL(destinationUrl);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestMethod("GET");
            
            http.setRequestProperty("Content-Type", "text/xml; charset="
                    + "UTF-8");
            http.setRequestProperty("Content-Transfer-Encoding", "text");

            // get the response code to prime the input stream
            int rc = http.getResponseCode();
            System.out.println("Server Response code is " + rc);

            // // Create an input stream from the server
            InputStream responseStream = http.getInputStream();
            String responseString = StreamToString
            .convertStreamToString(responseStream);

            http.disconnect();

            parseResponse(responseString);

            System.out.println("RESPONSE: \n" + responseString);

        } catch (IOException ex) {
            System.err.println(ex);
        }
        
    }
    
    /**
     * @param preReqNo
     * @param xmldata
     * @param destinationUrl
     */
    public void transmit(String xmldata) {
        try {

            // Establish connection with the server
            URL url = new URL(destinationUrl);
            // "http://usertest.sciquest.com/apps/Router/PRXMLImport");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestMethod("POST");

            http.setRequestProperty("Content-Type", "text/xml; charset="
                    + "UTF-8");
            http.setRequestProperty("Content-Transfer-Encoding", "text");

            // Create an output stream to the server
            BufferedWriter toServer = new BufferedWriter(
                    new OutputStreamWriter(http.getOutputStream()));

            // Write the data to the server, flush and close the stream
            toServer.write(xmldata);
            System.out.println("Sending data " + xmldata.length());

            toServer.flush();
            toServer.close();

            // get the response code to prime the input stream
            int rc = http.getResponseCode();
            System.out.println("Server Response code is " + rc);

            // // Create an input stream from the server
            InputStream responseStream = http.getInputStream();
            String responseString = StreamToString
            .convertStreamToString(responseStream);

            http.disconnect();

            parseResponse(responseString);

            System.out.println("RESPONSE: \n" + responseString);

        } catch (IOException ex) {
            System.err.println(ex);
        }

    }

    /**
     * @param responseStringIn
     */
    public void parseResponse(String responseStringIn) {
        InputStream is;
        try {
            is = new ByteArrayInputStream(responseStringIn.getBytes("UTF-8"));
            parseResponse(is);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }        
    }

    /**
     * @param responseStreamIn
     */
    public void parseResponse(InputStream responseStreamIn) {
        try {
            System.out.println(StreamToString.convertStreamToString(responseStreamIn));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}