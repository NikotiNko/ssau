package ftpparser.service;

import com.sun.istack.internal.Nullable;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 1
 * @since 02.11.2016.
 */
public class FTPService {


    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }

    public static FTPClient connect(String server, int port, String user, String pass) throws Exception {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            showServerReply(ftpClient);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new Exception("Operation failed. Server reply code: " + replyCode);
            }
            boolean success = ftpClient.login(user, pass);
            showServerReply(ftpClient);
            if (!success) {
                throw new Exception("Could not login to the server");
            } else {
                System.out.println("LOGGED IN SERVER");
                return ftpClient;
            }
        } catch (IOException ex) {
            throw new Exception("Oops! Something wrong happened");
        }
    }

    public static Map<String, Long> getFilesSizes(FTPClient ftpClient) throws IOException {
        Map<String, Long> sizes = new HashMap<>();
        fillMap(ftpClient, sizes, "/");
        return sizes;
    }

    private static void fillMap(FTPClient ftpClient, Map<String, Long> map, String path) throws IOException {
        FTPFile[] files = ftpClient.listFiles(path);
        for (FTPFile file : files) {
            if (file.isDirectory()) {
                if (!file.getName().equals(".") && !file.getName().equals("..")) {
                    System.out.println("Directory found:" + file.getName());
                    fillMap(ftpClient, map, path + file.getName() + "/");
                }
            } else {
                System.out.println("File found:" + file.getName());
                String extension;
                int index = file.getName().lastIndexOf('.');
                if (index != -1) {
                    extension = file.getName().substring(index + 1);
                } else {
                    extension = "undefined";
                }

                Long val = map.get(extension);
                if (val != null) {
                    map.put(extension, file.getSize() + val);
                } else {
                    map.put(extension, file.getSize());
                }
            }
        }
    }
}
