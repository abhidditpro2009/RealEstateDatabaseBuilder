package sjsu.cmpe295.models


/**
 * Property
 * A domain class describes the data object and it's mapping to the database
 */
class Property {

	Double zpID
	String Address
	String zipcode
	String city
	String state
	Double latitude
	Double longitude
	
	String useCode
	Integer taxAssesmentYear
	Double taxAssesment
	Integer yearBuilt
	Integer lotSizeSqFt
	Integer finishedSqFt
	Double bathroom
	Double bedroom
	Double totalRoom
	String lastSoldDate
	Double lastSoldPrice
	Double zest_amt
	Double zest_valueChange
	Double zest_low
	Double zest_high
	
	/* Default (injected) attributes of GORM */
//	Long	id
//	Long	version
	
	/* Automatic timestamping of GORM */
//	Date	dateCreated
//	Date	lastUpdated
	
//	static belongsTo	= []	// tells GORM to cascade commands: e.g., delete this object if the "parent" is deleted.
//	static hasOne		= []	// tells GORM to associate another domain object as an owner in a 1-1 mapping
//	static hasMany		= []	// tells GORM to associate other domain objects for a 1-n or n-m mapping
//	static mappedBy		= []	// specifies which property should be used in a mapping 
	
    static mapping = {
    }
    
	
    static constraints = {
        zpID(unique: true)
    }
	
	/*
	 * Methods of the Domain Class
	 */
//	@Override	// Override toString for a nicer / more descriptive UI 
//	public String toString() {
//		return "${name}";
//	}
}
