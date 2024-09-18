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
    const url = `/api/bucket`;
    fetch(url)
        .then(response => response.json())
        .then(bucketToys => {
            const toyListDiv = document.getElementById('toy-list');
            toyListDiv.innerHTML = '';
            bucketToys.forEach(bucketToy => {
                const toyDiv = createToyDiv(bucketToy.toy, bucketToy.count);
                toyListDiv.appendChild(toyDiv);
            });
        });
}

function createToyDiv(toy, count) {
    const toyDiv = document.createElement('div');
    toyDiv.classList.add('toy-item');
    toyDiv.setAttribute('data-toy-id', toy.id);

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

    const quantityBox = document.createElement('div');
    quantityBox.classList.add('quantity-box');
    quantityBox.textContent = count;

    const minusButton = document.createElement('button');
    minusButton.textContent = '-';
    minusButton.classList.add('minus-button');
    minusButton.addEventListener('click', () => updateQuantity(toy.id, -1));

    const plusButton = document.createElement('button');
    plusButton.textContent = '+';
    plusButton.classList.add('plus-button');
    plusButton.addEventListener('click', () => updateQuantity(toy.id, 1));

    const deleteButton = document.createElement('button');
    deleteButton.textContent = 'Delete';
    deleteButton.classList.add('delete-button');
    deleteButton.addEventListener('click', () => deleteToy(toy.id));

    infoDiv.appendChild(name);
    infoDiv.appendChild(description);
    infoDiv.appendChild(cost);
    infoDiv.appendChild(marketLink);

    toyDiv.appendChild(img);
    toyDiv.appendChild(infoDiv);
    toyDiv.appendChild(minusButton);
    toyDiv.appendChild(quantityBox);
    toyDiv.appendChild(plusButton);
    toyDiv.appendChild(deleteButton);

    return toyDiv;
}

function updateQuantity(toyId, change) {
    const quantityBox = document.querySelector(`.toy-item[data-toy-id='${toyId}'] .quantity-box`);
    let currentQuantity = parseInt(quantityBox.textContent);

    if (currentQuantity + change >= 0) {
        currentQuantity += change;
        quantityBox.textContent = currentQuantity;

        const url = `/api/bucket/update-quantity`;
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                toyId: toyId,
                count: currentQuantity
            })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при обновлении количества игрушки');
            }
            return response.json();
        });
    }
}

function deleteToy(id) {
    fetch(`/api/bucket/${id}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            fetchToys();
        } else {
            alert('Failed to delete the toy');
        }
    });
}

