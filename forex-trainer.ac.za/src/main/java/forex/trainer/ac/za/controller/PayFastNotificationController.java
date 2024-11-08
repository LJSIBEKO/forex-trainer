package forex.trainer.ac.za.controller;

import forex.trainer.ac.za.service.payfast.PayFastIntegration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/payfast")
public class PayFastNotificationController
{

    @Autowired
    private PayFastIntegration payFastIntegration;

    @PostMapping("/payment-notify")
    public String handleNotification(@RequestBody Map<String,String> notification) {
      log.info("Received notification: {}", notification);
        return "Notification received";
    }


    @GetMapping("{booking}")
    public ResponseEntity<Map<String, String>> handleBooking(@PathVariable UUID booking) {
        return ResponseEntity.ok(payFastIntegration.request(booking));
    }

}
