package org.example.jira;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

public class JiraWorklogAdder {
    private static final String API_URL = JiraConfig.JIRA_BASE_URL + "rest/api/2/issue/{issueId}/worklog";

    public static void main(String[] args) {
        // Jira issue ID
        String issueId = "TECHCN-2955";

        // RestTemplate configuration
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(JiraConfig.USERNAME, JiraConfig.PASSWORD));

        // Start and end dates of the worklog period
        Date startDate = new GregorianCalendar(2023, Calendar.APRIL, 1).getTime();
        Date endDate = new GregorianCalendar(2023, Calendar.APRIL, 10).getTime();

        // Dates to exclude from the worklog period
        List<Date> excludedDates = Arrays.asList(
                new GregorianCalendar(2023, Calendar.APRIL, 3).getTime()
        );

        // Dates to add to the worklog period
        List<Date> extraWorklogDates = Arrays.asList(
                new GregorianCalendar(2023, Calendar.APRIL, 8).getTime()
        );

        // Combine the excluded dates and extra worklog dates
        List<Date> allExcludedDates = new ArrayList<>();
        allExcludedDates.addAll(excludedDates);
        allExcludedDates.removeAll(extraWorklogDates);

        // Get today's date
        LocalDate today = LocalDate.now();

        // Worklog duration (1 day)
        Duration worklogDuration = Duration.ofDays(1);

        // Iterate over the dates of the worklog period
        for (LocalDate date = today.withDayOfMonth(1); date.getMonth().equals(today.getMonth()); date = date.plusDays(1)) {
            if (!allExcludedDates.contains(date)) {
                // Calculate the time spent on this date (in seconds)
                long timeSpentSeconds = calculateTimeSpentSeconds();

                // Create the JSON request body
                String jsonRequestBody = String.format("{\"started\": \"%sT12:34:00.000+0000\", \"timeSpentSeconds\": %d}", date, worklogDuration.getSeconds());

                // Set the HTTP headers
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Set the request entity
                HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequestBody, headers);

                // Send the request
                ResponseEntity<String> responseEntity = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class, issueId);

                // Print the response status code
                System.out.println(responseEntity.getStatusCode());
            }
        }
    }

    private static Date addDays(Date date, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    private static String formatDate(Date date) {
        return String.format("%tF", date);
    }

    private static long calculateTimeSpentSeconds() {
        // TODO: implement time spent calculation
        return 0;
    }
}
