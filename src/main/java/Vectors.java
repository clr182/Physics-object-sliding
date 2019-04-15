public class Vectors {

    private double i;
    private double j;
    private double k;

    public Vectors(double i, double j, double k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public Vectors() {
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = k;
    }

    public double getJ() {
        return j;
    }

    public void setJ(double j) {
        this.j = j;
    }




    @Override
    public String toString() {
        return "Vectors{" +
                "i="   + i +
                ", j=" + j +
                ", k=" + k +
                '}';
    }
}
