/**
 * Created by vlad on 20.04.17.
 */
$(document).ready(function () {
    Dropzone.autoDiscover = false;
    // Dropzone class:
    $("div#my_dropzone").dropzone({
        url: "/file/post" ,
        methid:"post",
        previewTemplate: document.getElementById('preview-template')
    });
});