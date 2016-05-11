package main;

import database.UsersDataSet;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

public class AccountServiceTest {
    private AccountServiceImpl accountService;

    final UsersDataSet testUser = new UsersDataSet("test", "testpass", "testemail");

    @Before
    public void setupAccountService(){
        accountService = new AccountServiceImpl("test_hibetnate.cfg.xml");
    }

    @Test
    public void testAddUser(){
        accountService.addUser(testUser);
        accountService.addUser(testUser);
        Collection <UsersDataSet> allUsers = accountService.getAllUsers();
        if(allUsers != null){
            allUsers.toArray();
        }
        int numberOfAddedUsers = 0;
        if(allUsers != null) {
            for (UsersDataSet user : allUsers) {
                if (user.getEmail().equals(testUser.getEmail()))
                    numberOfAddedUsers++;
            }
        }
        assertTrue(numberOfAddedUsers == 1);
    }

    @Test
    public void testGetRealUser() {
        accountService.addUser(testUser);
        final UsersDataSet trueUser = accountService.getUser(testUser.getID());
        assertNotNull(trueUser);
    }

    @Test
    public void testGetUnrealUser() {
        accountService.addUser(testUser);
        final UsersDataSet falseUser = accountService.getUser(testUser.getID()+1);
        assertNull(falseUser);
    }

    @Test
    public void testGetUserByRealSession() {
        accountService.logIn("0", testUser);
        UsersDataSet user = accountService.getUserBySession("0");
        assertNotNull(user);
        assertEquals(user, testUser);
    }

    @Test
    public void testGetUserByUnrealSession() {
        accountService.addUser(testUser);
        accountService.logIn("0", testUser);
        UsersDataSet user = accountService.getUserBySession("1");
        if (user != null && user.equals(testUser))
            assertFalse(true);
        else
            assertFalse(false);
    }



    @Test
    public void testDeleteRealUser() {
        accountService.addUser(testUser);
        UsersDataSet user = accountService.getUserByEmail("testemail");
        if(user == null)
            fail();
        Long id = user.getID();
        accountService.deleteUser(id);
        UsersDataSet deletedUser = accountService.getUser(id);
        assertNull(deletedUser);
    }

    @Test
    public void testUpdateUser() {
        accountService.addUser(testUser);
        UsersDataSet changeduser = new UsersDataSet("test1", "testpass1", "testemail1");
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
