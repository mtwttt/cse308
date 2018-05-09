var styleMap = "123"	;
var state = document.getElementById("state");
var states = "/json/"+ state.value+".json";
var map = "tempMap";

var counties = $.ajax({
            url: states,
            dataType: "json",
            success: console.log("County data successfully loaded."),
            error: function(xhr) {
                alert(xhr.statusText);
            }
 })
$.when(counties).done(function() {
    map = L.map('map')
                .setView([39.113014, -95.358887], 5);
    var basemap =L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png'
            				+'?access_token={accessToken}', {
                attribution: 'Map data &copy; <a href="http://openstreetmap.org">'
                		+'OpenStreetMap</a> contributors, '
                		+'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, '
                		+'Imagery © <a href="http://mapbox.com">Mapbox</a>',
                maxZoom: 18,
                id: 'jmauro.ld1o2np1',
                accessToken: 'pk.eyJ1Ijoiam1hdXJvIiwiYSI6ImVYb0lheE0ifQ.Js4ba2SyUxHPCIDl1Aq1cQ'
            }).addTo(map);
     styleMap = L.geoJSON(counties.responseJSON,{style: style,
            			onEachFeature: onEachFeature}).addTo(map);
     
     
     
     
     var map2 = L.map('map2')
     .setView([39.113014, -95.358887], 5);
	var basemap2 =L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png'
	 				+'?access_token={accessToken}', {
	     attribution: 'Map data &copy; <a href="http://openstreetmap.org">'
	     		+'OpenStreetMap</a> contributors, '
	     		+'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, '
	     		+'Imagery © <a href="http://mapbox.com">Mapbox</a>',
	     maxZoom: 18,
	     id: 'jmauro.ld1o2np1',
	     accessToken: 'pk.eyJ1Ijoiam1hdXJvIiwiYSI6ImVYb0lheE0ifQ.Js4ba2SyUxHPCIDl1Aq1cQ'
	 }).addTo(map2);
	var styleMap2 = L.geoJSON(counties.responseJSON,{style: style2,
	 			onEachFeature: onEachFeature}).addTo(map2);
});
  
console.log("--------------");
console.log(pids);


function style(feature) {
	switch (feature.properties.CONGRESSIO) {
    case '1': 
    	return { fillColor: 'red', color: 'grey', weight: 1, opacity: 0.75};
    case '2':   
    	return {fillColor: '#9F06F2', color: 'grey', weight: 1,opacity: 0.7};
    case '3': 
    	return {fillColor: '#04F9FD', color: 'grey', weight: 1, opacity: 0.7};
    case '4':   
    	return { fillColor: 'yellow', color: 'grey', weight: 1, opacity: 0.7};
    case '5':   
    	return { fillColor: 'green', color: 'grey', weight: 1, opacity: 0.7};
    case '6':   
    	return { fillColor: 'blue', color: 'grey', weight: 1, opacity: 0.7};
    case '7':   
    	return { fillColor: '#FB03D0', color: 'grey', weight: 1, opacity: 0.7};
    default:   
    	return {fillColor: 'grey', color: 'grey', weight: 1, opacity: 0.7, 
    	fillOpacity: 0.7};
	}
}

function style2(feature) {
	if(feature.pid in pids){
		console.log("123")
		switch (pids[feature.pid]) {
		    case 1: 
		    	return { fillColor: 'red', color: 'grey', weight: 1, opacity: 0.75};
		    case 2:   
		    	return {fillColor: '#9F06F2', color: 'grey', weight: 1,opacity: 0.7};
		    case 3: 
		    	return {fillColor: '#04F9FD', color: 'grey', weight: 1, opacity: 0.7};
		    case 4:   
		    	return { fillColor: 'yellow', color: 'grey', weight: 1, opacity: 0.7};
		    case 5:   
		    	return { fillColor: 'green', color: 'grey', weight: 1, opacity: 0.7};
		    case 6:   
		    	return { fillColor: 'blue', color: 'grey', weight: 1, opacity: 0.7};
		    case 7:   
		    	return { fillColor: '#FB03D0', color: 'grey', weight: 1, opacity: 0.7};
		    default:   
		    	return {fillColor: 'grey', color: 'grey', weight: 1, opacity: 0.7, 
		    	fillOpacity: 0.7};
			}
	}else{
		switch (feature.properties.CONGRESSIO) {
	    case '1': 
	    	return { fillColor: 'red', color: 'grey', weight: 1, opacity: 0.75};
	    case '2':   
	    	return {fillColor: '#9F06F2', color: 'grey', weight: 1,opacity: 0.7};
	    case '3': 
	    	return {fillColor: '#04F9FD', color: 'grey', weight: 1, opacity: 0.7};
	    case '4':   
	    	return { fillColor: 'yellow', color: 'grey', weight: 1, opacity: 0.7};
	    case '5':   
	    	return { fillColor: 'green', color: 'grey', weight: 1, opacity: 0.7};
	    case '6':   
	    	return { fillColor: 'blue', color: 'grey', weight: 1, opacity: 0.7};
	    case '7':   
	    	return { fillColor: '#FB03D0', color: 'grey', weight: 1, opacity: 0.7};
	    default:   
	    	return {fillColor: 'grey', color: 'grey', weight: 1, opacity: 0.7, 
	    	fillOpacity: 0.7};
		}
	}
}

function onEachFeature(feature, layer) {
	layer.bindPopup("Pid: "+layer.feature.pid+"</br>"
			+"Name: "+layer.feature.properties.NAME10+"</br>"
			+"Population: "+layer.feature.properties.POP100+"</br>"
			+"rVote for President 2008: "+ layer.feature.properties.PRES_R_08+"</br>"
			+"dVote for President 2008: "+ layer.feature.properties.PRES_D_08);
	layer.on('mouseover', function (e) {
        this.openPopup();
    });
    layer.on('mouseout', function (e) {
        this.closePopup();
    });
    layer.on('click', function (e){
    		console.log(selected);
    		if(selected.indexOf(layer.feature.pid)!=-1){
    			 selected.splice( selected.indexOf(layer.feature.pid), 1 );
    			 layer.setStyle(unhighlight);
    		}else{
        		selected.push(layer.feature.pid);
        	    layer.setStyle(highlight);
    		}
    });
	
}
