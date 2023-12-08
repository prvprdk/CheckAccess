
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;


public class CheckSSH {


    public static void main(String[] args) {

        // enter your data
        // by JSch
        String username = "";
        String password = "";
        String host = "";

        boolean check = checkAccessSSH(username, password, host);
        System.out.print(check);
    }


    public static boolean checkAccessSSH(String username, String password, String host) {

        Session session = null;

        try {
            session = new JSch().getSession(username, host);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            return session.isConnected();


        } catch (JSchException e) {
            return false;
        } finally {
            if (session != null) {
                session.disconnect();
            }
        }
    }


}
