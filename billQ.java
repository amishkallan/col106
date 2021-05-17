
class billQ{
    public static int bwait;
    public static int maxc;
    public customer c;
    public int q_id;   //id of the billing desk from which it came
    public int bu_wait;// number of burger in the bill waiting yet
    public int artime; // arrival time onto kitchen/chef
    public billQ prev,next;
    public billQ(customer ca, int Q, int time){
        c=ca;
        q_id=Q;
        artime=time;
        bu_wait=c.b_no;
    }

    public billQ(){
        // constructor for the first call
        // when Q of bill is declared there is no bills yet
        bwait=0;
       

    }


   public void add_bill(customer ca, int time , int cid){
        if(this.next!= null){
            if(time < next.artime){
                billQ temp= new billQ(ca,cid,time);
                bwait+=ca.b_no;
                //System.out.println("bwait ="+bwait);
                ca.status=Minheap.Q_no+1;
                temp.next=next;
                next=temp;
            }
            else if(time == next.artime){
                if(next.c.q_id<ca.q_id){
                    billQ temp= new billQ(ca,cid,time);
                    //System.out.println("\n\n bwait ="+bwait);
                    ca.status=Minheap.Q_no+1;
                    //System.out.println(" bwait ="+bwait);
                    bwait+=ca.b_no;
                    temp.next=next;
                    next=temp;
                }
                else{
                    next.add_bill(ca, time, cid);
                }
            }
            else{
                next.add_bill(ca, time, cid);
            }
        }
        //else=> this.next = null
        else{
            billQ temp= new billQ(ca,cid,time);
            bwait+=ca.b_no;
            ca.status=Minheap.Q_no+1;
            next=temp;
            }
        }
   
}
