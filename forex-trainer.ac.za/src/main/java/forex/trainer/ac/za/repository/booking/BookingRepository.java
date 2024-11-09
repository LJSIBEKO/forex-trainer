package forex.trainer.ac.za.repository.booking;

import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BookingRepository  extends JpaRepository<Booking, UUID>
{

    @Query("SELECT b FROM Booking b WHERE b.owner.id = ?1")
    List<Booking> findByCustomerId(UUID customerId);

    @Query("SELECT COUNT(b) FROM Booking b")
    long countTotalBookings();

    // Total number of completed (paid) bookings
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.paid = true")
    long countCompletedPayments();

    // Total number of pending (unpaid) bookings
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.paid = false")
    long countPendingPayments();

    // Sum of prices for completed (paid) bookings for total revenue
    @Query("SELECT SUM(b.price) FROM Booking b WHERE b.paid = true")
    Double calculateTotalRevenue();

    @Query("SELECT b FROM Booking b WHERE b.owner.id = ?1 and b.endTime >= ?2 ")
    List<Booking> getUserBookingThatHaveNotExpired(UUID id, LocalDateTime now);
}
