
public class PCB {
	private int PID ;
	private String State ; 
	private int CUT ;
	private int ECU ;
	private int MemoryInKB ;
	private int IRT ;
	private int WT ;
	private int TheRestOfMemory;
	private int waitingTimeInReadyQueue;
	public static int counter = 0;


	public PCB(int JobID , String State  , int ECU , int EMR) {
		PID = JobID ;
		this.State = State ;
		MemoryInKB = EMR ;
		this.ECU = ECU ;
		CUT = 0 ;
		IRT = 0 ;
		WT = 0 ;
		TheRestOfMemory = EMR;
		waitingTimeInReadyQueue = 0 ;
		
	}

	public int getPID() {
		return PID;
	}
	
	public int getMemoryInKB() {
		return MemoryInKB;
	}


	public String getState() {
		return State;
	}
	
	public int getIRT() {
		return IRT;
	}
	
	public int getWT() {
		return WT;
	}

	
	public void setIRT(int IRT) {
		 this.IRT = IRT;
	}
	
	public void setWT(int WT) {
		 this.WT = WT;
	}
	
	public int getwaitingTimeInReadyQueue() {
		return waitingTimeInReadyQueue;
	}

	
	public void setwaitingTimeInReadyQueue(int waitingTimeInReadyQueue) {
		 this.waitingTimeInReadyQueue = waitingTimeInReadyQueue;
	}
	
	
	public int getCUT() {
		return CUT;
	}
	
	public void setTheRestOfMemory(int TheRest) {
		TheRestOfMemory = TheRest ;
	}
	
	public int getTheRestOfMemory() {
		return TheRestOfMemory ;
	}


	public void setState(String state) {
		State = state;
	}
	
	public void checkState(String terminationStatus) {	
		if (State.equals("Terminated")) {
			++operatingProject.totalNumOfJobsProcessed ;
			TerminateProcess terminateProcess = new TerminateProcess(getPID() , getCUT() , getIRT() , getWT() , terminationStatus );
			operatingProject.ListOfAllJobsThatWasTerminate.add(terminateProcess);
			System.out.println("-------------------------------"+(++counter)+"------------------------");
			System.out.println("The process Terminated");
			System.out.println("PID: "+ getPID() +
		    " ,  CUT: "+ getCUT() + "  ,  IRT: "+ getIRT() + "  ,  WT: "+ getWT() +
		    "  ,  termination status: "	+ terminationStatus);
			
			
			return ;
		}
		return;
	}
	
	public void setCUT(int CUT) {
		
		if(CUT > ECU) {
			setState("Terminated");
			checkState("Abnormal");
			CPU.numOfJobsCompletedAbnormally ++ ;
		}else {
			this.CUT = CUT ;
	
		}
				
	}


}
