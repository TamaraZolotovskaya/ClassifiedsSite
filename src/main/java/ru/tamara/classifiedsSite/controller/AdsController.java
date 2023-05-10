package ru.tamara.classifiedsSite.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tamara.classifiedsSite.dto.*;
import ru.tamara.classifiedsSite.service.AdService;
import ru.tamara.classifiedsSite.service.CommentService;
import ru.tamara.classifiedsSite.service.ImageService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdsController {

    private final AdService adService;
    private final CommentService commentService;
    private final ImageService imageService;

    @Operation(
            summary = "Получить все объявления",
            tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAdsDto.class))
                    })
            })
    @GetMapping()
    public ResponseEntity<ResponseWrapperAdsDto> getAllAds() {
        return ResponseEntity.ok(adService.getAllAdsDto());
    }

    @Operation(
            summary = "Добавить объявление",
            tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content())
            })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdsDto> addAd(@RequestPart CreateAdsDto properties, @RequestPart MultipartFile image) {
        return ResponseEntity.ok(adService.createAds(properties, image));
    }

    @Operation(
            summary = "Получить комментарии объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperCommentDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content())
            },
            tags = "Комментарии"
    )
    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseWrapperCommentDto> getComments(
            @Parameter(description = "id объявления", required = true, in = ParameterIn.PATH,
                    schema = @Schema(type = "integer", format = "int32"))
            @PathVariable Integer id) {
        ResponseWrapperCommentDto comments = commentService.getCommentsDto(id);
        return ResponseEntity.ok().body(comments);
    }

    @Operation(
            summary = "Добавить комментарий к объявлению",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content())
            },
            tags = "Комментарии"
    )
    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDto> addComment(
            @Parameter(description = "id объявления", required = true, in = ParameterIn.PATH,
                    schema = @Schema(type = "integer", format = "int32"))
            @PathVariable Integer id,
            @RequestBody CommentDto commentDto) {
        CommentDto newCommentDto = commentService.createCommentDto(id, commentDto);
        return ResponseEntity.ok().body(newCommentDto);
    }

    @Operation(
            summary = "Получить информацию об объявлении",
            tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FullAdsDto.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content())
            })
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getAds(
            @Parameter(description = "id объявления", required = true, in = ParameterIn.PATH,
                    schema = @Schema(type = "integer", format = "int32"))
            @PathVariable Integer id) {
        return ResponseEntity.ok(adService.getFullAdDto(id));
    }

    @Operation(
            summary = "Удалить объявление",
            tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content", content = @Content()),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content())
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(
            @Parameter(description = "id объявления", required = true, in = ParameterIn.PATH,
                    schema = @Schema(type = "integer", format = "int32"))
            @PathVariable Integer id) {
        adService.removeAdDto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Обновить информацию об объявлении",
            tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = AdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content()),
            })
    @PatchMapping("/{id}")
    public ResponseEntity<AdsDto> updateAds(
            @Parameter(description = "id объявления", required = true, in = ParameterIn.PATH,
                    schema = @Schema(type = "integer", format = "int32"))
            @PathVariable Integer id,
            @Parameter(required = true)
            @RequestBody CreateAdsDto createAdsDto) {
        return ResponseEntity.ok(adService.updateAdDto(id, createAdsDto));
    }

    @Operation(
            summary = "Удалить комментарий",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            },
            tags = "Комментарии"
    )
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "id объявления", required = true, in = ParameterIn.PATH,
                    schema = @Schema(type = "integer", format = "int32"))
            @PathVariable Integer adId,
            @Parameter(description = "id комментария", required = true, in = ParameterIn.PATH,
                    schema = @Schema(type = "integer", format = "int32"))
            @PathVariable Integer commentId) {
        if (commentService.removeCommentDto(adId, commentId)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Operation(
            summary = "Обновить комментарий",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = CommentDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content())
            },
            tags = "Комментарии"
    )
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @Parameter(description = "id объявления", required = true, in = ParameterIn.PATH,
                    schema = @Schema(type = "integer", format = "int32"))
            @PathVariable Integer adId,
            @Parameter(description = "id комментария", required = true, in = ParameterIn.PATH,
                    schema = @Schema(type = "integer", format = "int32"))
            @PathVariable Integer commentId,
            @RequestBody CommentDto commentDto) {
        CommentDto newCommentDto = commentService.updateCommentDto(adId, commentId, commentDto);
        return ResponseEntity.ok().body(newCommentDto);
    }

    @Operation(
            summary = "Получить объявления авторизованного пользователя",
            tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperAdsDto.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content()),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Not Found", content = @Content())
            })
    @GetMapping(value = "/me")
    public ResponseEntity<ResponseWrapperAdsDto> getAdsMe() {
        return ResponseEntity.ok(adService.getAllUserAdsDto());
    }

    @Operation(
            summary = "Обновить картинку объявления",
            tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK", content = {
                            @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                    array = @ArraySchema(schema = @Schema(type = "string", format = "byte")))
                    }),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content())
            })
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage (
            @Parameter(description = "id объявления", required = true, in = ParameterIn.PATH, schema = @Schema(type = "integer", format = "int32"))
            @PathVariable Integer id,
            @Parameter(schema = @Schema(type = "string", format = "binary"))
            @RequestPart MultipartFile image) {
        adService.updateImageAdDto(id, image);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить картинку объявления",
            tags = "Объявления",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not found", content = @Content())
            })
    @GetMapping(value = "/{id}/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        return ResponseEntity.ok(imageService.getImagePathById(id));
    }
}
