package ru.tamara.classifiedsSite.service.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tamara.classifiedsSite.dto.CommentDto;
import ru.tamara.classifiedsSite.dto.ResponseWrapperCommentDto;
import ru.tamara.classifiedsSite.dto.Role;
import ru.tamara.classifiedsSite.entity.Ad;
import ru.tamara.classifiedsSite.entity.Comment;
import ru.tamara.classifiedsSite.entity.User;
import ru.tamara.classifiedsSite.exception.UserForbiddenException;
import ru.tamara.classifiedsSite.repository.AdRepository;
import ru.tamara.classifiedsSite.repository.CommentRepository;
import ru.tamara.classifiedsSite.service.UserService;
import ru.tamara.classifiedsSite.service.mapper.CommentMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {
    @Mock
    CommentRepository commentRepository;
    @Mock
    CommentMapper commentMapper;
    @Mock
    AdRepository adRepository;
    @Mock
    UserService userService;

    @InjectMocks
    CommentServiceImpl commentService;

    private final User expectedUser = new User();
    private final Ad expectedAd = new Ad();
    private final Comment expectedComment = new Comment();

    @BeforeEach
    public void setUp() {
        expectedUser.setUsername("user@gmail.com");

        expectedAd.setId(1);

        expectedComment.setId(1);
        expectedComment.setAd(expectedAd);
        expectedComment.setAuthor(expectedUser);
        expectedComment.setCreatedAt(LocalDateTime.now());
        expectedComment.setText("Тестовый комментарий");
    }

    @Test
    public void checkAccessTestWhenAccessExist() {
        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(expectedComment));
        when(userService.findAuthUser()).thenReturn(Optional.of(expectedUser));

        boolean actual = commentService.checkAccess(1);

        assertTrue(actual);
    }

    @Test
    public void checkAccessTestWhenAdmin() {
        User userAdmin = new User();
        userAdmin.setUsername("admin@gmail.com");
        userAdmin.setRole(Role.ADMIN);

        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(expectedComment));
        when(userService.findAuthUser()).thenReturn(Optional.of(userAdmin));

        boolean actual = commentService.checkAccess(1);

        assertTrue(actual);
    }

    @Test
    public void getCommentsDtoTest() {
        CommentDto commentDto = new CommentDto();
        commentDto.setPk(1);
        List<CommentDto> expected = new ArrayList<>();
        expected.add(commentDto);

        when(commentRepository.findAll()).thenReturn(List.of(expectedComment));
        when(commentMapper.mapToCommentDto(any(Comment.class))).thenReturn(commentDto);

        ResponseWrapperCommentDto actual = commentService.getCommentsDto(1);

        Assertions.assertThat(actual.getCount()).isEqualTo(1);
        Assertions.assertThat(actual.getResults()).isEqualTo(expected);
    }

    @Test
    public void createCommentDtoTest() {
        expectedComment.setAd(null);
        expectedComment.setAuthor(null);
        expectedComment.setCreatedAt(null);
        CommentDto commentDto = new CommentDto();
        CommentDto commentDtoResult = new CommentDto();

        when(adRepository.findById(anyInt())).thenReturn(Optional.of(expectedAd));
        when(commentMapper.mapToComment(any(CommentDto.class))).thenReturn(expectedComment);
        when(userService.findAuthUser()).thenReturn(Optional.of(expectedUser));
        when(commentMapper.mapToCommentDto(any(Comment.class))).thenReturn(commentDtoResult);

        commentService.createCommentDto(1,commentDto);

        verify(adRepository, times(1)).findById(1);
        verify(commentMapper, times(1)).mapToComment(commentDto);
        verify(userService, times(1)).findAuthUser();
        verify(commentMapper, times(1)).mapToCommentDto(expectedComment);

        ArgumentCaptor<Comment> argumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository, times(1)).save(argumentCaptor.capture());
        Comment actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getId()).isEqualTo(expectedComment.getId());
        Assertions.assertThat(actual.getAd()).isEqualTo(expectedComment.getAd());
        Assertions.assertThat(actual.getAuthor()).isEqualTo(expectedComment.getAuthor());
        Assertions.assertThat(actual.getCreatedAt()).isEqualTo(expectedComment.getCreatedAt());
    }

    @Test
    public void removeCommentDtoTestWhenAccessExist() {
        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(expectedComment));
        when(userService.findAuthUser()).thenReturn(Optional.of(expectedUser));

        boolean actual = commentService.removeCommentDto(1, 1);

        verify(commentRepository, times(1)).findById(anyInt());
        verify(userService, times(1)).findAuthUser();
        verify(commentRepository, times(1)).deleteById(anyInt());

        assertTrue(actual);
    }

    @Test
    public void removeCommentDtoTestWhenAccessDoesNotExist() {
        User user1 = new User();

        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(expectedComment));
        when(userService.findAuthUser()).thenReturn(Optional.of(user1));

        assertThrows(UserForbiddenException.class,() -> commentService.removeCommentDto(1, 1));

        verify(commentRepository, times(1)).findById(anyInt());
        verify(userService, times(1)).findAuthUser();
    }

    @Test
    public void updateCommentDtoTestWhenAccessExist() {
        expectedComment.setText(null);
        CommentDto commentDto = new CommentDto();
        commentDto.setText("Тестовый комментарий");
        CommentDto commentDtoResult = new CommentDto();

        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(expectedComment));
        when(userService.findAuthUser()).thenReturn(Optional.of(expectedUser));
        when(commentMapper.mapToCommentDto(any(Comment.class))).thenReturn(commentDtoResult);

        commentService.updateCommentDto(1, 1, commentDto);

        verify(commentRepository, times(2)).findById(1);
        verify(userService, times(1)).findAuthUser();
        verify(commentMapper, times(1)).mapToCommentDto(expectedComment);

        ArgumentCaptor<Comment> argumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository, times(1)).save(argumentCaptor.capture());
        Comment actual = argumentCaptor.getValue();

        Assertions.assertThat(actual.getId()).isEqualTo(expectedComment.getId());
        Assertions.assertThat(actual.getText()).isEqualTo(expectedComment.getText());
    }

    @Test
    public void updateCommentDtoTestWhenAccessDoesNotExist() {
        User user1 = new User();
        CommentDto commentDto = new CommentDto();

        when(commentRepository.findById(anyInt())).thenReturn(Optional.of(expectedComment));
        when(userService.findAuthUser()).thenReturn(Optional.of(user1));

        assertThrows(UserForbiddenException.class,() -> commentService.updateCommentDto(1, 1, commentDto));

        verify(commentRepository, times(2)).findById(anyInt());
        verify(userService, times(1)).findAuthUser();
    }
}
