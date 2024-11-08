package forex.trainer.ac.za.service.payfast;

import forex.trainer.ac.za.exception.RequestException;
import forex.trainer.ac.za.model.booking.Booking;
import forex.trainer.ac.za.repository.booking.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class PayFastIntegration
{
    private static final String MERCHANT_ID = "10035850";
    private static final String MERCHANT_KEY = "081l1weny7wgn";
    private static final String SANDBOX_URL = "https://sandbox.payfast.co.za/eng/process";
    private static final String BASE_URL = "http://localhost:4200";
    private static final String RETURN_URL = BASE_URL + "/payment-success";
    private static final String CANCEL_URL = BASE_URL + "/payment-cancel";
    private static final String NOTIFY_URL = "http://localhost:8081/api/payfast/payment-notify";

    private final BookingRepository bookingRepository;

    public PayFastIntegration(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Map<String, String> request(UUID bookingId) {
        // Retrieve the booking using the booking ID
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RequestException("Booking not found"));

        try {
            // Generate the payment URL for the specific booking
            return createPaymentRequest(booking);
        } catch (Exception e) {
            throw new RequestException("Failed to create payment request", e);
        }
    }

    private Map<String, String> createPaymentRequest(Booking booking) {
        // Prepare the parameters for PayFast request
        Map<String, String> params = new HashMap<>();
        params.put("merchant_id", MERCHANT_ID);
        params.put("merchant_key", MERCHANT_KEY);
        params.put("return_url", RETURN_URL);
        params.put("cancel_url", CANCEL_URL);
        params.put("notify_url", NOTIFY_URL);
        params.put("amount", String.valueOf(booking.getPrice())); // Assuming booking has a getAmount() method
        params.put("item_name", "Booking Payment");
        params.put("item_description", "Payment for booking ID " + booking.getId());

        // Return the parameters as a Map instead of a complete URL
        return params;
    }
}
