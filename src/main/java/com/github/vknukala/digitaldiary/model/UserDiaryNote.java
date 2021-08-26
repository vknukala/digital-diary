package com.github.vknukala.digitaldiary.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection="userdiaryentry")
@Data
@JsonNaming(value= PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDiaryNote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field("notes")
    private String notes;
    @Field("created_datetime")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy@HH:mm:ss.SSSZ")
    private LocalDateTime createdDateTime = LocalDateTime.now();
    @Field("updated_datetime")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy@HH:mm:ss.SSSZ")
    private LocalDateTime updatedDateTime = LocalDateTime.now();
    @Field("attachments")
    private List<String> attachments;//personal pics if any. Never store the image directly in DB. Save it somewhere and save the  reference name of the file
    @Field("user_id")
    @NotEmpty(message = "User id field should not be empty")
    private String userId;


}

