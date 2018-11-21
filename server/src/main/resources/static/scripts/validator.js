$(document).on("focusout","#account-number",() => {
    var x = document.getElementById("account-number").value; 
    //TODO: Validating bank account number hashes if are correct
    if(x.match(/^[0-9]+$/) && x.length==16) {
        document.getElementById("response").innerHTML = "Number ok"; 
        document.getElementById("response").style.color = "green";
        $('#singlebutton').attr("disabled", false);

    }
    else if (x.length>16){
        document.getElementById("response").innerHTML = "Number too long"; 
        document.getElementById("response").style.color = "red";
        $('#singlebutton').attr("disabled", true);
    }
    else if(x.length<16) {
        document.getElementById("response").innerHTML = "Number too short"; 
        document.getElementById("response").style.color = "red";
        $('#singlebutton').attr("disabled", true);

    }
    else {
        document.getElementById("response").innerHTML = "Account is not a number"; 
        document.getElementById("response").style.color = "red";
        $('#singlebutton').attr("disabled", true);

    }
}); 