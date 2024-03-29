package com.example.insta.faker;

import static com.example.insta.faker.PostFaker.createPosts;
import static com.example.insta.faker.UserFaker.createUsers;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import com.example.insta.domain.post.Post;
import com.example.insta.domain.user.User;
import java.util.List;
import java.util.Map;

public class InstaFaker {

  // n FakedUsers -> m FakedPosts
  //    public static Map<User, List<Post>> fakeUsersWithPosts(int numberOfUsers, int numberOfPosts)
  // {
  //
  //        var userPostMap = new HashMap<User, List<Post>>();
  //
  //        List<User> users = UserFaker.createUsers(numberOfPosts);
  //
  //        users.forEach(user -> {
  //
  //            List<Post> posts = PostFaker.createPosts(user.getId(), numberOfPosts);
  //
  //            userPostMap.put(user, posts);
  //        });
  //
  //        return userPostMap;
  //    }

  public static Map<User, List<Post>> fakeUsersWithPosts(int numberOfUsers, int numberOfPosts) {

    // Create n users
    return createUsers(numberOfUsers).stream()
        // Map each user to a list of posts
        .collect(toMap(identity(), user -> createPosts(user.getId(), numberOfPosts)));
  }

  // for testing
  public static void main(String[] args) {

    int numberOfUsers = 3;
    int numberOfPosts = 2;
    Map<User, List<Post>> userListMap = fakeUsersWithPosts(numberOfUsers, numberOfPosts);
    System.out.println(userListMap);
  }
}
