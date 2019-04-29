public class Vectors {

    private double x;
    private double y;
    private double z;

    public Vectors(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getMagnitude() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }

    public Vectors subtraction(Vectors v) {
        double x = this.x - v.getX();
        double y = this.y - v.getY();
        double z = this.z - v.getZ();

        return new Vectors(x, y, z);
    }

    public Vectors addition(Vectors v) {
        double x = this.x + v.getX();
        double y = this.y + v.getY();
        double z = this.z + v.getZ();

        return new Vectors(x, y, z);
    }

    public Vectors scalarMultiply(double scalar) {
        double x = this.x * scalar;
        double y = this.y * scalar;
        double z = this.z * scalar;

        return new Vectors(x, y, z);
    }

    public Vectors crossProduct(Vectors v) {
        double x = (this.y * v.getZ()) - (this.z * v.getY());
        double y = (this.z * v.getX()) - (this.x * v.getZ());
        double z = (this.x * v.getY()) - (this.y * v.getX());

        return new Vectors(x, y, z);
    }

    public double dotProduct(Vectors v) {
        double x = this.x * v.getX();
        double y = this.y * v.getY();
        double z = this.z * v.getZ();

        return x + y + z;
    }


        public Vectors hat() {
        double dot = Math.sqrt(this.dotProduct(this));

        return this.scalarMultiply(1 / dot);

    }

    @Override
    public String toString() {
        return "[" +
                 x + "\n" +
                 y + "\n" +
                 z + "\n" +
                ']';
    }
}
