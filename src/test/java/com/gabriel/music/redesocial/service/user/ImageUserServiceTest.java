package com.gabriel.music.redesocial.service.user;

import com.gabriel.music.redesocial.domain.user.ImageUser;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.user.ImageUserRepository;
import com.gabriel.music.redesocial.service.Exceptions.ErrorDeleteFileException;
import com.gabriel.music.redesocial.service.Exceptions.FileNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ImageUserServiceTest {

    public static final String IMAGE_REFERENCE = "image.png";
    @InjectMocks
    private ImageUserService imageUserService;

    @Mock
    private ImageUserRepository imageUserRepository;

    private ImageUser imageUser;
    private User user;

    public static final String NAME = "gabriel";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startImageUsers();
    }

    @Test
    void mustReturnAListOfImageUser_whenToCallFindAll() {
        when(imageUserRepository.findAll()).thenReturn(List.of(imageUser));
        List<ImageUser> response = imageUserService.findAll();
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(ImageUser.class, response.get(0).getClass());
    }

    @Test
    void mustDeleteImageInDatabase_whenToCallDeleteImageReferenceFromDatabase() throws FileNotFoundException {
        when(imageUserRepository.findByImageReference(any())).thenReturn(Optional.of(imageUser));
        imageUserService.deleteImageReferenceFromDatabase(IMAGE_REFERENCE);
        verify(imageUserRepository).deleteById(imageUser.getId());
    }

//    @Test
//    void mustDeleteCompletelyImageProfileOrBackgroundOfUser_whenToCallDeleteImageUser() throws FileNotFoundException, IOException, ErrorDeleteFileException {
//        when(imageUserRepository.findByImageReference(any())).thenReturn(Optional.of(imageUser));
//        when(imageUserService.deleteFileInDirectory(any())).thenReturn(true);
//        imageUserService.deleteImageProfileOrBackgroundOfUser(IMAGE_REFERENCE);
//        verify(imageUserRepository).findByImageReference(IMAGE_REFERENCE);
//        verify(imageUserService).deleteFileInDirectory(IMAGE_REFERENCE);
//    }

    @Test
    void saveAndWriteImageProfile() {
    }

    @Test
    void saveAndWriteBackgroundProfile() {
    }

    @Test
    void saveAndWritePhotoUser() {
    }

    @Test
    void deleteById() {
    }

    void startImageUsers() {
        user = new User();
        imageUser = new ImageUser(IMAGE_REFERENCE, user, null, null);
    }
}