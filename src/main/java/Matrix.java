public class Matrix {

    private Vectors position;
    private Vectors velocity;


    public Matrix(Vectors position, Vectors velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public Matrix divideMatrix(){
        Vectors positionNew = this.position.scalarMultiply(0.166);
        Vectors velocityNew = this.velocity.scalarMultiply(0.166);

        return new Matrix(positionNew, velocityNew);
    }

    public Matrix addMatrix(Matrix m){
        Vectors positionNew = this.position.addition(m.getPosition());
        Vectors velocityNew = this.velocity.addition(m.getVelocity());


        return  new Matrix(positionNew, velocityNew);
    }


    public Vectors getPosition() {
        return position;
    }

    public Vectors getVelocity() {
        return velocity;
    }
}
