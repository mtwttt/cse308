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
    	return {fillColor: 'green', color: 'grey', weight: 1,opacity: 0.7};
    case '3': 
    	return {fillColor: 'white', color: 'grey', weight: 1, opacity: 0.7};
    case '4':   
    	return { fillColor: 'blue', color: 'grey', weight: 1, opacity: 0.7};
    default:   
    	return {fillColor: 'grey', color: 'grey', weight: 1, opacity: 0.7, 
    	fillOpacity: 0.7};
}
}

function onEachFeature(feature, layer) {
	layer.bindPopup("Pid: "+layer.feature.pid+"</br>"
			+"Name: "+layer.feature.properties.NAME10+"</br>"
			+"Population: "+layer.feature.properties.POP100+"</br>"
			+"rVote for President 2008: "+ layer.feature.properties.PRES_R_08+"</br>"
			+"dVote for President 2008: "+ layer.feature.properties.PRES_D_08);
}