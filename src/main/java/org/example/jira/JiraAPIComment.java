package org.example.jira;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JiraAPIComment {
    private static final String JIRA_URL = "https://jira.homecredit.net/jira";
    private static final String ISSUE_KEY = "TECHCN-2950";
    private static final String JIRA_USERNAME = "eric.ding";
    private static final String JIRA_PASSWORD = "ejaF9eja";

    public static void main(String[] args) throws Exception {

        // 构建API请求的URL
        String apiUrl = JIRA_URL + "/rest/api/2/issue/" + ISSUE_KEY + "/comment";

        // 构建API请求的JSON数据
        String jsonData = "{\"body\": \"This is a test comment.\"}";

        // 构建API请求的连接
        URL url = new URL(apiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // 设置API请求的方法和身份验证
        con.setRequestMethod("POST");
        String auth = JIRA_USERNAME + ":" + JIRA_PASSWORD;
        String encodedAuth = new String(java.util.Base64.getEncoder().encode(auth.getBytes()));
        con.setRequestProperty("Authorization", "Basic " + encodedAuth);

        // 设置API请求的内容类型和长度
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Content-Length", String.valueOf(jsonData.length()));

        // 向API发送请求
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(jsonData);
        wr.flush();
        wr.close();

        // 获取API响应
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // 检查API响应是否成功
        if (responseCode == 201) {
            System.out.println("Comment added successfully!");
        } else {
            System.out.println("Failed to add comment!");
        }
    }
}
