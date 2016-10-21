// Add your javascript here
$( function() {
  /* $('#dragbar').touchstart(function(e){
   e.preventDefault();
 $(document).touchmove(function(e){          
   //$('#sidebar').css("bottom",e.pageX+2);
   //$('#main').css("top",e.pageX+2);
	console.log("values:x"+e.pageX+"value of y"+e.pageY)	  
       })
    });
   $(document).touchend(function(e){
       $(document).unbind('touchmove');
   });*/
   $("#dragbar").on('mousedown', function(e) {
	    $(document).on('mousemove', function(e) {
	   //
	   if(e.pageX < 400){
		   $('.container').css({"opacity":"1","transition":"1s linear"});
		}else{
		   $('.container').css({"opacity":"0","transition":"1s linear"});
		}
	   //
		   $('#sidebar').css("width",e.pageX);
		   $('#main').css("left",e.pageX);
		   $('#center').css("left",e.pageX);
		});
   });
    $(document).mouseup(function(e){
       $(document).unbind('mousemove');
   });   
   
   $("#dragbar").on('touchstart', function(e) {
	    $(document).on('touchmove', function(e) {
	  
	     //
	   if(e.originalEvent.touches[0].pageY < 500){
		   $('.container').css({"opacity":"1","transition":"1s linear"});
		}else{
		   $('.container').css({"opacity":"0","transition":"1s linear"});
		}
	   //
			$('#sidebar').css("height",e.originalEvent.touches[0].pageY);
			$('#main').css("top",e.originalEvent.touches[0].pageY);
			$('#center').css("top",e.originalEvent.touches[0].pageY);
        });
   });
   $("#dragbar").on('touchend', function(e) {
       $(document).unbind('touchmove');
   });   
   
   //Form Action 
    
	$('#button').on("click", function (){
		$('#button').addClass("flip");
		$('#bg').addClass("fill");	
		});   
		
	$('#bg').on("webkitAnimationEnd",function(e){
		$('#parent').css({"border-radius":"100%","width": "50px"});
		$('#tick').css({"opacity":"1","transition":"2s linear"});
		$('#button').removeClass("flip");
		$('#bg').removeClass("fill");	
		$('#button').remove();
		setTimeout(function() {
			$('form').css({"opacity":"0","transition":"1s linear"});
            $('#tran').addClass("show");
		}, 1000);
	
    console.log("log at end of monkey animation");
    }); 
   
  });
	
	
	
	//mousedown  -- touchstart
	//mouseup    -- touchend
	//mousemove  -- touchmove
	
	