package forex.trainer.ac.za.repository.booking;

import forex.trainer.ac.za.model.account.UserAccount;
import forex.trainer.ac.za.model.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BookingRepository  extends JpaRepository<Booking, UUID>
{

    @Query("SELECT b FROM Booking b WHERE b.owner = ?1")
    List<Booking> findByCustomerId(UserAccount customerId);
}
