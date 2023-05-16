package ru.tamara.classifiedsSite.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.multipart.MultipartFile;
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
        testAd.setId(1);
        testAd.setDescription("test description");
        testAd.setTitle("test title");
        testAd.setPrice(50);

        testCom.setId(2);

        commentDto.setPk(1);
        commentDto.setText("test text");

        createAdDto.setDescription("New Description");
        createAdDto.setTitle("New Title");
        createAdDto.setPrice(500);
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
        MultipartFile image = new MockMultipartFile("test.jpg", "test.jpg",
                "image/jpeg", "test image".getBytes());

        when(adService.createAds(createAdDto, image)).thenReturn(adDto);

        ResponseEntity<AdDto> response = adController.addAd(createAdDto, image);

        verify(adService).createAds(createAdDto, image);

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
        adDto.setTitle("New Title");
        adDto.setPrice(500);

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
        newCommentDto.setPk(1);
        newCommentDto.setText("test text");

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
        MultipartFile image = new MockMultipartFile("image.jpg", new byte[]{1, 2, 3});

        doNothing().when(adService).updateImageAdDto(testAd.getId(), image);

        ResponseEntity<byte[]> response = adController.updateImage(testAd.getId(), image);

        verify(adService).updateImageAdDto(testAd.getId(), image);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getImageTest() {
        String id = "1";
        byte[] mockImage = {1, 2, 3};

        when(imageService.getImageById(id)).thenReturn(mockImage);

        ResponseEntity<byte[]> response = adController.getImage(id);

        verify(imageService).getImageById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertArrayEquals(mockImage, response.getBody());
    }
}
