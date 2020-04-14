import java.util.LinkedList;
import java.util.Queue;

public class IODevice {
	public static PCB RunningProcess = null;
	public static boolean isFinished = false ;
	public static int numOfProcessServed = 0 ;
	public static int totalIRT = 0 ;
	public static int currentIRTForTheProcess  ;
	public static int notWorking = 0 ;
	public static Queue<PCB> ProcessServed = new LinkedList<PCB>();
	
	public static void Running(PCB pcb) {
		int currentIRT = 0 ;
		System.out.println(pcb.getPID()+"  The process is running in the IO Device ");
		PCB toSend = pcb ;
		RunningProcess = pcb ;
		int IRT = RunningProcess.getIRT();

		while(isFinished == false) {
			IRT++;
			currentIRT++;
			RunningProcess.setIRT(IRT);
			isFinished = generateInterrupt();
		}
		
		    isFinished = false ;
		
			++numOfProcessServed; 
			ProcessServed.add(RunningProcess);
			totalIRT += RunningProcess.getIRT();
			RunningProcess = null ;
			operatingProject.addToReadyQueue(toSend);
			currentIRTForTheProcess =currentIRT ;
			return;
			
	}//end of Running method
	
	public static boolean isIODeviceAvailable() {
		if (RunningProcess == null) {
			notWorking ++;
			
			return true ;
		}else {
			return false ;
		}
	}//end of isCPUAvailable method
	
	public static boolean generateInterrupt() {
		double randomNum = Math.random() * 1;
		double roundedRandomNumber = Math.round(randomNum * 100.0) / 100.0;
			 
			  if (roundedRandomNumber <= 0.20) {
				 return true ;
			 }else {
				  return false ; 
			 }
	}//end of generateInterrupt method
	
	
	public static double averageWaitingTime() {
		double average = 0;
		while(!ProcessServed.isEmpty() && ProcessServed.peek() != null) {
//			average = average + ProcessServed.poll().getWT();
			average += ProcessServed.poll().getWT();
		}
			average = average / numOfProcessServed ;
			double roundOff = Math.round(average * 100.0) / 100.0;

			return roundOff;		
	}
	
	public static double utilizationForIODevice() {
		double utilization = 1.0 ;
		double sub = 0;
		double div = 0 ;
		double t = 0;
		double sum = 0 ;

		sum = totalIRT + notWorking ;
		sub = sum - notWorking ;
		div = sub/sum ;
		t = div * 100 ;
		
		utilization = utilization * t ;
		double roundOff = Math.round(utilization * 100.0) / 100.0;

		return roundOff;
	}

}
