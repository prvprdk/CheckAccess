import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main {

    private static String strUrl;
    private static String username;
    private static String password;


    public static void main(String[] args) throws IOException {

        try {
            enterData();

            URL url = new URL(strUrl);
            HttpURLConnection con = getHttpURLConnection(url);

            String location = con.getHeaderField("Location");
            System.out.println(checkAccess(location));
            con.disconnect();


        } catch (Exception ep) {
            ep.getStackTrace();
            System.out.println(ep.getMessage());
        }
    }

    private static HttpURLConnection getHttpURLConnection(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setInstanceFollowRedirects(false);
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

        try (OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream())) {

            String data = String.format("log=%s&pwd=%s", username, password);
            out.write(data);
            out.flush();
        }
        return con;
    }

    private static void enterData() throws IOException {
        System.out.println("Please enter URL for testing. Format URL: 'https://your_site.ru/wp-login.php'");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        strUrl = reader.readLine();

        System.out.println("Please enter login");
        username = reader.readLine();

        System.out.println("Please enter password");
        password = reader.readLine();
    }

    private static boolean checkAccess(String location) {
        return location.contains("wp-admin");
    }
}