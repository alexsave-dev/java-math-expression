package expression;

import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class Providers {

    public static Stream<Arguments> testValidate() {
        return Stream.of(
                arguments("0.305", true),
                arguments("0.3051", true),
                arguments("(0.3051)", true),
                arguments("1+(1+(1+1)*(1+1))*(1+1)+1", true),
                arguments("-2+(-2+(-2)-2*(2+2))", true),
                arguments("1+4/2/2+2^2+2*2-2^(2-1+1)", true),
                arguments("10-2^(2-1+1)", true),
                arguments("2^10+2^(5+5)", true),
                arguments("1.01+(2.02-1+1/0.5*1.02)/0.1+0.25+41.1", true),
                arguments("0.000025+0.000012", true),
                arguments("-2-(-2-1-(-2)-(-2)-(-2-2-(-2)-2)-2-2)", true),
                arguments("2*(589+((2454*0.1548/0.01*(-2+9^2))+((25*123.12+45877*25)+25))-547)", true),
                arguments("(-1 + (-2))", true),
                arguments("-(-22+22*2)", true),
                arguments("-2^(-2)", true),
                arguments("-(-2^(-2))+2+(-(-2^(-2)))", true),
                arguments("(-2)*(-2)", true),
                arguments("(-2)/(-2)", true),
                arguments("2+8*(9/4-1.5)^(1+1)", true),
                arguments("0.005 ", true),
                arguments("0.0049 ", true),
                arguments("0+0.304", true),
                arguments(null, false),
                arguments("", false),
                arguments("abcd", false),
                arguments("+(22)", false),
                arguments("1+-2", false),
                arguments("1-2-", false),
                arguments("1-2(", false)
        );
    }

    public static Stream<Arguments> testParseToReversePolishNotation() {
        return Stream.of(
                arguments("0.305", "0.305"),
                arguments("0.3051", "0.3051"),
                arguments("1 + 2 / 2", "1 2 2 / +"),
                arguments("(-1 + 2) / 2", "1 _ 2 + 2 /"),
                arguments("3 + 4 * 2 / (1 - 5)^2", "3 4 2 * 1 5 - 2 ^ / +"),
                arguments("1+(1+(1+1)*(1+1))*(2+3)+4", "1 1 1 1 + 1 1 + * + 2 3 + * + 4 +"),
                arguments("1+(5+7)*(2+3)+4", "1 5 7 + 2 3 + * + 4 +"),
                arguments("1+ 12 * 13 +4", "1 12 13 * + 4 +")
        );
    }

    public static Stream<Arguments> testCalculate() {
        return Stream.of(
                arguments("0.305", 0.305),
                arguments("0.3051", 0.3051),
                arguments("1 + 2 / 2", 2.0),
                arguments("(-1 + 2) / 2", 0.5),
                arguments("3 + 4 * 2 / (1 - 5)^2", 3.5),
                arguments("1+(1+(1+1)*(1+1))*(1+1)+1", 12.0),
                arguments("-2+(-2+(-2)-2*(2+2))", -14.0),
                arguments("1+4/2/2+2^2+2*2-2^(2-1+1)", 6.0),
                arguments("10-2^(2-1+1)", 6.0),
                arguments("2^10+2^(5+5)", 2048.0),
                arguments("1.01+(2.02-1+1/0.5*1.02)/0.1+0.25+41.1", 72.96000000000001),
                arguments("0.000025+0.000012", 3.7000000000000005E-5),
                arguments("-2-(-2-1-(-2)-(-2)-(-2-2-(-2)-2)-2-2)", -3.0),
                arguments("2*(589+((2454*0.1548/0.01*(-2+9^2))+((25*123.12+45877*25)+25))-547)", 8302231.359999999),
                arguments("(-1 + (-2))", -3.0),
                arguments("-(-22+22*2)", -22.0),
                arguments("-2^(-2)", 0.25),
                arguments("-(-2^(-2))+2+(-(-2^(-2)))", 1.5),
                arguments("(-2)*(-2)", 4.0),
                arguments("(-2)/(-2)", 1.0),
                arguments("2+8*(9/4-1.5)^(1+1)", 6.5),
                arguments("2 ^5", 32.0),
                arguments("(-2) - ((-4) * 3.5)", 12.0)
        );
    }
}
