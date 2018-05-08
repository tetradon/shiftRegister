
import model.Polynomial;
import org.junit.*;
import util.db.DBConnector;
import util.db.PolynomialDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class DbTest {

    private PolynomialDAO dao = new PolynomialDAO();
    @Test
    public void testSelect() throws SQLException, IOException {
        Connection connection = DBConnector.getConnection();
        ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM polynomial WHERE oct = 45");
        rs.next();
        assertThat(rs.getInt("n")).isEqualTo(5);
        assertThat(rs.getInt("j")).isEqualTo(1);
        assertThat(rs.getInt("oct")).isEqualTo(45);
        assertThat(rs.getString("letter")).isEqualTo("E");
        assertThat(!rs.next());
    }

    @Test
    public void testDAO() throws SQLException {
        Polynomial poly = dao.getPolynomialByOct(67);
        assertThat(poly.getN()).isEqualTo(5);
        assertThat(poly.getJ()).isEqualTo(5);
        assertThat(poly.getOct()).isEqualTo(67);
        assertThat(poly.getLetter()).isEqualTo('H');
    }

    @Test
    public void testNullDAO() throws SQLException {
        assertThat(dao.getPolynomialByOct(0)).isEqualTo(null);
    }

    @Test
    public void testListDAO() throws SQLException {
        List list = dao.getPolynomialsByN(5);
        assertThat(list.size()).isEqualTo(3);
    }

    @Test
    public void testGetAllN() throws SQLException {

        System.out.println(dao.getAllN());
    }




}
