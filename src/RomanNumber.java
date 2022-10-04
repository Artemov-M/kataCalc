import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class RomanNumber {

    private static final String ROMAN_DIGIT_REGEX = "^M{0,3}(CM|CD|D?C{0,3})?(XC|XL|L?X{0,3})?(IX|IV|V?I{0,3})?$";
    private static final List<RomanDigit> ROMAN_DIGITS = RomanDigit.getReverseSortedValues();
    private String number;
    private int value;

    public RomanNumber(String s) {
        if (!isRomanDigits(s)) {
            throw new NumberFormatException(String.format("'%s' не является римским числом", s));
        }
        s = s.toUpperCase();
        this.number = s;
        int i = 0;
        while ((s.length() > 0) && (i < ROMAN_DIGITS.size())) {
            RomanDigit digit = ROMAN_DIGITS.get(i);
            if (s.startsWith(digit.name())) {
                this.value += digit.value;
                s = s.substring(digit.name().length());
            } else {
                i++;
            }
        }
    }

    public static boolean isRomanDigits(String s) {
        return s.toUpperCase().matches(ROMAN_DIGIT_REGEX);
    }

    public RomanNumber(int i) {
        if (i < 1 || i > 3999) {
            throw new IllegalArgumentException(String.format("'%d' невозможно преобразовать в римскую запись, " +
                    "допускаются значения от 1 до 3999", i));
        }
        this.value = i;
        StringBuilder sb = new StringBuilder();
        int j = 0;
        while ((i > 0) && (j < ROMAN_DIGITS.size())) {
            RomanDigit digit = ROMAN_DIGITS.get(j);
            if (i >= digit.value) {
                i -= digit.value;
                sb.append(digit.name());
            } else {
                j++;
            }
        }
        this.number = sb.toString();
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return number;
    }

    private enum RomanDigit {
        I(1),
        IV(4),
        V(5),
        IX(9),
        X(10),
        XL(40),
        L(50),
        XC(90),
        C(100),
        CD(400),
        D(500),
        CM(900),
        M(1000);

        int value;

        RomanDigit(int i) {
            value = i;
        }

        public static List<RomanDigit> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanDigit e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }
}
