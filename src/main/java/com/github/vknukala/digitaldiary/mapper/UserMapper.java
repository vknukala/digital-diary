package com.github.vknukala.digitaldiary.mapper;

import com.github.vknukala.digitaldiary.dto.CreateUserInfoRequest;
import com.github.vknukala.digitaldiary.dto.CreateUserRequest;
import com.github.vknukala.digitaldiary.model.User;
import com.github.vknukala.digitaldiary.model.UserInfo;
import com.github.vknukala.digitaldiary.view.UserInfoView;
import com.github.vknukala.digitaldiary.view.UserView;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract User toUser(CreateUserRequest request);

    public abstract UserView toUserView(User user);

    public abstract UserInfoView toUserInfoView(UserInfo userInfo);

    public abstract List<UserView> toUserViewList(List<User> users);

    public abstract List<UserInfoView> toUserInfoViewList(List<UserInfo> userInfos);

    public abstract UserInfo toUserInfo(CreateUserInfoRequest createUserInfoRequest);
}
