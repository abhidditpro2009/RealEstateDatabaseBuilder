package sjsu.cmpe295.services

import com.sun.org.apache.xml.internal.serialize.XMLSerializer
import grails.converters.JSON
import grails.converters.XML
import grails.plugins.rest.client.*
import grails.rest.*

import groovy.json.JsonBuilder
import org.codehaus.groovy.grails.web.json.JSONObject
import sjsu.cmpe295.models.Property;
import sjsu.cmpe295.models.SoldProperty


class ZillowService {

	static transactional = true
	
	def getSearchResults(IpZip,IpAddress) {
		println("In class ZillowService/getSearchResults()")
		//query = URLEncoder.encode(query, "UTF-8")
		//println(query)
		
		IpAddress = IpAddress.toString().replace(' ', '+')
		println("IPAddress: "+ IpAddress )
		def data =new URL("http://www.zillow.com/webservice/GetSearchResults.htm?zws-id=X1-ZWz1bcy5v58por_avwne&address="+IpAddress+"&citystatezip="+IpZip).getText()
		//def data = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=X1-ZWz1b9amgyx0y3_4n5nu&address="+IpAddress+"&citystatezip="+IpZip).getText()
		//def data = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=X1-ZWz1drkw2tiyob_3wguv&address="+IpAddress+"&citystatezip="+IpZip).getText()
		//def data = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=X1-ZWz1b8xkvm3ytn_3xvfc&address="+IpAddress+"&citystatezip="+IpZip).getText()
		//def data = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=X1-ZWz1drom8x6ozv_6jziz&address="+IpAddress+"&citystatezip="+IpZip).getText()
		//def data = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=X1-ZWz1drou51mwwb_6plsv&address="+IpAddress+"&citystatezip="+IpZip).getText()
		//def data = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=X1-ZWz1droy33v0uj_6sext&address="+IpAddress+"&citystatezip="+IpZip).getText()
		
		//def data = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=X1-ZWz1b8tqrg84jv_6o78e&address="+IpAddress+"&citystatezip="+IpZip).getText()
		//def data = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=X1-ZWz1droq6zesy3_6msnx&address="+IpAddress+"&citystatezip="+IpZip).getText()
		//def data = new URL("http://www.zillow.com/webservice/GetDeepSearchResults.htm?zws-id=X1-ZWz1b8p12sjeob_a1050&address="+IpAddress+"&citystatezip="+IpZip).getText()
		//def data = new URL("http://localhost:8080/RealEstate/static/mock_data/zillowData.xml").getText()
		
		// data of sanjose1 and sanjose7 remains to be added in DB
		
		// print the xml data on console
		println(data)
		
		def result = [:].withDefault {0 }//Create a hashmap
		
		
		//validate data
			
		if(data.contains("Request successfully processed"))
		{
			def xml = new XmlSlurper().parseText(data)
			
			
			// Address Attributes
			def address = [:].withDefault {0 }
			address.street = !xml.response.results.result.address.street.isEmpty() ? xml.response.results.result.address.street : "0"
			address.zipcode = !xml.response.results.result.address.zipcode.isEmpty() ? xml.response.results.result.address.zipcode : "0"
			address.city = !xml.response.results.result.address.city.isEmpty() ? xml.response.results.result.address.city  : "0"
			address.state = !xml.response.results.result.address.state.isEmpty() ? xml.response.results.result.address.state : "0"
			address.latitude = !xml.response.results.result.address.latitude.isEmpty()  ? xml.response.results.result.address.latitude : 0
			address.longitude = !xml.response.results.result.address.longitude.isEmpty()  ? xml.response.results.result.address.longitude : 0
			result.address = address
			
			//Independent Attributes
			
			result.useCode = !xml.response.results.result.useCode.isEmpty() ? xml.response.results.result.useCode : "0"
			result.taxAssessmentYear = xml.response.results.result.taxAssessmentYear != ''  ? xml.response.results.result.taxAssessmentYear : 0
			result.taxAssessment = xml.response.results.result.taxAssessment != ''  ? xml.response.results.result.taxAssessment : 0
			result.yearBuilt = !xml.response.results.result.yearBuilt.isEmpty() ? xml.response.results.result.yearBuilt : 0
			result.lotSizeSqFt = !xml.response.results.result.lotSizeSqFt.isEmpty() ? xml.response.results.result.lotSizeSqFt : 0
			result.finishedSqFt = !xml.response.results.result.lotSizeSqFt.isEmpty() ? xml.response.results.result.lotSizeSqFt : 0
			result.bathrooms = xml.response.results.result.bathrooms != '' ? xml.response.results.result.bathrooms : 0
			result.bedrooms = xml.response.results.result.bedrooms != '' ? xml.response.results.result.bedrooms : 0
			result.totalRooms = xml.response.results.result.totalRooms != '' ? xml.response.results.result.totalRooms : 0
			result.lastSoldDate = !xml.response.results.result.lastSoldDate.isEmpty() ? xml.response.results.result.lastSoldDate : "0"
			result.lastSoldPrice = !xml.response.results.result.lastSoldPrice.isEmpty() ? xml.response.results.result.lastSoldPrice : 0

			
			// Zestimate Attributes
			def zestimate = [:].withDefault {0 }
			zestimate.amount  = xml.response.results.result.zestimate.amount != '' ? xml.response.results.result.zestimate.amount : 0
			//zestimate.lastUpdated  = xml.response.results.result.zestimate.last-updated
			zestimate.valueChange   = xml.response.results.result.zestimate.valueChange != '' ? xml.response.results.result.zestimate.valueChange : 0
			// Valuation Range
			zestimate.low   = xml.response.results.result.zestimate.valuationRange.low != "" ? xml.response.results.result.zestimate.valuationRange.low : 0
			zestimate.high   = xml.response.results.result.zestimate.valuationRange.high != "" ? xml.response.results.result.zestimate.valuationRange.high : 0
			result.zestimate = zestimate
			// ZpId Attibute
			result.zpid = xml.response.results.result.zpid
			
			populateProperty(result)
		}
		else
			result.erroCode = "failed"
			println("Record not inserted")
			// Local Real Estate Attributes
			/*
			def localRealEstate = [:].withDefault {0 }
			localRealEstate.zindexValue = xml.response.results.result.localRealEstate.region.zindexValue != '' ? xml.response.results.result.localRealEstate.region.zindexValue : 0
			localRealEstate.zindexOneYearChange = xml.response.results.result.localRealEstate.region.zindexOneYearChange != '' ? xml.response.results.result.localRealEstate.region.zindexOneYearChange : 0
			result.localRealEstate = localRealEstate
			*/
			// Links data can be added Later ***IMP***
			
		return result
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
			//println("Record not inserted" )
			result.erroCode = "failed"
		}
		
	}
}

