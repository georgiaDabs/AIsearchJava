import java.util.*;
import java.io.*;
public class Controller
{
    private int[][] array;
    private int sizeOfPop;
   public int[][] getArray(){
       return array;
    }
    public Controller(String link,int sizeOfPop){
        this.sizeOfPop=sizeOfPop;
        try{
        File file=new File(link);
        Scanner sc=new Scanner(file);
        sc.nextLine();
        sc.next();
        sc.next();
        int size=stringToInt(sc.next());
        array=new int[size][size];
        boolean running =true;
        List<Integer> numbers=new ArrayList<Integer>();
        while(running){
            if(sc.hasNext()){
                String next=sc.next();
                String[] values=next.split(",");
                for(String str: values){
                    //System.out.println(str);
                    str=str.replaceAll("[^0-9]","");
                    numbers.add(Integer.parseInt(str));
                }
            }else{running=false;}
        }
        System.out.println(numbers);
        System.out.println("size:"+size);
        for (int i=0;i<size;i++){
            for(int j=i;j<size;j++){
                if(i!=j){
                    array[i][j]=numbers.get(0);
                    array[j][i]=array[i][j];
                    numbers.remove(0);
                }
            }
        }
        for(int[] row:array){
            for(int i: row){
                System.out.print(","+i);
            }
            System.out.println(" ");
        }
    }catch(IOException e){
        System.out.println("file not found");
    }
    }
    public int stringToInt(String str){
        str=str.replaceAll(",$","");
        int stringInt=Integer.parseInt(str);
        return stringInt;
    }
    public Individual makeRandom(){
        Individual ind=new Individual(array[0].length);
        ind.makeRandom(array);
        return ind;
    }
    
    public List<Individual> getParents(List<Individual> pop){
        int total=0;
        ArrayList<Wrapper> population=new ArrayList<Wrapper>();
        System.out.println("wrapping..");
        for(int i=0;i<pop.size();i++){
            //Individual ind=makeRandom();
            total+=pop.get(i).getCost(array);
            Wrapper p=new Wrapper(pop.get(i),pop.get(i).getCost(array));
            population.add(p);
        }
        System.out.println("finished making pop");
        float start=0;
        for(int i=0;i<(sizeOfPop-1);i++){
            population.get(i).makeRange(start,total);
            start=population.get(i).getEnd();
            //System.out.println("after making range "+population.get(i).rangeText());
        }
        population.get(population.size()-1).makeRange(start,total);
        System.out.println("finished making ranges");
        Random rand=new Random();
        float first=rand.nextFloat();
        Individual parent1=null;
        for(Wrapper w:population){
           // System.out.println("checking pop");
           // System.out.println("float is"+first);
           // System.out.println(w.rangeText());
            if(w.isInRange(first)){
                System.out.println("parent 1 found");
                parent1=w.getInd();
            }
        }
        System.out.println("finished parent1 method");
        Individual parent2=parent1;
        while(parent2==parent1){
            float second=rand.nextFloat();
            for(Wrapper w:population){
                if(w.isInRange(second)){
                   System.out.println("parent 2 found");
                    parent2=w.getInd();
                }
            }
            //System.out.println("parent 1 is "+parent1+" parent2 is "+parent2);
        }
       // System.out.println("finished parent 2");
        List<Individual> parents=new ArrayList<Individual>();
        parents.add(parent1);
        parents.add(parent2);
        return parents;
    }
    public List<Individual> makeChildren(List<Individual> parents){
       // System.out.println("parent1:"+parents.get(0).getTour());
       // System.out.println("parent2:"+parents.get(1).getTour());
        Individual parent1=parents.get(0);
        Individual parent2=parents.get(1);
        ArrayList<Individual> population=new ArrayList<Individual>();
        Random rand=new Random();
        int split=rand.nextInt(sizeOfPop);
        for(int i=0;i<split;i++){
            Individual ind=new Individual(array[0].length);
            ind.makeFromParent(parent1.getChromosomes(),parent2);
           // System.out.println("tour of chile"+i+":"+ind.getTour());
            population.add(ind);
        }
        for(int i=split;i<sizeOfPop;i++){
            Individual ind=new Individual(array[0].length);
            ind.makeFromParent(parent2.getChromosomes(),parent1);
            //System.out.println("tour of child"+i+":"+ind.getTour());
            population.add(ind);
        }
        return population;
    }
    public void mutate(List<Individual> pop){
        //System.out.println("pop size:"+pop.size());
        int amount=(int)(((float)pop.size()/100)*20.0);
        //System.out.println(amount);
        Random rand=new Random();
        for(int i=0;i<amount;i++){
            int r=rand.nextInt(pop.size());
            pop.get(r).mutate();
            //System.out.println("going to mutate "+r);
        }
        /*for(Individual ind: pop){
            System.out.println(ind.getTour());
        }*/
    }
    public Individual getBest(List<Individual> pop){
        Individual ind=pop.get(0);
        //System.out.println("getting best method");
        for(Individual i:pop){
            if(i.getCost(array)<ind.getCost(array)){
                ind=i;
            }
        }
        return ind.makeCopy();
    }
    public  List<Individual> firstGen(Controller c){
        List<Individual> pop=new ArrayList<Individual>();
        //int total=0;
        //ArrayList<Wrapper> population=new ArrayList<Wrapper>();
        for(int i=0;i<sizeOfPop;i++){
            Individual ind=makeRandom();
            //total+=ind.getCost();
            //Wrapper p=new Wrapper(ind);
            pop.add(ind);
        }
        //System.out.println("finished making pop");
        
        return pop;
    }
}
