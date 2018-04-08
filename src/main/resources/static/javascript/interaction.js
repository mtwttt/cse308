var dropdown = document.getElementsByClassName("dropdown-btn");
var i;

for (i = 0; i < dropdown.length; i++) {
      console.log("wtf");
      dropdown[i].addEventListener("click", function() {
        this.classList.toggle("active");
        var dropdownContent = this.nextElementSibling;
        if (dropdownContent.style.display === "block") {
          dropdownContent.style.display = "none";
        } else {
          dropdownContent.style.display = "block";
        }
      });
}



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
