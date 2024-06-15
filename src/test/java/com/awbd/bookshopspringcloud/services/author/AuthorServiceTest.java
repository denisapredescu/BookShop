//package com.awbd.bookshop.services.author;
//
//import com.awbd.bookshop.exceptions.exceptions.DatabaseError;
//import com.awbd.bookshop.exceptions.exceptions.NoFoundElementException;
//import com.awbd.bookshop.repositories.AuthorRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class AuthorServiceTest {
//    private static final Integer AUTHOR_ID = 0;
//    private static final Author AUTHOR = new Author(
//            AUTHOR_ID,
//            "firstName",
//            "lastName",
//            "nationality"
//    );
//
//    @InjectMocks
//    private AuthorService authorServiceUnderTest;
//    @Mock
//    private AuthorRepository authorRepository;
//    @Test
//    void addAuthor() {
//    }
//
//    @Test
//    void save() {
//        when(authorServiceUnderTest.getAuthor(AUTHOR.getFirstName(), AUTHOR.getLastName())).thenReturn(null);
//        when(authorRepository.getAuthor(AUTHOR.getFirstName(), AUTHOR.getLastName())).thenReturn(Optional.empty());
//        when(authorRepository.save(AUTHOR)).thenReturn(AUTHOR);
//
//        var result = authorServiceUnderTest.save(AUTHOR);
//        verify(authorRepository, times(1)).getAuthor(AUTHOR.getFirstName(), AUTHOR.getLastName());
//        verify(authorRepository, times(1)).save(AUTHOR);
//
//        assertEquals(AUTHOR, result);
//    }
//
//    @Test
//    void save_author_is_null() {
//        var result = authorServiceUnderTest.save(null);
//        verify(authorRepository, never()).getAuthor(any(), any());
//        verify(authorRepository, never()).save(any());
//
//        assertNull(result);
//    }
//
//    @Test
//    void save_already_in() {
//        when(authorRepository.getAuthor(AUTHOR.getFirstName(), AUTHOR.getLastName())).thenReturn(Optional.of(AUTHOR));
//
//        var result = authorServiceUnderTest.save(AUTHOR);
//        verify(authorRepository, times(1)).getAuthor(AUTHOR.getFirstName(), AUTHOR.getLastName());
//        verify(authorRepository, never()).save(AUTHOR);
//
//        assertEquals(AUTHOR, result);
//    }
//
//    @Test
//    void save_DatabaseError_at_findById() {
//        when(authorRepository.getAuthor(AUTHOR.getFirstName(), AUTHOR.getLastName())).thenThrow(DatabaseError.class);
//
//        assertThrows(DatabaseError.class, () -> authorServiceUnderTest.save(AUTHOR));
//        verify(authorRepository, times(1)).getAuthor(AUTHOR.getFirstName(), AUTHOR.getLastName());
//        verify(authorRepository, never()).save(AUTHOR);
//    }
//
//    @Test
//    void save_DatabaseError_at_save() {
//        when(authorServiceUnderTest.getAuthor(AUTHOR.getFirstName(), AUTHOR.getLastName())).thenReturn(null);
//        when(authorRepository.getAuthor(AUTHOR.getFirstName(), AUTHOR.getLastName())).thenReturn(Optional.empty());
//        when(authorRepository.save(AUTHOR)).thenThrow(DatabaseError.class);
//
//        assertThrows(DatabaseError.class, () -> authorServiceUnderTest.save(AUTHOR));
//        verify(authorRepository, times(1)).getAuthor(AUTHOR.getFirstName(), AUTHOR.getLastName());
//        verify(authorRepository, times(1)).save(AUTHOR);
//    }
//
//    @Test
//    void updateAuthor() {
//        when(authorRepository.findById(eq(AUTHOR_ID))).thenReturn(Optional.of(AUTHOR));
//
//        Author update_data = new Author(null, "update firstName", "update lastName", "update nationality");
//        AUTHOR.setLastName(update_data.getLastName());
//        AUTHOR.setFirstName(update_data.getFirstName());
//        AUTHOR.setNationality(update_data.getNationality());
//
//        when(authorRepository.save(any())).thenReturn(AUTHOR);
//
//        var result = authorServiceUnderTest.updateAuthor(update_data, AUTHOR_ID);
//        verify(authorRepository, times(1)).findById(AUTHOR_ID);
//        verify(authorRepository).save(AUTHOR);
//
//        assertEquals(AUTHOR, result);
//    }
//
//    @Test
//    void updateAuthor_NoSuchElementException() {
//        when(authorRepository.findById(eq(AUTHOR_ID))).thenThrow(NoFoundElementException.class);
//
//        assertThrows(NoFoundElementException.class, () -> authorServiceUnderTest.updateAuthor(any(), AUTHOR_ID));
//        verify(authorRepository, times(1)).findById(AUTHOR_ID);
//        verify(authorRepository, never()).save(any());
//    }
//
//    @Test
//    void updateAuthor_DatabaseError_at_findById() {
//        when(authorRepository.findById(eq(AUTHOR_ID))).thenThrow(DatabaseError.class);
//
//        assertThrows(DatabaseError.class, () -> authorServiceUnderTest.updateAuthor(any(), AUTHOR_ID));
//        verify(authorRepository, times(1)).findById(AUTHOR_ID);
//        verify(authorRepository, never()).save(AUTHOR);
//    }
//
//    @Test
//    void updateAuthor_DatabaseError_at_save() {
//        when(authorRepository.findById(eq(AUTHOR_ID))).thenReturn(Optional.of(AUTHOR));
//        when(authorRepository.save(any())).thenThrow(DatabaseError.class);
//
//        assertThrows(DatabaseError.class, () -> authorServiceUnderTest.updateAuthor(new Author(), AUTHOR_ID));
//        verify(authorRepository, times(1)).findById(AUTHOR_ID);
//        verify(authorRepository, times(1)).save(any());
//    }
//
//    @Test
//    void deleteAuthor() {
//        authorServiceUnderTest.deleteAuthor(AUTHOR_ID);
//        verify(authorRepository).deleteById(AUTHOR_ID);
//    }
//
//    @Test
//    void getAuthors() {
//        List<Author> authors = List.of(new Author(), new Author(), new Author());
//        when(authorRepository.getAuthors()).thenReturn(authors);
//
//        var result = authorServiceUnderTest.getAuthors();
//        assertEquals(authors, result);
//        assertEquals(authors.size(), result.size());
//    }
//
//    @Test
//    void getAuthors_DatabaseError() {
//        when(authorRepository.getAuthors()).thenThrow(DatabaseError.class);
//        assertThrows(DatabaseError.class, () -> authorServiceUnderTest.getAuthors());
//    }
//
//    @Test
//    void getAuthor() {
//        String firstName = "firstName";
//        String lastName = "lastName";
//
//        Author expectedAuthor = new Author();
//        expectedAuthor.setFirstName(firstName);
//        expectedAuthor.setLastName(lastName);
//
//        when(authorRepository.getAuthor(firstName, lastName)).thenReturn(Optional.of(expectedAuthor));
//
//        Author actualAuthor = authorServiceUnderTest.getAuthor(firstName, lastName);
//        verify(authorRepository, times(1)).getAuthor(firstName, lastName);
//        assertEquals(expectedAuthor, actualAuthor);
//    }
//
//    @Test
//    void getAuthor_returns_null() {
//        when(authorRepository.getAuthor(any(), any())).thenReturn(Optional.empty());
//
//        Author actualAuthor = authorServiceUnderTest.getAuthor(any(), any());
//        verify(authorRepository, times(1)).getAuthor(any(), any());
//        assertNull(actualAuthor);
//    }
//
//    @Test
//    void getAuthor_DatabaseError() {
//        when(authorRepository.getAuthor(any(), any())).thenThrow(DatabaseError.class);
//
//        assertThrows(DatabaseError.class, () -> authorServiceUnderTest.getAuthor(any(), any()));
//        verify(authorRepository, times(1)).getAuthor(any(), any());
//    }
//}