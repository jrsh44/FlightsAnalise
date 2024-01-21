package com.FlightsAnalise;

import com.FlightsAnalise.model.FlightOrder;
import com.FlightsAnalise.model.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SystemTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSystem() throws InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = """
                {
                    "flyFrom": "WAW",
                    "flyTo": "LON",
                    "dateFrom": "30/01/2024",
                    "dateTo": "05/02/2024",
                    "children": 0,
                    "adults": 1,
                    "numOfTests": 2,
                    "testTimeGap": 1
                }
                """;
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<FlightOrder> response = restTemplate.postForEntity("/orders", requestEntity, FlightOrder.class);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());

        int orderId = response.getBody().getId();

        Thread.sleep(Duration.ofMinutes(2).toMillis());

        ResponseEntity<Report> getReportResponse = restTemplate.getForEntity("/report/" + orderId, Report.class);
        assertEquals(200, getReportResponse.getStatusCodeValue());
        assertNotNull(getReportResponse.getBody());

        restTemplate.delete("/report/" + orderId);
    }

}
