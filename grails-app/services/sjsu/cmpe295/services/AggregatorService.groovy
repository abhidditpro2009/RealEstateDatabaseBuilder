package sjsu.cmpe295.services

import sjsu.cmpe295.models.Property

//import static grails.async.Promises.*
//import grails.async.Promise
//import grails.async.DelegateAsync


class AggregatorService {

	def property
    static transactional = true

//	@DelegateAsync ZillowService zillowService
	
    def getListings(query) {
		println("In class AggregatorService/getListings()");
//		Promise pZillow = task {
//			return zillowService.getSearchResults(query)
//		}
		/*Promise pTrulia = task {
			return truliaService.getSearchResults(query)
		}*/
		
		def zillowData = pZillow.get();
		println(zillowData)
		
		//def truliaData = pTrulia.get();
		
		//@TODO: aggregate and rank results here
		
		//property.save(zillowData) // save() is a predefined function for h2 database
		println("In class AggregatorService/last  getListings()");
    }
	
	def getDetails(query){
		//TODO: call all the underlining services eg: Zillowservice, truliaService etc
	}
}
