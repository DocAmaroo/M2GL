/**
 * Duplication de code;
 * Attente du refactoring:
 * - Supprimer la duplication du calcul de la somme des produits (getHT);
 */

public class Duplicate {
    public static void main(String[] args) {
        double cookie = 1;
        double brownie = 1.2;
        double tva = 0.2;

        System.out.println(getTTC(tva, cookie, brownie));
    }

    /**
     * Calcul la somme des produits hors taxes (HT)
     * @param sweets
     * @return
     */
    public static double getHT(double ...sweets) {
        double total = 0;

        for (double s : sweets)
            total += s;

        return total;
    }

    /**
     * Calcul le prix total toute taxes comprises (TTC)
     * @param tva
     * @param sweets
     * @return
     */
    public static double getTTC(double tva, double... sweets) {
        double total = 0;

        for (double s : sweets)
            total += s;

        return total + (total * tva);
    }
}