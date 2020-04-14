import java.util.*;

public class TheSystemHardware {

final static int hardDiskSize = 2097152 ; //2GB - > 2*1024*1024 = 2097152 Kb
final static int RAMsize = 196608 ; //192MB - >  192*1024 = 196608 Kb
public static int nemberOfProcess = 0 ;
public static Queue<Job> jobScheduller = new LinkedList<Job>();
public static Queue<Job> RAM = new LinkedList<Job>();
public static int CurrentHardDiskfullSize = 0 ; 
public static int CurrentRAMfullSize = 0 ;
public static int totalinRAM = 0 ;
public static int counter = 0;




public static boolean addjobIntoHardDisk(Job job) { //--------------------------Done checking 

	int toCheckSize = CurrentHardDiskfullSize + job.getEMR();
	if(toCheckSize <= hardDiskSize) {
		jobScheduller.add(job);
		++nemberOfProcess ;
		CurrentHardDiskfullSize += job.getEMR();
		System.out.println(job.getJObID()+"  Added to the HardDisk and to the jobScheduller ");

		return true ;
	}else {
		System.out.println("The hardDisk is full");
		return false ;
	}
} // end of addjobIntoHardDisk method




public static void fromJobSchedullerQueueToRAM() {
	while (!jobScheduller.isEmpty()) {
		Job jobInJobScheduller = jobScheduller.poll();
		Job smallestJob = jobInJobScheduller;
	    int minJobSize = jobInJobScheduller.getEMR();
	    Queue<Job> jobSchedullerCopy = new LinkedList<Job>();

	    while(!jobScheduller.isEmpty()) {
	    	jobInJobScheduller = jobScheduller.poll();
	    	if (minJobSize > jobInJobScheduller.getEMR()) {
	    	    jobSchedullerCopy.add(smallestJob);
	    		smallestJob = jobInJobScheduller ;
	    		minJobSize = jobInJobScheduller.getEMR();
	    	}else {
	    	    jobSchedullerCopy.add(jobInJobScheduller);
	    	}
	    }
	    if (isTheirSpaceInRAM(smallestJob) == false) {
		    jobSchedullerCopy.add(smallestJob);
	        copyQueue(jobSchedullerCopy,null, "HardDisk");
	        
		    System.out.println("Their's No space in the ram line 52"); //What should it do ?
	    	break ;
	    
	    }else { 
		      copyQueue(jobSchedullerCopy,null, "HardDisk");
	    	 addjobIntoRAM(smallestJob);
//	    	return ;
	    }
	}
    System.out.println("All jobs in Job scheduller have been send to the RAM"); //What should it do ?
    return;

//	}else {
//		return;
//
//	}


} // end of addjobIntoHardDisk method





public static void addjobIntoRAM(Job job) {
		RAM.add(job);
		System.out.println(job.getJObID()+"  Added to the RAM ");
		CurrentRAMfullSize += job.getEMR();
		if(job.getPCB() == null) {
			PCB pcb= operatingProject.createPCB(job.getJObID(), job.getECU(), job.getEMR());
			job.setPCB(pcb);
			System.out.println("PCB was created for :  "+job.getJObID());
		}

		operatingProject.addToReadyQueue(job.getPCB());
}// end of addjobIntoRAM method

public static boolean isTheirSpaceInRAM(Job job) {
	int toCheckSize = CurrentRAMfullSize + job.getEMR();
	if(toCheckSize <= RAMsize) {
		
		return true ;
	}else {
		return false;
	}


}


public static void RemoveFromQueue(int PID , String queueName) {
	if (queueName == "RAM") {
		Queue<Job> RAMCopy = new LinkedList<Job>();

		while(!RAM.isEmpty()) {
			if(PID == RAM.peek().getJObID()) {
				Job remove = RAM.poll();
				CurrentRAMfullSize -= remove.getEMR();
				System.out.println(PID +"  Removed from the RAM ");
				if(!jobScheduller.isEmpty()) {
					fromJobSchedullerQueueToRAM();
			}}
			else {
			Job job = RAM.poll();
			RAMCopy.add(job); }
		}
		copyQueue(RAMCopy, null ,"RAM");
		
	}else if (queueName == "readyQueue") {
		
		Queue<PCB> readyQueueCopy = new LinkedList<PCB>();

		while(! operatingProject.readyQueue.isEmpty()) {
			if(PID == operatingProject.readyQueue.peek().getPID()) {
				operatingProject.readyQueue.poll();
				System.out.println(PID +"  Removed from the ready queue ");
				}
			else {
			PCB process = operatingProject.readyQueue.poll();
			readyQueueCopy.add(process); }
		}
		copyQueue(null,readyQueueCopy ,"readyQueue");
		
	}

	
	
}// end of addjobIntoRAM method


public static void copyQueue(Queue<Job> queue2 ,Queue<PCB> queue3 ,String TypeOfMemory) {
	if (TypeOfMemory.equals("HardDisk")) {

		while(! queue2.isEmpty()) {
	    	jobScheduller.add(queue2.poll());
	    }
		return ;
	} else if (TypeOfMemory.equals("RAM")) {
		while(! queue2.isEmpty()) {
	    	RAM.add(queue2.poll());
	    }
		return ;
	} else if (TypeOfMemory.equals("readyQueue")) {
		while(! queue3.isEmpty()) {
			operatingProject.addToReadyQueue(queue3.poll());
	    }
		return ;

	}
    

}// end of copyQueu method











}
