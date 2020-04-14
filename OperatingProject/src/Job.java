
public class Job {
//	private Queue<PCB> PCBs = new LinkedList<PCB>();

	private int JObID ;
	private int ECU ; // units
	private int EMR ; //KB
	private PCB pcb ;

	public Job(int jobID , int ECU , int EMR) {
		JObID = jobID ;
		this.ECU = ECU;
		this.EMR = EMR;
	    System.out.println(jobID +" Job has been created"); //What should it do ?

	}

	public int getJObID() {
		return JObID;
	}

	
	public PCB getPCB() {
		return pcb ;
		
	}//end of getPCB method
	
	public void setPCB(PCB pcb) {
		 this.pcb = pcb ;
		
	}//end of getPCB method
	
	
	public int getECU() {
		return ECU ;
	}
	
	public int getEMR() {
		return EMR ;
	}
	
	
}
