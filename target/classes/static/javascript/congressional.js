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
    return {
        fillColor: 'red',
        color: 'grey',
    };
}

function onEachFeature(feature, layer) {
	layer.bindPopup(layer.feature.properties.STATEFP+"</br>"+layer.feature.properties.COUNTYFP);
}