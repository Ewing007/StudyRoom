package Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ewing
 * @Date: 2024-10-18-19:54
 * @Description:
 */
public class SeatGeneratorUtils {
    public static List<String> generateSeatNumbers(int capacity) {
        int rows = (int) Math.ceil(Math.sqrt(capacity));
        int columns = (int) Math.ceil((double) capacity / rows);
        List<String> seatNumbers = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            char rowChar = (char) ('A' + row);
            for (int col = 1; col <= columns && seatNumbers.size() < capacity; col++) {
                seatNumbers.add(rowChar + String.format("%02d", col));
            }
        }
        return seatNumbers;
    }
}
