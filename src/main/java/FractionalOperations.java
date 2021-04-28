import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FractionalOperations {
    public static void main(String[] args) {
        String result = getCalculationResult(args);
        System.out.println(result);
    }

    public static String getCalculationResult(String[] args) {
        validateInput(args);
        Fraction fraction1 = Fraction.fromString(args[0]);
        String operator = String.valueOf(args[1]);
        Fraction fraction2 = Fraction.fromString(args[2]);
        Fraction result = new Fraction(0,0);
        switch (operator){
            case "+":
                result = fraction1.add(fraction2);
                break;
            case "-":
                result = fraction1.sub(fraction2);
                break;
            case "*":
                result = fraction1.mul(fraction2);
                break;
            case "/":
                result = fraction1.div(fraction2);;
                break;
        }
        result.simplify();
        return result.toString();
    }

    private static void validateInput(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("The length of args should always be 3!");
        }
        validateOperands(args[0]);
        validateOperands(args[2]);
        validateOperators(args[1]);
    }

    private static void validateOperands(String s) {
        Pattern p = Pattern.compile("[+-]?([1-9]\\d*|0)_([1-9]\\d*|0)/([1-9]\\d*)|[+-]?([1-9]\\d*|0)/([1-9]\\d*)|([+-]?[1-9]\\d*|0)");
        Matcher m = p.matcher(s);
        if (!m.matches()) {
            throw new IllegalArgumentException("The fractions input are not valid!");
        }
    }

    private static void validateOperators(String s) {
        Set<String> validOperators = new HashSet<>();
        validOperators.add("+");
        validOperators.add("-");
        validOperators.add("*");
        validOperators.add("/");
        if (!validOperators.contains(s)) {
            throw new IllegalArgumentException("Operator must be one of the followings: '+', '-', '*', '/'!");
        }
    }

    public static class Fraction {
        public int numerator;
        public int denominator;

        public Fraction(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        // Transfer the string to a fraction with numerator and denominator, adding the whole part to numerator also
        public static Fraction fromString(String s) {
            int underscoreIndex = s.indexOf('_');
            int fractionIndex = s.indexOf('/');

            int whole = 0;
            int numerator = 0;
            int denominator = 0;
            if (underscoreIndex >= 0){
                whole = Integer.valueOf(s.substring(0, underscoreIndex));
            }

            if (fractionIndex < 0) {
                whole = Integer.valueOf(s);
                denominator = 1;
            } else {
                numerator = Integer.valueOf(s.substring(underscoreIndex + 1, fractionIndex));
                denominator = Integer.valueOf(s.substring(fractionIndex + 1));
            }

            if (whole < 0) {
                numerator = -numerator;
            }
            numerator += (whole * denominator);

            return new Fraction(numerator, denominator);
        }

        public Fraction add(Fraction other){
            int denominatorOne = denominator;
            int numeratorOne = numerator;
            int denominatorTwo = other.denominator;
            int numeratorTwo = other.numerator;
            int newDenominator = denominatorOne;
            int oneTimes = 1;
            int twoTimes = 1;

            if (denominatorOne != denominatorTwo) {
                newDenominator = lcm(denominatorOne, denominatorTwo);
                oneTimes = newDenominator / denominatorOne;
                twoTimes = newDenominator / denominatorTwo;
            }

            return new Fraction(numeratorOne * oneTimes  + numeratorTwo * twoTimes, newDenominator);
        }

        public Fraction sub(Fraction other){
            return add(new Fraction(-other.numerator, other.denominator));
        }

        public Fraction mul(Fraction other){
            return new Fraction(numerator * other.numerator, denominator * other.denominator);
        }

        public Fraction div(Fraction other){
            if (other.numerator == 0) {
                throw new ArithmeticException("Division by zero!");
            }
            return new Fraction(numerator * other.denominator, denominator * other.numerator);
        }

        // Calculate the greatest common divisor
        private int gcd(int a, int b){
            return b == 0 ? a : gcd(b, a % b);
        }

        // Calculate the least common multiple
        private int lcm(int a, int b){
            return a * b / gcd(a, b);
        }

        // Simplify from 2/4 => 1/2
        public void simplify(){
            int divisor = gcd(Math.abs(numerator), denominator);
            if (divisor > 1){
                numerator /= divisor;
                denominator /= divisor;
            }
        }

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            int numer = this.numerator;
            if (numer == 0) {
                return "0";
            }
            if (numer < 0) {
                sb.append('-');
            }
            numer = Math.abs(numer);
            if (numer >= denominator){
                int whole = numer / denominator;
                numer = numer % denominator;
                sb.append(whole);

                if (numer != 0){
                    sb.append('_');
                }
            }
            if (numer != 0){
                sb.append(numer).append('/').append(denominator);
            }
            return sb.toString();
        }
    }
}
