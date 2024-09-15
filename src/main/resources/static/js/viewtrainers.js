document.addEventListener('DOMContentLoaded', function() {
    // Edit trainer
    document.querySelectorAll('.edit-trainer').forEach(button => {
        button.addEventListener('click', function() {
            const trainerId = this.getAttribute('data-id');
            fetch(`/fetch-trainer/${trainerId}`)
                .then(response => response.json())
                .then(data => {
                    document.getElementById('updateTrainerId').value = data.trainerId;
                    document.getElementById('updateName').value = data.name;
                    document.getElementById('updateEmail').value = data.email;
                    document.getElementById('updatePhoneNumber').value = data.phoneNumber;
                    document.getElementById('updateSpecialization').value = data.specialization;
                    document.getElementById('updateExperience').value = data.experience;
                    document.getElementById('updateCertification').value = data.certification;
                    
                    // Open the update modal
                    new bootstrap.Modal(document.getElementById('updateTrainerModal')).show();
                });
        });
    });

    // Update trainer
    document.getElementById('updateTrainerForm').addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = new FormData(this);
        
        fetch('/update-trainer', {
            method: 'POST',
            body: formData
        })
        .then(response => response.text())
        .then(message => {
            alert(message);
            location.reload(); // Reload to see changes
        })
        .catch(error => console.error('Error:', error));
    });

    // Delete trainer
    document.querySelectorAll('.delete-trainer').forEach(button => {
        button.addEventListener('click', function() {
            const trainerId = this.getAttribute('data-id');
            document.getElementById('confirmDelete').setAttribute('data-id', trainerId);
            
            // Open the delete confirmation modal
            new bootstrap.Modal(document.getElementById('deleteTrainerModal')).show();
        });
    });

    // Confirm delete
    document.getElementById('confirmDelete').addEventListener('click', function() {
        const trainerId = this.getAttribute('data-id');
        
        fetch(`/delete-trainer/${trainerId}`, {
            method: 'DELETE'
        })
        .then(response => response.text())
        .then(message => {
            alert(message);
            document.querySelector(`tr[data-id="${trainerId}"]`).remove(); // Remove the row from the table
            bootstrap.Modal.getInstance(document.getElementById('deleteTrainerModal')).hide(); // Close the modal
        })
        .catch(error => console.error('Error:', error));
    });

    // Search functionality
    document.getElementById('searchInput').addEventListener('input', function() {
        const searchTerm = this.value.toLowerCase();
        document.querySelectorAll('#trainerTable tbody tr').forEach(row => {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(searchTerm) ? '' : 'none';
        });
    });

    // Filter by specialization
    document.getElementById('specializationFilter').addEventListener('change', function() {
        const selectedSpecialization = this.value.toLowerCase();
        document.querySelectorAll('#trainerTable tbody tr').forEach(row => {
            const specialization = row.querySelector('td:nth-child(3)').textContent.toLowerCase();
            row.style.display = (selectedSpecialization === 'all' || specialization === selectedSpecialization) ? '' : 'none';
        });
    });
});