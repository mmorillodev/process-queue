package br.com.aps_so.interfaces;

import br.com.aps_so.lists.Queue;
import br.com.aps_so.process_managers.Process;

public interface OnProcessChangeListener {
	public void onChange(Process newProcess, int currentTime, Queue<Process> raedyQueue);
}