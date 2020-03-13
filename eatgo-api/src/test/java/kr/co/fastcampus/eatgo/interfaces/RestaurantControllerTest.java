package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurantService;

//    @SpyBean(RestaurantRepositoryImpl.class)
//    private RestaurantRepository restaurantRepository;
//
//    @SpyBean(MenuItemRepositoryImpl.class)
//    private MenuItemRepositoryImpl menuItemRepository;
    @Test
    public  void list() throws Exception {
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(new Restaurant(1004L, "Bob zip","Seoul"));

        given(restaurantService.getRestaurants()).willReturn(restaurants);

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString("\"id\":1004")))
                .andExpect(content()
                        .string(containsString("\"name\":\"Bob zip\"")));

    }

    @Test
    public void detail() throws Exception {

        Restaurant restaurant1 = new Restaurant(1004L,"Bob zip","Seoul");
        Restaurant restaurant2 = new Restaurant(1005L,"Koo zip","Seoul");

        restaurant1.addMenuItem(new MenuItem("Kimchi"));
        restaurant2.addMenuItem(new MenuItem("Kimchi"));

        given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);
        given(restaurantService.getRestaurant(1005L)).willReturn(restaurant2);
        mvc.perform(get("/restaurants/1004"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1004")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Bob zip\"")
                ))
                .andExpect(content()
                        .string(containsString("Kimchi")));
        mvc.perform(get("/restaurants/1005"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("\"id\":1005")
                ))
                .andExpect(content().string(
                        containsString("\"name\":\"Koo zip\"")
                ))
                .andExpect(content()
                        .string(containsString("Kimchi")));
    }

    @Test
    public void create() throws Exception {

        mvc.perform(post("/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Wonzip\",\"address\":\"Seoul\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/restaurants/null"))
                .andExpect(content().string("{}"));

        verify(restaurantService).addRestaurant(any());

    }

}