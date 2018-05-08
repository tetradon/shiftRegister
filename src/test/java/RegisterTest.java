import model.Polynomial;
import model.ShiftRegister;
import org.junit.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class RegisterTest {
    @Test
    public void testNextState(){
        Polynomial poly = new Polynomial(9,11,1055,'E');
        ShiftRegister register = new ShiftRegister(poly,new int[]{0,0,0,0,0,0,0,0,1});
        assertThat(register.nextState()).isEqualTo(new int[]{1,0,0,0,0,0,0,0,0});
        assertThat(register.nextState()).isEqualTo(new int[]{0,1,0,0,0,0,0,0,0});
        assertThat(register.nextState()).isEqualTo(new int[]{0,0,1,0,0,0,0,0,0});
        assertThat(register.nextState()).isEqualTo(new int[]{0,0,0,1,0,0,0,0,0});
        assertThat(register.nextState()).isEqualTo(new int[]{1,0,0,0,1,0,0,0,0});
        assertThat(register.nextState()).isEqualTo(new int[]{0,1,0,0,0,1,0,0,0});
        assertThat(register.nextState()).isEqualTo(new int[]{1,0,1,0,0,0,1,0,0});
    }
}
