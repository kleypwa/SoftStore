document.addEventListener('DOMContentLoaded', function () {
    loadStaticToys();
});

function loadStaticToys() {
    const toys = [
        {
            id: 1,
            name: "Pikachu",
            description: "Pika pika!",
            cost: 10,
            image: "pi.jpg",
            market: { name: "Pokemon World" }
        },
        {
            id: 2,
            name: "Xurkitree",
            description: "Elecric god if this world",
            cost: 12,
            image: "xu.jpg",
            market: { name: "Pokemon World" }
        },
        {
            id: 3,
            name: "Belazik",
            description: "Really good toycar for kids",
            cost: 999,
            image: "be.PNG",
            market: { name: "Useless market" }
        }
    ];

    const toyListDiv = document.getElementById('toy-list');
    toyListDiv.innerHTML = '';
    toys.forEach(toy => {
        const toyDiv = createToyDiv(toy);
        toyListDiv.appendChild(toyDiv);
    });
}

function createToyDiv(toy) {
    const toyDiv = document.createElement('div');
    toyDiv.classList.add('toy-item');

    const img = document.createElement('img');
    img.src = `./images/${toy.image}`;
    img.alt = toy.name;

    const infoDiv = document.createElement('div');
    infoDiv.classList.add('toy-item-info');

    const name = document.createElement('h3');
    name.textContent = toy.name;

    const description = document.createElement('p');
    description.textContent = toy.description;

    const cost = document.createElement('p');
    cost.classList.add('cost');
    cost.textContent = `Cost: $${toy.cost}`;

    const marketLink = document.createElement('a');
    marketLink.href = "#";
    marketLink.textContent = `Shop at: ${toy.market.name}`;

    const addToBucketButton = document.createElement('button');
    addToBucketButton.textContent = 'Add to Bucket';
    addToBucketButton.classList.add('add-to-bucket-button');
    addToBucketButton.addEventListener('click', () => addToBucket(toy.id));

    infoDiv.appendChild(name);
    infoDiv.appendChild(description);
    infoDiv.appendChild(cost);
    infoDiv.appendChild(marketLink);
    infoDiv.appendChild(addToBucketButton);

    toyDiv.appendChild(img);
    toyDiv.appendChild(infoDiv);

    return toyDiv;
}

function addToBucket(toyId) {
    console.log(`Toy with ID: ${toyId} added to the bucket.`);
}

function applyFilters() {
    console.log('Filters applied (for static data).');
}
