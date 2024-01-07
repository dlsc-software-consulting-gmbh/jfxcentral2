package com.dlsc.jfxcentral2.model;

import com.dlsc.jfxcentral.data.model.ModelObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class SaveAndLikeModelTest {

    @Test
    public void constructorTest() {
        SaveAndLikeModel model = new SaveAndLikeModel("title", ModelObject.class, true, LocalDate.now(), true, LocalDate.now());
        assertEquals("title", model.getTitle());
        assertEquals(ModelObject.class, model.getTypeClazz());
        assertTrue(model.isSaved());
        assertNotNull(model.getSavedDate());
        assertTrue(model.isLiked());
        assertNotNull(model.getLikedDate());
    }
    
    @Test
    public void titleTest() {
        SaveAndLikeModel model = new SaveAndLikeModel();
        model.setTitle("new title");
        assertEquals("new title", model.getTitle());
    }

    @Test
    public void typeClazzTest() {
        SaveAndLikeModel model = new SaveAndLikeModel();
        model.setTypeClazz(ModelObject.class);
        assertEquals(ModelObject.class, model.getTypeClazz());
    }

    @Test
    public void savedTest() {
        SaveAndLikeModel model = new SaveAndLikeModel();
        model.setSaved(true);
        assertTrue(model.isSaved());
    }

    @Test
    public void savedDateTest() {
        SaveAndLikeModel model = new SaveAndLikeModel();
        LocalDate date = LocalDate.now();
        model.setSavedDate(date);
        assertEquals(date, model.getSavedDate());
    }

    @Test
    public void likedTest() {
        SaveAndLikeModel model = new SaveAndLikeModel();
        model.setLiked(true);
        assertTrue(model.isLiked());
    }

    @Test
    public void likedDateTest() {
        SaveAndLikeModel model = new SaveAndLikeModel();
        LocalDate date = LocalDate.now();
        model.setLikedDate(date);
        assertEquals(date, model.getLikedDate());
    }

    @Test
    public void toStringTest() {
        SaveAndLikeModel model = new SaveAndLikeModel("title", ModelObject.class, true, LocalDate.now(), true, LocalDate.now());
        assertTrue(model.toString().contains("title='title'"));
    }
}