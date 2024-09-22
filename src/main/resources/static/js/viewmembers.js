document.addEventListener("DOMContentLoaded", function() {
	const viewButtons = document.querySelectorAll(".btn-view-member");

	viewButtons.forEach(button => {
		button.addEventListener("click", function() {
			// Use getAttribute to fetch the th:data-id value
			const memberId = this.getAttribute("data-id");

			// Correct the URL to use memberId
			fetch(`/view-members/${memberId}`)
				.then(response => response.json())
				.then(member => {
					console.log(member); // Log the fetched member data

					// Populate the modal with member's data
					document.getElementById("modal-member-name").textContent = member.name;
					document.getElementById("modal-member-email").textContent = member.email;
					document.getElementById("modal-member-phone").textContent = member.phone;
					document.getElementById("modal-member-address").textContent = member.address;
					document.getElementById("modal-member-plan").textContent = member.planName;
					document.getElementById("modal-member-joinDate").textContent = member.joinDate;
					document.getElementById("modal-member-endDate").textContent = member.endDate;
					document.getElementById("modal-member-status").textContent = member.membershipStatus;
					document.getElementById("modal-member-daysLeft").textContent = member.daysLeft;

					// Show the modal
					const modal = new bootstrap.Modal(document.getElementById("memberModal"));
					modal.show();
				})
				.catch(error => {
					console.error("Error fetching member details:", error);
				});
		});
	});
});
