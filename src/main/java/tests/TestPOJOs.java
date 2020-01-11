package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.UserPOJO;
import org.junit.Test;


import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestPOJOs {
    @Test
    public void testUserPOJO() throws IOException {
        final String userName = "ilie";
        final String password = "iliepass";
        final String text = String.format("{ \"userOrEmail\":\"%s\", \"password\":\"%s\"}", userName, password);
        UserPOJO userPOJO = new ObjectMapper().readValue(text, UserPOJO.class);
        assertThat(userPOJO.getUserOrEmail(), is(userName));
        assertThat(userPOJO.getPassword(), is(password));
    }
}
