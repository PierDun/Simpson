package com.company;

import java.util.Scanner;

public class Main {

    private static double function (double x, int i) {
        switch(i) {
            case 1:
                if (x == 0) return 1;
                else return Math.sin(x) / x;
            case 2:
                return 2*x+1;
            case 3:
                if (x == 0) return Math.E;
                else return Math.pow((1 + x), (1 / x));
            case 4:
                return 1/x;
            case 5:
                return Math.sqrt(1+x*x);
            default:
                return 0.0f;
        }
    }

    private static double integral_N (int N, double a, double b, int w){
        double h, sum_off_even = 0, sum_off_odd = 0, sum;
        h = (b - a) / (2 * N);
        for (int i = 1; i <= 2*N-1; i += 2){
            sum_off_odd += function(a + h * i, w);
            sum_off_even += function(a + h * (i + 1), w);
        }
        sum = function(a, w) + 4 * Math.abs(sum_off_odd) + 2 * Math.abs(sum_off_even) - function(b, w);
        return (h/3)*sum;
    }

    private static double[] integral (int w, double a, double b, double eps){
        double I, I1;
        int N = 2;
        double[] integral = new double[2];

        do {
            I = integral_N(N, a, b, w);
            I1 = integral_N(2 * N, a, b, w);
            N *= 2;
        } while ((Math.abs(I1 - I)/15 > eps && N < 5000000));

        integral[0] = I1;
        integral[1] = N;
        return integral;
    }

    public static void main (String[] args)
    {
        System.out.println("Выберете функцию:\n" +
                "1:sin(x)/x\n" +
                "2:2x+1\n" +
                "3:(1+x)^(1/x)\n" +
                "4:1/x\n" +
                "5:sqrt(1+x^2)\n");
        Scanner in = new Scanner(System.in);

        int w = in.nextInt();

        System.out.println("Теперь введите пределы интегрирования и допустимую погрешность");

        double a = in.nextDouble();
        double b = in.nextDouble();
        double eps = in.nextDouble();
        double O;
        int N;
        double I1;

        if (eps == 0) System.out.print("Допустимая погрешность не должна быть нулевой");
        else if ((w == 4) && ((a >= 0 && b < 0) || (a <= 0 && b > 0))) System.out.println("Интеграл расходится или его невозможно рассчитать");
            else {
            if (b > a) {
                I1 = integral(w, a, b, eps)[0];
                N = (int) integral(w, a, b, eps)[1];
                O = Math.abs(I1 - integral_N(2 * N, a, b, w)) / 15;
            } else {
                I1 = integral(w, b, a, eps)[0];
                N = (int) integral(w, b, a, eps)[1];
                O = Math.abs(I1 - integral_N(2 * N, b, a, w)) / 15;
            }

            if (Double.isInfinite(I1) || Double.isNaN(I1)) System.out.println("Интеграл расходится или его невозможно рассчитать");
            else
            {
                System.out.println("Значение интеграла: " + I1 + "\nПогрешность по оценке Рунге: " + O + "\nЧисло разбиений:" + N);
                if (O > eps) System.out.println("Погрешность по оценке Рунге вышла больше, чем заданная погрешность. " +
                        "\nНе вышло добитьсядостаточного количества разбиений.");
            }

            in.close();
        }
    }
}
