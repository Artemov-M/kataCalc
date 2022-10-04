import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final int MIN_VAL = 1;
    private static final int MAX_VAL = 10;

    public static void main(String[] args) throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println(calc(reader.readLine()));
        }
    }

    public static String calc(String input) {

        var strings = input.split("[\\+\\-\\*/]");
        if (strings.length != 2) {
            throw new CalcException("формат математической операции не удовлетворяет заданию - два операнда и " +
                    "один оператор (+, -, /, *)");
        }
        var operation = input.substring(strings[0].length(), strings[0].length() + 1);
        var num1 = strings[0].strip();
        var num2 = strings[1].strip();

        if (RomanNumber.isRomanDigits(num1)) {
            if (RomanNumber.isRomanDigits(num2)) {
                int i1 = new RomanNumber(num1).getValue();
                int i2 = new RomanNumber(num2).getValue();
                return new RomanNumber(calculate(i1, i2, operation)).toString();
            } else if (num2.matches("\\d+")) {
                throw new CalcException("используются одновременно разные системы счисления");
            } else {
                throw new CalcException(String.format("'%s' не является числом", num2));
            }
        } else if (num1.matches("\\d+")) {
            if (num2.matches("\\d+")) {
                int i1 = Integer.parseInt(num1);
                int i2 = Integer.parseInt(num2);
                return calculate(i1, i2, operation).toString();
            } else if (RomanNumber.isRomanDigits(num2)) {
                throw new CalcException("используются одновременно разные системы счисления");
            } else {
                throw new CalcException(String.format("'%s' не является числом", num2));
            }
        } else {
            throw new CalcException(String.format("'%s' не является числом", num1));
        }
    }

    private static Integer calculate(int i1, int i2, String s) {
        checkValue(i1);
        checkValue(i2);
        return switch (s) {
            case "+" -> i1 + i2;
            case "-" -> i1 - i2;
            case "*" -> i1 * i2;
            case "/" -> i1 / i2;
            default -> throw new CalcException(String.format("'%s' введенный оператор не соответствует условию " +
                    "- один оператор (+, -, /, *)", s));
        };
    }

    private static void checkValue(int i) {
        if (i < MIN_VAL || i > MAX_VAL) {
            throw new CalcException(String.format("Введенное число '%d' не соответствует условию - числа " +
                    "от '%d' до '%d' включительно", i, MIN_VAL, MAX_VAL));
        }
    }
}