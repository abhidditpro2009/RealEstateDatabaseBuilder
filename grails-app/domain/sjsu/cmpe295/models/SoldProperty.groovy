package sjsu.cmpe295.models


/**
 * Property
 * A domain class describes the data object and it's mapping to the database
 */
class SoldProperty {

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
	
	
    static mapping = {
    }
    
	
    static constraints = {
        zpID(unique: true)
    }
	
}
