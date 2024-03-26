package com.example.insta.presentation.views;

import com.example.insta.domain.user.User;
import com.example.insta.presentation.views.Views.UserView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// MapStruct maps the User Domain Model into a User view via the `toUserView`

@Mapper
public interface UserViewMapper {

  UserViewMapper INSTANCE = Mappers.getMapper(UserViewMapper.class);

  UserView toUserView(User user);
}
