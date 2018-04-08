var usRaphael = {};
var R = Raphael("container", 1000, 900),
      attr = {
      "fill": "#d3d3d3",
      "stroke": "#fff",
      "stroke-opacity": "1",
      "stroke-linejoin": "round",
      "stroke-miterlimit": "4",
      "stroke-width": "0.75",
      "stroke-dasharray": "none"
    };
var selectState = "";

window.onload = function () {
    //Draw Map and store Raphael paths
    for (var state in usMap) {
      usRaphael[state] = R.path(usMap[state]).attr(attr);
    }
  };

function setState(state){
  selectState = state;
  for (var state in usMap){
        usRaphael[state].color = Raphael.getColor();
        usRaphael[state].animate({fill: "#d3d3d3"}, 500);
        usRaphael[state].toFront();
        R.safari();
  }
  if(selectState == 'ut'){
        console.log(selectState);
        usRaphael[selectState].color = Raphael.getColor();
        usRaphael[selectState].animate({fill: "#000080"}, 500);
        usRaphael[selectState].toFront();
        R.safari();
  }else if (selectState == 'ks'){
        console.log(selectState);
        usRaphael[selectState].color = Raphael.getColor();
        usRaphael[selectState].animate({fill: "#8B0000"}, 500);
        usRaphael[selectState].toFront();
        R.safari();
  }else if (selectState == 'co'){
        console.log(selectState);
        usRaphael[selectState].color = Raphael.getColor();
        usRaphael[selectState].animate({fill: "#FFFF00"}, 500);
        usRaphael[selectState].toFront();
        R.safari();
  }
      offmouse();           
      //Do Work on Map
      usRaphael[selectState].color = Raphael.getColor();
      (function (st) {
        st[0].style.cursor = "pointer";
        st[0].onmouseover = function () {
          if(selectState == 'ut'){
            usRaphael[selectState].animate({fill: st.color}, 500);
            usRaphael[selectState].toFront();
            R.safari();
          }else if(selectState == 'co'){
            usRaphael[selectState].animate({fill: st.color}, 500);
            usRaphael[selectState].toFront();
            R.safari();
          }else if(selectState == 'ks'){
            usRaphael[selectState].animate({fill: st.color}, 500);
            usRaphael[selectState].toFront();
            R.safari();
          }
        };
        st[0].onmouseout = function () {
          if(selectState == 'ut'){
            usRaphael[selectState].color = Raphael.getColor();
            usRaphael[selectState].animate({fill: "#000080"}, 500);
            usRaphael[selectState].toFront();
            R.safari();
          }else if(selectState == 'co'){
            usRaphael[selectState].color = Raphael.getColor();
            usRaphael[selectState].animate({fill: "#FFFF00"}, 500);
            usRaphael[selectState].toFront();
            R.safari();
          }else if(selectState == 'ks'){
            usRaphael[selectState].color = Raphael.getColor();
            usRaphael[selectState].animate({fill: "#8B0000"}, 500);
            usRaphael[selectState].toFront();
            R.safari();
          } 
          st.toFront();
          R.safari();
        };

        st[0].onclick = function () {
          if(selectState == 'ut'){
	        	  var url = "http://localhost:8080/demo/congressional_districts";
	        	  var form = $('<form action="' + url + '" method="post">' +
	        	    '<input type="text" name="state" value="utah" />' +
	        	    '</form>');
	        	  $('body').append(form);
	        	  form.submit();	
          }else if(selectState == 'co'){
	        	  var url = "http://localhost:8080/demo/CD/congressional_districts";
	        	  var form = $('<form action="' + url + '" method="post">' +
	        	    '<input type="text" name="state" />' +
	        	    '</form>');
	        	  $('body').append(form);
	        	  form.submit();	
          }else if(selectState == 'ks'){
        	  	  var url = "http://localhost:8080/demo/CD/congressional_districts";
	        	  var form = $('<form action="' + url + '" method="post">' +
	  	        	    '<input type="text" name="state"/>' +
	  	        	    '</form>');
	  	        	  $('body').append(form);
	  	        	  form.submit();	
          } 
          st.toFront();
          R.safari();
        };

      })(usRaphael[selectState]);
     
}

function offmouse(){
    usRaphael['ut'][0].onmouseout = undefined;
    usRaphael['ut'][0].onmouseover = undefined;
    usRaphael['ut'][0].onclick = undefined;
    usRaphael['co'][0].onmouseout = undefined;
    usRaphael['co'][0].onmouseover = undefined;
    usRaphael['co'][0].onclick = undefined;
    usRaphael['ks'][0].onmouseout = undefined;
    usRaphael['ks'][0].onmouseover = undefined;
    usRaphael['ks'][0].onclick = undefined;
    console.log("run");
}

