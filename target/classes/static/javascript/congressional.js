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
    	return { fillColor: 'white', color: 'grey', weight: 1, opacity: 0.7};
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
var ret = "";
function start(){
	var state = document.getElementById("state");
	console.log(state.value);
	for (var i in selected) {
	    ret = i+",";
	}
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
            		console.log("123");
            		flag = false;
        },error: function (request, status, error) {
        		console.log("12345");
        }
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
}

function continute(){
	flag = true;
}

function reset(){
	
}