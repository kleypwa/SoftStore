// Prevent menu items from overlapping when hovered
document.querySelectorAll('.menu-item, .auth-item').forEach(item => {
    item.addEventListener('mouseover', function() {
        this.style.transform = 'scale(1.1)';
    });

    item.addEventListener('mouseout', function() {
        this.style.transform = 'scale(1)';
    });
});

// Toggle the toy form visibility
document.getElementById('addToyButton').addEventListener('click', function() {
    const toyFormSection = document.getElementById('toyFormSection');
    toyFormSection.classList.toggle('hidden');
});

// Toggle the market form visibility
document.getElementById('addMarketButton').addEventListener('click', function() {
    const marketFormSection = document.getElementById('marketFormSection');
    marketFormSection.classList.toggle('hidden2');
});

// Fetch the markets and populate the dropdown
function loadMarkets() {
    fetch('/api/markets')
        .then(response => response.json())
        .then(markets => {
            const marketSelect = document.getElementById('market');
            marketSelect.innerHTML = ''; // Clear existing options
            markets.forEach(market => {
                const option = document.createElement('option');
                option.value = market.id;
                option.textContent = market.name;
                marketSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching markets:', error));
}

// Call the loadMarkets function to fill the dropdown when the page loads
loadMarkets();

// Handle toy form submission
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
        // Clear the form
        document.getElementById('addToyForm').reset();
        document.getElementById('toyFormSection').classList.add('hidden');
    })
    .catch(error => console.error('Error adding toy:', error));
});

// Handle market form submission
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
        // Clear the form
        document.getElementById('addMarketForm').reset();
        document.getElementById('marketFormSection').classList.add('hidden2');
        // Optionally reload markets if needed
        loadMarkets();
    })
    .catch(error => console.error('Error adding market:', error));
});