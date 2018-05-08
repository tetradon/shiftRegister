import org.junit.Test;
import util.calculation.Calculation;

import static org.assertj.core.api.Assertions.assertThat;


public class CalculationTest {
   @Test
    public void testBinaryConversion(){
       assertThat(Calculation.convertBinaryArrayToInteger(new int[]{1,0,1})).isEqualTo(5);
       assertThat(Calculation.convertBinaryArrayToInteger(new int[]{1,1,1})).isEqualTo(7);
       assertThat(Calculation.convertBinaryArrayToInteger(new int[]{0,1,1})).isEqualTo(3);
    }
}
