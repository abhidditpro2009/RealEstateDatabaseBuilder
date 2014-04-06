function loadMap2(address) {

	alert(address)
	var lat = 37.37172
	var lon = -122.03801

	var usCenterPoint = new GLatLng(lat, lon)
	var usZoom = 14
	if (GBrowserIsCompatible()) {
		var map = new GMap2(document.getElementById("map-canvas"))
		map.setCenter(usCenterPoint, usZoom)
		map.addControl(new GLargeMapControl())
		map.addControl(new GMapTypeControl()) 

		var marker = new GMarker(new GLatLng(lat, lon), {title:address})
		marker.bindInfoWindowHtml(address)
		map.addOverlay(marker)
	}
}


//Reference https://developers.google.com/maps/documentation/javascript/geocoding

var geocoder;
var map;
function initialize() {
  geocoder = new google.maps.Geocoder();
  //var latlng = new google.maps.LatLng(-34.397, 150.644);
  var mapOptions = {
    zoom: 12,
    //center: latlng,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  }
  map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
}

function codeAddress() {
	if(!geocoder || !map) initialize();
	
	  var address = document.getElementById('address').innerText;	//check outer browsers
	 
	  geocoder.geocode( { 'address': address}, function(results, status) {
	    if (status == google.maps.GeocoderStatus.OK) {
	      map.setCenter(results[0].geometry.location);
	      var marker = new google.maps.Marker({
	          map: map,
	          position: results[0].geometry.location
	      });
	      propertyMarkers(null);
	    } else {
	      alert('Geocode was not successful for the following reason: ' + status);
	    }
	  });
}

function propertyMarkers(properties){
	if(geocoder && map) {
	    var pinImage = new google.maps.MarkerImage("http://localhost:8080/RealEstate/static/images/marker_blue.png");//"${resource(dir : 'images', file : 'marker_blue.png')}");
	    
		var pos = new google.maps.LatLng(37.40074, -122.01629);
		var marker = new google.maps.Marker({
	          map: map,
	          position: pos,
	          icon: pinImage
	     });
	}
}

google.maps.event.addDomListener(window, 'load', codeAddress);