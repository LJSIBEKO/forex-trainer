package forex.trainer.ac.za.controller;


import forex.trainer.ac.za.dtos.BookingStatsDTO;
import forex.trainer.ac.za.dtos.CreateBooking;
import forex.trainer.ac.za.model.booking.Booking;
import forex.trainer.ac.za.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("{userId}")
    public ResponseEntity<List<Booking>> getBooking(@PathVariable UUID userId){
        return new ResponseEntity<>(bookingService.getAllBookingsForUser(userId),HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<BookingStatsDTO> getBookingStatistics() {
        // Get statistics from the service
        BookingStatsDTO stats = bookingService.getBookingStatistics();
        // Return the statistics as a response
        return ResponseEntity.ok(stats);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        // Get all bookings from the service
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

}
