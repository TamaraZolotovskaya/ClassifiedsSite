package ru.tamara.classifiedsSite.dto;

import lombok.Data;

@Data
public class CommentDto {
    //id комментария
    private int pk;
    //id автора комментария
    private int author;
    //ссылка на аватар автора комментария
    private String authorImage;
    //имя создателя комментария
    private String authorFirstName;
    //дата и время создания комментария в миллисекундах с 00:00:00 01.01.1970
    private long createdAt;
    //текст комментария
    private String text;
}
