import interfaces.OnProcessChangeListener;

public class Scheduler extends Thread{
	private int quantum;
	private long quantum_milis;
	private OnProcessChangeListener callback;
	private Runnable onDeployThreadListener;
	
	public Scheduler(int quantum, long quantum_milis) {
		this.quantum = quantum;
		this.quantum_milis = quantum_milis;
	}
	
	public void setOnProcessChangeListener(OnProcessChangeListener listener) {
		callback = listener;
	}
	
	public void setOnDeployThreadListener(Runnable runnable) {
		this.onDeployThreadListener = runnable;
	}
	
	@Override
	public void run() {
		if(onDeployThreadListener != null) {
			onDeployThreadListener.run();
			return;
		}
		
	}
}
