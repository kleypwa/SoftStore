document.querySelectorAll('.menu-item, .auth-item').forEach(item => {
    item.addEventListener('mouseover', function() {
        this.style.transform = 'scale(1.1)';
    });

    item.addEventListener('mouseout', function() {
        this.style.transform = 'scale(1)';
    });
});

document.addEventListener('DOMContentLoaded', function () {
    fetchToys();
});

function fetchToys(queryParams = '') {
    const url = `/api/toys${queryParams}`;
    fetch(url)
        .then(response => response.json())
        .then(toys => {
            const toyListDiv = document.getElementById('toy-list');
            toyListDiv.innerHTML = '';
            toys.forEach(toy => {
                const toyDiv = createToyDiv(toy);
                toyListDiv.appendChild(toyDiv);
            });
        });
}

function createToyDiv(toy) {
    const toyDiv = document.createElement('div');
    toyDiv.classList.add('toy-item');

    const img = document.createElement('img');
    img.src = `/images/${toy.image || 'default.png'}`;
    img.alt = toy.name;

    const infoDiv = document.createElement('div');
    infoDiv.classList.add('toy-item-info');

    const name = document.createElement('h3');
    name.textContent = toy.name || 'No Name';

    const description = document.createElement('p');
    description.textContent = toy.description || 'No Description';

    const cost = document.createElement('p');
    cost.classList.add('cost');
    cost.textContent = `Cost: $${toy.cost || 0}`;

    const marketLink = document.createElement('a');
    marketLink.href = "#";
    marketLink.textContent = `Shop at: ${toy.market?.name || 'Unknown Market'}`;

    const addToBucketButton = document.createElement('button');
    addToBucketButton.textContent = 'Add to Bucket';
    addToBucketButton.classList.add('add-to-bucket-button');
    addToBucketButton.addEventListener('click', () => addToBucket(toy.id, addToBucketButton)); // Передаем кнопку

    infoDiv.appendChild(name);
    infoDiv.appendChild(description);
    infoDiv.appendChild(cost);
    infoDiv.appendChild(marketLink);
    infoDiv.appendChild(addToBucketButton);

    toyDiv.appendChild(img);
    toyDiv.appendChild(infoDiv);

    return toyDiv;
}

function addToBucket(toyId, button) {
    const url = `/api/bucket/add/${toyId}`;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }
    })
    .then(response => {
        if (response.ok) {
            button.textContent = 'Added';
            button.disabled = true;
            button.classList.add('added-to-bucket');
        }
        return response.json();
    });
}


function applyFilters() {
    const name = document.getElementById('name').value;
    const marketName = document.getElementById('market-name').value;
    const minCost = document.getElementById('min-cost').value;
    const maxCost = document.getElementById('max-cost').value;

    const queryParams = new URLSearchParams();

    if (name) {
        queryParams.append('name', name);
    }
    if (marketName) {
        queryParams.append('marketName', marketName);
    }
    if (minCost) {
        queryParams.append('minCost', minCost);
    }
    if (maxCost) {
        queryParams.append('maxCost', maxCost);
    }

    fetchToys(`?${queryParams.toString()}`);
}
