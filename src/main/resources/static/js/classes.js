document.addEventListener('DOMContentLoaded', function() {
    // Edit class
    document.querySelectorAll('.edit-class').forEach(button => {
        button.addEventListener('click', function() {
            const classId = this.getAttribute('data-id');
            fetch(`/fetch-class/${classId}`)
                .then(response => response.json())
                .then(data => {
                    document.getElementById('updateClassId').value = data.classId;
                    document.getElementById('updateClassName').value = data.name;
                    document.getElementById('updateClassDateTime').value = data.classDateTime;
                    document.getElementById('updateClassDuration').value = data.duration;
                    document.getElementById('updateClassTrainer').value = data.instructorName;
                    document.getElementById('updateClassType').value = data.classType;

                    // Open the update modal
                    new bootstrap.Modal(document.getElementById('updateClassModal')).show();
                });
        });
    });

    // Update class
    document.getElementById('updateClassForm').addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = new FormData(this);
        
        fetch('/update-class', {
            method: 'POST',
            body: formData
        })
        .then(response => response.text())
        .then(message => {
            alert(message);
            location.reload(); // Reload to see changes
        })
        
    });

    // Delete class
    document.querySelectorAll('.delete-class').forEach(button => {
        button.addEventListener('click', function() {
            const classId = this.getAttribute('data-id');
            document.getElementById('confirmDeleteClass').setAttribute('data-id', classId);

            // Open the delete confirmation modal
            new bootstrap.Modal(document.getElementById('deleteClassModal')).show();
        });
    });

    // Confirm delete
    document.getElementById('confirmDeleteClass').addEventListener('click', function() {
        const classId = this.getAttribute('data-id');
        
        fetch(`/delete-class/${classId}`, {
            method: 'DELETE'
        })
        .then(response => response.text())
        .then(message => {
            alert(message);
            document.querySelector(`tr[data-id="${classId}"]`).remove(); // Remove the row from the table
            bootstrap.Modal.getInstance(document.getElementById('deleteClassModal')).hide(); // Close the modal
        })
       
    });

    // Search functionality
    document.getElementById('searchInput').addEventListener('input', function() {
        const searchTerm = this.value.toLowerCase();
        document.querySelectorAll('#classTable tbody tr').forEach(row => {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(searchTerm) ? '' : 'none';
        });
    });

    // Filter by class type
    document.getElementById('classTypeFilter').addEventListener('change', function() {
        const selectedClassType = this.value.toLowerCase();
        document.querySelectorAll('#classTable tbody tr').forEach(row => {
            const classType = row.querySelector('td:nth-child(6)').textContent.toLowerCase();
            row.style.display = (selectedClassType === 'all' || classType === selectedClassType) ? '' : 'none';
        });
    });
});
