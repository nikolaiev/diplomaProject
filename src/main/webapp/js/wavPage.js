/**
 * Created by vlad on 20.04.17.
 */


Dropzone.autoDiscover = false;


$(document).ready(function () {
        $('#file-dropzone').dropzone({
            maxFiles: 1,
            url: "/api/upload",
            metho:"post",
            maxFilesize: 2,
            acceptedFiles: "audio/wav",
            paramName: "file_name",
            maxThumbnailFilesize: 5,

            init: function() {

                this.on('success', function(file, json) {
                    $('#success-upload').removeAttr('hidden');
                });

                this.on("maxfilesexceeded", function(file){
                    $('#max-files-reached').removeAttr('hidden');
                });

                this.on('addedfile', function(file) {
                    $('#success-upload').removeAttr('hidden');
                });

                this.on('drop', function(file) {
                    $('#success-upload').removeAttr('hidden');
                });
            }
        });
});