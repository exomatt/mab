package com.exomat.mab.service;

import com.exomat.mab.model.Group;
import com.exomat.mab.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    class IllegalUsernameException extends RuntimeException {
        public IllegalUsernameException(String message) {
            super(message);
        }
    }

    class IncorrectEmailException extends RuntimeException {
    }

    User createUser(String username, String password, String name, String email) throws IllegalUsernameException, IncorrectEmailException, NoSuchAlgorithmException;

    User findUser(String username);

    User save(User user);

    void deleteUser(String username) throws IllegalUsernameException;

    boolean verifyUser(String username, String password) throws NoSuchAlgorithmException;

    User addUserToGroup(String username, String groupName);

    User deleteUserFromGroup(String username);

    List<User> findUsersInGroup(String groupName);

}
