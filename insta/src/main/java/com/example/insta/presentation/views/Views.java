package com.example.insta.presentation.views;

import com.example.insta.domain.media.Media;
import com.example.insta.domain.post.HashTag;
import com.example.insta.domain.user.Profile;
import com.example.insta.domain.user.Role;
import com.example.insta.domain.user.Social;
import java.util.List;
import java.util.Set;

// AutoMappers
// --------------------------------------------------------------------------------------------
// AutoMappers are libraries that automatically map between objects
// e.g. from a domain object to a view object (User -> UserView)
// They are used to reduce the amount of manual mapping code

// Famous AutoMappers are:
// - ModelMapper
// - MapStruct

// Views
// --------------------------------------------------------------------------------------------
// Views are used to represent a domain object for the frontend
// They are used to reduce the amount of data that is sent to the frontend
// They are used to hide sensitive data from the frontend

// There are three types of views:
// - flatted views
//    Views that contain only the fields of the domain object
//     e.g. UserView(email, roles, firstName, lastName)
// - structured views
//     Views that contain other views
//     e.g. UserView(ProfileView, SocialView)
// - hybrid views
//   Mix of Views and Domain Objects
//     e.g. UserView(email, Profile profile)

// Creating views can be done manually or automatically using AutoMappers
// - Manual mapping is done by creating a constructor in the view that takes a domain object
// - Automatic mapping is done by using an AutoMapper library

// Examples of Mapping
// --------------------------------------------------------------------------------------------
// Example of MANUAL mapping:
// public record UserView(String email, List<Role> roles, String firstName, String lastName) {
//     public UserView(User user) {
//         this(user.getEmail(), user.getRoles(),
//             user.getProfile().getFirstName(), user.getProfile().getLastName());
//     }

// Example of AUTO mapping:
// @Mapper
// public interface UserMapper {
//     UserView toUserView(User user);
// }

public abstract class Views {
  // What is a UserView?
  // A UserView is a view that contains the data for a user.
  public record UserView(
      String id, String email, List<Role> roles, Profile profile, Social social) {}

  // What is a PostView?
  // A PostView is a view that contains the data for a post.
  public record PostView(
      String id, String creatorId, String text, Media thumb, Set<HashTag> hashTags, int likes) {}

  // What is a LoginView?
  // A LoginView is a view that contains the data for the user and his associated data for login.
  public record LoginView(UserView user, List<PostView> posts
      // List<MessengerView> messenger,
      ) {}
}
