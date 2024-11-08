package forex.trainer.ac.za.controller;


import forex.trainer.ac.za.dtos.CreateBooking;
import forex.trainer.ac.za.model.booking.Booking;
import forex.trainer.ac.za.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("booking")
public class BookingController
{

    @Autowired
    private BookingService bookingService;

    @PostMapping("create")
    public ResponseEntity<Booking> createBooking(@RequestBody CreateBooking createBooking){
        return new ResponseEntity<>(bookingService.createbooking(createBooking), HttpStatus.OK);
    }

}
