package user_interface;

import java.awt.Cursor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import old_to_new_converter.Foodex2OldToNewThread;
import ui_progress_bar.FormProgressBar;
import user_interface.InputFileForm.ConvertionStartedListener;

/**
 * Main class for accessing the xlsx converter to old foodex 2 format.
 * @author avonva
 *
 */
public class UserInterfaceMain {

	public static void main ( String[] args ) {

		// create a new shell for the ui
		Display display = new Display();
		Shell shell = new Shell ( display );

		// open the form
		InputFileForm form = new InputFileForm( shell, display );
		
		form.display( "FoodEx2 new .xlsx to old .xlsx", ".xlsx", ".xlsx" );

		form.setListener( new ConvertionStartedListener() {

			@Override
			public void started(String inputFilename, String outputFilename) {

				form.setEnabled( false );
				
				shell.setCursor( display.getSystemCursor( Cursor.WAIT_CURSOR ) );
				
				FormProgressBar progressBar = new FormProgressBar( shell, "Xlsx format conversion" );
				progressBar.setLabel( "Loading data into application..." );
				progressBar.setProgress(1);

				Foodex2OldToNewThread thread = new Foodex2OldToNewThread(
						inputFilename, outputFilename );

				// set the progress bar for the thread
				thread.setProgressBar( progressBar );
				
				// set what to do if the thread finishes
				thread.setDoneListener( new Listener() {

					@Override
					public void handleEvent(Event arg0) {

						// get parameter
						boolean success = (boolean) arg0.data;

						String title;
						String message;
						int style;

						if ( success ) {
							title = "Done!";
							message = "The conversion was correctly terminated!";
							style = SWT.ICON_INFORMATION;
						}
						else {
							title = "Error";
							message = "Something went wrong, please check files format";
							style = SWT.ICON_ERROR;
						}

						// message box in the UI thread
						display.asyncExec( new Runnable() {

							@Override
							public void run() {

								shell.setCursor( display.getSystemCursor( Cursor.DEFAULT_CURSOR ) );
								
								MessageBox mb = new MessageBox( shell, style );
								mb.setText( title );
								mb.setMessage( message );
								mb.open();
								
								form.setEnabled( true );
							}
						});
					}
				});
				
				thread.start();
			}
		});

		// show the shell
		shell.open();

		// loop for updating ui
		while ( !form.shell.isDisposed() ) {
			if ( !form.display.readAndDispatch() )
				form.display.sleep();
		}

		// dispose display
		display.dispose();
	}
}
