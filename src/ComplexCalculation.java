package basics;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ComplexCalculation {

    public static void main(String[] args) {
        ComplexCalculation calculation = new ComplexCalculation();

        System.out.println(calculation.calculateResult(new BigInteger("4"), new BigInteger("4"), new BigInteger("4"), new BigInteger("4")));
    }


    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
        BigInteger result;

        List<PowerCalculatingThread> threads = new ArrayList<>();

        threads.add(new PowerCalculatingThread(base1, power1));
        threads.add(new PowerCalculatingThread(base2, power2));

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        result = BigInteger.ZERO;
        for (int index = 0; index < threads.size(); index++) {
            PowerCalculatingThread thread = threads.get(index);
            result = result.add(thread.getResult());
        }

        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
//            this.result = this.base.pow(Integer.parseInt(String.valueOf(this.power)));  t-> his is not going to work if power is a number that is too big to fit in an integer.

            result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO;
                 i.compareTo(power) != 0;
                 i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }

        }


        public BigInteger getResult() {
            return result;
        }
    }
}
