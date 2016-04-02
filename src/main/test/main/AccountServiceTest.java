package main;

import org.junit.Before;
import org.junit.Test;
import rest.UserProfile;

import java.util.Collection;

import static org.junit.Assert.*;

public class AccountServiceTest {
    private AccountServiceImpl accountService;

    final UserProfile testUser = new UserProfile("test", "testpass", "testemail");

    @Before
    public void setupAccountService(){
        accountService = new AccountServiceImpl();
    }

    @Test

    public void testAddUser(){
        accountService.addUser(testUser);
        accountService.addUser(testUser);
        Collection <UserProfile> allUsers = accountService.getAllUsers();
        allUsers.toArray();
        int numberOfAddedUsers = 0;
        for(UserProfile user : allUsers){
            if(user.getEmail().equals(testUser.getEmail()))
                numberOfAddedUsers++;
        }
        assertTrue(numberOfAddedUsers == 1);
    }

    @Test
    public void testGetRealUser() {
        accountService.addUser(testUser);
        final UserProfile trueUser = accountService.getUser(testUser.getID());
        assertNotNull(trueUser);
    }

    @Test
    public void testGetUnrealUser() {
        accountService.addUser(testUser);
        final UserProfile falseUser = accountService.getUser(testUser.getID()+1);
        assertNull(falseUser);
    }

    @Test
    public void testGetDeletedUser() {
        accountService.addUser(testUser);
        accountService.deleteUser(testUser.getID());
        final UserProfile falseUser = accountService.getUser(testUser.getID());
        assertNull(falseUser);
    }

    @Test
    public void testGetUserByRealSession() {
        accountService.logIn("0", testUser);
        UserProfile user = accountService.getUserBySession("0");
        assertNotNull(user);
        assertEquals(user, testUser);
    }

    @Test
    public void testGetUserByUnrealSession() {
        accountService.addUser(testUser);
        accountService.logIn("0", testUser);
        UserProfile user = accountService.getUserBySession("1");
        if (user != null && user.equals(testUser))
            assertFalse(true);
        else
            assertFalse(false);
    }

    @Test
    public void testGetUserByRealLogin() {
        accountService.addUser(testUser);
        UserProfile user = accountService.getUserByLogin("test");
        assertNotNull(user);
        assertEquals(user, testUser);
    }

    @Test
    public void testGetUserByUnrealLogin() {
        accountService.addUser(testUser);
        UserProfile user = accountService.getUserByLogin("1");
        if (user != null && user.equals(testUser))
            assertFalse(true);
        else
            assertFalse(false);
    }

    @SuppressWarnings("all")
    @Test
    public void testDeleteRealUser() {
        accountService.addUser(testUser);
        Long id = accountService.getUserByLogin("test").getID();
        accountService.deleteUser(id);
        UserProfile user = accountService.getUser(id);
        assertNull(user);
    }

    @Test
    public void testUpdateUser() {
        accountService.addUser(testUser);
        UserProfile changeduser = new UserProfile("test1", "testpass1", "testemail1");
        accountService.updateUser(testUser,changeduser);
        assertEquals(testUser.getLogin() + testUser.getEmail() + testUser.getPassword(),changeduser.getLogin() + changeduser.getEmail() + changeduser.getPassword());
    }

    @Test
    public void testIsAuthorized() {
        accountService.addUser(testUser);
        accountService.logIn("0", testUser);
        assertTrue(accountService.isAuthorized("0"));
    }

    @Test
    public void testIsNotAuthorized() {
        accountService.addUser(testUser);
        accountService.logIn("0", testUser);
        accountService.logOut("0");
        assertFalse(accountService.isAuthorized("0"));
    }
}
