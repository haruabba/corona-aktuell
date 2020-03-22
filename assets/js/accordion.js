var acc = document.getElementsByClassName("accordion");
var i;

for (i = 0; i < acc.length; i++) {
  acc[i].addEventListener("click", function() {
    this.classList.toggle("active");
    var panel = this.nextElementSibling;  

    
    if (panel.style.maxHeight){
      panel.previousElementSibling.lastElementChild.lastElementChild.firstElementChild.className = "plus vertical-line";
      panel.style.maxHeight = null;
    } else {
              for(var it of acc) {
                it.lastElementChild.lastElementChild.firstElementChild.className = "plus vertical-line";
        it.classList.remove("active");
        it.nextElementSibling.style.maxHeight = null;
        
    
        }
      panel.previousElementSibling.lastElementChild.lastElementChild.firstElementChild.className = "plus";
      panel.style.maxHeight = panel.scrollHeight + "px";
    } 
  });
}