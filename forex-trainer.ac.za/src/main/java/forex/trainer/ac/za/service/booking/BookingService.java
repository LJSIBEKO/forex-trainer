package forex.trainer.ac.za.service.booking;


import forex.trainer.ac.za.dtos.CreateBooking;
import forex.trainer.ac.za.exception.RequestException;
import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.booking.Booking;
import forex.trainer.ac.za.model.course.Course;
import forex.trainer.ac.za.repository.account.UserAccountRepository;
import forex.trainer.ac.za.repository.booking.BookingRepository;
import forex.trainer.ac.za.repository.cource.CourceRepositorty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService
{
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private CourceRepositorty courceRepositorty;


    // Create or update a booking
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    // Get a booking by ID
    public Optional<Booking> getBookingById(UUID id) {
        return bookingRepository.findById(id);
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Delete a booking by ID
    public void deleteBooking(UUID id) {
        bookingRepository.deleteById(id);
    }

    public Booking createbooking(CreateBooking createBooking)
    {
        Booking booking = new Booking();
        UserAccount userAccount = userAccountRepository.findById(createBooking.getUserId()).orElseThrow(()->
                new RequestException("User Account Not Found"));

        Course course = courceRepositorty.findById(createBooking.getCourseId()).orElseThrow(() -> new RequestException("Course Not Found"));

        booking.setCource(course);
        booking.setOwner(userAccount);
        booking.setPrice(course.getAmount());
        booking.setPaid(false);
        booking.setStartTime(createBooking.getStartDate());
        booking.setEndTime(createBooking.getEndDate());

        return  bookingRepository.save(booking);
    }


}
