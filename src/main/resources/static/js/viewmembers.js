$(document).ready(function() {
        // Handle clicking the 'Update' button
        $('.update-trainer').on('click', function() {
            var trainerId = $(this).data('id');
            
            // Fetch trainer details via AJAX
            $.get('/fetch-trainer/' + trainerId, function(data) {
                $('#updateId').val(data.trainerId);
                $('#name').val(data.name);
                $('#email').val(data.email);
                $('#phoneNumber').val(data.phoneNumber);
                $('#specialization').val(data.specialization);
                $('#experience').val(data.experience);
                $('#certification').val(data.certification);
                
                // Show the modal
                $('#updateModal').modal('show');
            });
        });

        // Handle form submission for updating the trainer
        $('#updateForm').on('submit', function(e) {
            e.preventDefault();
            var formData = new FormData(this);
            $.ajax({
                url: '/update-trainer',
                method: 'POST',
                data: formData,
                processData: false,
                contentType: false,
                success: function(response) {
                    // Find the updated trainer card and update its content
                    var updatedTrainerId = $('#updateId').val();
                    var $card = $('button.update-trainer[data-id="' + updatedTrainerId + '"]').closest('.card');
                    $card.find('.card-title').text($('#name').val());
                    $card.find('.card-text').eq(0).find('span').text($('#email').val());
                    $card.find('.card-text').eq(1).find('span').text($('#phoneNumber').val());
                    $card.find('.card-text').eq(2).find('span').text($('#specialization').val());
                    $card.find('.card-text').eq(3).find('span').text($('#experience').val() + ' years');
                    $card.find('.card-text').eq(4).find('a').attr('href', $('#certification').val());
                    $card.find('.card-img-top').attr('src', response.imageUrl);

                    // Hide the modal
                    $('#updateModal').modal('hide');
                    alert('Trainer updated successfully');
                },
                error: function(xhr) {
                    alert('Error updating trainer: ' + xhr.responseText);
                }
            });
        });

        // Handle clicking the 'Delete' button
        $('.delete-trainer').on('click', function() {
            var trainerId = $(this).data('id');
            $('#deleteConfirmation .delete-confirm').attr('data-id', trainerId);
            $('#deleteConfirmation').modal('show');
        });

        // Handle confirming the deletion
        $('.delete-confirm').on('click', function() {
            var trainerId = $(this).data('id');
            $.ajax({
                url: '/delete-trainer/' + trainerId,
                method: 'DELETE',
                success: function(response) {
                    // Remove the card from the page without reloading
                    $('button.delete-trainer[data-id="' + trainerId + '"]').closest('.card').remove();

                    // Hide the modal
                    $('#deleteConfirmation').modal('hide');
                    alert('Trainer deleted successfully');
                },
                error: function() {
                    alert('Error deleting trainer.');
                }
            });
        });
    });