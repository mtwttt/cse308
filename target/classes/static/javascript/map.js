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
  if(selectState == 'id'){
        usRaphael[selectState].color = Raphael.getColor();
        usRaphael[selectState].animate({fill: "#000080"}, 500);
        usRaphael[selectState].toFront();
        R.safari();
  }else if (selectState == 'ks'){
        usRaphael[selectState].color = Raphael.getColor();
        usRaphael[selectState].animate({fill: "#8B0000"}, 500);
        usRaphael[selectState].toFront();
        R.safari();
  }else if (selectState == 'co'){
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
          if(selectState == 'id'){
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
          if(selectState == 'id'){
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
          if(selectState == 'id'){
	        	  var url = "http://localhost:8080/demo/CD";
	        	  var form = $('<form action="' + url + '" method="post">' +
	        	    '<input type="text" name="name" value="idaho" />' +
	        	    '</form>');
	        	  $('body').append(form);
	        	  form.submit();	
          }else if(selectState == 'co'){
	        	  var url = "http://localhost:8080/demo/CD";
	        	  var form = $('<form action="' + url + '" method="post">' +
	        	    '<input type="text" name="name" value="colorado" />' +
	        	    '</form>');
	        	  $('body').append(form);
	        	  form.submit();	
          }else if(selectState == 'ks'){
        	  	  var url = "http://localhost:8080/demo/CD";
	        	  var form = $('<form action="' + url + '" method="post">' +
	  	        	    '<input type="text" name="name" value= "kansas"/>' +
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
    usRaphael['id'][0].onmouseout = undefined;
    usRaphael['id'][0].onmouseover = undefined;
    usRaphael['id'][0].onclick = undefined;
    usRaphael['co'][0].onmouseout = undefined;
    usRaphael['co'][0].onmouseover = undefined;
    usRaphael['co'][0].onclick = undefined;
    usRaphael['ks'][0].onmouseout = undefined;
    usRaphael['ks'][0].onmouseover = undefined;
    usRaphael['ks'][0].onclick = undefined;
}

