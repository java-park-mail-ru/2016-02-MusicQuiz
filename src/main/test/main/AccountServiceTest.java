package main;

import org.junit.Before;
import org.junit.Test;
import rest.UserProfile;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountServiceTest {
    private AccountServiceImpl accountService;

    @Before
    public void setupAccountService(){
        accountService = new AccountServiceImpl();
    }

    @Test
    public void testAddUser() throws Exception {
        final boolean result = accountService.addUser(new UserProfile("test", "testpass", "testemail"));
        assertTrue(result);
    }

    @Test
    public void testAddSameUserFail(){
        accountService.addUser(new UserProfile("test", "testpass", "testemail"));
        final boolean result = accountService.addUser(new UserProfile("test", "testpass", "testemail"));
        assertFalse(result);
    }


}
