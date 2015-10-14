package com.lean56.andplug.utils;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * InputStreamUtils
 * InputStream/String/Byte Transfer
 *
 * @author
 * @author Charles
 */
public class InputStreamUtils {

    private final static String TAG = InputStreamUtils.class.getSimpleName();

    final static int BUFFER_SIZE = 4096;

    /**
     * InputStream To String
     *
     * @param in InputStream
     * @return String
     * @throws Exception
     */
    public static String inputStream2String(InputStream in) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);
        data = null;
        return new String(outStream.toByteArray(), "ISO-8859-1");
    }

    /**
     * InputStream To String with encoding
     *
     * @param in
     * @param encoding
     * @return
     * @throws Exception
     */
    public static String inputStream2String(InputStream in, String encoding) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);

        data = null;
        return new String(outStream.toByteArray(), encoding);
    }

    /**
     * String 2 InputStream
     *
     * @param in
     * @return
     * @throws Exception
     */
    public static InputStream string2InputStream(String in) throws Exception {
        return new ByteArrayInputStream(in.getBytes("ISO-8859-1"));
    }

    /**
     * InputStream to byte array
     *
     * @param in InputStream
     * @return byte[]
     * @throws IOException
     */
    public static byte[] inputStream2Byte(InputStream in) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[BUFFER_SIZE];
        int len;
        try {
            while ((len = in.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            in.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return outputStream.toByteArray();
    }

    /**
     * byte array to InputStream
     *
     * @param in
     * @return
     * @throws Exception
     */
    public static InputStream byteTOInputStream(byte[] in) throws Exception {
        return new ByteArrayInputStream(in);
    }

    /**
     * byte array to String
     *
     * @param in
     * @return
     * @throws Exception
     */
    public static String byteTOString(byte[] in) throws Exception {
        return inputStream2String(byteTOInputStream(in));
    }
}
