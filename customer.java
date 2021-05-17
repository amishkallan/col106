 class customer {
    public int id;
    public int b_no;
    public int b_on_grill;
    public int b_in_hand;
    public int ta;  // time of arrival
    public int td;  // time of departure
    public int q_id;
    public int status;
    customer(int cid, int time, int noofb){
        id=cid;
        ta=time;
        b_no=noofb;
        td=-1;
        b_in_hand=0;
        b_on_grill=0;
    }
    public void recieveburg(int time){
        b_in_hand++;
        if(b_in_hand==b_no){
            td=time;
            status=Minheap.Q_no+2;
        }

    }
    
}
