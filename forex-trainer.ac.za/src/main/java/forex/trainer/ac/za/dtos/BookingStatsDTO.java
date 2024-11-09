package forex.trainer.ac.za.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingStatsDTO
{
    private int totalBookings;
    private int completedPayments;
    private int pendingPayments;
    private double totalRevenue;
}
