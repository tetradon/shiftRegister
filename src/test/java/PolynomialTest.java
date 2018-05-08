import model.Polynomial;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PolynomialTest {

    @Test
    public void testConversion(){
        assertThat(new Polynomial(5,5,67,'H').getBinaryRepresentation()).isEqualTo(new int[]{1, 1, 0, 1, 1, 1});
        assertThat(new Polynomial(4,1,23,'F').getBinaryRepresentation()).isEqualTo(new int[]{1,0,0,1,1});
        assertThat(new Polynomial(9,3,1131,'H').getBinaryRepresentation()).isEqualTo(new int[]{1,0,0,1,0,1,1,0,0,1});
    }

    @Test
    public void testCharacteristicMatrix(){
        Polynomial poly = new Polynomial(4,1,23,'F');
        assertThat(poly.getCharacteristicMatrix()).
                isEqualTo(new int [][]{
                        {0,0,1,1},
                        {1,0,0,0},
                        {0,1,0,0},
                        {0,0,1,0}});
    }

    @Test
    public void testPeriod(){
        Polynomial poly = new Polynomial(9,3,1131,'H');
        assertThat(poly.getPeriod()).isEqualTo(511);
        poly = new Polynomial(8,15,727,'D');
        assertThat(poly.getPeriod()).isEqualTo(17);
    }


}
