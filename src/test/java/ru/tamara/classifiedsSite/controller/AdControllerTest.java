package ru.tamara.classifiedsSite.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import ru.tamara.classifiedsSite.dto.*;
import ru.tamara.classifiedsSite.entity.Ad;
import ru.tamara.classifiedsSite.entity.Comment;
import ru.tamara.classifiedsSite.security.WebSecurityConfig;
import ru.tamara.classifiedsSite.service.AdService;
import ru.tamara.classifiedsSite.service.CommentService;
import ru.tamara.classifiedsSite.service.ImageService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.tamara.classifiedsSite.TestConstants.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {WebSecurityConfig.class})
public class AdControllerTest {

    @InjectMocks
    AdController adController;
    @Mock
    private AdService adService;
    @Mock
    private CommentService commentService;
    @Mock
    private ImageService imageService;
    private final Ad testAd = new Ad();
    private final Comment testCom = new Comment();
    private final CommentDto commentDto = new CommentDto();
    private final CreateAdDto createAdDto = new CreateAdDto();

    @BeforeEach
    public void setUp() {
        testAd.setId(ID);
        testAd.setDescription(AD_DESCRIPTION);
        testAd.setTitle(AD_TITLE);
        testAd.setPrice(PRICE);
        testCom.setId(ID);

        commentDto.setPk(ID);
        commentDto.setText(TEXT);

        createAdDto.setDescription(AD_DTO_DESCRIPTION);
        createAdDto.setTitle(AD_DTO_TITLE);
        createAdDto.setPrice(PRICE);
    }

    @Test
    public void getAllAdsTest() {
        List<AdDto> adDtoList = Collections.singletonList(new AdDto());
        ResponseWrapperAdDto adsDto = new ResponseWrapperAdDto(adDtoList);

        when(adService.getAllAdsDto()).thenReturn(adsDto);

        ResponseEntity<ResponseWrapperAdDto> responseEntity = adController.getAllAds();

        verify(adService).getAllAdsDto();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    public void addAdTest() throws Exception {
        AdDto adDto = new AdDto();
        when(adService.createAds(createAdDto, IMAGE)).thenReturn(adDto);

        ResponseEntity<AdDto> response = adController.addAd(createAdDto, IMAGE);

        verify(adService).createAds(createAdDto, IMAGE);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adDto, response.getBody());
        assertNotNull(adDto);
    }

    @Test
    public void getCommentsTest() {
        List<CommentDto> commentDtoList = Collections.singletonList(new CommentDto());
        ResponseWrapperCommentDto comments = new ResponseWrapperCommentDto(commentDtoList);

        when(commentService.getCommentsDto(testAd.getId())).thenReturn(comments);

        ResponseEntity<ResponseWrapperCommentDto> response = adController.getComments(testAd.getId());

        verify(commentService).getCommentsDto(testAd.getId());

        assertEquals(comments, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void addCommentTest() {
        CommentDto newCommentDto = new CommentDto();

        when(commentService.createCommentDto(testAd.getId(), commentDto)).thenReturn(newCommentDto);

        ResponseEntity<CommentDto> response = adController.addComment(testAd.getId(), commentDto);

        verify(commentService).createCommentDto(testAd.getId(), commentDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newCommentDto, response.getBody());
        assertNotNull(newCommentDto);
    }

    @Test
    public void getFullAdTest() {
        FullAdDto fullAdDto = new FullAdDto();

        when(adService.getFullAdDto(testAd.getId())).thenReturn(fullAdDto);

        ResponseEntity<FullAdDto> response = adController.getAds(testAd.getId());

        verify(adService).getFullAdDto(testAd.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fullAdDto, response.getBody());
    }

    @Test
    public void removeAdTest() {
        when(adService.removeAdDto(testAd.getId())).thenReturn(true);

        ResponseEntity<?> response = adController.removeAd(testAd.getId());

        verify(adService).removeAdDto(testAd.getId());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void updateAdTest() {
        AdDto adDto = new AdDto();
        adDto.setTitle(AD_DTO_TITLE);
        adDto.setPrice(PRICE);

        when(adService.updateAdDto(testAd.getId(), createAdDto)).thenReturn(adDto);

        ResponseEntity<AdDto> response = adController.updateAds(testAd.getId(), createAdDto);

        verify(adService).updateAdDto(testAd.getId(), createAdDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(createAdDto.getTitle(), Objects.requireNonNull(response.getBody()).getTitle());
        assertEquals(createAdDto.getPrice(), response.getBody().getPrice());
    }

    @Test
    public void deleteCommentWithOkTest() {
        when(commentService.removeCommentDto(testAd.getId(), testCom.getId())).thenReturn(true);

        ResponseEntity<Void> response = adController.deleteComment(testAd.getId(), testCom.getId());

        verify(commentService).removeCommentDto(testAd.getId(), testCom.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void deleteCommentWithNotFoundTest() {
        when(commentService.removeCommentDto(testAd.getId(), testCom.getId())).thenReturn(false);

        ResponseEntity<Void> response = adController.deleteComment(testAd.getId(), testCom.getId());

        verify(commentService).removeCommentDto(testAd.getId(), testCom.getId());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateCommentTest() {
        CommentDto newCommentDto = new CommentDto();
        newCommentDto.setPk(ID);
        newCommentDto.setText(TEXT);

        when(commentService.updateCommentDto(testAd.getId(), testCom.getId(), commentDto)).thenReturn(newCommentDto);

        ResponseEntity<CommentDto> response = adController.updateComment(testAd.getId(), testCom.getId(), commentDto);

        verify(commentService).updateCommentDto(testAd.getId(), testCom.getId(), commentDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newCommentDto, response.getBody());
        assertEquals(commentDto.getPk(), Objects.requireNonNull(response.getBody()).getPk());
        assertEquals(commentDto.getText(), response.getBody().getText());
        assertEquals(commentDto.getAuthor(), response.getBody().getAuthor());
    }

    @Test
    public void getAdsMeTest() {
        List<AdDto> adDtoList = Collections.singletonList(new AdDto());
        ResponseWrapperAdDto adsDto = new ResponseWrapperAdDto(adDtoList);

        when(adService.getAllUserAdsDto()).thenReturn(adsDto);

        ResponseEntity<ResponseWrapperAdDto> response = adController.getAdsMe();

        verify(adService).getAllUserAdsDto();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adsDto, response.getBody());
    }

    @Test
    public void updateImageTest() throws IOException {
        doNothing().when(adService).updateImageAdDto(testAd.getId(), IMAGE);
        ResponseEntity<byte[]> response = adController.updateImage(testAd.getId(), IMAGE);

        verify(adService).updateImageAdDto(testAd.getId(), IMAGE);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getImageTest() {
        when(imageService.getImageById(IMAGE_ID)).thenReturn(BYTES);

        ResponseEntity<byte[]> response = adController.getImage(IMAGE_ID);

        verify(imageService).getImageById(IMAGE_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertArrayEquals(BYTES, response.getBody());
    }
}
