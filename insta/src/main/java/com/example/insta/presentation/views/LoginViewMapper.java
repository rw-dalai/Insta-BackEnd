package com.example.insta.presentation.views;

import com.example.insta.domain.post.Post;
import com.example.insta.domain.user.User;
import com.example.insta.presentation.views.Views.LoginView;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// We can use multiple mappers in one mapper
@Mapper(uses = {UserViewMapper.class, PostViewMapper.class})
public interface LoginViewMapper {

  LoginViewMapper INSTANCE = Mappers.getMapper(LoginViewMapper.class);

  LoginView toLoginView(User user, List<Post> posts);
}
