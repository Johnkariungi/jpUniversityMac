$(document).ready(function() {
    $(".cartItemQty").on('change', function() { /*update input button*/
    var id = this.id;
    $('#update-item-' + id).css('display', 'inline-block'); /*id of the button to display*/
    });
});