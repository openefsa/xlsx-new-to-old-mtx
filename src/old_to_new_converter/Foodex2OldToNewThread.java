package old_to_new_converter;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import progress_bar.FormProgressBar;

public class Foodex2OldToNewThread extends Thread {

	private FormProgressBar progressBar;
	private String input;
	private String output;
	
	private Listener doneListener;
	
	/**
	 * Listener called when the thread finishes. The
	 * {@link Event#data} contains a boolean which
	 * is true if the process was successful and
	 * false otherwise.
	 * @param doneListener
	 */
	public void setDoneListener(Listener doneListener) {
		this.doneListener = doneListener;
	}
	
	/**
	 * Set the progress bar for the process
	 * @param progressBar
	 */
	public void setProgressBar(FormProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	
	public Foodex2OldToNewThread( String input, String output ) {
		this.input = input;
		this.output = output;
	}
	
	@Override
	public void run() {
		
		boolean success = true;
		
		try {
			// converter for 
			Foodex2OldToNew converter = new Foodex2OldToNew( input, output );
			converter.setProgressBar( progressBar );
			converter.convert();
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
		}
		
		// call the listener passing if we
		// completed the process or not
		if ( doneListener != null ) {
			Event event = new Event();
			event.data = success;
			doneListener.handleEvent( event );
		}
	}
}
