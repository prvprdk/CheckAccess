import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

public class CheckFTP {

    public static void main(String[] args) throws IOException {


        // enter your data
        String username = "";
        String password = "";
        String host = "";

        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(host);
        ftpClient.login(username, password);

        // After connection attempt, you should check the reply code to verify

        int reply = ftpClient.getReplyCode();
        boolean check = FTPReply.isPositiveCompletion(reply);
        System.out.println(check);

        ftpClient.disconnect();
    }
}
