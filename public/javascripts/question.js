$(document).ready(function(){
    var qN = 0;
    var bQC = 0;
    var mQC = 0;
    var fQC = 0;
    var cN = [];
    $("#addquestion").click(function () {
        var newCarePlanDiv = $(document.createElement('div')).attr("id", 'questionNumber' + qN);
        if(document.getElementById('question').value == 'Boolean'){
            newCarePlanDiv.after().html(
                '<div id = "bool" class = "form-group">'+
                    '<label class = "col-md-1 control-label" style = "text-align:right;">'+(qN+1)+'</label>'+
                    '<div class = "col-md-5">'+
                        '<input id="bQC" name = "booleanQuestion['+bQC+'][question]" type = "text" class = "form-control" placeholder = "Boolean Question">'+
                        '<input name = "booleanQuestion['+bQC+'][questionNumber]" type = "text" value = "'+(qN+1)+'" hidden>'+
                        '<input name = "booleanQuestion['+bQC+'][choices][0][index]" type = "text" value = "1" hidden>'+
                        '<input name = "booleanQuestion['+bQC+'][choices][0][value]" type = "text" value = "Yes" hidden>'+
                        '<input name = "booleanQuestion['+bQC+'][choices][1][index]" type = "text" value = "2" hidden>'+
                        '<input name = "booleanQuestion['+bQC+'][choices][1][value]" type = "text" value = "No" hidden>'+
                    '</div>'+
                    '<div class = "col-md-2">'+
                        '<label class = "control-label">1:Yes 2:No</label>'+
                    '</div>'+
                    '<div class = "col-md-2">'+
                        '<input id="bQC" name = "booleanQuestion['+bQC+'][dependOnPreviousQuestion]" type = "text" class = "form-control" placeholder = "Question Number">'+
                    '</div>'+
                    '<div class = "col-md-2">'+
                        '<input id="bQC" name = "booleanQuestion['+bQC+'][previousQuestionAnswerShouldBe]" type = "text" class = "form-control" placeholder = "Response">'+
                    '</div>'+
                '</div>'
            );
            newCarePlanDiv.appendTo("#questiongroup");
            $("#removequestion").val("bQC");
            bQC++;
        }
        else if(document.getElementById('question').value=='Multiple'){
            newCarePlanDiv.after().html(
                '<div class = "form-group">'+
                    '<label class = "col-md-1" control-label" style = "text-align:right;">'+(qN+1)+'</label>'+
                    '<div class = "col-md-6">'+  
                        '<input id="mQC" type = "text" name = "multipleChoiceQuestion['+mQC+'][question]" class = "form-control" placeholder = "Multiple choice question"/>'+
                        '<input name = "multipleChoiceQuestion['+mQC+'][questionNumber]" type = "text" value = "'+(qN+1)+'" hidden>'+
                    '</div>'+
                    '<div class = "col-md-1"></div>'+
                    '<div class = "col-md-2">'+
                        '<input id="mQC" name = "multipleChoiceQuestion['+mQC+'][dependOnPreviousQuestion]" type = "text" class = "form-control" placeholder = "Question Number">'+
                    '</div>'+
                    '<div class = "col-md-2">'+
                        '<input id="mQC" name = "multipleChoiceQuestion['+mQC+'][previousQuestionAnswerShouldBe]" type = "text" class = "form-control" placeholder = "Response">'+
                    '</div>'+
                '</div>'+
                '<div id = "optiongroup'+mQC+'" >'+
                    '<div class = "form-group">'+
                        '<label class = "col-md-2" control-label" style="text-align:right;">1</label>'+
                        '<div class = "col-md-2">'+
                            '<input name = "multipleChoiceQuestion['+mQC+'][choices][0][index]" type = "text" value = "0" hidden>'+
                            '<input name = "multipleChoiceQuestion['+mQC+'][choices][0][value]" type = "text" class = "form-control" placeholder = "Choice-1">'+
                        '</div>'+
                        '<button type = "button" class = "btn btn-default addchoice" value = "'+mQC+'" id = "addchoice">'+
                            '<span class = "glyphicon glyphicon-plus"></span>'+
                        '</button>'+
                        '<button type="button" id="removechoice" value= "'+mQC+'" class="btn btn-default removechoice">'+
                            '<span class="glyphicon glyphicon-minus"></span>'+
                        '</button>'+
                    '</div>'+
                '</div>'
            );

            newCarePlanDiv.appendTo("#questiongroup");
            $("#removequestion").val("mQC");
            cN[mQC] = 0;
            mQC++; 
        }
        else {
            newCarePlanDiv.after().html(
                '<div id = "free" class = "form-group">'+
                    '<label class = "col-md-1" control-label" style="text-align:right;">'+(qN+1)+'</label>'+
                    '<div class = "col-md-6">'+
                        '<input id="fQC" type = "text" name = "freeQuestion['+fQC+'][question]" class = "form-control" placeholder = "Question"/>'+
                        '<input name = "freeQuestion['+fQC+'][questionNumber]" type = "text" value = "'+(qN+1)+'" hidden>'+
                    '</div>'+
                    '<div class = "col-md-1"></div>'+
                    '<div class = "col-md-2">'+
                        '<input id="fQC" name = "freeQuestion['+fQC+'][dependOnPreviousQuestion]" type = "text" class = "form-control" placeholder = "Question Number">'+
                    '</div>'+
                    '<div class = "col-md-2">'+
                        '<input id="fQC" name = "freeQuestion['+fQC+'][previousQuestionAnswerShouldBe]" type = "text" class = "form-control" placeholder = "Response">'+
                    '</div>'+
                '</div>'
            );
            newCarePlanDiv.appendTo("#questiongroup");
            $("#removequestion").val("fQC");
            fQC++;
        }
        qN++;
    });

    $("#removequestion").click(function (){    
        if(qN == 0){
            alert("No more question to remove");
            return false;
        }   
        qN--;
        var id = $("#questionNumber"+qN).find('input:text').attr("id");
        if(id=="bQC"){
            bQC--;
        }
        else if(id=="mQC"){
            mQC--;
        }
        else if(id=="fQC"){
            fQC--;
        }
        $("#removequestion").val(id);
        $('#questionNumber'+qN).remove();
    });

    $("body").on('click',".addchoice" , function() {
        var gN = $(this).val();
        cN[gN]++;
        $('#optiongroup'+ gN).append(
            '<div class="form-group">'+
                '<label class = "col-md-2" control-label" style="text-align:right;">'+(cN[gN]+1)+'</label>'+
                '<div class="col-md-2">'+
                    '<input name="multipleChoiceQuestion['+gN+'][choices]['+cN[gN]+'][index]" type="text" value="'+cN[gN]+'" hidden>'+
                    '<input name="multipleChoiceQuestion['+gN+'][choices]['+cN[gN]+'][value]" type="text" class="form-control" placeholder="choice-'+(cN[gN]+1)+'">'+
                '</div>'+
            '</div>'
        );
    });

    $("body").on('click',".removechoice", function() { 
        var gN = $(this).val();
        if(cN[gN]==0){
            alert("can't remove");
            return false;
        }
        cN[gN]--;
        $("#optiongroup"+gN).children("div:last").remove();
    });
});