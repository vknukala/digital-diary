package com.github.vknukala.digitaldiary.controller;

import com.github.vknukala.digitaldiary.model.UserDiaryNote;
import com.github.vknukala.digitaldiary.service.UserDiaryNotesService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserDiaryNotesController {

    private final UserDiaryNotesService userDiaryNotesService;

    @Operation(summary = "Get diary notes for given user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched diary notes for given user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDiaryNote.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "204", description = "No diary notes found for the user",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @GetMapping("/user/{user-name}/diarynotes")
    public ResponseEntity<List<UserDiaryNote>> getUserDiaryNotes(@Parameter(description = "name of user to find diary notes")
                                                                 @PathVariable("user-name") String username){
        try {
            List<UserDiaryNote> userDiaryNotes = new ArrayList<>();
            userDiaryNotesService.getUserDiaryNotesByUsername(username).forEach(userDiaryNotes::add);
            if (userDiaryNotes.isEmpty()) {
                log.info("There are no diary notes for the user {}",username);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(userDiaryNotes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /*@Operation(summary = "Create a user diary notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created a diary note",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDiaryNote.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)})
    @PostMapping("/userdiarynotes")
    public ResponseEntity<UserDiaryNote> createDiaryNotes(@RequestBody UserDiaryNote userDiaryNote){
        String userId = userDiaryNote.getUserId();
        Optional<User> user = userService.findByUserId(userId);

        if (user.isPresent()) {
            userService.createUserDiaryNote(userDiaryNote);
            log.info("Diary notes for user {} created successfully",userId);
            return new ResponseEntity<UserDiaryNote>(userDiaryNote, HttpStatus.CREATED);
        } else {
            throw new UserNotFoundException(userId);
        }
    }*/

}
