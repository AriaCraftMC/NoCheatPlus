package fr.neatmonster.nocheatplus.utilities;


import fr.neatmonster.nocheatplus.logging.StaticLog;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HTTPUtils {
    public static final String USER_AGENT_FIELD = "user-agent";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36";

    public static String connect(String s) {
        HttpGet get = new HttpGet(s);
        get.setHeader(HttpHeaders.USER_AGENT, USER_AGENT);
        try (
                CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(get);
        ) {
            if (response.getEntity() != null) {
                return EntityUtils.toString(response.getEntity());
            }
            return "";
        } catch (IOException e) {
            StaticLog.logWarning("无法连接网络。");
        }
        return "";
    }


    public static void main(String[] args) {
        System.out.println(connect("https://github.com/"));
        System.out.println(connect("https://raw.githubusercontent.com/jiangdashao/Matrix-Issues/master/version.txt"));
    }
}
