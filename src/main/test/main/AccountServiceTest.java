package main;

import org.jetbrains.annotations.Nullable;
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

    @Test
    public void testGetRealUser() {
        UserProfile user= new UserProfile("real", "realpass", "realemail");
        accountService.addUser(user);
        final UserProfile trueuser = accountService.getUser(user.getID());
        if (trueuser == null)
            assertTrue(false);
        else
            assertTrue(true);
    }

    @Test
    public void testGetUnrealUser() {
        UserProfile user= new UserProfile("real", "realpass", "realemail1");
        accountService.addUser(user);
        final UserProfile falseuser = accountService.getUser(user.getID()+1);
        if (falseuser == null)
            assertFalse(false);
        else
            assertFalse(true);
    }

    @Test
    public void testGetDeletedUser() {
        UserProfile user= new UserProfile("real", "realpass", "realemail2");
        accountService.addUser(user);
        accountService.deleteUser(user.getID());
        final UserProfile falseuser = accountService.getUser(user.getID());
        if (falseuser == null)
            assertFalse(false);
        else
            assertFalse(true);
    }

    @Test
    public void testGetUserByRealSession() {
        UserProfile currentuser = new UserProfile("test", "testpass", "testemail");
        accountService.logIn("0", currentuser);
        UserProfile user = accountService.getUserBySession("0");
        if (user!=null && user.equals(currentuser))
            assertTrue(true);
        else
            assertTrue(false);
    }

    @Test
    public void testGetUserByUnrealSession() {
        UserProfile currentuser = new UserProfile("test", "testpass", "testemail");
        accountService.addUser(currentuser);
        accountService.logIn("0", currentuser);
        UserProfile user = accountService.getUserBySession("1");
        if (user != null && user.equals(currentuser))
            assertFalse(true);
        else
            assertFalse(false);
    }

    @Test
    public void testGetUserByRealLogin() {
        UserProfile currentuser = new UserProfile("test", "testpass", "testemail");
        accountService.addUser(currentuser);
        UserProfile user = accountService.getUserByLogin("test");
        if (user != null && user.getLogin().equals("test") && user.getEmail().equals("testemail"))
            assertTrue(true);
        else
            assertTrue(false);
    }

    @Test
    public void testGetUserByUnrealLogin() {
        UserProfile currentuser = new UserProfile("test", "testpass", "testemail");
        accountService.addUser(currentuser);
        UserProfile user = accountService.getUserByLogin("test1");
        if (user != null && user.getLogin().equals("test") && user.getEmail().equals("testemail"))
            assertFalse(true);
        else
            assertFalse(false);
    }

    @Test
    public void testDeleteRealUser() {
        UserProfile currentuser = new UserProfile("test", "testpass", "testemail");
        accountService.addUser(currentuser);
        Long id = accountService.getUserByLogin("test").getID();
        if (id != null) {
            accountService.deleteUser(id);
            UserProfile user = accountService.getUser(id);
            if (user == null)
                assertTrue(true);
            else
                assertTrue(false);
        }
    }

    @Test
    public void testUpdateUser() {
        UserProfile user = new UserProfile("test", "testpass", "testemail");
        accountService.addUser(user);
        UserProfile changeduser = new UserProfile("test1", "testpass1", "testemail");
        accountService.updateUser(user,changeduser);
        if (user.getEmail().equals(changeduser.getEmail()) && user.getLogin().equals(changeduser.getLogin() )
                    && user.getPassword().equals(changeduser.getPassword()))
            assertTrue(true);
        else
            assertTrue(false);
    }

    @Test
    public void testIsAuthorized() {
        UserProfile user = new UserProfile("test", "testpass", "testemail");
        accountService.addUser(user);
        accountService.logIn("0", user);
        if (accountService.isAuthorized("0"))
            assertTrue(true);
        else
            assertTrue(false);
    }

    @Test
    public void testIsNotAuthorized() {
        UserProfile user = new UserProfile("test", "testpass", "testemail");
        accountService.addUser(user);
        accountService.logIn("0", user);
        accountService.logOut("0");
        if (accountService.isAuthorized("0"))
            assertFalse(true);
        else
            assertFalse(false);
    }
}
