package sjsu.cmpe295.models


/**
 * Property
 * A domain class describes the data object and it's mapping to the database
 */
class NormalizedSoldProperty {

	Double zpID
	String city
	//Double latitude
	//Double longitude
	Double amenities
	Double crimerate
	Double education
	Double housing 
	Double weather
	Double costofliving
	Double employment
	Double useCode
	Double taxAssesment
	Double age
	Double lotSizeSqFt
	Double finishedSqFt
	Double bathroom
	Double bedroom
	Double daysPending    // From last Sell
	Double lastSoldPrice
	Double zest_amt
	Double zest_valueChange
	Double zest_low
	Double zest_high
	Boolean priceAppreciated
	
    static mapping = {
    }
    
	
    static constraints = {
        zpID(unique: true)
    }
	
}
