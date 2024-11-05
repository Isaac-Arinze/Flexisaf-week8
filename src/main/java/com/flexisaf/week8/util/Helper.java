package com.flexisaf.week8.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexisaf.week8.constant.Generic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


public class Helper {

    public static final DecimalFormat df2dp = new DecimalFormat("0.00");

    public static final SimpleDateFormat LONG_DATE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.S");

    public static final SimpleDateFormat DATE_FORMAT_DASH = new SimpleDateFormat(
            "yyyy-MM-dd");

    private static final Logger logger = LoggerFactory.getLogger(Helper.class);

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static void log(String message) {
        logger.info(message);
    }

    public static void print(Object object) {
        try {
            logger.info(objectMapper.writeValueAsString(object));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> boolean isEmpty(T value) {
        if (value == null)
            return true;
        if (value instanceof String && ((String) value).trim().isEmpty())
            return true;
        return false;
    }

    public static <T> boolean isNotEmpty(T value) {
        return !isEmpty(value);
    }

    public static String getAlphaNumeric(int size) {
        String stringToPickFrom = Generic.ALL_ALPHANUMERIC;
        StringBuffer stringBuffer = new StringBuffer(size);
        for (int i = 0; i < size; i++) {
            int index = (int) (stringToPickFrom.length() * Math.random());
            stringBuffer.append(stringToPickFrom.charAt(index));
        }
        return stringBuffer.toString();
    }

    public static String getLowerAlphabets(int size) {
        String stringToPickFrom = Generic.ALL_LOWER_ALPHABETS;
        StringBuffer stringBuffer = new StringBuffer(size);
        for (int i = 0; i < size; i++) {
            int index = (int) (stringToPickFrom.length() * Math.random());
            stringBuffer.append(stringToPickFrom.charAt(index));
        }
        return stringBuffer.toString();
    }

    public static String getCapAlphabets(int size) {
        String stringToPickFrom = Generic.ALL_CAP_ALPHABETS;
        StringBuffer stringBuffer = new StringBuffer(size);
        for (int i = 0; i < size; i++) {
            int index = (int) (stringToPickFrom.length() * Math.random());
            stringBuffer.append(stringToPickFrom.charAt(index));
        }
        return stringBuffer.toString();
    }

    public static String getNumeric(int size) {
        String numbersToPickFrom = Generic.ALL_NUMERIC;
        StringBuffer stringBuffer = new StringBuffer(size);
        for (int i = 0; i < size; i++) {
            int index = (int) (numbersToPickFrom.length() * Math.random());
            stringBuffer.append(numbersToPickFrom.charAt(index));
        }
        return stringBuffer.toString();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();// .replaceAll("-", "");
    }

    public static String getTransactionReference() {
        // String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        Long currentDateTime = System.currentTimeMillis();
        String transactionRef = /** uuid +"t"+ **/
                String.valueOf(currentDateTime);

        return transactionRef.toUpperCase();
    }

    public static String encodeBase64(String data) {
        return Base64.getEncoder().withoutPadding().encodeToString(data.getBytes());
    }

    public static String encodeBase64String(String data) {
        Base64.Encoder encoder = Base64.getUrlEncoder();
        String encodedUrl = encoder.encodeToString(data.getBytes());
        return encodedUrl;
    }

    public static byte[] decodeBase64String(String data) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(data);
        return bytes;
    }

    public static LocalDateTime getDateTime() {
        return LocalDateTime.now(getTimeZone());
    }

    public static LocalDate getDate() {
        return LocalDate.now(getTimeZone());
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // YYYY-MM-DD
        return dateFormat.format(new Date()).toString(); // will print like 2014-02-20
    }

    public static long getTime() {
        return new Date().getTime();
    }

    public static String getDateTime(LocalDateTime dt) {
        return dt.format(getDateTimeFormatter());
    }

    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
    }

    public static DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public static ZoneOffset getTimeZone() {
        return ZoneOffset.of("+1");
    }

    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        if (Helper.isEmpty(dateToConvert))
            return null;
        return dateToConvert.toInstant().atZone(getTimeZone()).toLocalDateTime();
    }

    public static List<String> isValidPassword(String passwordhere) {

        List<String> errorList = new ArrayList<String>();

        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        if (passwordhere.length() <= Generic.PASSWORD_LENGTH) {
            errorList.add("Password length must have at least "
                    + String.valueOf(Generic.PASSWORD_LENGTH) + " character !!");
        }
        if (!lowerCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least one lowercase character !!");
        }
        if (!digitCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least one digit character !!");
        }

        return errorList;

    }

    public static List<String> isValidPassword(String passwordhere, int passwordLength) {

        List<String> errorList = new ArrayList<String>();

        // Pattern specailCharPatten = Pattern.compile("[^a-z0-9 ]",
        // Pattern.CASE_INSENSITIVE);
        // Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        if (passwordhere.length() < passwordLength) {
            errorList.add("Password length must have at least " + passwordLength
                    + " character !!");
        }
        if (!lowerCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least one lowercase character !!");
        }
        if (!digitCasePatten.matcher(passwordhere).find()) {
            errorList.add("Password must have at least one digit character !!");
        }

        return errorList;

    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }

    public static String getMimeType(byte data[]) throws Exception {
        InputStream is = new BufferedInputStream(new ByteArrayInputStream(data));
        // BufferedInputStream is = new BufferedInputStream(new
        // ByteArrayInputStream(data));
        // InputStream is = new ByteArrayInputStream(data);
        String mimeType = URLConnection.guessContentTypeFromStream(is);
        return mimeType;
    }

    public static boolean isValidString(String str) {
        return !(str == null || str.isEmpty());
    }

    public static boolean isValidEmail(String email) {
        return isValidString(email) && email.matches(Generic.EMAIL_REG_EX);
    }

    private static int getDayOfTheYear() {
        // int dayOfYear = LocalDate.now().getDayOfYear();
        Calendar calendar = Calendar.getInstance();
        int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        return dayOfYear;
    }

    public static String getPrimeFactors(int number) {
        // Print the number of 2s that divide n
        while (number % 2 == 0) {
            number /= 2;
        }

        // n must be odd at this point. So we can
        // skip one element (Note i = i +2)
        for (int i = 3; i <= Math.sqrt(number); i += 2) {
            // While i divides n, print i and divide n
            while (number % i == 0) {
                number /= i;
            }
        }

        // This condition is to handle the case whien
        // n is a prime number greater than 2
        /*
         * if (number > 2) System.out.print(number);
         */

        return null;
    }

    private static int getNthPrime(int nth) {
        int num, count, i;
        num = 1;
        count = 0;
        while (count < nth) {
            num = num + 1;
            for (i = 2; i <= num; i++) {
                if (num % i == 0) {
                    break;
                }
            }
            if (i == num) {
                count = count + 1;
            }
        }
        return num;
    }


}


