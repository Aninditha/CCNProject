public class Entity0 extends Entity
{    
    // Perform any necessary initialization in the constructor
    public Entity0()
    {
//    	System.out.println("Hello, this is Entity0!!");
        distanceTable[0][0] = 0;
        distanceTable[0][1] = 999;
        distanceTable[0][2] = 999;
        distanceTable[0][3] = 999;
        distanceTable[1][0] = 999;
    	distanceTable[1][1] = 1;
        distanceTable[1][2] = 999;
        distanceTable[1][3] = 999;
        distanceTable[2][0] = 999;
        distanceTable[2][1] = 999;
        distanceTable[2][2] = 3;
        distanceTable[2][3] = 999;
        distanceTable[3][0] = 999;
        distanceTable[3][1] = 999;
        distanceTable[3][2] = 999;
        distanceTable[3][3] = 7;
        int source = 0;
        int dest;
        int i;
        int[] mincost = {0,1,3,7};
        
        for(i=1;i<NetworkSimulator.NUMENTITIES;i++){
        	dest = i;
        	Packet p = new Packet(source, dest, mincost);
        	NetworkSimulator.toLayer2(p);
        }
        printDT();
        
    }
    
    // Handle updates when a packet is received.  Students will need to call
    // NetworkSimulator.toLayer2() with new packets based upon what they
    // send to update.  Be careful to construct the source and destination of
    // the packet correctly.  Read the warning in NetworkSimulator.java for more
    // details.
    public void update(Packet p)
    {  
    	int[] mincost = new int[4];
    	boolean Costupdate=false;
    	boolean disupdate=false;
//    	Finding out the minimum value in each row
    	int temp;
    	for(int i=0; i<NetworkSimulator.NUMENTITIES;i++){
    		 temp = distanceTable[i][i];
    		for(int j=0; j<NetworkSimulator.NUMENTITIES;j++){
    			 if(temp>distanceTable[i][j]) temp=distanceTable[i][j];
    			 
    		}
    	     mincost[i] = temp;	
    	}
//    	Distance Vector Algorithm
    	for(int i=0;i<NetworkSimulator.NUMENTITIES;i++){
    	
    		if((distanceTable[p.getSource()][p.getSource()]+p.getMincost(i))<distanceTable[i][p.getSource()]){
    		
    			distanceTable[i][p.getSource()]=distanceTable[p.getSource()][p.getSource()]+p.getMincost(i);
    			disupdate=true;
    			
    	        if(mincost[i]>distanceTable[i][p.getSource()]){
    	    	mincost[i]=	distanceTable[i][p.getSource()];
    	    	Costupdate=true;
    	        } 
    	    }
    	}
    	if(disupdate)System.out.println("Node0's distance table has been updated with changes");
//    	If minimum cost is updated then a new packet will be sent to directly connected neighbors
    	if(Costupdate){
    		
    		int source=0;
    		int dest;
    		for(int i=1;i<NetworkSimulator.NUMENTITIES;i++){
    			dest = i;
            	Packet packet = new Packet(source, dest, mincost);
            	NetworkSimulator.toLayer2(packet);
            	System.out.println("Source "+source+ " has a packet that is being transmitted to destination "+dest);
    		}
    	}
    }
    
    public void linkCostChangeHandler(int whichLink, int newCost)
    {
    }
    
    public void printDT()
    {
        System.out.println();
        System.out.println("           via");
        System.out.println(" D0 |   1   2   3");
        System.out.println("----+------------");
        for (int i = 1; i < NetworkSimulator.NUMENTITIES; i++)
        {
            System.out.print("   " + i + "|");
            for (int j = 1; j < NetworkSimulator.NUMENTITIES; j++)
            {
                if (distanceTable[i][j] < 10)
                {    
                    System.out.print("   ");
                }
                else if (distanceTable[i][j] < 100)
                {
                    System.out.print("  ");
                }
                else 
                {
                    System.out.print(" ");
                }
                
                System.out.print(distanceTable[i][j]);
            }
            System.out.println();
        }
    }
}