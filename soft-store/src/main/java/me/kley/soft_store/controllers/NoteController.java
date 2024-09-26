package me.kley.soft_store.controllers;


import me.kley.soft_store.models.Note;
import me.kley.soft_store.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.findAll();
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<String> addNote(@RequestBody Note note) {
        return noteService.addNote(note);
    }
}