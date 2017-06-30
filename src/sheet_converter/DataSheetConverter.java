package sheet_converter;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Workbook;

import naming_convention.Headers;
import open_xml_reader.ResultDataSet;
import sheet_header.SheetHeader;

/**
 * Convert the Term sheet of the new xslx into
 * the old xlsx sheet named DATA.
 * @author avonva
 *
 */
public class DataSheetConverter extends SheetConverter {

	public DataSheetConverter( Workbook workbook, ResultDataSet rs ) {
		super ( workbook, rs );
	}

	@Override
	public String getCellValue(String columnName, ResultDataSet rs) {
		
		String value = null;
		
		// if we have to write the old flag column, we need to
		// compute it since it has a different meaning compared
		// to the flag used in the new format
		if ( columnName.contains( OldHeaders.SUFFIX_FLAG ) ) {
			value = getApplicabilityFlag ( columnName, rs );
		}
		else {

			// get the value using the new header of the
			// new xlsx format
			// to write it into the old xlsx format

			value = rs.getString( columnName );
		}
		
		return value;
	}

	/**
	 * Compute the applicability flag using the information
	 * contained in the new xlsx term sheet.
	 * @param columnName
	 * @param rs
	 * @return
	 */
	private String getApplicabilityFlag ( String columnName, 
			ResultDataSet rs ) {

		String flag = null;

		// remove the flag suffix to get the hierarchy code
		// no hierarchy code has "flag" in the code, so we
		// can do this.
		String hierarchyCode = columnName.replaceFirst( 
				Headers.SUFFIX_FLAG, "" );

		// get if the term in this hierarchy is reportable or not
		String reportable = rs.getString( hierarchyCode + Headers.SUFFIX_REPORT );

		// get the deprecated flag from the new format
		String deprecated = rs.getString( Headers.DEPRECATED );
		
		// get the parent code of the term
		String parentCode = rs.getString( hierarchyCode + Headers.SUFFIX_PARENT_CODE );

		// if no parent code is found, then the term is
		// not in use in the current hierarchy
		if ( parentCode.isEmpty() ) {
			flag = "0";  // not in use in this domain
		}
		else {

			if ( deprecated.equals( "1" ) )
				flag = "2";  // deprecated
			else if ( reportable.equals( "1" ) )
				flag = "1";  // reportable, not deprecated
			else
				flag = "3";  // not reportable
		}

		return flag;
	}

	/**
	 * Get the headers of the catalogue sheet
	 * @return
	 */
	public HashMap<String, SheetHeader> getHeaders() {

		HashMap<String, SheetHeader> headers = new HashMap<>();

		int i = 0;
		// prepare headers for the catalogue sheet
		// the order of headers in the headers array reflect
		// the order of the excel columns

		headers.put( Headers.FOODEX_OLD_CODE, new SheetHeader(i++, OldHeaders.FOODEX_OLD_CODE) );
		headers.put( Headers.MATRIX_CODE, new SheetHeader(i++, OldHeaders.MATRIX_CODE) );
		headers.put( Headers.GEMS_CODE, new SheetHeader(i++, OldHeaders.GEMS_CODE) );
		headers.put( Headers.LANGUAL_CODE, new SheetHeader(i++, OldHeaders.LANGUAL_CODE) );

		headers.put( Headers.TERM_CODE, new SheetHeader(i++, OldHeaders.CODE) );
		headers.put( Headers.TERM_EXT_NAME, new SheetHeader(i++, OldHeaders.NAME) );
		headers.put( Headers.TERM_TYPE, new SheetHeader(i++, OldHeaders.TERM_TYPE) );
		headers.put( Headers.DETAIL_LEVEL, new SheetHeader(i++, OldHeaders.DETAIL_LEVEL) );
		headers.put( Headers.TERM_SCOPENOTE, new SheetHeader(i++, OldHeaders.SCOPENOTES) );

		headers.put( Headers.SCIENTIFIC_NAMES, new SheetHeader(i++, OldHeaders.SCIENTIFIC_NAMES) );
		headers.put( Headers.COMMON_NAMES, new SheetHeader(i++, OldHeaders.COMMON_NAMES) );
		headers.put( Headers.FACET_SCHEMA, new SheetHeader(i++, OldHeaders.FACET_SCHEMA) );
		headers.put( Headers.FACET_APPL, new SheetHeader(i++, OldHeaders.FACET_APPL) );
		headers.put( Headers.IMPLICIT_FACETS, new SheetHeader(i++, OldHeaders.IMPLICIT_FACETS) );
		headers.put( Headers.ALL_FACETS, new SheetHeader(i++, OldHeaders.ALL_FACETS) );

		// here we put the headers for the hierarchies data
		// related to terms
		for ( String code : getHierarchiesOrderedCodes() ) {

			String newParentCode = code + Headers.SUFFIX_PARENT_CODE;
			String newHierCode = code + Headers.SUFFIX_HIER_CODE;
			String newFlagCode = code + Headers.SUFFIX_FLAG;

			String oldCode = OldHeaders.getOldHierarchyCode( code );
			String oldParentCode = oldCode + OldHeaders.SUFFIX_PARENT_CODE;
			String oldHierCode = oldCode + OldHeaders.SUFFIX_HIER_CODE;
			String oldFlagCode = oldCode + OldHeaders.SUFFIX_FLAG;
			
			headers.put( newParentCode, new SheetHeader(i++, oldParentCode) );
			headers.put( newHierCode, new SheetHeader(i++, oldHierCode) );
			headers.put( newFlagCode, new SheetHeader(i++, oldFlagCode) );
		}

		headers.put( Headers.VALID_FROM, new SheetHeader(i++, OldHeaders.VALID_FROM) );
		headers.put( Headers.VALID_TO, new SheetHeader(i++, OldHeaders.VALID_TO) );
		headers.put( Headers.LAST_VERSION, new SheetHeader(i++, OldHeaders.LAST_VERSION) );
		headers.put( Headers.LAST_UPDATE, new SheetHeader(i++, OldHeaders.LAST_UPDATE) );

		return headers;
	}

	/**
	 * Define the order of the hierarchies in the sheet
	 * @return
	 */
	private ArrayList<String> getHierarchiesOrderedCodes () {

		ArrayList<String> oldOrder = new ArrayList<>();
		oldOrder.add( Headers.REPORT );
		oldOrder.add( Headers.MASTER );
		oldOrder.add( Headers.PEST );
		oldOrder.add( Headers.BIOMO );
		oldOrder.add( Headers.FEED );
		oldOrder.add( Headers.EXPO );
		oldOrder.add( Headers.VET_DRUG );
		oldOrder.add( Headers.BIOTANIC );
		oldOrder.add( Headers.PART );
		oldOrder.add( Headers.SOURCE );
		oldOrder.add( Headers.RAC_SOURCE );
		oldOrder.add( Headers.PROCESS );
		oldOrder.add( Headers.INGRED );
		oldOrder.add( Headers.MEDIUM );
		oldOrder.add( Headers.SWEET );
		oldOrder.add( Headers.FORT );
		oldOrder.add( Headers.QUAL );
		oldOrder.add( Headers.COOK_EXT );
		oldOrder.add( Headers.GEN );
		oldOrder.add( Headers.PROD );
		oldOrder.add( Headers.PACK_FORMAT );
		oldOrder.add( Headers.PACK_MAT );
		oldOrder.add( Headers.STATE );
		oldOrder.add( Headers.FAT );
		oldOrder.add( Headers.ALCOHOL );
		oldOrder.add( Headers.DOUGH );
		oldOrder.add( Headers.COOK_METH );
		oldOrder.add( Headers.PREP );
		oldOrder.add( Headers.PRESERV );
		oldOrder.add( Headers.TREAT );
		oldOrder.add( Headers.PART_CON );
		oldOrder.add( Headers.PLACE );
		oldOrder.add( Headers.TARGET_CON );
		oldOrder.add( Headers.USE );
		oldOrder.add( Headers.RISK_INGRED );
		oldOrder.add( Headers.F_PURPOSE );
		oldOrder.add( Headers.REPLEV );
		oldOrder.add( Headers.ANIMAGE );
		oldOrder.add( Headers.GENDER );
		oldOrder.add( Headers.LEGIS );

		return oldOrder;
	}

	@Override
	public boolean validateRow(ResultDataSet rs) {
		return true;
	}
}
