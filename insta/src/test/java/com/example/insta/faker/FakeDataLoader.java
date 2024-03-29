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
  private final int FAKED_USERS = 3;
  private final int FAKED_POST = 10;

  private final UserRepository userRepository;
  private final PostRepository postRepository;

  public void loadFakeData() {

    Map<User, List<Post>> userPostMap = fakeUsersWithPosts(FAKED_USERS, FAKED_POST);

    // Returns all users from the map
    Set<User> users = userPostMap.keySet();

    // Returns all posts from the map as a collection of lists
    // Collection<List<Post>> posts = userPostMap.values();

    // We have to flatten the list of posts into a single list
    List<Post> flattenedPosts =
        userPostMap.values().stream()
            // For each list of posts, stream the list and collect it into a single list
            .flatMap(posts -> posts.stream())
            .toList();

    userRepository.saveAll(users);
    postRepository.saveAll(flattenedPosts);
  }
}
