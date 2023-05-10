package ru.tamara.classifiedsSite.service.mapper.impl;

import org.springframework.stereotype.Component;
import ru.tamara.classifiedsSite.dto.CommentDto;
import ru.tamara.classifiedsSite.entity.Comment;
import ru.tamara.classifiedsSite.service.mapper.CommentMapper;

import java.time.ZoneId;
import java.util.Optional;

@Component
public class CommentMapperImpl implements CommentMapper {
    public CommentDto mapToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setPk(comment.getId());
        commentDto.setAuthor(comment.getAuthor().getId());
        Optional.ofNullable(comment.getAuthor().getImage()).ifPresent(image -> commentDto.setAuthorImage(
                "/users/" + comment.getAuthor().getImage().getId() + "/image"));
        commentDto.setAuthorFirstName(comment.getAuthor().getFirstName());
        commentDto.setCreatedAt(comment.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        commentDto.setText(comment.getText());
        return commentDto;
    }

    public Comment mapToComment(CommentDto commentDto) {
        Comment mappedComment = new Comment();
        mappedComment.setId(commentDto.getPk());
//        mappedComment.getAuthor().setId(commentDto.getAuthor());
//        mappedComment.getAuthor().getImage().setImagePath(commentDto.getAuthorImage());
//        mappedComment.getAuthor().setFirstName(commentDto.getAuthorFirstName());
//        mappedComment.setCreatedAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(commentDto.getCreatedAt()), ZoneId.systemDefault()));
        mappedComment.setText(commentDto.getText());
        return mappedComment;
    }
}
