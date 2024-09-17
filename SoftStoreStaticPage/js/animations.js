const newspapers = [
    { id: 1, content: 'This is the first newspaper article.', author: 1 },
    { id: 2, content: 'This is the second newspaper article.', author: 2 }
];

const authors = [
    { id: 1, username: 'JohnDoe' },
    { id: 2, username: 'JaneSmith' }
];

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
    const container = document.getElementById('newspaper-container');
    container.innerHTML = '';
    newspapers.forEach(newspaper => {
        const div = document.createElement('div');
        div.classList.add('newspaper-item');
        div.innerHTML = `<p>${newspaper.content}</p>`;

        const authorData = authors.find(author => author.id === newspaper.author);
        if (authorData) {
            const authorP = document.createElement('p');
            authorP.style.fontStyle = 'italic';
            authorP.style.fontSize = '0.8em';
            authorP.innerText = `— ${authorData.username}.`;
            div.appendChild(authorP);         }

        container.appendChild(div);
    });
}
