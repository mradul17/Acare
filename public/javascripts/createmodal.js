
window.onload = function() 
    {

 
          $("#myModal").modal({
            backdrop: 'static',
            keyboard: false
            }).addClass("fade");
    }


     function change(){
 $("#myModal").removeClass("fade").modal("hide");
   $("#myModal1").modal({
  backdrop: 'static',
  keyboard: false
}).addClass("fade");
};

function change1(){
 $("#myModal1").removeClass("fade").modal("hide");
   $("#myModal2").modal({
  backdrop: 'static',
  keyboard: false
}).addClass("fade");
};

function back(){
 $("#myModal1").removeClass("fade").modal("hide");
   $("#myModal").modal({
  backdrop: 'static',
  keyboard: false
}).addClass("fade");
};

function back1(){
 $("#myModal2").removeClass("fade").modal("hide");
    $("#myModal1").modal({
  backdrop: 'static',
  keyboard: false
}).addClass("fade");
};
