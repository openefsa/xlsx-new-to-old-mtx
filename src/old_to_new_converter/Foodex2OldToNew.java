package old_to_new_converter;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import naming_convention.Headers;
import open_xml_reader.ResultDataSet;
import open_xml_reader.WorkbookReader;
import progress_bar.FormProgressBar;
import sheet_converter.AttribSheetConverter;
import sheet_converter.DataSheetConverter;
import sheet_converter.OldHeaders;
import sheet_converter.SheetConverter;

/**
 * This class takes as input the new xlsx format file
 * for the Foodex catalogue and converts it to recreate
 * the old Foodex xlsx export (for compatibility issues).
 * @author avonva
 *
 */
public class Foodex2OldToNew {

	private FormProgressBar progressBar;
	
	private String input;
	private String output;
	
	public Foodex2OldToNew( String input, String output ) {
		this.input = input;
		this.output = output;
	}
	
	/**
	 * Set a progress bar for the process
	 * @param progressBar
	 */
	public void setProgressBar(FormProgressBar progressBar) {
		this.progressBar = progressBar;
	}
	
	/**
	 * Convert the new xlsx format into the old one
	 * @throws Exception
	 */
	public void convert() throws Exception {
		
		Workbook workbook = new SXSSFWorkbook();
		
		// get the excel data
		WorkbookReader rawData = new WorkbookReader( input );
		
		// get the catalogue sheet and check if the catalogues are compatible
		// (the open catalogue and the one we want to import)
		rawData.processSheetName( Headers.TERM_SHEET_NAME );
		
		ResultDataSet termData = rawData.next();

		SheetConverter converter = new DataSheetConverter( workbook, termData );
		
		// set the converter progress bar
		if ( progressBar != null ) {
			progressBar.setLabel( "Converting " + Headers.TERM_SHEET_NAME + " sheet into "
					+ OldHeaders.DATA_SHEET_NAME + " sheet" );
			converter.setProgressBar( progressBar, 80 );
		}
		
		converter.convert( OldHeaders.DATA_SHEET_NAME );
		
		
		
		// get the catalogue sheet and check if the catalogues are compatible
		// (the open catalogue and the one we want to import)
		rawData.processSheetName( Headers.ATTR_SHEET_NAME );
		
		ResultDataSet attrData = rawData.next();
		
		converter = new AttribSheetConverter( workbook, attrData );
		
		if ( progressBar != null ) {
			progressBar.setLabel( "Converting " + Headers.ATTR_SHEET_NAME + " sheet into "
					+ OldHeaders.ATTR_SHEET_NAME + " sheet" );
			converter.setProgressBar( progressBar, 10 );
		}
		
		converter.convert( OldHeaders.ATTR_SHEET_NAME );
		
		
		
		if ( progressBar != null )
			progressBar.setLabel( "Writing .xlsx file..." );
		
		// write in the workbook into the output file
		OutputStream out = new FileOutputStream( output );
		workbook.write( out );
		
		if ( progressBar != null )
			progressBar.setProgress( 100 );
		
		// close the progress bar
		if ( progressBar != null )
			progressBar.close();
		
		rawData.close();
		workbook.close();

		System.out.println ( "Conversion finished" );
	}
}
