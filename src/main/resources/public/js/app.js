$(document).ready(function() {
    console.log("test")
    if ($("#redirects").length > 0) {
        $("a.delete").click(function(e) {
            e.preventDefault();

            var con = confirm("Are you sure you want to delete redirect number "+$(this).attr("data-id"));

            if(con) {
                $.post($(this).attr("href"), {id: $(this).attr("data-id")}, function() {
                    location.reload();
                });
            }

        })
    }
})
