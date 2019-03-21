import aux_interfaces.Prioriser;

public class Process implements Prioriser{
	private String name;
	private int priority;
	
	public Process(String name){
		this.name = name;
	}
	
	public Process(String name, int priority){
		this.name = name;
		this.priority = priority;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPiority(int priority) {
		this.priority = priority;
	}
	
	public String toString() {
		return "Process: " + name + 
				"\nPriority: " + priority;
	}

	@Override
	public int getPriority() {
		return priority;
	}
}
