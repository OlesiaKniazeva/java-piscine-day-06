package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number < 2) {
            throw new IllegalNumberException("Number should be more when 1");
        } else if (number == 2 || number == 3) {
            return true;
        } else if (number % 2 == 0) {
            return false;
        }

        for (long i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public int digitsSum(int number) {
        int sum;

        sum = 0;

        for (int i = number; i > 0; i /= 10) {
            sum += i % 10;
        }
        return sum;
    }

}



