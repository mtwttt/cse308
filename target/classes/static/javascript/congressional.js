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
    	return {fillColor: 'aqua', color: 'grey', weight: 1,opacity: 0.7};
    case '3': 
    	return {fillColor: 'olive', color: 'grey', weight: 1, opacity: 0.7};
    case '4':   
    	return { fillColor: 'yellow', color: 'grey', weight: 1, opacity: 0.7};
    case '5':   
    	return { fillColor: 'blue', color: 'grey', weight: 1, opacity: 0.7};
    case '6':   
    	return { fillColor: 'pink', color: 'grey', weight: 1, opacity: 0.7};
    case '7':   
    	return { fillColor: 'green', color: 'grey', weight: 1, opacity: 0.7};
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
    var selectpid = document.getElementById("selectpid");
    layer.on('click', function (e){
    		console.log(selected);
    		if(selected.indexOf(layer.feature.pid)!=-1){
    			 selected.splice( selected.indexOf(layer.feature.pid), 1 );
    			 layer.setStyle(unhighlight);
    			 selectpid.value = selected;
    		}else{
        		selected.push(layer.feature.pid);
        	    layer.setStyle(highlight);
        	    selectpid.value = selected;
    		}
    });
	
}

var flag = true;

var json_data = {};
json_data["year"] = 2008;
json_data["selectpid"] = selected;
var state = document.getElementById("state");

var ret = "";
function start(){
	state = document.getElementById("state");
	var population = document.getElementById("population");
	var racial = document.getElementById("racial");
	var partisan = document.getElementById("partisan");
	var compactness = document.getElementById("compactness");
	
	console.log(state.value);
	console.log(selected);
	for (var i = 0; i< selected.length;i++) {
	    ret = ret +selected[i]+",";
	}
	console.log(ret);
	$.ajax({
        type: "post",
        url: "http://localhost:8080/demo/redraw",
        data: { populationW: population.value,
			racialW: racial.value,
			partisanW: partisan.value,
			compactnessW : compactness.value,
			year: 2008,
			name: state.value,
			selectpid: ret},
        success: function (response) {
        		if(flag){
            		console.log("123");
            		updateMap(response,state.value);
            		start();
        		}else{
        			updateMap(response,state.value);
        			flag = true;
        		}
        },error: function (request, status, error) {
        		console.log("12345");
        }
    });		
}

function updateMap(pids,name){
    var state = "/json/"+name+".json";
    var maps = document.getElementById("map");
    console.log(pids);
    console.log(maps);
    var counties = $.ajax({
        url: state,
        dataType: "json",
        success: console.log("County data successfully loaded."),
        error: function(xhr) {
            alert(xhr.statusText)
        }
    })
    $.when(counties).done(function() {
    		console.log("xxxxxxxxxxxxxxxxxxxxx");
    		maps.remove();
    		document.getElementById('weathermap').innerHTML = "<div id='map' style='width: 100%; height: 100%;'></div>";
    		maps = document.getElementById("map");
    		console.log(maps);
        var map = L.map(maps)
            .setView([39.113014, -95.358887], 8);
		console.log("11111111111111111");
        var basemap =L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}', {
            attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, '
            +'<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © '
            +'<a href="http://mapbox.com">Mapbox</a>',
            maxZoom: 18,
            id: 'jmauro.ld1o2np1',
            accessToken: 'pk.eyJ1Ijoiam1hdXJvIiwiYSI6ImVYb0lheE0ifQ.Js4ba2SyUxHPCIDl1Aq1cQ'
        }).addTo(map);
        var kyCounties = L.geoJSON(counties.responseJSON,{style:function(feature) {     
		        		if(feature.pid in pids){
						if(pids[feature.pid] == 1){
							return { fillColor: 'red', color: 'black', weight: 1, opacity: 0.75};
						}else if (pids[feature.pid] == 2 ){
							return {fillColor: 'aqua', color: 'black', weight: 1,opacity: 0.7};
						}else if (pids[feature.pid] == 3){
							return {fillColor: 'olive', color: 'black', weight: 1, opacity: 0.7};
						}else if (pids[feature.pid] == 4){
							return { fillColor: 'yellow', color: 'black', weight: 1, opacity: 0.7};
						}else if (pids[feature.pid] == 5){
							return { fillColor: 'blue', color: 'black', weight: 1, opacity: 0.7};
						}else if (pids[feature.pid] == 6){
							return { fillColor: 'pink', color: 'black', weight: 1, opacity: 0.7};
						}else if (pids[feature.pid] == 7){
							return { fillColor: 'green', color: 'black', weight: 1, opacity: 0.7};
						}else{
							return {fillColor: 'grey', color: 'black', weight: 1, opacity: 0.7, 	fillOpacity: 0.7};
						}
		        		}
		        		else{
		        			switch (feature.properties.CONGRESSIO) {
			                case '1': 
			                	return { fillColor: 'red', color: 'grey', weight: 1, opacity: 0.75};
			                case '2':   
			                	return {fillColor: 'aqua', color: 'grey', weight: 1,opacity: 0.7};
			                case '3': 
			                	return {fillColor: 'olive', color: 'grey', weight: 1, opacity: 0.7};
			                case '4':   
			                	return { fillColor: 'yellow', color: 'grey', weight: 1, opacity: 0.7};
			                case '5':   
			                	return { fillColor: 'blue', color: 'grey', weight: 1, opacity: 0.7};
			                case '6':   
			                	return { fillColor: 'pink', color: 'grey', weight: 1, opacity: 0.7};
			                case '7':   
			                	return { fillColor: 'green', color: 'grey', weight: 1, opacity: 0.7};
			                default:   
			                	return {fillColor: 'grey', color: 'grey', weight: 1, opacity: 0.7, 
			                	fillOpacity: 0.7};
			            }
		        		}
          }, onEachFeature: onEachFeature}).addTo(map);
    });
}
/*
 *        data: { 
 *        		populationW: population.value,
        			racialW: racial.value,
        			partisanW: partisan.value,
        			compactnessW : compactness.value,
        			year: 2008,
        			name: state.value,
        			selectpid: selected},
 */
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

function continute(){
	flag = true;
}

function reset(){
	flag = true;
}