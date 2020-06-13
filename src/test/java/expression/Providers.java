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
                arguments("1+4/2/2+2^2+2*2-2^(2-1+1)", false),
                arguments("10-2^(2-1+1)", false),
                arguments("2^10+2^(5+5)", false),
                arguments("1.01+(2.02-1+1/0.5*1.02)/0.1+0.25+41.1", true),
                arguments("0.000025+0.000012", true),
                arguments("-2-(-2-1-(-2)-(-2)-(-2-2-(-2)-2)-2-2)", true),
                arguments("2*(589+((2454*0.1548/0.01*(-2+9^2))+((25*123.12+45877*25)+25))-547)", false),
                arguments("(-1 + (-2))", false),
                arguments("-(-22+22*2)", true),
                arguments("-2^(-2)", false),
                arguments("-(-2^(-2))+2+(-(-2^(-2)))", false),
                arguments("(-2)*(-2)", true),
                arguments("(-2)/(-2)", true),
                arguments("2+8*(9/4-1.5)^(1+1)", false),
                arguments("0.005 ", false),
                arguments("0.0049 ", false),
                arguments("0+0.304", true)
        );
    }

}
