import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {

        Scanner kb = new Scanner(System.in);
        System.out.println("Set i, j, k values");
        System.out.println("press the enter key after each value");
        double i = kb.nextInt();
        double j = kb.nextInt();
        double k = kb.nextInt();


        double mg = 0.7;
        double mk = 0.5;
        double mass = 4;

        Vectors objectArr = new Vectors(i, j, k);

        //step 1
        double FG =calcFG(mass);

        //step 2
        Vectors FGN = calcFGN(FG, objectArr);

        //step 3
        Vectors PGN = calcPGN(FG, FGN);

        //step 4
        double FN = calcFN(FGN);

        //step 5
        Vectors FF = calcFF(mg, mk, FN, PGN);

        Vectors FGPBARR = calcFGPBARR(FG, FGN);

        //step 6
        Vectors FNET = calcFNET(FF, FGPBARR);

        //step 7
        Vectors A = calcACC(mass, FNET);
    }


    public static double calcFG(double mass){

        double gk[] = {0, 0, 1};
        double fg = ((-1)*(mass))*((9.81)*(gk[2]));

        System.out.println("FG:");
        System.out.println( " (" + gk[0] + "),\n ("+ gk[1] + "),\n(" + fg + ")");
        System.out.println(" ");
        return fg;
    }

    public static Vectors calcFGN(double FG, Vectors objectArr){
        double N = Math.sqrt(((objectArr.getI())*(objectArr.getI()))+
                ((objectArr.getJ())*(objectArr.getJ())) +
                ((objectArr.getK())*(objectArr.getK())));

        System.out.println("N: \n" + N);
        System.out.println(" ");

        double NhI = ((1/N) * objectArr.getI());
        double NhJ = ((1/N) * objectArr.getJ());
        double NhK = ((1/N) * objectArr.getK());

        Vectors NHat = new Vectors(NhI, NhJ, NhK);
        System.out.println("NHAT: \n" +NHat);
        System.out.println(" ");

        double sum = 0 + 0 + (FG * NhK);
        Vectors FGN = new Vectors(sum*NhI, sum*NhJ, sum*NhK);

        System.out.println("FGN: \n" +FGN);
        System.out.println(" ");
        return FGN;
    }

    public static Vectors calcFGPBARR(double fg, Vectors fgn){
        Vectors fgpBAR = new Vectors(( 0 - fgn.getI()),( 0 -fgn.getJ()),(fg - fgn.getK()));
        System.out.println("FGPBAR: \n" + fgpBAR);

        return fgpBAR;
    }


    public static Vectors calcPGN(double fg, Vectors fgn){
        Vectors fgpBAR = new Vectors(( 0 - fgn.getI()),( 0 -fgn.getJ()),(fg - fgn.getK()));
        System.out.println("FGPBAR: \n" + fgpBAR);

        double fgp = Math.sqrt(fgpBAR.getI()*fgpBAR.getI() +
                fgpBAR.getJ()*fgpBAR.getJ() +
                fgpBAR.getK()*fgpBAR.getK());
        System.out.println("fgp: \n" + fgp);
        System.out.println(" ");


        double DhI = ((1/fgp) * fgpBAR.getI());
        double DhJ = ((1/fgp) * fgpBAR.getJ());
        double DhK = ((1/fgp) * fgpBAR.getK());
        Vectors dHat = new Vectors(DhI, DhJ, DhK);
        System.out.println("dHat: \n" + dHat);
        System.out.println(" ");
        return dHat ;
    }

    public static double calcFN(Vectors fgn){
        double fnBARI = fgn.getI()*-1;
        double fnBARJ = fgn.getJ()*-1;
        double fnBARK = fgn.getK()*-1;

        double fn = Math.sqrt((fnBARI * fnBARI) + (fnBARJ * fnBARJ) + (fnBARK*fnBARK));

        System.out.println("FN: \n" + fn);
        System.out.println(" ");
        return fn;
    }

    public static Vectors calcFF(double MG, double MK, double FN, Vectors DHat){
        double ff1 = MG * FN;
        double ff2 = MK * FN;

        System.out.println("ff1: " + ff1);
        System.out.println("ff2: " + ff2);

        System.out.println("dhat1: " + DHat.getI());
        double ffBARI = ff2 * -DHat.getI();
        double ffBARJ = ff2 * -DHat.getJ();
        double ffBARK = ff2 * -DHat.getK();

        Vectors ffBAR = new Vectors(ffBARI, ffBARJ, ffBARK);

        System.out.println("ffBAR: \n" + ffBAR);
        System.out.println(" ");
        return ffBAR;
    }

    public static Vectors calcFNET(Vectors FF, Vectors FGP){
       double FNetI = FF.getI() + FGP.getI();
       double FNetJ = FF.getJ() + FGP.getJ();
       double FNetK = FF.getK() + FGP.getK();

       Vectors FNet = new Vectors(FNetI, FNetJ, FNetK);
        System.out.println("FNET:  \n" + FNet);
       return FNet;
    }

    public static Vectors calcACC(double mass, Vectors FNet){
        double ACCI = 1/mass * FNet.getI();
        double ACCJ = 1/mass * FNet.getJ();
        double ACCK = 1/mass * FNet.getK();


        Vectors ACC = new Vectors(ACCI, ACCJ, ACCK);

        System.out.println("ACC: " + ACC);
        return ACC;
    }
}
