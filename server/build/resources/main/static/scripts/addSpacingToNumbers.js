$(document).ready( function() {
    var contents = $('.spacing-number').get();
    console.log(contents);
    contents.forEach(addSpacing);
});

function addSpacing(element, index, array) {
    var accountNumber = element.innerHTML;
    console.log(accountNumber);
    element.innerHTML = (accountNumber.slice(0, 4) + '  ' + accountNumber.slice(4, 8) + '  ' + accountNumber.slice(8,12) + '  ' + accountNumber.slice(12,16));
}
