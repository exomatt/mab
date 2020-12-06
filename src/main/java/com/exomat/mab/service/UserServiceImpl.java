package com.exomat.mab.service;

import com.exomat.mab.model.Group;
import com.exomat.mab.model.User;
import com.exomat.mab.repository.GroupRepository;
import com.exomat.mab.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GroupService groupService;
    private final HashService hashService;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public UserServiceImpl(UserRepository userRepository, GroupService groupService, HashService hashService) {
        this.userRepository = userRepository;
        this.groupService = groupService;
        this.hashService = hashService;
    }

    @Override
    public User createUser(String username, String password, String name, String email) throws IllegalUsernameException, IncorrectEmailException, NoSuchAlgorithmException {
        if (!validate(email))
            throw new IncorrectEmailException();
        if (userRepository.existsByUsername(username)) {
            throw new IllegalUsernameException("Username is already in use");
        }
        User user = new User(username, hashService.getHash(password), name, email);
        return userRepository.save(user);
    }

    @Override
    public User findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalUsernameException("Cannot found user with that username"));
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalUsernameException("Cannot found user with that id"));

    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) throws IllegalUsernameException {
        User user = findUser(username);
        userRepository.deleteById(user.getId());
    }

    @Override
    public boolean verifyUser(String username, String password) throws NoSuchAlgorithmException {
        return userRepository.existsByUsernameAndPassword(username, hashService.getHash(password));
    }

    @Override
    public User addUserToGroup(String username, String groupName) {
        User user = findUser(username);
        Group group = groupService.findGroup(groupName);
        user.setUserGroup(group);
        return userRepository.save(user);
    }

    @Override
    public User deleteUserFromGroup(String username) {
        User user = findUser(username);
        user.setUserGroup(null);
        return userRepository.save(user);
    }

    @Override
    public List<User> findUsersInGroup(String groupName) {
        return groupService.findGroup(groupName).getUsers();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
