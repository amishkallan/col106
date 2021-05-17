
 class Burner {
    public static int vac;
    public static int maxb;
    public customer c;
    public int extime;
    public Burner  prev, next;
    Burner(customer a , int time , int max_b){
        c=a;
        extime=time+10;
    }
    public Burner(int max_b){
        vac = max_b;
        maxb=max_b;
    }
    public Burner(){}
    
    public Burner rem_cooked(int time,billQ b,Burner g){
        if(c==null){
            if(next!=null){
                return next;
            }
            return this;
        }
        if(this.extime <= time){
            vac+=1;
            Burner last= this;
            while(last!=null){
                last= last.next;
            }

            g.add_to_cook(extime, b,1);
            
            //--- sent this burger to customer---
             c.recieveburg(extime+1);
            if(next!=null){
                if(g.next==null){return next.rem_cooked(time,b,g);}
                else{return next.rem_cooked(time, b, g.next);}
            }
            else{
                Burner temp= new Burner();
                this.next=temp;
                return temp;
            }
        }
        else{
            return this;
        }
    }
    public void add_to_cook(int time, billQ b,int flag){
        // flag = 1 if coming as soon as patty is removed ie coming from rem_cooked
        // flag = 0 if coming from MMburgers so arriving time is current time so use the time of arrival to kitchen
        if(b.c==null){
            // no element in the queue
            //System.out.println("no element in the queue");
            return ;
        }
        if(vac==0){
            // no vacancy
            //System.out.println("no vacancy");
            return ;

        }
        if(b.bu_wait!=0){

            vac--;
            b.bu_wait--;
            billQ.bwait--;
            Burner burn;
            if(flag==0){
               burn = new Burner(b.c,b.artime,maxb);
            }
            else{
                burn= new Burner(b.c,time,maxb);
            }
            //System.out.println("added burger exit time= "+burn.extime);
            this.next=burn;
            next.add_to_cook(time, b,flag);
        }
        //else => vacancy is present but no burgers waitingfor current bill
        else{
            if(b.next!=null){
                add_to_cook(time, b.next,flag);
            }
        }
    }
    
    
}

