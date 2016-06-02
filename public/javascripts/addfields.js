$(document).ready(function(){
    var counter = 1;
    medication();
    route(); 
    datepick(); 

    function medication() {
  
        $( ".mname" ).keyup(function() {
	        var dInput = $(this).val();
	        if(dInput.length>1) {
	            $(function() {
	              	var arr = [];
	              	$.getJSON("/medication?text="+dInput, function(data) {
	                	$( ".mname" ).autocomplete({
		                  	source: data,
		                  	focus: function( event, ui ) {
		                  		$(this).val(ui.item.label);
		                    	return false;
		                  	},
		                  	select: function( event, ui ) {
		                    	$(this).val(ui.item.label);
		                    	var i = $(this).index('input:text');
		                    	if(i>13)
		                      		var count=i%13;
		                    	else
		                      		count="";
		                    	$( "#mcode"+count).val( ui.item.value );
		                    	$( "#mcodeSystem"+count).val( "2.16.840.1.113883.6.88" );
		                    	$( "#mcodeSystemname"+count).val( "RxNorm" );
		                    	return false;
		                  	},
	                	});
	              	});
	            });
	        }
        });
    }  

    function route() {
        $( ".rname" ).keyup(function() {
          	var dInput = $(this).val();
          	if(dInput.length>1)	{
            	$(function(){
	              	var arr = [];
	              	$.getJSON("/route?text="+dInput, function(data) {
	                	$( ".rname" ).autocomplete({
	                  		source: data,
	                  		focus: function( event, ui ) {
	                  			$(this).val(ui.item.label);
	                    		return false;
	                  		},
	                  		select: function( event, ui ) {
	                    		$(this).val(ui.item.label);
	                    		var i = $(this).index('input:text');
	                    		if(i>17)
	                      			var count=(i-4)%13;
	                    		else
	                      			count="";
	                    		$( "#rcode"+count).val( ui.item.value );
	                    		$( "#rcodeSystem"+count).val( ui.item.label1 );
	                    		$( "#rcodeSystemname"+count).val( ui.item.label2 );
	                    		return false;
	                  		},
	                	});
	              	});
            	});
          	}
        });
    }

    function datepick() {
        $( ".startdate" ).datepicker();
        $( ".enddate" ).datepicker();
    }
        
    $("#addButton").click(function () {  
        
        var newCarePlanDiv = $(document.createElement('div')).attr("id", 'careplan' + counter).addClass("col-sm-5 col-md-12");           
        newCarePlanDiv.after().html('<div class="form-group" ><div class="col-md-2"><label>Medicaton</label></div><div class="col-md-3"><input class="mname form-control" type="text" id="mname'+counter+'" placeholder="Name" name="medicationName['+counter+'][product][mname]"></div><div class="col-md-2"><input class="mcode form-control " type="text" placeholder="Code" id="mcode'+counter+'" name="medicationName['+counter+'][product][mcode]" readonly></div><div class="col-md-2"><input class="mcodeSystem form-control" id= "mcodeSystem'+counter+'" type="text" placeholder="CodeSystem" name="medicationName['+counter+'][product][msystemcode]" readonly></div><div class="col-md-3"><input class="mcodeSystemname form-control" id="mcodeSystemname'+counter+'" type="text" placeholder="CodeSystemName" name="medicationName['+counter+'][product][msystemcodename]" readonly></div></div><div class="form-group" ><div class="col-md-2"><label>Route:</label></div><div class="col-md-3"><input class="form-control rname" type="text" placeholder="Name" id="rname'+counter+'" name="medicationName['+counter+'][route][rname]"></div><div class="col-md-2"><input class="form-control rcode" type="text" placeholder="Code" id="rcode'+counter+'" name="medicationName['+counter+'][route][rcode]" readonly></div><div class="col-md-2"><input class="form-control rcodeSystem" type="text" placeholder="CodeSystem" id="rcodeSystem'+counter+'" name="medicationName['+counter+'][route][rsystemcode]" readonly></div><div class="col-md-3"><input class="form-control rcodeSystemname" type="text" placeholder="Codesystemname" id="rcodeSystemname'+counter+'" name="medicationName['+counter+'][route][rsystemcodename]" readonly></div></div><div class="form-group" ><div class="col-md-2"><label>Dose:</label></div><div class="col-md-3"><input class="form-control" type="text" placeholder="Quantity" id="dquantity" name="medicationName['+counter+'][dose][quantity]"></div><div class="col-md-2"><input class="form-control" type="text" placeholder="Units" id="dunits" name="medicationName['+counter+'][dose][dunits]"></div><div class="col-md-2"> <label>Start Date:</label></div><div class="col-md-3"><input class="startdate form-control" type="text" placeholder="Start date" id="startdate" name="medicationName['+counter+'][time][startdate]"></div></div><div class="form-group" ><div class="col-md-2"><label>Frequency:</label></div><div class="col-md-3"><input class="form-control" type="text" placeholder="Peried" id="fperied" name="medicationName['+counter+'][frequency][period]"></div><div class="col-md-2"><input class="form-control" type="text" placeholder="Units"id="funits" name="medicationName['+counter+'][frequency][funits]"></div> <div class="col-md-2"><label>End Date:</label></div><div class="col-md-3"><input class="enddate form-control" type="text" placeholder="End date" id="enddate" name="medicationName['+counter+'][time][enddate]"></div></div>');      
        newCarePlanDiv.appendTo("#CarePlanGroup");
        counter++; 
        $(".mname").each(function() {
            medication();
        });
        $(".rname").each(function() {
            route();
        });
        $(".enddate").each(function() {
            datepick();
        });
        $(".startdate").each(function() {
            datepick();
        });
    });

    $("#removeButton").click(function () {
        if(counter==1) {
            alert("No more textbox to remove");
            return false;
        }         
        counter--;   
        $("#careplan" + counter).remove();  
    });
});