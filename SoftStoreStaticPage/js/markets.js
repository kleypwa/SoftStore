document.addEventListener('DOMContentLoaded', function () {
    fetchMarkets();
});

let currentMarketId = null;

const staticMarkets = [
    {
        id: 1,
        name: "Pokemon World",
        description: "All the Pokemon toys you need!",
        toys: [
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
                description: "Electric god of this world",
                cost: 12,
                image: "xu.jpg",
                market: { name: "Pokemon World" }
            }
        ]
    },
    {
        id: 2,
        name: "Useless Shop",
        description: "You won't need it, but you might want it!",
        toys: [
            {
                id: 3,
                name: "Belazik",
                description: "Really good toy car for kids",
                cost: 999,
                image: "be.PNG",
                market: { name: "Useless Shop" }
            }
        ]
    },
    {
        id: 3,
        name: "Garbage Collector",
        description: "Nothing to see here, move along.",
        toys: [] 
    }
];

function fetchMarkets() {
    const marketsListDiv = document.getElementById('markets-list');
    marketsListDiv.innerHTML = ''; 
    staticMarkets.forEach(market => {
        const marketDiv = createMarketDiv(market);
        marketsListDiv.appendChild(marketDiv);
    });
}

function createMarketDiv(market) {
    const marketDiv = document.createElement('div');
    marketDiv.classList.add('market-item');

    const infoDiv = document.createElement('div');
    infoDiv.classList.add('market-item-info');

    const name = document.createElement('h3');
    name.textContent = market.name || 'No Name';

    const description = document.createElement('p');
    description.textContent = market.description || 'No Description';

    infoDiv.appendChild(name);
    infoDiv.appendChild(description);
    marketDiv.appendChild(infoDiv);

    marketDiv.addEventListener('click', function() {
        if (currentMarketId === market.id) {
            document.getElementById('toys-list').classList.toggle('hidden');
        } else {
            currentMarketId = market.id;
            displayToys(market.toys);
            document.getElementById('toys-list').classList.remove('hidden');
        }
    });

    return marketDiv;
}

function displayToys(toys) {
    const toysListDiv = document.getElementById('toys-list');
    toysListDiv.innerHTML = '';

    if (toys.length === 0) {
        const message = document.createElement('p');
        message.textContent = "That market has no toys now";
        message.classList.add('no-toys-message');
        toysListDiv.appendChild(message);
    } else {
        toys.forEach(toy => {
            const toyDiv = createToyDiv(toy);
            toysListDiv.appendChild(toyDiv);
        });
    }
}

function createToyDiv(toy) {
    const toyDiv = document.createElement('div');
    toyDiv.classList.add('toy-item');

    const img = document.createElement('img');
    img.src = `./images/${toy.image || 'default.png'}`;
    img.alt = toy.name;
    img.classList.add('toy-image');

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

    infoDiv.appendChild(name);
    infoDiv.appendChild(description);
    infoDiv.appendChild(cost);
    infoDiv.appendChild(marketLink);

    toyDiv.appendChild(img);
    toyDiv.appendChild(infoDiv);

    return toyDiv;
}
