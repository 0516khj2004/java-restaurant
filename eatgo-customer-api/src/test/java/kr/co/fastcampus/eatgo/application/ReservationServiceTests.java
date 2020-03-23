package kr.co.fastcampus.eatgo.application;

import kr.co.fastcampus.eatgo.domain.Reservation;
import kr.co.fastcampus.eatgo.domain.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ReservationServiceTests {

    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        reservationService = new ReservationService(reservationRepository);

    }
    @Test
    public void addReservation(){

        Long restaurantId = 369L;
        Long userId= 1004L;
        String name ="John";
        String date = "2020-4-26";
        String time = "18:00";
        Integer partySize = 20;

        given(reservationRepository.save(any()))
                .will(invocation -> {
                    Reservation reservation = invocation.getArgument(0);
                    return reservation;
                });

        Reservation reservation =  reservationService.addReservation(
                restaurantId, userId, name,date,time,partySize
        );

        assertThat(reservation.getName(), is(name));

        verify(reservationRepository).save(any(Reservation.class));
    }

}