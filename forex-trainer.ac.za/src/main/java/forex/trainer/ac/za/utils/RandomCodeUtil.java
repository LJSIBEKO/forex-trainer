package forex.trainer.ac.za.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomCodeUtil
{
    private static final Random RANDOM = new Random();

    public static String generateUniqueRandomNumbersAsString(int count) {
        Set<String> uniqueNumbers = new HashSet<>();

        while (uniqueNumbers.size() < count) {
            String randomNumber = generateRandomNumber();
            uniqueNumbers.add(randomNumber);
        }

        return String.join(", ", uniqueNumbers);
    }

    private static String generateRandomNumber() {
        int number = RANDOM.nextInt(90000) + 10000;
        return String.valueOf(number);
    }

}
