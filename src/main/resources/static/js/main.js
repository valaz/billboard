$(document).ready(function () {
    $(".random-color").each(function() {
        var hue = 'rgb(' + (Math.floor((256-199)*Math.random()) + 200) + ',' + (Math.floor((256-199)*Math.random()) + 200) + ',' + (Math.floor((256-199)*Math.random()) + 200) + ')';
        $(this).css("background-color", hue);
    });
});

$( ".clickable" ).click(function() {
    var href = $(this).attr('href');
    location.href=href;
});
