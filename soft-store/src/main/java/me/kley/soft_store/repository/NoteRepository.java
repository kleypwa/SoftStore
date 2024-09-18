package me.kley.soft_store.repository;

import me.kley.soft_store.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findByAuthor(Long author);
}
