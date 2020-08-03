package com.fraud_detection;

import com.fraud_detection.dto.TransactionDTO;
import com.fraud_detection.external_services.Location;
import com.fraud_detection.external_services.LocationService;
import com.fraud_detection.models.User;
import com.fraud_detection.services.FraudDetectionService;
import com.fraud_detection.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FraudDetectionTransactionTests {

    @Autowired
    private FraudDetectionService fraudDetectionService;

    @MockBean
    private LocationService locationService;

    @MockBean
    private UserService userService;

    private TransactionDTO transactionDTO;
    private User user;

    @Before
    public void beforeTest() {
        transactionDTO = new TransactionDTO(
                "bob@gmail.com",
                100,
                "213.61.80.122",
                null
        );

        user = new User(
                1,
                "Bob",
                "bob@gmail.com",
                "213.61.89.122",
                1.0,
                100.0
        );
        when(userService.getUserByEmail(transactionDTO.getEmail())).thenReturn(user);
    }

    @Test
    public void testFrequencyDeviationChangedNotification_Issue() {
        Location location = new Location("success", "Germany", "Munich");
        given(locationService.getLocation(any())).willReturn(location);

        user.setFrequency(10.0);

        List<String> listOfIssues = fraudDetectionService.getListOfIssues(transactionDTO);

        assertThat(listOfIssues, contains("Frequency deviation per month was changed"));
    }

    @Test
    public void testFrequencyDeviationChangedNotification_Ok() {
        Location location = new Location("success", "Germany", "Munich");
        given(locationService.getLocation(any())).willReturn(location);

        List<String> listOfIssues = fraudDetectionService.getListOfIssues(transactionDTO);

        assertThat(listOfIssues, not(hasItem("Frequency deviation per month was changed")));
    }

    @Test
    public void testLocationNotification_Issue() {
        String userLastLocation = "213.61.89.122";

        Location location = new Location("success", "Germany", "Berlin");
        when(locationService.getLocation(transactionDTO.getLocation())).thenReturn(location);

        Location lastUserLocation = new Location("success", "Germany", "Munich");
        when(locationService.getLocation(userLastLocation)).thenReturn(lastUserLocation);

        List<String> listOfIssues = fraudDetectionService.getListOfIssues(transactionDTO);

        assertThat(
                listOfIssues,
                hasItem("Suspicious location: Germany, Berlin")
        );
    }

    @Test
    public void testLocationNotification_Ok() {
        Location location = new Location("success", "Germany", "Berlin");
        when(locationService.getLocation(transactionDTO.getLocation())).thenReturn(location);

        Location lastUserLocation = new Location("success", "Germany", "Berlin");
        when(locationService.getLocation(any())).thenReturn(lastUserLocation);

        List<String> listOfIssues = fraudDetectionService.getListOfIssues(transactionDTO);

        assertThat(
                listOfIssues,
                not(hasItem("Suspicious location: Germany, Berlin"))
        );
    }

    @Test
    public void testAmountIsTooBigNotification_Ok() {
        transactionDTO.setSkipLocation(true);

        List<String> listOfIssues = fraudDetectionService.getListOfIssues(transactionDTO);

        assertThat(
                listOfIssues,
                not(hasItem("Payment amount is much bigger than before"))
        );
    }

    @Test
    public void testAmountIsTooBigNotification_Issue() {
        transactionDTO.setSkipLocation(true);

        user.setBalance(6000.0);
        transactionDTO.setAmount(5000.0);

        List<String> listOfIssues = fraudDetectionService.getListOfIssues(transactionDTO);

        assertThat(
                listOfIssues,
                hasItem("Payment amount is much bigger than before")
        );
    }
}
