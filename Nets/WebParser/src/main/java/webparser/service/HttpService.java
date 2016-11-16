package webparser.service;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class HttpService {

    private static Map<String, String> headers;

    private static Socket socket;

    private static void openSocket(String host,int port) throws IOException {
        socket = new Socket(host, port);
        socket.setSoTimeout(1000);
    }

    private static void closeSocket() throws IOException {
        socket.close();
    }

    public static String sendRequest(String host,String path, String method) throws Exception {
        return getBody(connectWith(host, path, method));

    }
    public static Map<String, String> getHeaders() {
        return headers;
    }

    public static String sendRequest(String host, String method) throws Exception {
        String result = connectWith(host, "/", method);
        headers = parseHeaders(getHeaders(result));
        return result;
    }

    private static String connectWith(String host, String path, String method) throws Exception {
        openSocket(host,80);
        sendHeader(socket.getOutputStream(), host ,path, method);
        String response = getResponse(socket.getInputStream());
        closeSocket();
        return response;
    }

    private static String getBody(String response){
        String body;
        // body parsing
        int headersEndIndex = response.indexOf("\r\n\r\n");
        if (headersEndIndex == -1) {
            headersEndIndex = response.indexOf("\n\n");
            if (headersEndIndex == -1) {
                body = response;

            } else {
                body = response.substring(headersEndIndex + 2);
            }
        } else {
            body = response.substring(headersEndIndex + 4);
        }
        return body;
    }

    private static String getHeaders(String response){
        String headers;
        // headers parsing
        int headersEndIndex = response.indexOf("\r\n\r\n");
        if (headersEndIndex == -1) {
            headersEndIndex = response.indexOf("\n\n");
            if (headersEndIndex == -1) {
                headers = "";
            } else {
                headers = response.substring(0, headersEndIndex);
            }
        } else {
            headers = response.substring(0, headersEndIndex);
        }
        return headers;
    }

    private static void sendHeader(OutputStream os, String host, String path, String method) throws IOException {
        os.write((method + " " + path + " HTTP/1.1\r\n").getBytes());
        os.write(("Host: " + host + "\r\n\r\n").getBytes());
        System.out.println("Header have been sent. Path:" + path);
    }

    private static String getResponse(InputStream is) throws IOException, InterruptedException, TimeoutException, ExecutionException {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bfr = new BufferedReader(isr);
        StringBuffer sbf = new StringBuffer();
        try {
            int readByte = bfr.read();
            sbf.append((char) readByte);
            while (readByte > 0) {
                readByte = bfr.read();
                sbf.append((char) readByte);
            }

        } catch (SocketTimeoutException t) {
            System.out.println("Loading interrupted. Timeout.\n");
        }
        return sbf.toString();
    }

    private static Map<String, String> parseHeaders(String headers) {
        Map<String, String> result = new HashMap<>();

        String[] lines = headers.split("\n");
        for (String line : lines) {
            if (!line.isEmpty()) {
                line = line.trim();
                int index = line.indexOf(':');
                if (index!=-1)
                    result.put(line.substring(0, index), line.substring(index+1));
            }
        }
        return result;
    }


}