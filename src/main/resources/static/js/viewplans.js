$(document).ready(function() {
           // Handle clicking the 'Update' button
           $('.update-plan').on('click', function() {
               var planId = $(this).data('id');
               
               // Fetch plan details via AJAX
               $.get('/fetch-plan/' + planId, function(data) {
                   $('#updateId').val(data.id);
                   $('#planName').val(data.planName);
                   $('#durationInMonths').val(data.durationInMonths);
                   $('#price').val(data.price);
                   
                   // Show the modal
                   $('#updateModal').modal('show');
               });
           });

           // Handle form submission for updating the plan
           $('#updateForm').on('submit', function(e) {
               e.preventDefault();
               var formData = $(this).serialize();
               $.ajax({
                   url: '/update-plan',
                   method: 'POST',
                   data: formData,
                   success: function(response) {
                       // Find the updated plan row and update its content
                       var updatedPlanId = $('#updateId').val();
                       var $row = $('button.update-plan[data-id="' + updatedPlanId + '"]').closest('tr');
                       $row.find('td').eq(0).text($('#planName').val());
                       $row.find('td').eq(1).text($('#durationInMonths').val());
                       $row.find('td').eq(2).text($('#price').val());
                       
                       // Hide the modal
                       $('#updateModal').modal('hide');
                       alert('Plan updated successfully');
                   },
                   error: function(xhr) {
                       alert('Error updating plan: ' + xhr.responseText);
                   }
               });
           });

           // Handle clicking the 'Delete' button
           $('.delete-plan').on('click', function() {
               var planId = $(this).data('id');
               $('#deleteConfirmation .delete-confirm').attr('data-id', planId);
               $('#deleteConfirmation').modal('show');
           });

           // Handle confirming the deletion
           $('.delete-confirm').on('click', function() {
               var planId = $(this).data('id');
               $.ajax({
                   url: '/delete-plan/' + planId,
                   method: 'DELETE',
                   success: function(response) {
                       // Remove the row from the table without reloading the page
                       $('button.delete-plan[data-id="' + planId + '"]').closest('tr').remove();

                       // Hide the modal
                       $('#deleteConfirmation').modal('hide');
                       alert('Plan deleted successfully');
                   },
                   error: function() {
                       alert('Error deleting plan.');
                   }
               });
           });
       });