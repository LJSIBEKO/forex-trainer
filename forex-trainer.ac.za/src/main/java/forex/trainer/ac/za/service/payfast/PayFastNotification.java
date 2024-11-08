package forex.trainer.ac.za.service.payfast;

import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class PayFastNotification
{

    private static final String MERCHANT_KEY = "081l1weny7wgn";
    public boolean verifyPayment(Map<String, String> responseParams) {
        // Rebuild the query string and compare it with the signature
        StringBuilder queryString = new StringBuilder();
        responseParams.forEach((key, value) -> queryString.append(key).append("=").append(value).append("&"));
        queryString.deleteCharAt(queryString.length() - 1); // Remove last "&"

        // Recreate the expected signature
        String signature = md5(MERCHANT_KEY + "&" + queryString.toString());

        // Compare the signature
        return signature.equals(responseParams.get("signature"));
    }

    private String md5(String s) {
        return  "";
    }

    // Method to handle payment verification using IPN
    public void handleIPNNotification(Map<String, String> ipnData) {
        if (verifyPayment(ipnData)) {
            String paymentStatus = ipnData.get("payment_status");
            if ("COMPLETE".equals(paymentStatus)) {
                // Payment was successful
                System.out.println("Payment is successful!");
            } else {
                // Payment failed
                System.out.println("Payment failed.");
            }
        } else {
            // Invalid signature
            System.out.println("Invalid signature received.");
        }
    }
}
