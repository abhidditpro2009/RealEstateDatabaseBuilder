package sjsu.cmpe295.services

import com.xlson.groovycsv.CsvParser

import sjsu.cmpe295.models.Property

class DataCollectorService {
	def zillowService
	def dataNormalizerService
	
	def getAddressesFromCsv()
	{	
		def noOfRecordsInserted = 0
		def noOfZillowCalls = 0
		def csvIterator
		def csvFileObject = new File( "E:\\CMPE295B\\data\\SanJose2.csv" ).withReader {
			csvIterator = CsvParser.parseCsv( it )
			csvIterator.each {
				def res = zillowService.getSearchResults(it[0], it[1])
				noOfZillowCalls++;
				if(res.erroCode.equalsIgnoreCase("success"))
					noOfRecordsInserted++;
			  }
		  }
		
		println("Total number of records inserted: "+ noOfRecordsInserted)
		println("Total number of zillow calls made: "+ noOfZillowCalls)
	}
	
	def generateNormalizedDataFromResultCsv()
	{
		def noOfRecordsScanned = 0
		def noOfRowsInserted = 0
		def csvIterator
		def csvFileObject = new File( "E:\\CMPE295B\\results\\soldResult.csv" ).withReader {
			csvIterator = CsvParser.parseCsv( it )
			csvIterator.each {
				String res = dataNormalizerService.normalizeData(it[3].toString().toDouble(),
																it[4].toString().toDouble(),it[5].toString(),it[6].toString().toDouble(),it[8].toString().toDouble(),
																it[11].toString().toDouble(),it[13].toString().toDouble(),it[16].toString(),
																it[18].toString().toDouble(),it[19].toString().toDouble(),it[20].toString().toDouble(),it[21].toString().toDouble(),
																it[23].toString().toDouble());
				noOfRecordsScanned++;
				if(res.equalsIgnoreCase("success"))
					{
						noOfRowsInserted++;
						println("Record no:"+noOfRowsInserted+" inserted")
					}
			  }
		  }
		
		println("Total number of records scanned: "+ noOfRecordsScanned)
		println("Total number of records inserted: "+ noOfRowsInserted)
	}
}
