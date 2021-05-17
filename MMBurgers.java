

public class MMBurgers implements MMBurgersInterface {
    public int time; // current time
    public  int counter;// total number of billing counter or queues
    public int maxgrill;// maximum grills/ burners of stove 
    public Minheap Qheap;
    public billQ b_first,b_end;
    public Burner burn,burn_end;
    public AVLTree tree;

     MMBurgers(){
        time = 0;
    }

    public boolean isEmpty(){
        if(Burner.vac==maxgrill){
            return true;
        }
        return false;

    } 
    
    public void setK(int k) throws IllegalNumberException{
        if(k<=0){
            throw new IllegalNumberException("number of counters should be positive");
        }
        else{
            counter=k;
            Qheap= new Minheap(counter);
            b_first= new billQ();
            tree= new AVLTree();
            b_end=b_first;
        }
    }   
    
    public void setM(int m) throws IllegalNumberException{
        if(m<=0){
            throw new IllegalNumberException("number of burgers should be positive");
        }
        else{
            maxgrill=m;
            burn= new Burner(m);
            burn_end=burn;
        }
    } 

    public void advanceTime(int t) throws IllegalNumberException{
        if(time>t){
            throw new IllegalNumberException(" time cannot travel back provide with higher time from advancetime ");
        }
        else if(time==t){
            return;
        }
        else{
            time =t;
            
            Qheap.checking(1, time, b_end); // sending bill to the chef =>adding patties to the queue
           // System.out.println("billQ.waiting = "+billQ.bwait);
            
            //if new Q is created then first object is not used so to make upthat issue.
            if(b_first.c==null){
                if(b_first.next!=null){
                    b_first=b_first.next;
                }
            }
            //moving to the end if new entries are added at the end
            while(b_end.next!=null){
                b_end=b_end.next;
            }
                     
            // set billQ to bill with burgers waiting
            //if no burgers are waiting set it to null value
            if(billQ.bwait==0){
                b_first= new billQ();
            }
           else{
                while(b_first.bu_wait==0){
                    if(b_first.next==null){break;}
                    b_first=b_first.next;
                }
            }
            burn=burn.rem_cooked(time,b_first,burn_end); // remove all cooked patty from the grill and also add patty to grill from queue
            while(burn_end.next!=null){
                burn_end=burn_end.next;
            }
            burn_end.add_to_cook(time , b_first,0); // add burger from queue to grill this add patty only if there is vacant space
            //System.out.println("billQ.waiting = "+billQ.bwait);

        }
    } 

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException{
        if(time>t){
            throw new IllegalNumberException(" provide with higher time from arrivecustomer");
        }
        else{
            advanceTime(t);
            customer a= new customer(id,t,numb);
            Qheap.add_customer(a);
            tree.insert(a);
        }
    } 

    public int customerState(int id, int t) throws IllegalNumberException{
        if(time>t){
            throw new IllegalNumberException(" provide with higher time from customerstate");
        }
        else{
            advanceTime(t);
            customer a = tree.search(id);
            if(a==null){
                return 0;
            }
            else {
                return a.status;
            }
        }
    } 

    public int griddleState(int t) throws IllegalNumberException{
        if(time>t){
            throw new IllegalNumberException("  provide with higher time from griddlestate");
        }
        else{
            advanceTime(t);
            return Burner.maxb-Burner.vac;
        }

    } 

    public int griddleWait(int t) throws IllegalNumberException{
        if(time>t){
            throw new IllegalNumberException(" time cannot travel back provide with higher time from griddle wait ");
        }
        else{
            advanceTime(t);
            return billQ.bwait;
        }
    } 

    public int customerWaitTime(int id) throws IllegalNumberException{
        
        customer a = tree.search(id);
        if(a==null){
            throw new IllegalNumberException("id cannot be found in database");
        } 
        else{
            return a.td-a.ta;
        }
    } 

	public float avgWaitTime(){
        float tot_time= (float) tree.rettime(tree.root);
        int tot_cust = tree.retcount(tree.root);
        float avgtime = tot_time/tot_cust;
        return  avgtime;
    } 

    
}
