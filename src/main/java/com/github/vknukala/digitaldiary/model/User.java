package com.github.vknukala.digitaldiary.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;


@Document(collection = "users")
@Data
@JsonNaming(value= PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @Field("user_name")
    @Indexed(unique = true)
    @NotEmpty(message = "User name should not be empty")
    private String username;
    @Field("password")
    @NotEmpty(message = "Last name should not be empty")
    private String password;
    @CreatedDate
    @Field("created_date_time")
    private LocalDateTime createdDateTime;
    @LastModifiedDate
    @Field("updated_date_time")
    private LocalDateTime updatedDateTime;

    private boolean enabled = true;

    public User(){
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
