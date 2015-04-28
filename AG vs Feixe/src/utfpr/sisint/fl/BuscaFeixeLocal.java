/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utfpr.sisint.fl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import utfpr.sisint.problema.ProblemConstants;

/**
 *
 * @author henrique
 */
public class BuscaFeixeLocal {

    public static Integer TOP_SIZE = 10;

    public BuscaFeixeLocal() {
    }

    public String doSearch() {
        ArrayList<Solucao> topSolutions = new ArrayList<>();
        ArrayList<Solucao> pool = new ArrayList<>();

        ArrayList<String> initialSolutions = new ArrayList<>();
        for (int u = 0; u < TOP_SIZE; u++) {
            String ax = "";

            while (true) {
                for (int y = 0; y < ProblemConstants.DESCRIPTION_LENGHT; y++) {
                    Integer randomInt = ((Double) (Math.ceil(Math.random() * 2) - 1)).intValue();

                    ax = ax.concat(randomInt.toString());
                }

                if (!initialSolutions.contains(ax)) {
                    initialSolutions.add(ax);
                    break;
                }
            }
            
            topSolutions.add(new Solucao(ax));
        }

        int k = 1;

        while (k < 100) {
            for (Solucao s : topSolutions) {
                pool.add(s);
                pool.addAll(Arrays.asList(s.getSucessores()));
            }

            topSolutions.clear();

            Solucao[] solutions = new Solucao[0];
            solutions = pool.toArray(solutions);

            Arrays.sort(solutions, new Comparator<Solucao>() {

                @Override
                public int compare(Solucao t, Solucao t1) {
                    return (int) ((t.getFitness() * 1000) - (t1.getFitness() * 1000));
                }
            });

            for (int j = 1; j <= TOP_SIZE; j++) {
                topSolutions.add(solutions[solutions.length - j]);
            }

            pool.clear();

            System.out.print("Top solutions on iteration " + k + ": ");
            for (int h = 0; h < TOP_SIZE; h++) {
                System.out.print(topSolutions.get(h).getFitness() + ", ");
            }
            System.out.println("");
            k++;
        }

        return "";
    }

    public static void main(String... args) {
        BuscaFeixeLocal busca = new BuscaFeixeLocal();
        System.out.println(busca.doSearch());
    }
}
