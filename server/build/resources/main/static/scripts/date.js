window.onload = setInterval(showDate,1000);

function showDate() {
    var dt = new Date();
    document.getElementById("dateTime").innerHTML = dt.toLocaleString();
}