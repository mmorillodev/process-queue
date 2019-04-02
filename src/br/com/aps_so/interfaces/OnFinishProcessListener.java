package br.com.aps_so.interfaces;

import br.com.aps_so.process_managers.Process;

public interface OnFinishProcessListener {
	public void onFinish(Process oldProcess, int currentTime);
}
