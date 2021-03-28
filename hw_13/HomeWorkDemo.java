package hw_13;



import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HomeWorkDemo {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();


    public static String createNewUser(User user) throws IOException, InterruptedException {
        HttpResponse<String> response = CLIENT.send(HttpUtil.createUser(user),HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
    public static String changeUser (User user) throws IOException, InterruptedException {
        user.setName("SIMMAK");
        HttpResponse<String> response = CLIENT.send(HttpUtil.changeUser("1",user), HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
    public static int deleteUser(User user) throws IOException, InterruptedException {
        HttpResponse<String> re = CLIENT.send(HttpUtil.deleteUser("11", user), HttpResponse.BodyHandlers.ofString());
        return re.statusCode();
    }
    public static String getUsers() throws IOException, InterruptedException {
        HttpResponse<String> response = CLIENT.send(HttpUtil.getUsers(), HttpResponse.BodyHandlers.ofString());
        return response.body();

    }
    public static String getUserById(String id) throws IOException, InterruptedException {
        HttpResponse<String> response = CLIENT.send(HttpUtil.getUserById(id), HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
    public static String getUserByUserName(String userName) throws IOException, InterruptedException {
        HttpResponse<String> response = CLIENT.send(HttpUtil.getUserByUserName(userName), HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
    public static void getUserPostsAndComments(User user) throws IOException, InterruptedException {
        HttpResponse<String> response = CLIENT.send(HttpUtil.getUserPosts(user), HttpResponse.BodyHandlers.ofString());
        List<Post> posts = new Gson().fromJson(response.body(), new TypeToken<List<Post>>(){}.getType());
        Post maxPost = Collections.max(posts, Comparator.comparingInt(p -> Integer.parseInt(p.getId())));
        String fileName = "user-" + user.getId() + "-post-" + maxPost.getId() + "-comments.json";

        HttpResponse<Path> comments = CLIENT.send(HttpUtil.getComments(maxPost), HttpResponse.BodyHandlers.ofFile(Paths.get(fileName)));
        System.out.println(comments.body());
    }
    public static List<ToDos> getNotComplitedTodos(User user) throws IOException, InterruptedException {
        HttpResponse<String> response = CLIENT.send(HttpUtil.getUserToDos(user), HttpResponse.BodyHandlers.ofString());
        List<ToDos> toDos = new Gson().fromJson(response.body(), new TypeToken<List<ToDos>>() {
        }.getType());
        return toDos.stream()
                .filter(toDos1 -> !toDos1.isCompleted())
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        User MV = new User("Maksym", "Valchyshen", 1, "Pushkina str.",
                "PROGRAMMER@JAVA.NET","777","Azarn1k");

        try {
            //Ex.1.1
            System.out.println("Create :\n" + createNewUser(MV));
            //Ex 1.2
            System.out.println("Change Name : \n" + changeUser(MV));
            //Ex 1.3
            System.out.println("Delete User StatusCode\n" + deleteUser(MV));
            //Ex 1.4
            System.out.println("All users \n" + getUsers());
            //Ex 1.5
            System.out.println("User at ID: 10 \n" + getUserById("10"));
            //Ex 1.6
            System.out.println("User with USERNAME: Samantha \n" + getUserByUserName("Samantha"));
            //Ex 2
            createNewUser(MV);
            getUserPostsAndComments(MV);
            //Ex 3
            System.out.println(getNotComplitedTodos(MV));


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
