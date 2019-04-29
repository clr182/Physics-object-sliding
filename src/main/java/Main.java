import java.io.*;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    public static final double GRAVITY = 9.81;

    public static void main(String[] args) throws IOException {

        Scanner kb = new Scanner(System.in);
        System.out.println("Set i, j, k values");
        System.out.println("press the enter key after each value");
        double i = kb.nextInt();
        double j = kb.nextInt();
        double k = kb.nextInt();


        //Test Values
        double mStatic = 0.6;
        double mKinetic = 0.5;
        double mass = 4;

        Vectors normal = new Vectors(0, 3, 4);

        Vectors normalHat = normal.hat();

        Vectors objectArr = new Vectors(i, j, k);

        //step 1
        Vectors forceGravity = calcForceGravity(mass);

        System.out.println("Force Gravity: " + forceGravity.toString());

        //step 2
        Vectors forceGravityNormal = calcForceGravityNormal(forceGravity, normalHat);

        System.out.println("Force Gravity Normal: " + forceGravityNormal.toString());

        //step 3
        Vectors forceGravityPlane = calcForceGravityPlane(forceGravity, forceGravityNormal);

        System.out.println("Force Gravity Plane: " + forceGravityPlane.toString());

        double forceGravityPlaneMag = forceGravityPlane.getMagnitude();

        Vectors dHat = forceGravityPlane.hat();

        System.out.println("D Hat: " + dHat.toString());

        //step 4
        Vectors forceNormal = calcForceNormal(forceGravityPlane);

        System.out.println("Force Normal: " + forceNormal.toString());

        double forceNormalMag = forceNormal.getMagnitude();

        //step 5
        Vectors forceFriction = calcForceFriction(mStatic, mKinetic, forceNormalMag, forceGravityPlaneMag, dHat);

        System.out.println("Force Friction: " + forceFriction.toString());

        //step 6
        Vectors forceNet = calcForceNet(forceFriction, forceGravityPlane, forceNormal);

        System.out.println("Net Force: " + forceNet.toString());

        //step 7
        Vectors acelerationVectors = calcACC(mass, forceNet);

        System.out.println("Acceleration: " + acelerationVectors.toString());

        Eulers(acelerationVectors);
        rK4(acelerationVectors);
    }


    public static Vectors calcForceGravity(double mass) {

        Vectors kHat = new Vectors(0, 0, 1);

        double massGravity = mass * GRAVITY;

        return kHat.scalarMultiply(-massGravity);

    }

    public static Vectors calcForceGravityNormal(Vectors forceGravity, Vectors normalHat) {

        Vectors forceGravityNormal = null;

        double forceGravityDotNHat = forceGravity.dotProduct(normalHat);

        return normalHat.scalarMultiply(forceGravityDotNHat);

    }

    public static Vectors calcForceGravityPlane(Vectors forceGravity, Vectors forceGravityNormal) {
        Vectors forceGravityPlane = null;

        forceGravityPlane = forceGravity.subtraction(forceGravityNormal);

        return forceGravityPlane;

    }

    public static Vectors calcForceNormal(Vectors forceGravityPlane) {
        Vectors forceNormal = null;

        forceNormal = forceGravityPlane.scalarMultiply(-1);

        return forceNormal;
    }

    public static Vectors calcForceFriction(double mStatic, double mKinetic, double forceNormalMag, double forceGravityPlaneMag, Vectors DHat) {

        double forceFriction;

        double forceFrictionStatic = mStatic * forceNormalMag;
        double forceFrictionKinetic = mKinetic * forceNormalMag;

        if (forceFrictionStatic < forceGravityPlaneMag) {
            forceFriction = forceFrictionKinetic;
        } else {
            forceFriction = forceFrictionStatic;
        }
        Vectors negativeD = DHat.scalarMultiply(-1);

        Vectors forceFrictionVector = negativeD.scalarMultiply(forceFriction);


        return forceFrictionVector;
    }

    public static Vectors calcForceNet(Vectors forceFriction, Vectors forceGravityPlane, Vectors forceNormal) {
        Vectors forceNet = forceGravityPlane.addition(forceNormal).addition(forceFriction);

        return forceNet;
    }

    public static Vectors calcACC(double mass, Vectors forceNet) {

        Vectors acceleration = null;

        acceleration = forceNet.scalarMultiply((1 / mass));

        return acceleration;
    }

    public static void Eulers(Vectors acceleration) throws IOException {

        double fps = 60;
        double t = 0;
        double h = 1 / fps;
        int loop = 0;

        Vectors position = new Vectors(1, 1, 1);
        Vectors velocity = new Vectors(1, 1, 1);
        Vectors gravity = new Vectors(0, 0, -9.81);


        FileWriter fileWriter = new FileWriter("position-velocity-euler.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);


        while (loop < 10) {
            loop++;
            t = t + h;
            position = position.addition((velocity.scalarMultiply(h)));
            velocity = velocity.addition(acceleration.scalarMultiply(h));

            System.out.println("Position " + loop + ": " + position.toString());
            System.out.println("Velocity " + loop + ": " + velocity.toString());

            String fileContent = "Position " + loop + ": " + position.toString() + "\n" + "Velocity " + loop + ": " + velocity.toString();

            printWriter.println(fileContent);
        }

        printWriter.close();
    }

    public static void rK4(Vectors acceleration) throws IOException {

        double fps = 60;
        double t = 0;
        double h = 1 / fps;
        int loop = 0;

        Matrix k1;
        Matrix k2;
        Matrix k3;
        Matrix k4;

        Vectors positionO = new Vectors(0,0,0);
        Vectors velocityO = new Vectors(0,0,0);

        FileWriter fileWriter = new FileWriter("position-velocity-rk4.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);


        while (loop < 10) {
            loop ++;
            t = t + h;
            Vectors position;
            Vectors velocity;

            position = velocityO.scalarMultiply(h);
            velocity = acceleration.scalarMultiply(h);

            k1 = new Matrix(position, velocity);

            position = positionO.addition(position.scalarMultiply(0.5));
            velocity = velocityO.addition(velocity.scalarMultiply(0.5));

            position = velocity.scalarMultiply(h);
            velocity = acceleration.scalarMultiply(h);

            k2= new Matrix(position, velocity);

            position = velocity.scalarMultiply(h);
            velocity = acceleration.scalarMultiply(h);

            k3 = new Matrix(position, velocity);

            position = positionO.addition(position.scalarMultiply(0.5));
            velocity = velocityO.addition(velocity.scalarMultiply(0.5));


            position = velocity.scalarMultiply(h);
            velocity = acceleration.scalarMultiply(h);

            k4 = new Matrix(position,velocity);

            Matrix k6 = k1.addMatrix(k2).addMatrix(k3).addMatrix(k4);

            Matrix k = k6.divideMatrix();

            Vectors newPos = k.getPosition();
            Vectors newVel = k.getVelocity();

            System.out.println("Position " + loop + ": " + newPos.toString());
            System.out.println("Velocity " + loop + ": " + newVel.toString());

            String fileContent = "Position " + loop + ": " + newPos.toString() + "\n" + "Velocity " + loop + ": " + newVel.toString();

            printWriter.println(fileContent);

        }

        printWriter.close();


    }
}