package sheet_converter;

import java.util.HashMap;

import naming_convention.Headers;

/**
 * Class which contains the old naming convention
 * of the old xlsx format of the FoodEx2 .xlsx.
 * @author avonva
 *
 */
public class OldHeaders {
	
	public static final String DATA_SHEET_NAME = "DATA";
	public static final String ATTR_SHEET_NAME = "ATTRIB";

	public static final String FOODEX_OLD_CODE = "foodexOldCode";
	public static final String MATRIX_CODE = "matrixCode";
	public static final String GEMS_CODE = "GEMSCode";
	public static final String LANGUAL_CODE = "LangualCode";
	public static final String CODE = "code";
	public static final String NAME = "name";
	public static final String TERM_TYPE = "statef";
	public static final String DETAIL_LEVEL = "corex";
	public static final String SCOPENOTES = "scopenotes";
	public static final String SCIENTIFIC_NAMES = "scientificnames";
	public static final String COMMON_NAMES = "commonnames";
	public static final String FACET_SCHEMA = "facetSchema";
	public static final String FACET_APPL = "facetApplicability";
	public static final String IMPLICIT_FACETS = "implicitFacets";
	public static final String ALL_FACETS = "allFacets";
	public static final String VALID_FROM = "validFrom";
	public static final String VALID_TO = "validTo";
	public static final String LAST_VERSION = "lastVersion";
	public static final String LAST_UPDATE = "lastUpdate";
	
	public static final String SUFFIX_PARENT_CODE = "ParentCode";
	public static final String SUFFIX_HIER_CODE = "HierarchyCode";
	public static final String SUFFIX_FLAG = "Flag";

	// ATTRIB
	public static final String ATTR_CODE = "Code";
	public static final String ATTR_NAME = "Name";
	public static final String ATTR_HIERARCHY = "hierarchy";
	public static final String ATTR_TYPE = "type";
	
	public static HashMap<String, String> newToOldHierarchyCode;
	
	/**
	 * Get the old version of a hierarchy code starting
	 * from the new version
	 * @param newCode
	 * @return the old code
	 */
	public static final String getOldHierarchyCode ( String newCode ) {
		HashMap<String, String> map = getHierarchyCodeMap();
		String value = map.get( newCode );
		
		if ( value == null )
			System.err.println( "No old hierarchy code value found for " + newCode );
		
		return value;
	}
	
	private static final HashMap<String, String> getHierarchyCodeMap () {
		
		if ( newToOldHierarchyCode == null )
			newToOldHierarchyCode = new HashMap<>();
		else
			return newToOldHierarchyCode;
		
		newToOldHierarchyCode.put( Headers.REPORT, OldHeaders.REPORT );
		newToOldHierarchyCode.put( Headers.MASTER, OldHeaders.MASTER );
		newToOldHierarchyCode.put( Headers.PEST, OldHeaders.PEST );
		newToOldHierarchyCode.put( Headers.BIOMO, OldHeaders.BIOMO );
		newToOldHierarchyCode.put( Headers.FEED, OldHeaders.FEED );
		newToOldHierarchyCode.put( Headers.EXPO, OldHeaders.EXPO );
		newToOldHierarchyCode.put( Headers.VET_DRUG, OldHeaders.VET_DRUG );
		newToOldHierarchyCode.put( Headers.BIOTANIC, OldHeaders.BIOTANIC );
		newToOldHierarchyCode.put( Headers.PART, OldHeaders.PART );
		newToOldHierarchyCode.put( Headers.SOURCE, OldHeaders.SOURCE );
		newToOldHierarchyCode.put( Headers.RAC_SOURCE, OldHeaders.RAC_SOURCE );
		newToOldHierarchyCode.put( Headers.PROCESS, OldHeaders.PROCESS );
		newToOldHierarchyCode.put( Headers.INGRED, OldHeaders.INGRED );
		newToOldHierarchyCode.put( Headers.MEDIUM, OldHeaders.MEDIUM );
		newToOldHierarchyCode.put( Headers.SWEET, OldHeaders.SWEET );
		newToOldHierarchyCode.put( Headers.FORT, OldHeaders.FORT );
		newToOldHierarchyCode.put( Headers.QUAL, OldHeaders.QUAL );
		newToOldHierarchyCode.put( Headers.COOK_EXT, OldHeaders.COOK_EXT );
		newToOldHierarchyCode.put( Headers.GEN, OldHeaders.GEN );
		newToOldHierarchyCode.put( Headers.PROD, OldHeaders.PROD );
		newToOldHierarchyCode.put( Headers.PACK_FORMAT, OldHeaders.PACK_FORMAT );
		newToOldHierarchyCode.put( Headers.PACK_MAT, OldHeaders.PACK_MAT );
		newToOldHierarchyCode.put( Headers.STATE, OldHeaders.STATE );
		newToOldHierarchyCode.put( Headers.FAT, OldHeaders.FAT );
		newToOldHierarchyCode.put( Headers.ALCOHOL, OldHeaders.ALCOHOL );
		newToOldHierarchyCode.put( Headers.DOUGH, OldHeaders.DOUGH );
		newToOldHierarchyCode.put( Headers.COOK_METH, OldHeaders.COOK_METH );
		newToOldHierarchyCode.put( Headers.PREP, OldHeaders.PREP );
		newToOldHierarchyCode.put( Headers.PRESERV, OldHeaders.PRESERV );
		newToOldHierarchyCode.put( Headers.TREAT, OldHeaders.TREAT );
		newToOldHierarchyCode.put( Headers.PART_CON, OldHeaders.PART_CON );
		newToOldHierarchyCode.put( Headers.PLACE, OldHeaders.PLACE );
		newToOldHierarchyCode.put( Headers.TARGET_CON, OldHeaders.TARGET_CON );
		newToOldHierarchyCode.put( Headers.USE, OldHeaders.USE );
		newToOldHierarchyCode.put( Headers.RISK_INGRED, OldHeaders.RISK_INGRED );
		newToOldHierarchyCode.put( Headers.F_PURPOSE, OldHeaders.F_PURPOSE );
		newToOldHierarchyCode.put( Headers.REPLEV, OldHeaders.REPLEV );
		newToOldHierarchyCode.put( Headers.ANIMAGE, OldHeaders.ANIMAGE );
		newToOldHierarchyCode.put( Headers.GENDER, OldHeaders.GENDER );
		newToOldHierarchyCode.put( Headers.LEGIS, OldHeaders.LEGIS );
		return newToOldHierarchyCode;
	}

	// hierarchies codes (ordered)
	public static final String REPORT = "report";
	public static final String MASTER = "master";
	public static final String PEST = "pest";
	public static final String BIOMO = "biomo";
	public static final String FEED = "feed";
	public static final String EXPO = "expo";
	public static final String VET_DRUG = "vetdrug";
	public static final String BIOTANIC = "botanic";
	public static final String PART = "part";
	public static final String SOURCE = "source";
	public static final String RAC_SOURCE = "racsource";
	public static final String PROCESS = "process";
	public static final String INGRED = "ingred";
	public static final String MEDIUM = "medium";
	public static final String SWEET = "sweet";
	public static final String FORT = "fort";
	public static final String QUAL = "qual";
	public static final String COOK_EXT = "cookExt";
	public static final String GEN = "gen";
	public static final String PROD = "prod";
	public static final String PACK_FORMAT = "packFormat";
	public static final String PACK_MAT = "packMat";
	public static final String STATE = "state";
	public static final String FAT = "fat";
	public static final String ALCOHOL = "alcohol";
	public static final String DOUGH = "dough";
	public static final String COOK_METH = "cookMeth";
	public static final String PREP = "prep";
	public static final String PRESERV = "preserv";
	public static final String TREAT = "treat";
	public static final String PART_CON = "partCon";
	public static final String PLACE = "place";
	public static final String TARGET_CON = "targCon";
	public static final String USE = "use";
	public static final String RISK_INGRED = "riskIngred";
	public static final String F_PURPOSE = "fpurpose";
	public static final String REPLEV = "replev";
	public static final String ANIMAGE = "animage";
	public static final String GENDER = "gender";
	public static final String LEGIS = "legis";
}
