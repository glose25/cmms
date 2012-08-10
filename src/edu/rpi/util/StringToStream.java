package edu.rpi.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class StringToStream {
    public static InputStream convertStringToStream(String stringIn) {
        System.out.println("inside string to stream");
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
