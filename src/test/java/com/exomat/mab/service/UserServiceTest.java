package com.exomat.mab.service;

import com.exomat.mab.model.Group;
import com.exomat.mab.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @Test
    void testNewUserSave() throws NoSuchAlgorithmException {
        User user = userService.createUser("test", "testowy", "mateusz", "exomat@wp.pl");
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(user.getId(), userService.findUser(user.getUsername()).getId());
    }

    @Test
    void testUsernameAlreadyInUse() throws NoSuchAlgorithmException {
        User user = userService.createUser("admin", "admin", "mateusz", "exomat@wp.pl");
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(user.getId(), userService.findUser(user.getUsername()).getId());
        Assertions.assertThrows(UserService.IllegalUsernameException.class, () -> {
            userService.createUser("admin", "testowy", "mateusz", "exomat@wp.pl");
        });
    }

    @Test
    void testEmailNotValid() throws NoSuchAlgorithmException {
        Assertions.assertThrows(UserService.IncorrectEmailException.class, () -> {
            userService.createUser("admin", "testowy", "mateusz", "exomatwp.pl");
        });
    }

    @Test
    void testFindUser() throws NoSuchAlgorithmException {
        String username = "testowy";
        User user = userService.createUser(username, "testowy", "mateusz", "exomat@wp.pl");
        Assertions.assertNotNull(userService.findUser(username));
        Assertions.assertEquals(user.getId(), userService.findUser(username).getId());
    }

    @Test
    void testFindUserError() {
        String username = "testowyjakis";
        Assertions.assertThrows(UserService.IllegalUsernameException.class, () -> {
            userService.findUser(username);
        });
    }

    @Test
    void testDeleteUser() throws NoSuchAlgorithmException {
        String username = "dousuniecia";
        User user = userService.createUser(username, "testowy", "mateusz", "exomat@wp.pl");
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(user.getId(), userService.findUser(user.getUsername()).getId());
        userService.deleteUser(username);
        Assertions.assertThrows(UserService.IllegalUsernameException.class, () -> {
            userService.findUser(username);
        });
    }

    @Test
    void testDeleteUserError() {
        String username = "testowyjakis";
        Assertions.assertThrows(UserService.IllegalUsernameException.class, () -> {
            userService.deleteUser(username);
        });
    }

    @Test
    void testVerifyUser() throws NoSuchAlgorithmException {
        String username = "verify";
        String password = "testowy";
        User user = userService.createUser(username, password, "mateusz", "exomat@wp.pl");
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());
        Assertions.assertEquals(user.getId(), userService.findUser(user.getUsername()).getId());
        Assertions.assertTrue(userService.verifyUser(username, password));
    }

    @Test
    void testVerifyUserErro() throws NoSuchAlgorithmException {
        String username = "something";
        String password = "something";
        Assertions.assertFalse(userService.verifyUser(username, password));
    }

    @Test
    void addUserToGroup() throws NoSuchAlgorithmException {
        User user = userService.createUser("admin12", "testowy", "mateusz", "exomat@wp.pl");
        Group group = groupService.save(new Group("testowa"));
        Assertions.assertNotNull(user);
        user.setUserGroup(group);
        User savedUser = userService.save(user);
        Assertions.assertEquals(group.getId(), savedUser.getUserGroup().getId());
    }

    @Test
    void addUserToGroupWithMethod() throws NoSuchAlgorithmException {
        String username = "piotrek";
        String groupName = "testowa";
        User user = userService.createUser(username, "testowy", "mateusz", "exomat@wp.pl");
        Group group = groupService.save(new Group(groupName));
        Assertions.assertNotNull(user);
        userService.addUserToGroup(username, groupName);
        User userAfterAssign = userService.findUser(username);
        Assertions.assertEquals(group.getId(), userAfterAssign.getUserGroup().getId());
    }

    @Test
    void delUserFromGroupWithMethod() throws NoSuchAlgorithmException {
        String username = "michal";
        String groupName = "testowa1";
        User user = userService.createUser(username, "testowy", "mateusz", "exomat@wp.pl");
        Group group = groupService.save(new Group(groupName));
        Assertions.assertNotNull(user);
        userService.addUserToGroup(username, groupName);
        User userAfterAssign = userService.findUser(username);
        Assertions.assertEquals(group.getId(), userAfterAssign.getUserGroup().getId());
        userService.deleteUserFromGroup(username);
        userAfterAssign = userService.findUser(username);
        Assertions.assertNull(userAfterAssign.getUserGroup());
        List<User> usersInGroup = userService.findUsersInGroup(groupName);
        Assertions.assertEquals(0, usersInGroup.size());
    }

    @Test
    void findUsersInGroup() throws NoSuchAlgorithmException {
        String groupName = "testowa2";
        User user = userService.createUser("admintest1", "testowy", "mateusz", "exomat@wp.pl");
        User user2 = userService.createUser("admintest2", "testowy", "mateusz", "exomat@wp.pl");
        User user3 = userService.createUser("admintest3", "testowy", "mateusz", "exomat@wp.pl");
        Group group = groupService.save(new Group(groupName));
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user2);
        Assertions.assertNotNull(user3);
        userService.addUserToGroup(user.getUsername(), groupName);
        userService.addUserToGroup(user2.getUsername(), groupName);
        userService.addUserToGroup(user3.getUsername(), groupName);
        List<User> usersInGroup = userService.findUsersInGroup(groupName);
        Assertions.assertEquals(3, usersInGroup.size());
    }

    @Test
    void useWrongGroupName(){
        String groupName = "testowajakas";
        Assertions.assertThrows(GroupService.IllegalGroupNameException.class, () -> {
            groupService.findGroup(groupName);
        });
    }

    @Test
    void useWrongGroupNameInSearch(){
        String groupName = "testowajakas";
        Assertions.assertThrows(GroupService.IllegalGroupNameException.class, () -> {
            userService.findUsersInGroup(groupName);
        });
    }

    @Test
    void useWrongUserNameInAdd(){
        String userName = "testowajakasnazwa";
        Assertions.assertThrows(UserService.IllegalUsernameException.class, () -> {
            userService.addUserToGroup(userName, userName);
        });
    }

    @Test
    void useWrongGroupNameInAdd() throws NoSuchAlgorithmException {
        String userName = "andrzej";
        User user = userService.createUser("andrzej", "testowy", "mateusz", "exomat@wp.pl");
        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getId(), userService.findUser(user.getUsername()).getId());
        String groupName = "testowajakas";
        Assertions.assertThrows(GroupService.IllegalGroupNameException.class, () -> {
            userService.addUserToGroup(userName, groupName);
        });
    }

    @Test
    void useWrongUserNameInDel(){
        String userName = "testowajakasnazwa";
        Assertions.assertThrows(UserService.IllegalUsernameException.class, () -> {
            userService.deleteUser(userName);
        });
    }
}
