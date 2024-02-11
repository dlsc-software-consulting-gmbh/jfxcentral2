package com.dlsc.jfxcentral2.model;

import org.junit.jupiter.api.Test;
import java.time.ZonedDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {

    @Test
    public void testId() {
        Comment comment = new Comment();
        String idValue = "testId";
        comment.setId(idValue);
        assertEquals(idValue, comment.getId());
    }

    @Test
    public void testContent() {
        Comment comment = new Comment();
        String contentValue = "Hello, World!";
        comment.setContent(contentValue);
        assertEquals(contentValue, comment.getContent());
    }   

    @Test
    public void testUser() {
        Comment comment = new Comment();
        User userValue = new User("id", "name", null, Collections.emptyList());
        comment.setUser(userValue);
        assertEquals(userValue, comment.getUser());
    }

    @Test
    public void testDate() {
        Comment comment = new Comment();
        ZonedDateTime dateValue = ZonedDateTime.now();
        comment.setDate(dateValue);
        assertEquals(dateValue, comment.getDate());
    }

    @Test
    public void testLikes() {
        Comment comment = new Comment();
        int likesValue = 5;
        comment.setLikes(likesValue);
        assertEquals(likesValue, comment.getLikes());
    }

    @Test
    public void testDeleted() {
        Comment comment = new Comment();
        comment.setDeleted(true);
        assertTrue(comment.isDeleted());
        comment.setDeleted(false);
        assertFalse(comment.isDeleted());
    }

    @Test
    public void testLiked() {
        Comment comment = new Comment();
        comment.setLiked(true);
        assertTrue(comment.isLiked());
        comment.setLiked(false);
        assertFalse(comment.isLiked());
    }
}