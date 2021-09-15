package com.github.vknukala.digitaldiary.mapper;

import com.github.vknukala.digitaldiary.dto.CreateUserDiaryNotesRequest;
import com.github.vknukala.digitaldiary.dto.CreateUserInfoRequest;
import com.github.vknukala.digitaldiary.dto.CreateUserRequest;
import com.github.vknukala.digitaldiary.dto.UpdateUserPasswordRequest;
import com.github.vknukala.digitaldiary.model.User;
import com.github.vknukala.digitaldiary.model.UserDiaryNote;
import com.github.vknukala.digitaldiary.model.UserInfo;
import com.github.vknukala.digitaldiary.view.UserDiaryNotesView;
import com.github.vknukala.digitaldiary.view.UserInfoView;
import com.github.vknukala.digitaldiary.view.UserView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract User toUser(CreateUserRequest request);

    public abstract UserInfo toUserInfo(CreateUserInfoRequest createUserInfoRequest);

    public abstract UserDiaryNote toUserDiaryNote(CreateUserDiaryNotesRequest createUserDiaryNotesRequest);

    public abstract UserView toUserView(User user);

    public abstract UserInfoView toUserInfoView(UserInfo userInfo);

    public abstract UserDiaryNotesView toUserDiaryNoteView(UserDiaryNote userDiaryNote);

    public abstract List<UserView> toUserViewList(List<User> users);

    public abstract List<UserInfoView> toUserInfoViewList(List<UserInfo> userInfos);

    public abstract List<UserDiaryNotesView> toUserDiaryNotesViewList(List<UserDiaryNote> userDiaryNotes);

    public abstract User updateUser(@MappingTarget User tobeUpdatedUser, UpdateUserPasswordRequest updateUserPasswordRequest);

    public abstract UserInfo updateUserInfo(@MappingTarget UserInfo tobeUpdatedUserInfo, CreateUserInfoRequest createUserInfoRequest);


}
