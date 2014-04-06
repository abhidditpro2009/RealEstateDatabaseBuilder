package sjsu.cmpe295.services

import com.sun.org.apache.xml.internal.serialize.XMLSerializer
import grails.converters.JSON
import grails.converters.XML
import grails.plugins.rest.client.*
import grails.rest.*

import groovy.json.JsonBuilder
import org.codehaus.groovy.grails.web.json.JSONObject
import sjsu.cmpe295.models.NormalizedSoldProperty
import sjsu.cmpe295.models.NormalizedUnSoldProperty
import sjsu.cmpe295.models.Property;
import sjsu.cmpe295.models.SoldProperty


class DataNormalizerService {

	static transactional = true
	
	// scaling constants
	final def A1 = 2
	final def A = 1.5
	final def B1 = 1
	final def B = 0.5
	final def C1 = 0
	final def C = -0.5
	final def D1 = -1
	final def D = -1.5
	final def F = -2
	
	def printRecords() {
		println("In class DataNormalizerService/getSearchResults()")
		
		// We need to assign scales to each attribute
		
		Property property = new Property()
		def listprop = property.findAll()
		listprop.each{println(it.getCity()) }
		
		// iterate through all records and do the scaling
		//	scaleCity(it.get) }
		
		
	}
	
	
	def populateProperty(result)
	{
		try
		{
			println(result.toString())
			SoldProperty property = new SoldProperty()
			//Property property = new Property()
			property.setZpID(result.zpid.toDouble())
			property.setAddress(result.address.street.toString())
			property.setCity(result.address.city.toString())
			property.setState(result.address.state.toString())
			property.setZipcode(result.address.zipcode.toString())
			property.setLatitude(result.address.latitude.toDouble())
			property.setLongitude(result.address.longitude.toDouble())
			
			property.setUseCode(result.useCode.toString())
			property.setTaxAssesmentYear(result.taxAssessmentYear.toInteger())
			property.setTaxAssesment(result.taxAssessment.toDouble())
			property.setYearBuilt((result.yearBuilt).toInteger())
			property.setLotSizeSqFt((result.lotSizeSqFt).toInteger())
			property.setFinishedSqFt((result.finishedSqFt).toInteger())
			property.setBathroom(result.bathrooms.toDouble())
			property.setBedroom(result.bedrooms.toDouble())
			property.setTotalRoom(result.totalRooms.toDouble())
			property.setLastSoldDate(result.lastSoldDate.toString())
			property.setLastSoldPrice(result.lastSoldPrice.toDouble())
			property.setZest_amt(result.zestimate.amount.toDouble()) 
			property.setZest_valueChange(result.zestimate.valueChange.toDouble())
			property.setZest_high(result.zestimate.high.toDouble())
			property.setZest_low(result.zestimate.low.toDouble())
			 
			println(property.getZipcode()) 
			property.save(flush:true)
			println(property.getErrors())
			
			result.erroCode = "success"
		}
		catch(Exception e)
		{
			println("Record not inserted" )
			result.erroCode = "failed"
		}
		
	}
	
	def public normalizeData(Double bathroom, Double bedroom,String city, Double fArea,Double lArea,Double lastSoldPrice, Double tax, String useCode, Double zestAmt,Double zestHigh, Double zestLow, Double zestVal, Double zpid )
	{	
		String result= "failure"
		try 
		{
			NormalizedSoldProperty property = new NormalizedSoldProperty();
			
			// Set external factors
			// These factors are manually entered from areavibes.com
			property = populateExternalFactors(property,city.replace(" ", ""))
			
			//populateInternal factors
			property = populateInternalFactors(property, bathroom, bedroom, fArea, lArea,lastSoldPrice,  tax,  useCode,  zestAmt, zestHigh,  zestLow,  zestVal,  zpid)
			
			property.setCity(city.replace(" ", "").toLowerCase())
			
			if(lastSoldPrice > zestAmt )
				property.setPriceAppreciated(true)
			else
				property.setPriceAppreciated(false)
			
				
			println(property.getAmenities());
			println(property.getCity())
			//println(property.getPriceAppreciated())
			property.save(flush:true)
			println(property.getErrors())
			if(property.getErrors().toString().contains(" 0 "))
				result = "success"
			else
			{
				println("Record not inserted")
				result = "failure"
			}
		}
		catch(Exception e)
		{	println(e.getMessage())
			e.printStackTrace()
			println("Record not inserted")
			result = "failure"
			return result
		}
		
		return result
	}
	
	def populateExternalFactors(NormalizedSoldProperty property,String city)
	{	
		
		switch(city.toLowerCase())
		{	
			// with reference to areavibes.com
			// <100 = 1
			// =100 = 2
			// <120 = 3
			// <140 = 4
			// >140 = 5
			// >160 = 6
			
			case "alameda":
				//property.setCostofliving(-3)
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(B)
				property.setEducation(C1)
				property.setEmployment(B)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "brisbane":
				//scaledCity = -3
				property.setAmenities(A)
				property.setCostofliving(F)
				property.setCrimerate(B1)
				property.setEducation(B)
				property.setEmployment(B1)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "belmont":
				//scaledCity = -6
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(B)
				property.setEmployment(A)
				property.setHousing(A1)
				property.setWeather(B1)
				break;
			case "campbell":
				//scaledCity = -5
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(C1)
				property.setEducation(C1)
				property.setEmployment(B1)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "dalycity":
				//scaledCity = -5
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(C)
				property.setEmployment(B1)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "eastpaloalto":
				//scaledCity = -4
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(D)
				property.setEducation(F)
				property.setEmployment(B)
				property.setHousing(A1)
				property.setWeather(B1)
				break;
			case "elgranada" :
				//scaledCity = -6
				property.setAmenities(D)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(B)
				property.setEmployment(A)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "felton" :
				//scaledCity = -4
				property.setAmenities(D)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(B)
				property.setEmployment(B)
				property.setHousing(A1)
				property.setWeather(C1)
				break;
			case "fostercity":
				//scaledCity = -6
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(B)
				property.setEmployment(A1)
				property.setHousing(A1)
				property.setWeather(B1)
				break;
			case "fremont" :
				//scaledCity = -6
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(C1)
				property.setEmployment(A)
				property.setHousing(A1)
				property.setWeather(B1)
				break;
			case "halfmoonbay" :
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(C)
				property.setEmployment(A)
				property.setHousing(A1)
				property.setWeather(B1)
				break;
			case "hillsborough":
				property.setAmenities(D)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(B)
				property.setEmployment(A1)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "laHonda":
				// evened out as no data available
				property.setAmenities(C)
				property.setCostofliving(C)
				property.setCrimerate(C)
				property.setEducation(C)
				property.setEmployment(C)
				property.setHousing(C)
				property.setWeather(C)
				break;
			case "livermore":
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(B)
				property.setEducation(C1)
				property.setEmployment(A)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "menlopark":
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(B)
				property.setEmployment(A)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "millbrae":
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(C1)
				property.setEmployment(B1)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "mossbeach" :
				property.setAmenities(F)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(A)
				property.setEmployment(A)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "mountainhouse":
				// evened out as no data available
				property.setAmenities(C)
				property.setCostofliving(C)
				property.setCrimerate(C)
				property.setEducation(C)
				property.setEmployment(C)
				property.setHousing(C)
				property.setWeather(C)
				break;
			case "mountainview":
				property.setAmenities(A1)
				property.setCostofliving(D)
				property.setCrimerate(A1)
				property.setEducation(B)
				property.setEmployment(B1)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "oakland" :
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(F)
				property.setEducation(D1)
				property.setEmployment(C1)
				property.setHousing(A)
				property.setWeather(B)
				break;
			case "pacifica":
				property.setAmenities(A)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(B)
				property.setEmployment(A)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "pleasanton":
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(B)
				property.setEmployment(A)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "redwoodcity" :
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A)
				property.setEducation(C)
				property.setEmployment(B1)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "sanbruno" :
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A)
				property.setEducation(C1)
				property.setEmployment(B1)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "sanfrancisco":
				//scaledCity = -6
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(F)
				property.setEducation(C)
				property.setEmployment(B)
				property.setHousing(A1)
				property.setWeather(C1)
				break;
			case "sancarlos":
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(B)
				property.setEmployment(A)
				property.setHousing(A1)
				property.setWeather(B1)
				break;
			case "sanjose":
				//scaledCity = -5
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(B)
				property.setEducation(C)
				property.setEmployment(B1)
				property.setHousing(A1)
				property.setWeather(B1)
				break;
			case "sanramon" :
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(B1)
				property.setEmployment(A1)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "sanmateo":
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A)
				property.setEducation(C1)
				property.setEmployment(B1)
				property.setHousing(A1)
				property.setWeather(B1)
				break;
			case "santaclara":
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A)
				property.setEducation(C1)
				property.setEmployment(B1)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "santacruz":
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(F)
				property.setEducation(C1)
				property.setEmployment(B)
				property.setHousing(A1)
				property.setWeather(C1)
				break;
			case "scottsvalley":
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A1)
				property.setEducation(B)
				property.setEmployment(A)
				property.setHousing(A1)
				property.setWeather(C1)
				break;
			case "southsanfrancisco" :
				property.setAmenities(A1)
				property.setCostofliving(F)
				property.setCrimerate(A)
				property.setEducation(C)
				property.setEmployment(B1)
				property.setHousing(A1)
				property.setWeather(B)
				break;
			case "tracy":
				property.setAmenities(A1)
				property.setCostofliving(D)
				property.setCrimerate(A)
				property.setEducation(C)
				property.setEmployment(B1)
				property.setHousing(A)
				property.setWeather(B1)
				break;
			case "":
				// if city attribute is null, average out the outliers
				property.setAmenities(C)
				property.setCostofliving(C)
				property.setCrimerate(C)
				property.setEducation(C)
				property.setEmployment(C)
				property.setHousing(C)
				property.setWeather(C)
				break;
			case null:
				// if city attribute is null, average out the outliers
				property.setAmenities(C)
				property.setCostofliving(C)
				property.setCrimerate(C)
				property.setEducation(C)
				property.setEmployment(C)
				property.setHousing(C)
				property.setWeather(C)
				break;
		}
		
		return property;
	}
	
	def populateInternalFactors(NormalizedSoldProperty property,Double  bathroom,Double  bedroom,Double fArea,Double lArea,Double lastSoldPrice, Double tax,String useCode,Double zestAmt,Double zestHigh,Double zestLow,Double zestVal, Double zpid)
	{	
		property.setZpID(zpid)
		
		//scale bedrooms and bathroom
		property = scaleBathRooms(property, bathroom)
		property = scaleRooms(property, bedroom)
		
		// scale finished and lot sq ft area
		property = scaleFArea(property,fArea)
		property = scaleLArea(property,lArea)
		
		//scale Usecode
		property = scaleUseCode(property,useCode)
		
		//Real and Double values like taz, zestimate not scaled
		property.setLastSoldPrice(lastSoldPrice)
		property.setTaxAssesment(tax)
		property.setZest_amt(zestAmt)
		property.setZest_high(zestHigh)
		property.setZest_low(zestLow)
		property.setZest_valueChange(zestVal)
		
		
		return property;
	}
	
	def scaleUseCode(NormalizedSoldProperty property, String useCode)
	{
		switch(useCode)
		{
			case "Condominium":
				property.setUseCode(B)
				break;
			case "SingleFamily":
				property.setUseCode(C)
				break;
			case "Condominium":
				property.setUseCode(B)
				break;
			case "Townhouse":
				property.setUseCode(B1)
				break;
			case "Duplex":
				property.setUseCode(A)
				break;
			case "MultiFamily2To4":
				property.setUseCode(A1)
				break;
			
			case "0":
				property.setUseCode(C)
				break;
			case 0:
				property.setUseCode(C)
				break;
			case null:
				property.setUseCode(C)
				break;
		}
		
		return property
	}
	
	def scaleRooms(NormalizedSoldProperty property, Double bedroom)
	{
		switch (bedroom)
		{
			case {it<= 1}:
				property.setBedroom(D)
				break;
			case {it = 2}:
				property.setBedroom(C)
				break;
			case {it = 3 }:
				property.setBedroom(C1)
				break;
			case {it = 4}:
				property.setBedroom(B)
				break;
			case {it = 5}:
				property.setBedroom(B1)
				break;
			case { it = 6}:
				property.setBedroom(A)
				break;
			case { it >= 7}:
				property.setBedroom(A1)
				break;
			
		}
		
		return property
	}
	
	def scaleBathRooms(NormalizedSoldProperty property, Double bathroom)
	{
		switch (bathroom)
		{
			case {it<= 1}:
				property.setBathroom(C)
				break;
			case {it <=1.5}:
				property.setBathroom(C1)
				break;
			case {it <=2}:
				property.setBathroom(B)
				break;
			case {it <=2.5}:
				property.setBathroom(B1)
				break;
			case {it <= 3}:
				property.setBathroom(A)
				break;
			case { it >= 3.5}:
				property.setBathroom(A1)
				break;
			
		}
		
		return property
	}
	
	def scaleFArea(NormalizedSoldProperty property, Double Farea)
	{
		switch (Farea)
		{
			case {it < 1500}:
				property.setFinishedSqFt(F)
				break;
			case {it <= 2000}:
				property.setFinishedSqFt(D)
				break;
			case {it <= 300}:
				property.setFinishedSqFt(D1)
				break;
			case {it <= 4000}:
				property.setFinishedSqFt(C)
				break;
			case {it <= 5000}:
				property.setFinishedSqFt(C1)
				break;
			case { it <= 8000}:
				property.setFinishedSqFt(B)
				break;
			case { it <= 15000}:
				property.setFinishedSqFt(B1)
				break;
			case { it <= 25000}:
				property.setFinishedSqFt(A)
				break;
			case { it > 25000}:
				property.setFinishedSqFt(A1)
				break;
			
		}
		
		return property
	}
	
	def scaleLArea(NormalizedSoldProperty property, Double Larea)
	{
		switch (Larea)
		{
			case {it < 1500}:
				property.setLotSizeSqFt(F)
				break;
			case {it <= 2000}:
				property.setLotSizeSqFt(D)
				break;
			case {it <= 300}:
				property.setLotSizeSqFt(D1)
				break;
			case {it <= 4000}:
				property.setLotSizeSqFt(C)
				break;
			case {it <= 5000}:
				property.setLotSizeSqFt(C1)
				break;
			case { it <= 8000}:
				property.setLotSizeSqFt(B)
				break;
			case { it <= 15000}:
				property.setLotSizeSqFt(B1)
				break;
			case { it <= 25000}:
				property.setLotSizeSqFt(A)
				break;
			case { it > 25000}:
				property.setLotSizeSqFt(A1)
				break;
			
		}
		
		return property
	}
}

