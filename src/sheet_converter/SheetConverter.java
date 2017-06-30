package sheet_converter;

import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import open_xml_reader.ResultDataSet;
import sheet_header.SheetHeader;
import ui_progress_bar.FormProgressBar;

public abstract class SheetConverter {

	private FormProgressBar progressBar;
	private int maxfill = 100;
	
	private Workbook workbook;
	private ResultDataSet rs;
	
	private int rowNum = 0;
	private HashMap<String, SheetHeader> headers;
	
	/**
	 * Set a progress bar if needed
	 * @param progressBar
	 * @param maxfill the maximum proportion of the
	 * progress bar (1 to 100) which can be filled by
	 * this process
	 */
	public void setProgressBar( FormProgressBar progressBar, int maxfill ) {
		this.progressBar = progressBar;
		this.maxfill = maxfill;
	}
	
	public SheetConverter( Workbook workbook, ResultDataSet rs ) {
		this.workbook = workbook;
		this.rs = rs;
	}
	
	/**
	 * Convert the term sheet into the old version sheet
	 * @param sheetName the name of the sheet which will be
	 * created
	 * @return the created sheet with headers and data
	 */
	public Sheet convert( String sheetName ) {
		Sheet sheet = workbook.createSheet( sheetName );
		
		insertHeaders( sheet );
		insertData( sheet, rs );
		
		return sheet;
	}

	
	/**
	 * Insert the old headers into the xlsx sheet
	 * @param sheet
	 */
	private void insertHeaders( Sheet sheet ) {
		
		this.headers = getHeaders();
		
		Row row = createRow( sheet );
		
		// insert the headers
		for ( SheetHeader header : headers.values() )
			createCell ( header.getColumnIndex(), row, header.getColumnName() );
	}
	
	/**
	 * Insert the data into the sheet
	 * @param sheet
	 */
	private void insertData ( Sheet sheet, ResultDataSet rs ) {
		
		int size = rs.getRowsCount();
		
		// get of how much we have to increment the progress bar
		// we can increment at maximum of maxFill, so we add
		// for each row maxFill/#rows
		double increment = (double) maxfill / size;
		
		// use the result data set to insert data
		while ( rs.next() ) {

			// insert a single row into the sheet
			// if it passed the validation
			if ( validateRow ( rs ) )
				insertDataRow ( sheet, rs );
			
			// refresh the progress bar if needed
			if ( progressBar != null )
				progressBar.addProgress( increment );
		}
	}
	
	/**
	 * Insert a single data row into the DATA sheet
	 * @param sheet
	 * @param rs
	 */
	private void insertDataRow ( Sheet sheet, ResultDataSet rs ) {
		
		Row row = createRow( sheet );
		
		// for each header we set the cell value related to it
		for ( String columnName : headers.keySet() ) {

			String value = getCellValue( columnName, rs );
			
			// skip if no value
			if ( value == null )
				continue;
			
			// add the cell to the sheet using the defined
			// order of the old headers
			createCell( headers.get( columnName ).getColumnIndex(), row, value );
		}
	}
	
	/**
	 * Check if a new row should be inserted or not
	 * into the excel
	 * @param rs the result set containing the data
	 * related to the current row
	 * @return true if the current row should be inserted,
	 * false otherwise
	 */
	public abstract boolean validateRow ( ResultDataSet rs );
	
	/**
	 * Create a new row in the selected sheet
	 * @param sheet
	 * @return
	 */
	private Row createRow( Sheet sheet ) {
		return sheet.createRow( rowNum++ );
	}
	
	/**
	 * Create a new cell if not present, otherwise add the content $ separated
	 * to the previous cell value
	 * @param row
	 * @param name
	 * @return
	 */
	private static Cell createCell ( int columnIndex, Row row, String value ) {

		if ( columnIndex < 0 )
			return null;

		Cell cell = row.createCell( columnIndex );
		cell.setCellValue( value );
		return cell;
	}
	
	/**
	 * Get the sheet headers
	 * @return
	 */
	public abstract HashMap<String, SheetHeader> getHeaders();
	
	/**
	 * Get the cell value contained in the result data set for
	 * the chosen column (identified by its name)
	 * @param columnName
	 * @param rs
	 * @return the value which will be inserted in the old format xlsx
	 * under the column identified by {@code columnName }
	 */
	public abstract String getCellValue ( String columnName, ResultDataSet rs );
}
