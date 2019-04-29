import java.math.BigDecimal;

public class VectorsTest {
    public static void main(String[] args) {
        Vectors v1 = new Vectors(1,2,3);
        Vectors v2 = new Vectors(1,2,3);

        Vectors v3 = v1.addition(v2);

        Vectors vel = new Vectors(1,1,1);

        double fps = 60;

        double h = 1/fps;

        System.out.println(h);




    }
}
