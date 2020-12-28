package com.picsart.bookstorelibrary.service.impl;

import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.model.User;
import com.picsart.bookstorelibrary.utils.Md5PasswordConverter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.picsart.bookstorelibrary.validator.UserValidator.*;


public class UserService {

    private static final String USERS_FILE_PATH = "src/com/picsart/bookstorelibrary/resources/user.txt";
    private static UserService userService;
    private FileProcessingService fileService;

    private UserService() {
        this.fileService = FileProcessingService.getInstance();
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public void register(User user) throws IOException, CustomValidationException, NoSuchAlgorithmException {
        validateUser(user);
        user.setPassword(Md5PasswordConverter.md5(user.getPassword()));
        user.setUserId(calculateUserId());
        fileService.exportDataToFile(USERS_FILE_PATH, user.toString());

    }

    private Integer calculateUserId() throws IOException {
        List<User> users = getUsers();
        Integer id = 0;
        if (users.size() > 0) {
            id = users.get(users.size() - 1).getUserId();
        }
        return id + 1;
    }

    public void validateUser(User user) throws CustomValidationException, IOException {
        validateFullName(user.getName(), user.getSurName());
        validateEmail(user.getEmail());
        validateUserName(user.getUserName());
        validateUniqueness(user.getUserName());
        validatePassword(user.getPassword());
    }

    public void validateUniqueness(String userName) throws CustomValidationException, IOException {
        List<String> userNames = getUserNames();
        if (userNames.contains(userName)) {
            throw new CustomValidationException("username already exists");
        }
    }

    public void login(String userName, String password) throws CustomValidationException, IOException, NoSuchAlgorithmException {
        if (isValidString(userName) && isValidString(password)) {
            String passHash = Md5PasswordConverter.md5(password);
            User user = getUserByUserName(userName);
            if (!user.getPassword().equals(passHash)) {
                throw new CustomValidationException("Invalid username or password");
            }
        } else {
            throw new CustomValidationException("Please enter username and password");
        }
    }


    public List<String> getUserNames() throws IOException {
        List<String> users = fileService.readFromFile(USERS_FILE_PATH);
        List<String> userNames = new ArrayList<>();
        for (String user : users) {
            String[] splitter = user.split(",");
            userNames.add(splitter[2]);
        }
        return userNames;
    }

    public List<User> getUsers() throws IOException {
        List<String> usersContent = fileService.readFromFile(USERS_FILE_PATH);
        List<User> users = new ArrayList<>();
        for (String user : usersContent) {
            users.add(userShortLoginInfo(user));
        }
        return users;
    }

    public User getUserByUserName(String userName) throws IOException, CustomValidationException {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        throw new CustomValidationException("User Not Found");
    }

    private User userShortLoginInfo(String userString) {
        User user = new User();
        String[] split = userString.split(",");
        user.setUserId(Integer.parseInt(split[0]));
        user.setUserName(split[2]);
        user.setPassword(split[4]);
        return user;
    }
}
