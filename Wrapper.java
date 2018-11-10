
public class Wrapper
{
    private Individual ind=null;
    private float start=0;
   private float end=0;
   private int cost=0;
    public Wrapper(Individual ind, int cost){
        this.ind=ind;
        this.cost=cost;
    }
    public Individual getInd(){
        return ind;
    
    }
    public void makeRange(float start,int total){
        System.out.println("cost:"+cost);
        float length=cost/((float)total);
        this.start=start;
        end=start+length;
        //System.out.println("start:"+start+" total:"+total+" length:"+length+" end:"+end);
    }
    public boolean isInRange(float x){
        return ((start<x)&&(end>x));
        
    }
    public String rangeText(){
        return("start:"+start+" end:"+end);
    }
    public float getEnd(){
        return end;
    }
}
