package com.example.insta.presentation.views;

import com.example.insta.domain.user.Profile;
import com.example.insta.domain.user.Role;
import com.example.insta.domain.user.Social;
import com.example.insta.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-29T12:17:57+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class UserViewMapperImpl implements UserViewMapper {

    @Override
    public Views.UserView toUserView(User user) {
        if ( user == null ) {
            return null;
        }

        String id = null;
        String email = null;
        List<Role> roles = null;
        Profile profile = null;
        Social social = null;

        id = user.getId();
        email = user.getEmail();
        List<Role> list = user.getRoles();
        if ( list != null ) {
            roles = new ArrayList<Role>( list );
        }
        profile = user.getProfile();
        social = user.getSocial();

        Views.UserView userView = new Views.UserView( id, email, roles, profile, social );

        return userView;
    }
}
