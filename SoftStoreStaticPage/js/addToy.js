
document.querySelectorAll('.menu-item, .auth-item').forEach(item => {
    item.addEventListener('mouseover', function() {
        this.style.transform = 'scale(1.1)';
    });

    item.addEventListener('mouseout', function() {
        this.style.transform = 'scale(1)';
    });
});

document.getElementById('addToyButton').addEventListener('click', function() {
    const toyFormSection = document.getElementById('toyFormSection');
    toyFormSection.classList.toggle('hidden');
});

document.getElementById('addMarketButton').addEventListener('click', function() {
    const marketFormSection = document.getElementById('marketFormSection');
    marketFormSection.classList.toggle('hidden2');
});

function loadMarkets() {
    fetch('/api/markets')
        .then(response => response.json())
        .then(markets => {
            const marketSelect = document.getElementById('market');
            marketSelect.innerHTML = '';
            markets.forEach(market => {
                const option = document.createElement('option');
                option.value = market.id;
                option.textContent = market.name;
                marketSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching markets:', error));
}

loadMarkets();

document.getElementById('addToyForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const toyData = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        cost: parseFloat(document.getElementById('cost').value),
        marketId: document.getElementById('market').value
    };

    fetch('/api/toys', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(toyData)
    })
    .then(data => {
        alert('Toy added successfully!');
        document.getElementById('addToyForm').reset();
        document.getElementById('toyFormSection').classList.add('hidden');
    })
    .catch(error => console.error('Error adding toy:', error));
});

document.getElementById('addMarketForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const marketData = {
        name: document.getElementById('marketName').value,
        description: document.getElementById('marketDescription').value
    };

    fetch('/api/markets', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(marketData)
    })
    .then(data => {
        alert('Market added successfully!');
        
        document.getElementById('addMarketForm').reset();
        document.getElementById('marketFormSection').classList.add('hidden2');
        
        loadMarkets();
    })
    .catch(error => console.error('Error adding market:', error));
});