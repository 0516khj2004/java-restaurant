package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.User;
import kr.co.fastcampus.eatgo.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserServiceTests {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        userService = new UserService(userRepository);
    }
    @Test
    public void getUsers(){
        List<User>  mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
                .email("0516khj2004@naver.com")
                .name("Koo")
                .level(1L)
                .build());
        given(userRepository.findAll()).willReturn(mockUsers);
        List<User> users = userService.getUsers();

        User user = users.get(0);

        assertThat(user.getName(), is("Koo"));
    }

    @Test
    public void addUser(){
        String email = "admin@naver.com";
        String name = "Administrator";

        User mockUser = User.builder().email(email).name(name).build();

        given(userRepository.save(any())).willReturn(mockUser);
        User user = userService.addUser(email, name);
        assertThat(user.getName(), is(name));
    }

    @Test
    public void updateUser(){
        Long id =1004L;
        String email = "admin@naver.com";
        String name = "Superman";
        Long level = 100L;

        User mockUser = User.builder()
                .id(id)
                .name("Administrator")
                .email(email)
                .level(1L)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));
        User user = userService.updateUser(id,email,name,level);

        verify(userRepository).findById(eq(id));

        assertThat(user.getName(), is("Superman"));
        assertThat(user.isAdmin(), is(true));
    }

    @Test
    public void deactiveUser(){
        Long id =1004L;
        User mockUser = User.builder()
                .id(id)
                .email("admin@naver.com")
                .name("Administrator")
                .level(100L)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.deactiveUser(1004L);

        verify(userRepository).findById(1004L);

        assertThat(user.isAdmin(), is(false));
        assertThat(user.isActive(), is(false));
    }

}