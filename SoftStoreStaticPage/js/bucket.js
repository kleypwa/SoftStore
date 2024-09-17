document.addEventListener('DOMContentLoaded', function () {
    fetchToys();
});

const staticBucketToys = [
    {
        toy: {
            id: 1,
            name: "Pikachu",
            description: "Pika pika!",
            cost: 10,
            image: "pi.jpg",
            market: { name: "Pokemon World" }
        },
        count: 5 
    },
    {
        toy: {
            id: 3,
            name: "Belazik",
            description: "Really good toy car for kids",
            cost: 999,
            image: "be.PNG",
            market: { name: "Useless Shop" }
        },
        count: 2 
    }
];

function fetchToys() {
    const toyListDiv = document.getElementById('toy-list');
    toyListDiv.innerHTML = ''; 

    staticBucketToys.forEach(bucketToy => {
        const toyDiv = createToyDiv(bucketToy.toy, bucketToy.count); 
        toyListDiv.appendChild(toyDiv); 
    });
}

function createToyDiv(toy, count) {
    const toyDiv = document.createElement('div');
    toyDiv.classList.add('toy-item');
    toyDiv.setAttribute('data-toy-id', toy.id); 

    const img = document.createElement('img');
    img.src = `./images/${toy.image || 'default.png'}`;
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

        console.log(`Updating toy ID ${toyId} with new quantity: ${currentQuantity}`);
    }
}

function deleteToy(id) {
    console.log(`Deleting toy ID ${id} from the bucket`);
    
    const updatedBucketToys = staticBucketToys.filter(bucketToy => bucketToy.toy.id !== id);
    staticBucketToys.length = 0;
    staticBucketToys.push(...updatedBucketToys); 

    fetchToys();
}
