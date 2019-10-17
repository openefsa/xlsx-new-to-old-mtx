package sheet_converter;

import java.util.HashMap;

import org.apache.poi.ss.usermodel.Workbook;

import naming_convention.Headers;
import open_xml_reader.ResultDataSet;
import sheet_header.SheetHeader;

/**
 * This class is used to recreate the old sheet ATTRIB,
 * which contains the attributes related to facet hierarchies.
 * All the other attributes are ignored.
 * @author avonva
 *
 */
public class AttribSheetConverter extends SheetConverter {

	public AttribSheetConverter(Workbook workbook, ResultDataSet rs) {
		super(workbook, rs);
	}

	@Override
	public HashMap<String, SheetHeader> getHeaders() {
		
		HashMap<String, SheetHeader> headers = new HashMap<>();
		
		int i = 0;
		headers.put( Headers.CODE, new SheetHeader(i++, OldHeaders.ATTR_CODE) );
		headers.put( Headers.LABEL, new SheetHeader(i++, OldHeaders.ATTR_NAME) );
		headers.put( Headers.NAME, new SheetHeader(i++, OldHeaders.ATTR_HIERARCHY) );
		headers.put( Headers.ATTR_TYPE, new SheetHeader(i++, OldHeaders.ATTR_TYPE) );
		headers.put( Headers.VALID_FROM, new SheetHeader(i++, OldHeaders.VALID_FROM) );
		headers.put( Headers.VALID_TO, new SheetHeader(i++, OldHeaders.VALID_TO) );
		headers.put( Headers.LAST_VERSION, new SheetHeader(i++, OldHeaders.LAST_VERSION) );
		headers.put( Headers.LAST_UPDATE, new SheetHeader(i++, OldHeaders.LAST_UPDATE) );
		return headers;
	}
	
	@Override
	public String getCellValue(String columnName, ResultDataSet rs) {
		
		// change the attribute type since we have a different
		// coding system in the two xlsx formats
		// we always return A since we are using only
		// attributes related to hierarchies which in the
		// old format had the A as type
		if ( columnName.equals( Headers.ATTR_TYPE ) )
			return "A";
		
		String value = rs.getString ( columnName );
		
		// if we have the hierarchy code for the attributes
		// we need to get its old version for compatibility
		// reasons (the hierarchy code is the name of the
		// attribute in the new format)
		if ( columnName.equals( Headers.NAME ) )
			return OldHeaders.getOldHierarchyCode( value );
		
		return value;
	}

	@Override
	public boolean validateRow(ResultDataSet rs) {
		
		// check if we have an attribute related to a hierarchy or not
		String catCode = rs.getString( Headers.ATTR_CAT_CODE );
		
		// if not related skip row
		if ( catCode == null || catCode.isEmpty() )
			return false;
		
		return true;
	}
}
