package com.example.insta.presentation.views;

import com.example.insta.domain.post.Post;
import com.example.insta.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-29T12:17:57+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
public class LoginViewMapperImpl implements LoginViewMapper {

    private final UserViewMapper userViewMapper = UserViewMapper.INSTANCE;
    private final PostViewMapper postViewMapper = PostViewMapper.INSTANCE;

    @Override
    public Views.LoginView toLoginView(User user, List<Post> posts) {
        if ( user == null && posts == null ) {
            return null;
        }

        Views.UserView user1 = null;
        user1 = userViewMapper.toUserView( user );
        List<Views.PostView> posts1 = null;
        posts1 = postListToPostViewList( posts );

        Views.LoginView loginView = new Views.LoginView( user1, posts1 );

        return loginView;
    }

    protected List<Views.PostView> postListToPostViewList(List<Post> list) {
        if ( list == null ) {
            return null;
        }

        List<Views.PostView> list1 = new ArrayList<Views.PostView>( list.size() );
        for ( Post post : list ) {
            list1.add( postViewMapper.toPostView( post ) );
        }

        return list1;
    }
}
