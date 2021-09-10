package com.github.vknukala.digitaldiary.controller;

import com.github.vknukala.digitaldiary.dto.CreateUserDiaryNotesRequest;
import com.github.vknukala.digitaldiary.mapper.UserMapper;
import com.github.vknukala.digitaldiary.model.User;
import com.github.vknukala.digitaldiary.model.UserDiaryNote;
import com.github.vknukala.digitaldiary.service.UserDiaryNotesService;
import com.github.vknukala.digitaldiary.service.UserService;
import com.github.vknukala.digitaldiary.view.UserDiaryNotesView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserDiaryNotesController {

    private final UserDiaryNotesService userDiaryNotesService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get diary notes for given user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched diary notes for given user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDiaryNotesView.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "204", description = "No diary notes found for the user",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping("/user/{user-name}/diarynotes")
    public ResponseEntity<List<UserDiaryNotesView>> getUserDiaryNotes(@Parameter(description = "name of user to find diary notes")
                                                                 @PathVariable("user-name") String username){
        try {
            List<UserDiaryNote> userDiaryNotes = new ArrayList<>();
            List<UserDiaryNotesView> userDiaryNotesViews = new ArrayList<>();
            userDiaryNotesService.getUserDiaryNotesByUsername(username).forEach(userDiaryNotes::add);
            if (userDiaryNotes.isEmpty()) {
                log.info("There are no diary notes for the user {}",username);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            userDiaryNotesViews = userMapper.toUserDiaryNotesViewList(userDiaryNotes);
            return new ResponseEntity<>(userDiaryNotesViews, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Create a user diary notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a diary note",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDiaryNotesView.class)) }),
            @ApiResponse(responseCode = "401", description = "The user is unauthorized",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @PostMapping("/user/{user-name}/diarynotes")
    public ResponseEntity<UserDiaryNotesView> createDiaryNotes(@Parameter(description = "username to create a diary notes")
                                                          @PathVariable("user-name") String username,
                                                               @RequestBody CreateUserDiaryNotesRequest createUserDiaryNotesRequest){
        try{
            User user = userService.loadUserByUsername(username);
            UserDiaryNote userDiaryNote = userMapper.toUserDiaryNote(createUserDiaryNotesRequest);
            userDiaryNote.setUsername(user.getUsername());
            userDiaryNotesService.createUserDiaryNote(userDiaryNote);
            log.info("Diary notes for user {} created successfully",username);
            return new ResponseEntity<>(userMapper.toUserDiaryNoteView(userDiaryNote), HttpStatus.CREATED);
        } catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
