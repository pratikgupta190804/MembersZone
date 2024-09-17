$(document).ready(function() {
	// Handle clicking the 'Edit' button
	$('.edit-class').on('click', function() {
		var classId = $(this).data('id');

		// Fetch class details via AJAX
		$.get('/fetch-class/' + classId, function(data) {
			$('#updateClassId').val(data.id);
			$('#updateName').val(data.name);
			$('#updateEmail').val(data.email);
			$('#updatePhoneNumber').val(data.phoneNumber);
			$('#updateDateTime').val(data.classDateTime.slice(0, 16)); // Format for datetime-local input
			$('#updateDuration').val(data.duration);
			$('#updateTrainer').val(data.instructorName);
			$('#updateClassType').val(data.className);

			// Show the modal
			$('#updateClassModal').modal('show');
		});
	});

	// Handle form submission for updating the class
	$('#updateClassForm').on('submit', function(e) {
		e.preventDefault();
		var formData = new FormData(this);

		// Manually append the ID if not already included
		if (!formData.has('id')) {
			formData.append('id', $('#updateClassId').val());
		}

		$.ajax({
			url: '/update-class',
			method: 'POST',
			data: formData,
			processData: false,
			contentType: false,
			success: function(response) {
				alert('Class updated successfully');

				// Hide the modal
				$('#updateClassModal').modal('hide');

				// Update the corresponding row in the table
				updateTableRow(formData);
			},
			error: function(xhr) {
				alert('Error updating class: ' + xhr.responseText);
			}
		});
	});

	// Handle clicking the 'Delete' button
	$('.delete-class').on('click', function() {
	     var classId = $(this).data('id');

	     // Set the class ID to the confirmation button in the delete modal
	     $('#confirmDeleteClass').data('id', classId);

	     // Show the delete confirmation modal
	     $('#deleteClassModal').modal('show');
	 });

	 // Handle confirming the deletion of a class
	 $('#confirmDeleteClass').on('click', function() {
	     var classId = $(this).data('id');
	     
	     $.ajax({
	         url: '/delete-class/' + classId,
	         method: 'DELETE',
	         success: function(response) {
	             alert('Class deleted successfully');
	             $('#deleteClassModal').modal('hide');
	             $('tr[data-class-id="' + classId + '"]').remove();
	         },
	         error: function(xhr) {
	             alert('Error deleting class: ' + xhr.responseText);
	         }
	     });
	 });

	function updateTableRow(formData) {
		var classId = formData.get('id');
		var $row = $('tr[data-id="' + classId + '"]');

		if ($row.length) {
			// Update the row with the new form data
			$row.find('td').eq(0).text(formData.get('name'));
			$row.find('td').eq(1).text(new Date(formData.get('classDateTime')).toLocaleString());  // Format date and time
			$row.find('td').eq(2).text(formData.get('phoneNumber'));
			$row.find('td').eq(3).text(formData.get('instructorName'));
			$row.find('td').eq(4).text(formData.get('duration'));
			$row.find('td').eq(5).text(formData.get('className'));
		}
	}

	// Search functionality
	$('#searchInput').on('input', function() {
		var searchTerm = $(this).val().toLowerCase();
		$('#classTable tbody tr').each(function() {
			var text = $(this).text().toLowerCase();
			$(this).toggle(text.indexOf(searchTerm) !== -1);
		});
	});

	// Filter by class type
	$('#classTypeFilter').on('change', function() {
		var selectedClassType = $(this).val().toLowerCase();
		$('#classTable tbody tr').each(function() {
			var classType = $(this).find('td:nth-child(6)').text().toLowerCase();
			$(this).toggle(selectedClassType === 'all' || classType === selectedClassType);
		});
	});
});
