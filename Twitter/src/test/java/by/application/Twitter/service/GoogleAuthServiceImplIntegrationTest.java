package by.application.Twitter.service;

import by.application.Twitter.model.User;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
public class GoogleAuthServiceImplIntegrationTest {
    @TestConfiguration
    static class GoogleAuthServiceImplTestContextConfiguration {

        @Bean
        public GoogleAuthService googleAuthService() {
            return new GoogleAuthServiceImpl();
        }
    }

    @Autowired
    private GoogleAuthService googleAuthService;
    @MockBean
    private OAuth2AuthorizedClientService authorizedClientService;
    @MockBean
    private UserService userService;

    private Map<String, String> testMap;

    @Before
    public void setUp() {
        testMap = new HashMap<String, String>();
        testMap.put("email", "testEmail@gmail.com");
        testMap.put("given_name", "testGivenName");
        testMap.put("family_name", "testFamilyName");
    }

    @Test
    public void loadUser() {
        //when
        User userFromGoogle = googleAuthService.loadUser(testMap);

        // then
        Assertions.assertThat(userFromGoogle.getPassword())
                .isNotEmpty();
    }

    @Test
    public void getUsernameFromEmail() {
        //when
        String usernameFromEmail = googleAuthService.getUsernameFromEmail("testEmail@gmail.com");

        // then
        Assertions.assertThat(usernameFromEmail)
                .isEqualTo("testEmail");
    }

    @Test
    public void generateRandomPassword() {
        //when
        String randomPassword = googleAuthService.generateRandomPassword();

        // then
        Assertions.assertThat(randomPassword)
                .isNotEmpty();
    }
}
