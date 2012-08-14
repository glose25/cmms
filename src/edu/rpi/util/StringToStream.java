package edu.rpi.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

public class StringToStream {
    private static Logger logger = Logger.getLogger(StringToStream.class);

    public static InputStream convertStringToStream(String stringIn) {
        logger.debug("inside string to stream");
        /*
         * Convert String to InputStream using ByteArrayInputStream 
         * class. This class constructor takes the string byte array 
         * which can be done by calling the getBytes() method.
         */
        InputStream is = null;
        try {
            is = new ByteArrayInputStream(stringIn.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return is;
    }
}
