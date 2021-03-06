package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserTests {
    @Test
    public void creation(){
        User user = User.builder()
                .email("0516khj2004@naver.com")
                .name("koo")
                .level(100L)
                .build();

        assertThat(user.getName() , is("koo"));
        assertThat(user.isAdmin() ,is(true));
        assertThat(user.isActive()  ,is(true));

        user.deativate();
        assertThat(user.isActive()  ,is(false));
    }

    @Test
    public  void restaurantOwner(){
        User user  = User.builder().level(1L).build();
        assertThat(user.isRestaurantOwner(), is(false));

        user.setRestaurantId(1004L);
        assertThat(user.isRestaurantOwner(), is(true));
        assertThat(user.getRestaurantId(), is(1004L));
    }
}