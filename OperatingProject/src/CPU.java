import java.util.LinkedList;
import java.util.Queue;

public class CPU {

	public static PCB RunningProcess ;
	public static int numOfJobsCompletedNormally = 0;
	public static int numOfJobsCompletedAbnormally = 0;
	public static int numOfProcessServed = 0;
	public static int notWorking = 0 ;
	public static double sum = 0;
	public static int totalCut = 0 ;
	public static Queue<PCB> ProcessServed = new LinkedList<PCB>();
	public static double randomNum ;
	public static double roundedRandomNumber ;
	public static int currentCUTForTheProcess ; 



	public static void Running(PCB process){
		int currentCPU = 0 ;
		System.out.println(process.getPID()+"  The process is running in the CPU ");
		PCB toSend = process ;
		RunningProcess = process ;
		RunningProcess.setState("Running");
		int CUT = RunningProcess.getCUT();
//		int EMR = RunningProcess.getTheRestOfMemory();

//		 System.out.println("Before While");

		while (RunningProcess.getState() != "Waiting" || RunningProcess.getState() != "Terminated" ) {
			CUT ++ ;
			currentCPU++;
			totalCut ++ ;
			RunningProcess.setCUT(CUT);
			if (RunningProcess.getState() == "Terminated") {
				break;
			}else {
				 randomNum = Math.random() * 1;
				 roundedRandomNumber = Math.round(randomNum * 100.0) / 100.0;
				 
				if (roundedRandomNumber <= 0.20) { 
					// IO request
					 RunningProcess.setState("Waiting");
						break;
				 } 
				
				 randomNum = Math.random() * 1;
				 roundedRandomNumber = Math.round(randomNum * 100.0) / 100.0;
				 
				 if (roundedRandomNumber <= 0.1) { 
					//Process terminates normally
					 RunningProcess.setState("Terminated");
					 RunningProcess.checkState("Normal");
					 numOfJobsCompletedNormally++;
							break;
					 }
				 
				 randomNum = Math.random() * 1;
				 roundedRandomNumber = Math.round(randomNum * 100.0) / 100.0;
				 
				 if (roundedRandomNumber <= 0.05) { 
					//Process terminates abnormally
					 RunningProcess.setState("Terminated");
					 RunningProcess.checkState("Abnormal");
					 numOfJobsCompletedAbnormally++;
							break;
					 }
				
			}
		}
		currentCUTForTheProcess = currentCPU ;

		if(RunningProcess.getState() == "Terminated") {
			ProcessServed.add(RunningProcess);
			numOfProcessServed++;
			TheSystemHardware.RemoveFromQueue(toSend.getPID() ,"RAM");
			RunningProcess = null ;

			return ;
		}else if(RunningProcess.getState() == "Waiting") {
			operatingProject.addToIOQueue(toSend);
			RunningProcess = null ;
			return;
		}


	}//end of Running method 
	
	
	public static boolean isCPUAvailable() {
		if (RunningProcess == null) {
			notWorking++;
			return true ;
		}else {
			return false ;
		}
	}//end of isCPUAvailable method
	
		
	public static double averageWaitingTime() {
		double average = 0 ;
		while(!ProcessServed.isEmpty()) {
			PCB pcb = ProcessServed.poll() ;
			average += pcb.getwaitingTimeInReadyQueue();
		}

			average = average / numOfProcessServed ;
			
			double roundOff = Math.round(average * 100.0) / 100.0;

		return roundOff;
	}
	
	public static double utilizationForCPU() {
		double utilization = 1.0 ;
		double sub = 0;
		double div = 0 ;
		double t = 0;

		sum = totalCut + notWorking ;
		sub = sum - notWorking ;
		div = sub/sum ;
		t = div * 100 ;
		
		utilization = utilization * t ;
		double roundOff = Math.round(utilization * 100.0) / 100.0;

		return roundOff;
		
		
	}

	
}
