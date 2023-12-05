import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    private static String strUrl;
    private static String username;
    private static String password;


    public static void main(String[] args) throws IOException {

        try {
            enterData();

            String valUrl = getUrlForRequest(strUrl);
            URL url = new URL(valUrl);
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
        System.out.println("Please enter URL for testing");
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

    private  static String validateUrl(String url) {
        String match = "";
        Pattern pattern = Pattern.compile("[A-z0-9\\-\\.]+\\.(ru|com)");
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()) {
            match = matcher.group();
        }
        return match;
    }

    private static String getUrlForRequest (String url){
        return String.format("https://%s/wp-login.php", validateUrl(url));
    }
}