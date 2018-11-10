import java.util.*;

public class Generation
{
    public Generation(){
        int sizeOfPop=500;
        Controller c=new Controller("J:/AIsearchJava-master/AISearchtestcase.txt",sizeOfPop);
        List<Individual> pop=c.firstGen(c);
        System.out.println("made first gen");
        Individual best=null;
        HashMap<Individual,Integer> bests=new HashMap<Individual,Integer>();
        for(int i=0;i<500;i++){
            System.out.println("made gen "+i);
        getNextGeneration(c,pop);
        best=c.getBest(pop);
        bests.put(best,best.getCost(c.getArray()));
    }
    for(Individual b:bests.keySet()){
        //System.out.println("best is "+bests.get(b));
        if(bests.get(b)<bests.get(best)){
            best=b;}
            
    }
    System.out.println("cost"+best.getCost(c.getArray()));
    System.out.println("tour:"+best.getTour());
    }
    public void getNextGeneration(Controller c,List<Individual> pop){
        System.out.println("making parents");
        List<Individual> parents=c.getParents(pop);
        System.out.println("made parents, making children");
        pop=c.makeChildren(parents);
        System.out.println("made children, mutating");
        c.mutate(pop);
    }
}
