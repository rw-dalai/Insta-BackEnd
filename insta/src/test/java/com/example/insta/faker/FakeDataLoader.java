package com.example.insta.faker;

import static com.example.insta.faker.InstaFaker.fakeUsersWithPosts;

import com.example.insta.domain.post.Post;
import com.example.insta.domain.user.User;
import com.example.insta.persistence.PostRepository;
import com.example.insta.persistence.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FakeDataLoader {
  private final UserRepository userRepository;
  private final PostRepository postRepository;

  public void loadFakeData(int numberOfUsers, int numberOfPosts) {

    Map<User, List<Post>> userPostMap = fakeUsersWithPosts(numberOfUsers, numberOfUsers);

    // Returns all users from the map
    Set<User> allUsers = userPostMap.keySet();

    // Returns all posts from the map as a collection of lists
    // Collection<List<Post>> posts = userPostMap.values();

    // We have to flatten the list of posts into a single list
    List<Post> allPosts = userPostMap.values().stream().flatMap(posts -> posts.stream()).toList();

    System.out.println("Users: " + allUsers);
    System.out.println("Posts: " + allPosts);

    userRepository.saveAll(allUsers);
    postRepository.saveAll(allPosts);
  }
}
