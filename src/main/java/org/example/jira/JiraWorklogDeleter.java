package org.example.jira;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JiraWorklogDeleter {
    private static final String JIRA_API_WORKLOG_PATH = "rest/api/2/issue/{issueKey}/worklog";

    public static void main(String[] args) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        // Set up authentication header
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(JiraConfig.USERNAME, JiraConfig.PASSWORD);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Calculate start and end dates for the month of April
        LocalDate startOfMonth = LocalDate.of(2023, 4, 1);
        LocalDate endOfMonth = LocalDate.of(2023, 5, 1).minusDays(1);

        // Set up JQL query to get worklog entries for the month of April
        String jqlQuery = String.format("worklogDate >= \"%s\" AND worklogDate <= \"%s\"",
                startOfMonth.format(DateTimeFormatter.ISO_LOCAL_DATE),
                endOfMonth.format(DateTimeFormatter.ISO_LOCAL_DATE));

        // Make a GET request to Jira API to get the issue keys for worklog entries matching the JQL query
        String issueKeysUrl = JiraConfig.JIRA_BASE_URL + "rest/api/2/search?jql=" + jqlQuery + "&fields=key";
        ResponseEntity<String> issueKeysResponse = restTemplate.exchange(issueKeysUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        // Delete all worklog entries for the issue keys returned by the search
        String issueKeysJson = issueKeysResponse.getBody();
        JsonNode issueKeysNode = new ObjectMapper().readTree(issueKeysJson).get("issues");
        for (JsonNode issueKeyNode : issueKeysNode) {
            String issueKey = issueKeyNode.get("key").asText();
            String deleteWorklogUrl = JiraConfig.JIRA_BASE_URL + JIRA_API_WORKLOG_PATH.replace("{issueKey}", issueKey);
            restTemplate.exchange(deleteWorklogUrl, HttpMethod.DELETE, new HttpEntity<>(headers), String.class);
            System.out.println("Deleted worklog entries for issue " + issueKey);
        }
    }
}
