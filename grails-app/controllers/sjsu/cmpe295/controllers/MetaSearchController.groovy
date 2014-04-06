package sjsu.cmpe295.controllers

import sjsu.cmpe295.models.Property;
import sjsu.cmpe295.services.PropertyService;
import sjsu.cmpe295.services.PropertyService;

class MetaSearchController {
	def propertyService
	def aggregatorService
	def dataCollectorService
	def dataNormalizerService
	
	
    def index() {
		println("In class MetaSearchController/index()")
		redirect(action : 'home')
		println("In class MetaSearchController/last index()")
		
	}

	def home() {
		//construct home page
		println("In class MetaSearchController/home()")
		println("In class MetaSearchController/last home()")
		//dataCollectorService.getAddressesFromCsv();
		//dataNormalizerService.printRecords()
		dataCollectorService.generateNormalizedDataFromResultCsv();
	}
	
	def listings() {
		
		//fetch all necessary info and construct listing page
		println("In class MetaSearchController/listings()")
		def q = params.query
		aggregatorService.getListings(q)
		println("In class MetaSearchController/in listings() :" +q)
		def pList = Property.list()
		println("In class MetaSearchController/last listings()")
		[ query : q, properties : pList ] // data to be sent to the views
		
	}
	
	def details(){
		//@TODO: fetch property details and pass to the view
	}
}
