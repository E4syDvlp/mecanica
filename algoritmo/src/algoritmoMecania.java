import java.util.function.Function;

    public class algoritmoMecania {

        public static double posicion(double t) {
            return 0.1 * Math.pow(t, 3) - 0.3 * Math.pow(t, 2) + 0.2 * Math.sin(4 * t);
        }

        public static double velocidad(double t) {
            return 0.3 * Math.pow(t, 2) - 0.6 * t + 0.8 * Math.cos(4 * t);
        }

        public static double aceleración(double t) {
            return 0.6 * t - 0.6 - 3.2 * Math.sin(4 * t);
        }

        public static double newtonRaphson(Function<Double, Double> func, Function<Double, Double> deriv, double n1, double tolerancia, int maxIter) {
            double x = n1;
            for (int i = 0; i < maxIter; i++) {
                double f = func.apply(x);
                double df = deriv.apply(x);
                if (Math.abs(f) < tolerancia) {
                    return x;
                }
                if (df == 0) {
                    throw new ArithmeticException("Derivada cero. Newton-Raphson falló.");
                }
                x -= f / df;
            }
            throw new ArithmeticException("No se encontró raíz dentro del número máximo de iteraciones.");
        }

        public static void main(String[] args) {

            Function<Double, Double> posicionFuncion = algoritmoMecania::posicion;
            Function<Double, Double> velocidadFuncion = algoritmoMecania::velocidad;
            Function<Double, Double> aceleracionFuncion = algoritmoMecania::aceleración;

            System.out.println("Raíces (x(t) = 0):");
            try {
                double root1 = newtonRaphson(posicionFuncion, t -> velocidad(t), 0.5, 1e-6, 100);
                System.out.println("t = " + root1);
            } catch (Exception e) {
                System.out.println("Error buscando raíz: " + e.getMessage());
            }

            System.out.println("\nRaíces (v(t) = 0):");
            try {
                double parada1 = newtonRaphson(velocidadFuncion, t -> aceleración(t), 1.0, 1e-6, 100);
                System.out.println("t = " + parada1);
                double parada2 = newtonRaphson(velocidadFuncion, t -> aceleración(t), 3.0, 1e-6, 100);
                System.out.println("t = " + parada2);
            } catch (Exception e) {
                System.out.println("Error buscando raíz: " + e.getMessage());
            }

            System.out.println("\nCaída de la hormiga:");
            try {
                double tiempoCaida = newtonRaphson(velocidadFuncion, t -> aceleración(t), 3.25, 1e-6, 100);
                double posicionCaida = posicion(tiempoCaida);
                System.out.println("Tiempo de caída: t = " + tiempoCaida);
                System.out.println("Posición al caer: x = " + posicionCaida);
            } catch (Exception e) {
                System.out.println("Error buscando raíz: " + e.getMessage());
            }

            System.out.println("\nRaíces (a(t) = 0):");
            try {
                double act1 = newtonRaphson(aceleracionFuncion, t -> 0.6 - 12.8 * Math.cos(4 * t), 1.0, 1e-6, 100);
                double act2 = newtonRaphson(aceleracionFuncion, t -> 0.6 - 12.8 * Math.cos( 4 * t), 2.5, 1e-6, 100);
                System.out.println("t = " + act1);
                System.out.println("t = " + act2);
            } catch (Exception e) {
                System.out.println("Error buscando raíz: " + e.getMessage());
            }
        }
}
