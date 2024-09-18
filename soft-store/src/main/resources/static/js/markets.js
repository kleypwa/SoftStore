document.querySelectorAll('.menu-item, .auth-item').forEach(item => {
    item.addEventListener('mouseover', function() {
        this.style.transform = 'scale(1.1)';
    });

    item.addEventListener('mouseout', function() {
        this.style.transform = 'scale(1)';
    });
});

document.addEventListener('DOMContentLoaded', function () {
    fetchMarkets();
});

let currentMarketId = null;

function fetchMarkets() {
    const url = `/api/markets`;
    fetch(url)
        .then(response => response.json())
        .then(markets => {
            const marketsListDiv = document.getElementById('markets-list');
            marketsListDiv.innerHTML = '';
            markets.forEach(market => {
                const marketDiv = createMarketDiv(market);
                marketsListDiv.appendChild(marketDiv);
            });
        })
        .catch(error => {
            console.error('Error fetching markets:', error);
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
            displayToys(market.id);
            document.getElementById('toys-list').classList.remove('hidden');
        }
    });

    return marketDiv;
}

function displayToys(marketId) {
    const url = `/api/toys/${marketId}`;
    fetch(url)
        .then(response => response.json())
        .then(toys => {
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
        })
        .catch(error => {
            console.error('Error fetching toys:', error);
        });
}

function createToyDiv(toy) {
    const toyDiv = document.createElement('div');
    toyDiv.classList.add('toy-item');

    const img = document.createElement('img');
    img.src = `/images/${toy.image || 'default.png'}`;
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
    marketLink.href = `#`;
    marketLink.textContent = `Shop at: ${toy.market?.name || 'Unknown Market'}`;

    infoDiv.appendChild(name);
    infoDiv.appendChild(description);
    infoDiv.appendChild(cost);
    infoDiv.appendChild(marketLink);

    toyDiv.appendChild(img);
    toyDiv.appendChild(infoDiv);

    return toyDiv;
}
