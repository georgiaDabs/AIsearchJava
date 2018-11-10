import java.util.*;
public class Individual
{
    private HashMap<Integer,Integer> tour;
    private int size;
    private Random random=new Random();
    private List<Integer> visited;
    private int cost;
    public Individual(int size){
        visited=new ArrayList<Integer>();
        tour=new HashMap<Integer,Integer>();
        
        this.size=size;
        cost=0;
    }    
    public List<Integer> getVisited(){
        return visited;
    }
    public void makeRandom(int[][] array){
        
        int first;
        int start=random.nextInt(size);
        first=start;
        visited.add(start);
        int next=-1;
        while(visited.size()!=(size)){
            next=random.nextInt(size);
            if(!visited.contains(next)){
                tour.put(start,next);
                visited.add(next);
                //System.out.println(start+" to "+next+" is cost "+array[start][next]);
                cost+=array[start][next];
                start=next;
            }
        }
        tour.put(next,first);
    }
    public HashMap<Integer,Integer> getTour(){
        return tour;
        
    }
    public int getCost(int[][] array){
        cost=0;
        for(Integer key: tour.keySet()){
            cost+=array[key][tour.get(key)];
        }
        return cost;
      
    }
    public List<Integer> getChromosomes(){
       // System.out.println("making chromosomes");
       System.out.println(visited);
        Random rand=new Random();
        int start =rand.nextInt(visited.size()-1)+1;
        int size=rand.nextInt(visited.size()-1)+1;
       // System.out.println("start:"+start+" size:"+size);
        List<Integer> sublist=null;
        if(start+size>visited.size()){
            sublist=visited.subList(start,visited.size());
           // System.out.println("sublist1 from "+start+" to "+visited.size());
           // System.out.println("sublist2 from "+0+" to "+(start+size-visited.size()));
            sublist.addAll(visited.subList(0,start+size-visited.size()));
        }else{
        sublist=visited.subList(start,visited.size());
    }
    System.out.println(sublist);
        return sublist;
    }
    public Integer getNextInTour(int start){
        return tour.get(start);
    }
    public int getSize(){
        return this.size;
    }
    public Individual makeCopy(){
        Individual ind=new Individual(size);
        
        for(Integer key:tour.keySet()){
            ind.getTour().put(key,tour.get(key));
        }
        for(Integer city:visited){
            ind.getVisited().add(city);
        }
        return ind;
    }
    public void mutate(){
        Random rand=new Random();
        //System.out.println("size"+size);
        int n1=rand.nextInt(size-1);
        
        int n2=rand.nextInt(size-1);
        //System.out.println("going to swap "+n1+" with "+n2);
        int before1=visited.get(((n1-1)+size)%size);
        int before2=visited.get(((n2-1)+size)%size);
        int after1=visited.get((n1+1)%size);
        int after2=visited.get((n2+1)%size);
        
        
        int temp1=visited.get(n1);
        visited.set(n1,visited.get(n2));
        visited.set(n2,temp1);
        tour.put(before1,visited.get(n1));
        tour.put(visited.get(n1),after1);
        tour.put(before2,visited.get(n2));
        tour.put(visited.get(n2),after2);
    }
    public void makeFromParent(List<Integer> sublist,Individual parent2){
        
        int start=-1;
        for(Integer i:sublist){
            visited.add(i);
            if(start!=-1){
                tour.put(start,i);
            }
            start=i;
        }
        Integer end=-1;
        Integer next=parent2.getNextInTour(visited.get(visited.size()-1));
        while((visited.size())!=parent2.getSize()){
            //System.out.println("visited.conatins next:"+visited.contains(next));
            //System.out.println("next is"+next);
            //System.out.println(getTour()+" size:");
            //System.out.println(parent2.getTour()+" parent 2 size:"+parent2.getSize());
            if(visited.contains(next)){
                next=parent2.getNextInTour(next);
            }else /*if((visited.size()+1)==parent2.getSize()){
                System.out.println("running middle bloack");
                System.out.println("visited:"+visited);
                tour.put(next,visited.get(0));
            }else*/{
                tour.put(visited.get(visited.size()-1),next);
                visited.add(next);
                end=next;
                next=parent2.getNextInTour(next);
                
            }
        }
        tour.put(visited.get(visited.size()-1),visited.get(0));
    }
}
