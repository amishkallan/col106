class counter {
    int n_cust;
    int c_id;
    counter_q first,last;
     
    public counter( int id){
        n_cust=0;
        c_id=id;
    }
}
// queue of each counter
class counter_q{
    customer c;
    counter_q front , back;
    int  etl; //expected time of leaving the q;
    counter_q(customer a){
        c=a;
    }

}

class Minheap{
    public static int Q_no;
    counter [] c_heap;
    billQ b_first,b_last;
    Minheap(int k){
        Q_no= k;
        c_heap= new counter[k];
        for ( int i =0; i < k ;i++ ){
            c_heap[i]= new counter(i+1);
        }
    }
    public void print(){
        for( int i =0;i<Q_no;i++){
            System.out.println("counter id = "+c_heap[i].c_id+"number of c = "+c_heap[i].n_cust );
            
        }
    }
    public void add_customer( customer a){
        // update heap => add 1 to root and percolate down
        // before percolating add to stack and calculate expected time of billing
        counter_q new_c = new counter_q(a);
        new_c.c.q_id=c_heap[0].c_id;
        new_c.c.status=c_heap[0].c_id;
        c_heap[0].n_cust+=1;
        //setting up of stack;
        if(c_heap[0].n_cust==1){
            // he is first in the queue
            c_heap[0].first = new_c;
            c_heap[0].last = new_c;
            new_c.front=null;
            new_c.etl = new_c.c.ta+c_heap[0].c_id;
        }
        else{
            new_c.front = c_heap[0].last;
            new_c.etl = c_heap[0].last.etl + c_heap[0].c_id;
            c_heap[0].last.back=new_c;
            c_heap[0].last=new_c;
        }
        percolate_down(1);

    }
    public void percolate_down(int i){
        int swap_i=-1;
        if(2*i >= Q_no)
        {
            // no child 
            return;
        }
        else if ( 2*i == Q_no){
            // only one child
            if(c_heap[i-1].n_cust > c_heap[2*i-1].n_cust ){
                swap_i = 2*i;
            }
            else if(c_heap[i-1].n_cust == c_heap[2*i-1].n_cust ){
                if(c_heap[i-1].c_id > c_heap[2*i-1].c_id ){
                    swap_i=2*i;
                }    
            }
        }
        else{
            // if both child is present
            //choose among both children.
            
            if(c_heap[2*i-1].n_cust < c_heap[(2*i+1)-1].n_cust)
            {
                swap_i = 2*i;
            }
            else if(c_heap[2*i-1].n_cust > c_heap[(2*i+1)-1].n_cust){
                swap_i = 2*i+1;
            }
            else{
                //if both child are equal
                // then check id (however id wont have duplicates)
                if(c_heap[2*i-1].c_id < c_heap[(2*i+1)-1].c_id){
                    swap_i = 2*i;
                }
                else{
                    swap_i =  ( 2*i+1) ;
                }
            }
        }
        if(swap_i!= -1){
            if(c_heap[swap_i-1].n_cust <c_heap[i-1].n_cust){
                counter temp;
                temp = c_heap[i-1];
                c_heap[i-1]=c_heap[swap_i-1];
                c_heap[swap_i-1]=temp;
                percolate_down(swap_i);
            }
            else if(c_heap[swap_i-1].n_cust == c_heap[i-1].n_cust){
                if(c_heap[swap_i-1].c_id < c_heap[i-1].c_id){
                    counter temp;
                    temp = c_heap[i-1];
                    c_heap[i-1]=c_heap[swap_i-1];
                    c_heap[swap_i-1]=temp;
                    percolate_down(swap_i); 
                }
            }
        }
        else{
            return;
        }
    }
    // event for checking at time t in all Qs for removing cust after billing
    public void checking(int i, int time, billQ b){
        if(i>Q_no){
            bulid_heap();
            return;
        }
        else{ 
            update_heap(time,c_heap[i-1],b);
            checking(i+1,time,b);
        }   
    }
    public void update_heap(int time, counter h, billQ b){
        if(h.first==null){
            return;
        }
        else if(h.first.etl <=time){

           b.add_bill( h.first.c,h.first.etl ,h.c_id);

            if(h.first.back!=null){
                
                h.first = h.first.back;
            }
            else{
                h.first=null;
            }
            h.n_cust-=1;
            update_heap(time, h,b);
        }
    }
    
    public void bulid_heap(){
        for (int i=Q_no/2;i>0;i--){
            percolate_down(i);
        }
    }
}
class Minheap_test{
    public static void main(String[] args){
        Minheap m= new Minheap(5);
        //m.print();
        customer a= new customer(1,0,0);
        customer b= new customer(2,0,0);
        customer c= new customer(3,0,0);
        customer d= new customer(4,0,0);
        customer e= new customer(5,0,1);
        customer f= new customer(6,0,1);
        customer g= new customer(7,0,1);
        customer h= new customer(8,0,1);
        customer i= new customer(9,0,1);
        m.add_customer(a);
        m.add_customer(b);
        m.add_customer(c);
        m.add_customer(d);
        m.add_customer(e);
        m.add_customer(f);
        m.add_customer(g);
        m.add_customer(h);
        m.add_customer(i);
        m.print();
        billQ bfirst = new billQ();
        billQ blast=bfirst;

        m.checking(1,10, blast);
        if(bfirst.c==null){
            if(bfirst.next!=null){
                bfirst=bfirst.next;
            }
        }
        System.out.println("burger waiting to get cooked = "+ billQ.bwait+"\nbillq");
        while(bfirst!=null){
            System.out.println("cutomer in wait" + bfirst.c.id+ "came from = "+ bfirst.q_id);

            bfirst=bfirst.next;
        }
        
        //System.out.println("id of first in 2nd q" + m.c_heap[1].first.c.id);

        m.print();
        

    }
}
