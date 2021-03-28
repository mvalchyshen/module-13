package module13;


import java.io.IOException;
import java.net.URI;
import java.util.List;

public class HttpDemo {
    private static final String CREATE_USER_URL = "http://pingponggoit.herokuapp.com/createUser";
    private static final String GET_USERBYID_URL = "http://pingponggoit.herokuapp.com/getUserById";
    private static final String GET_USERS_URL = "http://pingponggoit.herokuapp.com/getUsers";
    private static final String REMOVE_USER_URL = "http://pingponggoit.herokuapp.com/removeUser";
    private static final String OVERWRITE_USER_URL = "http://pingponggoit.herokuapp.com/overwrite";

    public static void main(String[] args) throws IOException, InterruptedException {


        User user = getUser();
        //Exercise 1
        User createdUser = HttpUtil.sendPost(URI.create(CREATE_USER_URL), user);
        System.out.println("Task 1 " + createdUser);
        //Exercise 2
        User task2 = HttpUtil.sendGet(URI.create(String.format("%s?id=%d", GET_USERBYID_URL, createdUser.getId())));
        System.out.println("Exercise 2 " + task2);
        //Exercise 3
        User taks3 = getUser();
        User createdUserTask3 = HttpUtil.sendPost(URI.create(CREATE_USER_URL), taks3);
        System.out.println("Created user " + createdUserTask3);
        List<User> users = HttpUtil.sendGetWithListUsers(URI.create(GET_USERS_URL));
        System.out.println("Users Before delete" + users);
        HttpUtil.sendDelete(URI.create(REMOVE_USER_URL), createdUserTask3);
        List<User> usersAfterDelete = HttpUtil.sendGetWithListUsers(URI.create(GET_USERS_URL));
        System.out.println("Users After delete" + usersAfterDelete);
        //Exercise 4
        //1.getallusers 2. change user name 3. getallusers
        User task4 = getUser();
        List<User> exFourUsers = HttpUtil.sendGetWithListUsers(URI.create(GET_USERS_URL));
        System.out.println("List of Users Before changing task4 user \n" + exFourUsers);
        HttpUtil.overWrite(URI.create(String.format("%s?id=%d",OVERWRITE_USER_URL,task2.getId())), task2);
        List<User> overWrittenUsers = HttpUtil.sendGetWithListUsers(URI.create(GET_USERS_URL));
        System.out.println("List of Users After changing task4 user \n" + overWrittenUsers);
        System.out.println("-------------------------");
        System.out.println("Exercise 4 " + task2);

    }


    private static User getUser() {
        User user = new User();
        user.setId(1);
        user.setName("Maksym");
        user.setSurname("Valchyshen");
        user.setSalary(1000);
        user.setGender("male");
        return user;
    }
}
