
public class TerminateProcess {
	private int PID ;
	private int CUT ;
	private int IRT ;
	private int WT ;
	private String NormalORupNormal ;

	
	public TerminateProcess(int PID , int CUT , int IRT , int WT , String NormalORupNormal) {
		this.PID = PID ;
		this.CUT = CUT ;
		this.IRT = IRT ;
		this.WT = WT ;
		this.NormalORupNormal = NormalORupNormal ;
	}
	
	
	public int getPID() {
		return PID;
	}


	public int getCUT() {
		return CUT;
	}


	public int getIRT() {
		return IRT;
	}


	public int getWT() {
		return WT;
	}


	public String getNormalORupNormal() {
		return NormalORupNormal;
	}


}
