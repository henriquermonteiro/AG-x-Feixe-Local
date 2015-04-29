/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utfpr.sisint.ag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import utfpr.sisint.problema.ProblemConstants;
import utfpr.sisint.problema.Solucao;

/**
 *
 * @author henrique
 */
public class AlgGenetico {

    public static Integer TOP_SIZE = 10;

    public AlgGenetico() {
    }

    public String doSearch() {
        ArrayList<Solucao> topSolutions = new ArrayList<>();
        ArrayList<Solucao> pool = new ArrayList<>();

        if (ProblemConstants.SCENARIO_ID == -1) {
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
        } else {
            throw new UnsupportedOperationException();
        }

        System.out.print("Initial pool.");
        for (int h = 0; h < TOP_SIZE; h++) {
            System.out.print(topSolutions.get(h).getFitness() + ", ");
        }
        System.out.println("");

        int k = 1;

        while (k < 1000) {
            Double fitnessTotal = 0.0;

            fitnessTotal = topSolutions.stream().map((s) -> s.getFitness()).reduce(fitnessTotal, (accumulator, _item) -> accumulator + _item);

            for (int j = 0; j < ProblemConstants.MATTING_COUNT; j++) {
                Double rand = Math.random();
                Double axFDP = 0.0;

                Solucao s1 = null;

                for (Solucao s : topSolutions) {
                    axFDP += s.getFitness() / fitnessTotal;

                    if (axFDP > rand) {
                        s1 = s;
                        break;
                    }
                }

                rand = Math.random();
                axFDP = 0.0;
                Solucao s2 = null;

                for (Solucao s : topSolutions) {
                    axFDP += s.getFitness() / fitnessTotal;

                    if (axFDP > rand) {
                        s2 = s;
                        break;
                    }
                }

                if (s1 == null || s2 == null) {
                    throw new NullPointerException("Noob! Errou a Função Densidade de Probabilidade");
                }

                rand = Math.random();
                if (rand < ProblemConstants.CROSSOVER_CHANCE) {
                    Double position = ((Double) ((Math.random() * (ProblemConstants.DESCRIPTION_LENGHT - 2)) + 1));
                    Solucao[] crossed = s1.crossover(s2, position.intValue());

                    pool.add(crossed[0]);
                    pool.add(crossed[1]);
                }
            }

            ArrayList<Solucao> mutation = new ArrayList<>();

            for (Solucao s : pool) {
                Double rand = Math.random();

                if (rand < ProblemConstants.MUTATION_CHANCE) {
                    Double position = Math.random() * (ProblemConstants.DESCRIPTION_LENGHT - 1);

                    mutation.add(s.mutatePosition(position.intValue()));
                }
            }

            pool.addAll(mutation);

            topSolutions.clear();

            Solucao[] solutions = new Solucao[0];
            solutions = pool.toArray(solutions);

            Arrays.sort(solutions, (Solucao t, Solucao t1) -> (int) ((t.getFitness() * 1000) - (t1.getFitness() * 1000)));

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

        return topSolutions.get(0).getFitness().toString();
    }

    public static void main(String... args) {
        AlgGenetico alg = new AlgGenetico();

        StringBuilder stringB = new StringBuilder();
        
        Boolean compare_mode = true;
        
        if (compare_mode) {
            for(int k = 0; k < 250; k++){
                stringB.append(k).append(";").append(alg.doSearch()).append(";\n");
            }
        }else{
            alg.doSearch();
        }
        
        System.out.println("-------------------------------");
        System.out.println(stringB);
    }
}
