var styleMap = "123"	;
var state = document.getElementById("state");
var states = "/json/"+ state.value+".json";
var map = "tempMap";
var repeat = 0;
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
});
  
var population = document.getElementById("population");
var racial = document.getElementById("racial");
var partisan = document.getElementById("partisan");
var compactness = document.getElementById("compactness");
var output = document.getElementById("popu");
var output1 = document.getElementById("raci");
var output2 = document.getElementById("part");
var output3 = document.getElementById("comp");

output.innerHTML = population.value;
population.oninput = function() {
  output.innerHTML = this.value;
}

output1.innerHTML = racial.value;
racial.oninput = function() {
  output1.innerHTML = this.value;
}

output2.innerHTML = partisan.value;
partisan.oninput = function() {
  output2.innerHTML = this.value;
}

output3.innerHTML = compactness.value;
compactness.oninput = function() {
  output3.innerHTML = this.value;
}

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

var highlight = {
	    'color': 'black',
	    'weight': 2,
	    'opacity': 1
};

var unhighlight = {
	    'color': 'gray',
	    'weight': 1,
	    'opacity': 0.7
};

var selected = []

function onEachFeature(feature, layer) {
	var cdInfo = null
	if (layer.feature.properties.CONGRESSIO.valueOf() =="1"){
		cdInfo = cd1;
	}
	if (layer.feature.properties.CONGRESSIO.valueOf() =="2"){
		cdInfo = cd2;
	}
	if (layer.feature.properties.CONGRESSIO.valueOf() =="3"){
		cdInfo = cd3;
	}
	if (layer.feature.properties.CONGRESSIO.valueOf() =="4"){
		cdInfo = cd4;
	}
	if (layer.feature.properties.CONGRESSIO.valueOf() =="5"){
		cdInfo = cd5;
	}
	if (layer.feature.properties.CONGRESSIO.valueOf() =="6"){
		cdInfo = cd6;
	}
	if (layer.feature.properties.CONGRESSIO.valueOf() =="7"){
		cdInfo = cd7;
	}
	if(cdInfo != null){
	layer.bindPopup("Precinct Original Info: </br>"+"Pid: "+layer.feature.pid+"</br>"
			+"Name: "+layer.feature.properties.NAME10+"</br>"
			+"Population: "+layer.feature.properties.POP100+"</br>"
			+"rVote for President 2008: "+ layer.feature.properties.PRES_R_08+"</br>"
			+"dVote for President 2008: "+ layer.feature.properties.PRES_D_08+"</br>"+cdInfo);
	}
	else{
		layer.bindPopup("Precinct Original Info: </br>"+"Pid: "+layer.feature.pid+"</br>"
				+"Name: "+layer.feature.properties.NAME10+"</br>"
				+"Population: "+layer.feature.properties.POP100+"</br>"
				+"rVote for President 2008: "+ layer.feature.properties.PRES_R_08+"</br>"
				+"dVote for President 2008: "+ layer.feature.properties.PRES_D_08+"</br>");
	}
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

var flag = true;

var json_data = {};
json_data["year"] = 2008;
json_data["selectpid"] = selected;
var state = document.getElementById("state");

var ret = "";

function isEmpty(obj) {
	  return Object.keys(obj).length === 0;
}

function start(){
	state = document.getElementById("state");
	var population = document.getElementById("population");
	var racial = document.getElementById("racial");
	var partisan = document.getElementById("partisan");
	var compactness = document.getElementById("compactness");
	var contiguity = document.getElementById("contiguity");
	var representative = document.getElementById("representative");

	console.log(state.value);
	console.log(selected);
	for (var i = 0; i< selected.length;i++) {
	    ret = ret +selected[i]+",";
	}
	console.log(ret);
	if(flag){
		$.ajax({
	        type: "post",
	        url: "http://localhost:8080/demo/redraw",
	        data: { populationW: population.value,
				racialW: racial.value,
				partisanW: partisan.value,
				compactnessW : compactness.value,
				year: 2008,
				name: state.value,
				selectpid: ret,
				contiguity: contiguity.checked,
				representative: representative.checked},
	        success: function (response) {
	        		if( isEmpty(response) == false){
	        			repeat += 1;
	            		updateMap(response,state.value);
	            		start();
	        		}else{
	            		updateMap(response,state.value);
	            		alert("terminate");
	            		repeat = 0;
	        		}
	        },error: function (request, status, error) {
	        		console.log("12345");
	        }
	    		});		
	}else{
		flag = true;
	}
}

function updateMap(pids,name){
	console.log("123");
	styleMap.eachLayer( function (layer){
		if(layer.feature.pid in pids){
			if(pids[layer.feature.pid] == 1){
				layer.setStyle({ fillColor: 'red', color: 'black', weight: 1, opacity: 0.75});
			}else if (pids[layer.feature.pid] == 2 ){
				layer.setStyle({fillColor: '#9F06F2', color: 'black', weight: 1,opacity: 0.7});
			}else if (pids[layer.feature.pid] == 3){
				layer.setStyle({fillColor: '#04F9FD', color: 'black', weight: 1, opacity: 0.7});
			}else if (pids[layer.feature.pid] == 4){
				layer.setStyle({ fillColor: 'yellow', color: 'black', weight: 1, opacity: 0.7});
			}else if (pids[layer.feature.pid] == 5){
				layer.setStyle({ fillColor: 'green', color: 'black', weight: 1, opacity: 0.7});
			}else if (pids[layer.feature.pid] == 6){
				layer.setStyle({ fillColor: 'blue', color: 'black', weight: 1, opacity: 0.7});
			}else if (pids[layer.feature.pid] == 7){
				layer.setStyle({ fillColor: '#FB03D0', color: 'black', weight: 1, opacity: 0.7});
			}else{
				layer.setStyle({fillColor: 'grey', color: 'black', weight: 1, opacity: 0.7, 	fillOpacity: 0.7});
			}
		}
	});
}
function stop(){
	flag = false;
	$.ajax({
        type: "post",
        url: "http://localhost:8080/demo/stop",
        data: { stop: false},
        success: function (response) {
        		console.log("got it");
        },error: function (request, status, error) {
        		console.log("12345");
        }
    });	
}

function restore(){
	flag = true;
	$.ajax({
        type: "post",
        url: "http://localhost:8080/demo/stop",
        data: { stop: true},
        success: function (response) {
        		start();
        		console.log("got it");
        },error: function (request, status, error) {
        		console.log("12345");
        }
    });	
}

function reset(){
	state = document.getElementById("state");
	$.ajax({
        type: "post",
        url: "http://localhost:8080/demo/resetMap",
        data: { name: state.value},
        success: function (response) {
        		console.log("got it");
        },error: function (request, status, error) {
        		console.log("12345");
        }
    });	
	
	styleMap.eachLayer( function (layer){
			console.log(layer.feature.properties.CONGRESSIO);
			if(layer.feature.properties.CONGRESSIO == 1){
				layer.setStyle({ fillColor: 'red', color: 'grey', weight: 1, opacity: 0.75});
			}else if (layer.feature.properties.CONGRESSIO == 2 ){
				layer.setStyle({fillColor: '#9F06F2', color: 'grey', weight: 1,opacity: 0.7});
			}else if (layer.feature.properties.CONGRESSIO == 3){
				layer.setStyle({fillColor: '#04F9FD', color: 'grey', weight: 1, opacity: 0.7});
			}else if (layer.feature.properties.CONGRESSIO == 4){
				layer.setStyle({ fillColor: 'yellow', color: 'grey', weight: 1, opacity: 0.7});
			}else if (layer.feature.properties.CONGRESSIO == 5){
				layer.setStyle({ fillColor: 'green', color: 'grey', weight: 1, opacity: 0.7});
			}else if (layer.feature.properties.CONGRESSIO == 6){
				layer.setStyle({ fillColor: 'blue', color: 'grey', weight: 1, opacity: 0.7});
			}else if (layer.feature.properties.CONGRESSIO == 7){
				layer.setStyle({ fillColor: '#FB03D0', color: 'grey', weight: 1, opacity: 0.7});
			}else{
				layer.setStyle({fillColor: 'grey', color: 'grey', weight: 1, opacity: 0.7, 	fillOpacity: 0.7});
			}
	});
}


function findLocation(){
	var selectpid = parseInt(document.getElementById("precinctid").value);
	var lat = 0;
	var lon = 0;
	var zoom = 10;
	var area = 0;
	styleMap.eachLayer( function (layer){
		if(layer.feature.pid == selectpid){
			lat = layer.feature.properties.INTPTLAT10;
			lon = layer.feature.properties.INTPTLON10;
			area = layer.feature.properties.ALAND10 + layer.feature.properties.AWATER10;
			layer.openPopup();
		}
	});
	if(area < 700000){
		zoom = 12;
	}else if (area < 1000000){
		zoom = 11;
	}
	console.log(lat);
	console.log(lon);
	console.log(selectpid);
	console.log(zoom);
	map.setView(new L.LatLng(lat,lon), zoom);

}

function movePrecinct(){
	var moveP = parseInt(document.getElementById("moveP").value);
	$.ajax({
        type: "post",
        url: "http://localhost:8080/demo/moveP",
        data: { moveP: moveP},
        success: function (response) {
        		updatePrecinct(response,moveP);
        },error: function (request, status, error) {
        		console.log("12345");
        }
    });
}


function updatePrecinct(CD,precinct){
	console.log("123");
	if(precinct != -1){
	styleMap.eachLayer( function (layer){
		if(layer.feature.pid == precinct){
			if(CD == 1){
				layer.setStyle({ fillColor: 'red', color: 'grey', weight: 1, opacity: 0.75});
			}else if (CD == 2 ){
				layer.setStyle({fillColor: '#9F06F2', color: 'grey', weight: 1,opacity: 0.7});
			}else if (CD == 3){
				layer.setStyle({fillColor: '#04F9FD', color: 'grey', weight: 1, opacity: 0.7});
			}else if (CD == 4){
				layer.setStyle({ fillColor: 'yellow', color: 'grey', weight: 1, opacity: 0.7});
			}else if (CD == 5){
				layer.setStyle({ fillColor: 'green', color: 'grey', weight: 1, opacity: 0.7});
			}else if (CD == 6){
				layer.setStyle({ fillColor: 'blue', color: 'grey', weight: 1, opacity: 0.7});
			}else if (CD == 7){
				layer.setStyle({ fillColor: '#FB03D0', color: 'grey', weight: 1, opacity: 0.7});
			}else{
				layer.setStyle({fillColor: 'grey', color: 'grey', weight: 1, opacity: 0.7, 	fillOpacity: 0.7});
				}
			}
		});
	}
}

