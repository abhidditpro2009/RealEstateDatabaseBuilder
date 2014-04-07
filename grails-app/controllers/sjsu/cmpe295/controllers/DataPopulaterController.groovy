package sjsu.cmpe295.controllers

import sjsu.cmpe295.models.Property;


class DataPopulaterController {

	def dataCollectorService
	
	
    def index() {
		println("In class DataPopulaterController/index()")
		redirect(action : 'home')
		println("In class DataPopulaterController/last index()")
		
	}

	def home() {
		//construct home page
		println("In class DataPopulaterController/home()")
		println("In class DataPopulaterController/last home()")
		dataCollectorService.getAddressesFromCsv();
	}
	
	def details(){
		//@TODO: fetch property details and pass to the view
	}
}
