package me.kley.soft_store.controllers;


import me.kley.soft_store.models.Note;
import me.kley.soft_store.service.AppUserService;
import me.kley.soft_store.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    private final NoteService noteService;

    @Autowired
    private final AppUserService appUserService;

    public NoteController(NoteService noteService, AppUserService appUserService) {
        this.noteService = noteService;
        this.appUserService = appUserService;
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.findAll();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<String> addNote(@RequestBody Note note) {
        note.setAuthor(appUserService.getCurrentAuthenticatedUser().getId());
        Note isSaved = noteService.saveNote(note);

        if (isSaved != null) {
            return ResponseEntity.ok("Note successfully added.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add note.");
        }
    }
}