package com.crio.rentRead.service;

import com.crio.rentRead.model.Book;
import com.crio.rentRead.model.Role;
import com.crio.rentRead.model.User;
import com.crio.rentRead.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceTests {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthService authService;

    @Mock
    private BookService bookService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void RentBookTest() {

        User user1 = new User(
                1,
                "v1@mail.com",
                "password",
                "vivek",
                "kumar",
                Role.USER,
                new ArrayList<>()
        );
        Book book1 = new Book(1, "title1", "author1", "genre1", true);

        User user1new = new User(user1);
        user1new.getRentedBooks().add(book1);

        when(authService.getLoggedInUser()).thenReturn(user1);
        when(bookService.getBook(1)).thenReturn(book1);


        when(userRepository.save(ArgumentMatchers.any())).thenReturn(user1);

        User updatedUser = userService.rentBook(1);

        assertEquals(1, updatedUser.getRentedBooks().size());
        assertFalse(book1.isAvailable());
        assertTrue(updatedUser.getRentedBooks().contains(book1));
    }
}
