package ru.otus.library.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentTest {

    @Test
    public void testGetStringForShow() {
        Comment comment = new Comment("test content", "test user");
        comment.setId(1L);
        assertEquals(String.format("1. test user commented:%n\ttest content"), comment.getStringForShow());
    }

}
