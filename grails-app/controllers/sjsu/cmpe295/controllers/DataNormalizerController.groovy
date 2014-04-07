package sjsu.cmpe295.controllers

import sjsu.cmpe295.models.Property;


class DataNormalizerController {
	def dataCollectorService

	
	
    def index() {
		println("In class DataPopulaterController.groovy/index()")
		redirect(action : 'home')
	}

	def home() {
		//construct home page
		println("In class DataPopulaterController.groovy/home()")
		dataCollectorService.generateNormalizedDataFromResultCsv();
	}
	
	def details(){
		//@TODO: fetch property details and pass to the view
	}
}
