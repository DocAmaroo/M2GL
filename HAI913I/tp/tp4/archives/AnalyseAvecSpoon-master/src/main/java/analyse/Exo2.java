package analyse;

import model.CoupleClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;


public class Exo2 {

    public static CoupleClass coupleFort(ArrayList<CoupleClass> cc, String classname){
        double bestScore = -1;
        int pos = -1;
        for(int i =0; i < cc.size(); i++){
            if(cc.get(i).getC1().equals(classname)){
                if(cc.get(i).getCpt() > bestScore ){
                    bestScore = cc.get(i).getCpt();
                    pos = i;
                }
            }
        }
        return cc.get(pos);
    }

    public static void clustering_hierrachique(String path) throws IOException {
        ArrayList<CoupleClass> cc = CoupleClass.extractFromFile(path);
        ArrayList<String> clusters = new ArrayList<String>();

        for (CoupleClass c : cc){
            if(!clusters.contains(c.getC1())) {
                clusters.add(c.getC1());
            }
        }

        while(clusters.size()> 1){
            String current = clusters.get(0);
            CoupleClass cfort = Exo2.coupleFort(cc, current);
            clusters.remove(current);
            clusters.remove(cfort.getC2());
            System.out.println(current + " rel fort avec ->" + cfort.getC2()+ "(new size ->" + clusters.size());

        }

    }


    public static void main(String[] args) {

        try {
            Exo2.clustering_hierrachique("graph.dot");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


