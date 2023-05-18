package ru.tamara.classifiedsSite;

import org.springframework.mock.web.MockMultipartFile;

public class TestConstants {
    public final static String USERNAME = "user@mail.com";
    public final static String FIRSTNAME = "firstName";
    public final static String LASTNAME ="lastName";
    public final static String PHONE ="+7-111-0000000";
    public final static String PASSWORD = "password";
    public final static String DIF_USERNAME = "differentUser@mail.com";
    public final static String DIF_PASSWORD = "differentPassword";
    public final static String ENCRYPTED_PASSWORD = "encryptedPassword";
    public final static String NEW_FIRST_NAME = "newFirstName";
    public final static String NEW_LAST_NAME ="newLastName";
    public final static String NEW_PHONE ="+7-222-0000000";
    public final static String IMAGE_ID = "ImageId";
    public final static String TEXT = "textComment";
    public final static String AD_DESCRIPTION = "AdDescription";
    public final static String AD_TITLE = "AdTitle";
    public final static String AD_DTO_DESCRIPTION = "AdDtoDescription";
    public final static String AD_DTO_TITLE = "AdDtoTitle";
    public final static int ID = 1;
    public final static int PRICE = 500;
    public final static byte[] BYTES = new byte[] {0, 1, 2};
    public final static byte[] OLD_BYTES = new byte[] {3, 4, 5};
    public final static MockMultipartFile IMAGE = new MockMultipartFile("image", "image.png", "image/png",
            "test image".getBytes());
}
