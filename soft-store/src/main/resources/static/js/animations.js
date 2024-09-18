// Prevent menu items from overlapping when hovered
document.querySelectorAll('.menu-item, .auth-item').forEach(item => {
    item.addEventListener('mouseover', function() {
        this.style.transform = 'scale(1.1)';
    });

    item.addEventListener('mouseout', function() {
        this.style.transform = 'scale(1)';
    });
});

document.addEventListener('DOMContentLoaded', () => {
    fetchNewspapers();
});

function fetchNewspapers() {
    fetch('/api/notes')
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById('newspaper-container');
            container.innerHTML = ''; // Clear the container
            data.forEach(newspaper => {
                const div = document.createElement('div');
                div.classList.add('newspaper-item');
                div.innerHTML = `<p>${newspaper.content}</p>`;

                // Fetch the author details
                fetch(`/api/appusers/${newspaper.author}`)
                    .then(response => response.json())
                    .then(authorData => {
                        const authorP = document.createElement('p');
                        authorP.style.fontStyle = 'italic';
                        authorP.style.fontSize = '0.8em';
                        authorP.innerText = `— ${authorData.username}.`;
                        div.appendChild(authorP); // Append the author below the content
                    })
                    .catch(error => {
                        console.error('Error fetching author:', error);
                    });

                container.appendChild(div);
            });
        })
        .catch(error => {
            console.error('Error fetching newspapers:', error);
        });
}
