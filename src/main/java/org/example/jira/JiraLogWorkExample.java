package org.example.jira;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JiraLogWorkExample {
    private static final String JIRA_BASE_URL = "https://jira.homecredit.net/jira";
    private static final String JIRA_ISSUE_KEY = "TECHCN-2950";
    private static final String JIRA_USERNAME = "eric.ding";
    private static final String JIRA_PASSWORD = "ejaF9eja";

    public static void main(String[] args) {
        logWork();
    }

    public static void logWork() {
        // Encode Jira API credentials as base64
        String credentials = JIRA_USERNAME + ":" + JIRA_PASSWORD;
        byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedCredentials);

        // Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authHeader);

        // Get start and end dates for this month
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        // Log work for each day of this month
        List<LocalDate> dates = getDatesBetween(startOfMonth, endOfMonth);
        for (LocalDate date : dates) {
            // Skip weekends
            if (date.getDayOfWeek().getValue() > 5) {
                continue;
            }

            JSONObject requestBody = new JSONObject();
            requestBody.put("comment", "Work logged via Jira API");
            requestBody.put("timeSpentSeconds", 3600);
            requestBody.put("started", date.format(DateTimeFormatter.ISO_DATE) + "T09:00:00.000+0000");

            HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
            String url = JIRA_BASE_URL + "/rest/api/2/issue/" + JIRA_ISSUE_KEY + "/worklog";
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                System.out.println("Success to add worklog for " + date);
            } else {
                System.out.println("Failed to add worklog for " + date);
            }
        }
    }

    private static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            dates.add(date);
            date = date.plusDays(1);
        }
        return dates;
    }
}
