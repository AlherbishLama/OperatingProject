import java.io.*;
import java.util.*;
public class operatingProject {
	
	public static Queue<Job> jobs = new LinkedList<Job>();
	public static Queue<Job> jobsCopy = new LinkedList<Job>();
	public static Queue<PCB> readyQueue = new LinkedList<PCB>();
	public static Queue<PCB> IOQueue = new LinkedList<PCB>();
	public static Queue<TerminateProcess> ListOfAllJobsThatWasTerminate = new LinkedList<TerminateProcess>();
	public static int totalNumOfJobsProcessed = 0 ;
	public static int counter = 0 ;


	
	public static void generateJobsFile() {
		
		File jobFile = new File("Jobs.txt");
		FileWriter fr = null ;
		int counter = 1 ;
		try {
			fr = new FileWriter(jobFile);
			for(int i = 0 ; i<100 ; i++) {
				fr.write("JID: " + counter +"\n");
				fr.write("ECU: " + generateECU()+"\n");
				fr.write("EMR: " + generateEMR()+"\n");
				counter++ ;
			} // end of the loop
			
			fr.close();

		} catch (Exception e){
			System.out.println("Error in line 32"+e.getMessage());
			
		}
		System.out.println("The Jobs file have been generated");
		ReadJobsFromFile();
		return;
		
	}//end of generateJobsFile method
	
	
	
public static void generateResultFile() {
		
		File jobFile = new File("Results.txt");
		FileWriter fr = null ;
		try {
			fr = new FileWriter(jobFile);
			
				fr.write("The Total number of jobs processed : "+totalNumOfJobsProcessed+"\n");
				fr.write(jobSizeCalculation() + "\n");
				fr.write("Number of jobs that completed normally is : " + CPU.numOfJobsCompletedNormally +"\n"); 
				fr.write("Number of jobs that completed abnormally is : "+CPU.numOfJobsCompletedAbnormally + "\n");
				fr.write("CPU utilization is : " + CPU.utilizationForCPU() + "% \n" );
				fr.write("Number of jobs serviced by I/O device. is :" + IODevice.numOfProcessServed +"\n");
				fr.write("I/O device utilization is :" +IODevice.utilizationForIODevice()+ "% \n" );
				fr.write("Average waiting time is :"+ (IODevice.averageWaitingTime()+CPU.averageWaitingTime()) +"\n");	
				fr.write("***************************************************************************************************** \n" );
				fr.write("                       List of all process that was terminate \n" );	
				while(! ListOfAllJobsThatWasTerminate.isEmpty()) {
					TerminateProcess t = ListOfAllJobsThatWasTerminate.poll();
					fr.write("PID: "+ t.getPID() +
						    " ,  CUT: "+ t.getCUT() + "  ,  IRT: "+ t.getIRT() + "  ,  WT: "+ t.getWT() +
						    "  ,  termination status: "	+ t.getNormalORupNormal() +"\n");	
				}



			
			fr.close();

		} catch (Exception e){
			System.out.println(e.getMessage());
			
		}
		System.out.println("The Results file have been generated");
		
	}//end of generateResultFile method

	
	
	public static void ReadJobsFromFile() {
		
		try {
			File jobFile = new File ("Jobs.txt") ;
			FileReader fr = new FileReader(jobFile);		
			BufferedReader reader = new BufferedReader(fr);			
			for(int i=0 ; i<100 ; i++) {
				String JID_line = reader.readLine();
				String ECU_line = reader.readLine();
				String EMR_line = reader.readLine();
				String id = JID_line.substring(5);
				String ECU = ECU_line.substring(5);
				String EMR = EMR_line.substring(5);
				Job job = new Job(Integer.valueOf(id) ,Integer.valueOf(ECU) , Integer.valueOf(EMR));
				jobs.add(job);
				jobsCopy.add(job);
			}
			fr.close();

		} catch (Exception e){
			System.out.println("Error in 86 "+e.getMessage());
			
		}
		System.out.println("All Jobs has been read from the file ");
		FromJobQeueToHardDisk();
//		FromReadyQueueToCPU();
		if(CPU.isCPUAvailable() == true && IODevice.isIODeviceAvailable() == true && readyQueue.isEmpty() == true && IOQueue.isEmpty()==true) {
			generateResultFile();
		}
		
	}//end of ReadJobsFromFile method
	
	public static int generateECU() {
	int min = 16 ;
	int max = 512 ;
	int randomNum ;
	Random r = new Random();

		 randomNum = r.nextInt((max-min)+1)+min;
	
		return randomNum ;
	}
	
	public static int generateEMR() {
	int min = 16 ;
	int max = 256 ;
	int randomNum ;
	Random r = new Random();

		 randomNum = r.nextInt((max-min)+1)+min;
	
		
		return randomNum ;
	}
	
	public static void FromJobQeueToHardDisk() {
		if(!jobs.isEmpty()) {
			boolean isTheirSpace = TheSystemHardware.addjobIntoHardDisk(jobs.peek()) ;
			while(isTheirSpace == true && !jobs.isEmpty() ) {
					 jobs.poll();
					if(jobs.peek() == null) {
						jobs.poll();
						return;
					}
					isTheirSpace = TheSystemHardware.addjobIntoHardDisk(jobs.peek()) ;
					if(jobs.peek().getJObID() == 100 && isTheirSpace == true) {
						System.out.println(jobs.isEmpty());
						 jobs.poll();
					}
			}
			
//			System.out.println("out of FromJobQeueToHardDisk");
			if(isTheirSpace == false || jobs.isEmpty()) {
				while(!TheSystemHardware.jobScheduller.isEmpty()) {
					TheSystemHardware.fromJobSchedullerQueueToRAM();

				}
				FromReadyQueueToCPU();
									return;
			
			}
			return ;
				
		}
		System.out.println("No jobs Added to the hardDisk");

		return ;

		
	}
	
	
	public static void addToReadyQueue(PCB process) {
		if(IODevice.isIODeviceAvailable() == true && IOQueue.isEmpty() == true) {
			readyQueue.add(process);
			process.setState("Ready");
			System.out.println(process.getPID()+"  The process was added to ready queue");
			return ;
//			FromReadyQueueToCPU();
		}else {
			readyQueue.add(process);
			process.setState("Ready");
			System.out.println(process.getPID()+"  The process was added to ready queue");

			return;	
		}
		
	}//end of addToReadyQueue method
	
	
	public static void FromReadyQueueToCPU() {
		int witingTimeInReadyQueue = 0;
		PCB inCPU = readyQueue.poll() ;
		inCPU.setwaitingTimeInReadyQueue(inCPU.getwaitingTimeInReadyQueue());
		CPU.Running(inCPU);
		witingTimeInReadyQueue += CPU.currentCUTForTheProcess ;
		
		while(!readyQueue.isEmpty()) {
				if(CPU.isCPUAvailable() == true) {
					inCPU = readyQueue.poll() ;
					inCPU.setwaitingTimeInReadyQueue(witingTimeInReadyQueue);
					CPU.Running(inCPU);
					witingTimeInReadyQueue += CPU.currentCUTForTheProcess ;

				}
		}
		if(readyQueue.isEmpty() && TheSystemHardware.jobScheduller.isEmpty() && CPU.isCPUAvailable() && !IOQueue.isEmpty() && IODevice.isIODeviceAvailable()) {
			FromIOQueueToIODevice();
		}else if(readyQueue.isEmpty() && TheSystemHardware.jobScheduller.isEmpty() && CPU.isCPUAvailable() && IOQueue.isEmpty()&& IODevice.isIODeviceAvailable()){
			System.out.println("Donnnnnnnnne");
			return ;

		}
		return ;
		
	}//end of FromReadyQueueToCPU method
//	
//	
//	
//	
	public static void addToIOQueue(PCB pcb) {
		if(pcb == null) {
			return ;
		}
		IOQueue.add(pcb);
		pcb.setState("Wating");
		System.out.println(pcb.getPID()+"  The process is watinig in the IO queue ");

		return ;
				
	}//end of addToIOQueue method
	
	public static void FromIOQueueToIODevice() {
	 int WT = 0 ;
		PCB inIOQueue = IOQueue.poll() ;
		inIOQueue.setWT(inIOQueue.getWT());
		IODevice.Running(inIOQueue);
		WT += IODevice.currentIRTForTheProcess ;

		while(!IOQueue.isEmpty()) {
				if(IODevice.isIODeviceAvailable() == true) {
					inIOQueue = IOQueue.poll() ;
					inIOQueue.setWT(inIOQueue.getWT() + WT);
					IODevice.Running(inIOQueue);
					WT += IODevice.currentIRTForTheProcess ;

				}
		}
		FromReadyQueueToCPU();

	}
		
	public static String jobSizeCalculation() {
		int totalSize = 0 ;
		int numberOfJobs = 0;
		int jobSize = 0;
		int minimum = 0 ;
		int max = 0;
		double average = 0 ;
		
		if(!jobsCopy.isEmpty()) {
			 jobSize = jobsCopy.poll().getEMR();
			 minimum = jobSize ;
			 totalSize += jobSize ;
			 max = jobSize;
			 numberOfJobs ++;
		}
		
		
		while(!jobsCopy.isEmpty()) {
			jobSize = jobsCopy.poll().getEMR();
			totalSize += jobSize ;
			numberOfJobs ++ ;
			
			if(minimum > jobSize) {
				minimum = jobSize ;
			}else if (max < jobSize) {
				max = jobSize ;
			}
			
			
		}
		if(numberOfJobs > 0) {
			average = totalSize / numberOfJobs ;
			
		}
		
		return "The Average job size is : " + average +
				"\n The Minimum job size is : " + minimum +
				"\n The Maximum job size is : " + max ;
	}//end of jobSizeCalculation method
	
	public static PCB createPCB(int jobID , int ECU , int EMR) {
		 PCB pcb = new PCB(jobID , "New" , ECU , EMR); //PID , State , ECU , EMR
		 return pcb ;
	}//end of getPCB method

}	

