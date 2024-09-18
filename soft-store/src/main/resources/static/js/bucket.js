document.addEventListener('DOMContentLoaded', function () {
    fetchToys();
});

function fetchToys(queryParams = '') {
    const url = `/api/bucket`; // Получаем данные о корзине с сервера
    fetch(url)
        .then(response => response.json())
        .then(bucketToys => {
            const toyListDiv = document.getElementById('toy-list');
            toyListDiv.innerHTML = ''; // Очищаем список игрушек
            bucketToys.forEach(bucketToy => {
                const toyDiv = createToyDiv(bucketToy.toy, bucketToy.count); // Создаем элемент для каждой игрушки
                toyListDiv.appendChild(toyDiv); // Добавляем на страницу
            });
        });
}

function createToyDiv(toy, count) {
    // Создаем контейнер для каждой игрушки
    const toyDiv = document.createElement('div');
    toyDiv.classList.add('toy-item');
    toyDiv.setAttribute('data-toy-id', toy.id); // Добавляем id игрушки для отслеживания

    // Изображение игрушки
    const img = document.createElement('img');
    img.src = `/images/${toy.image || 'default.png'}`;
    img.alt = toy.name;

    // Информация об игрушке
    const infoDiv = document.createElement('div');
    infoDiv.classList.add('toy-item-info');

    // Название игрушки
    const name = document.createElement('h3');
    name.textContent = toy.name || 'No Name'; // Обработка отсутствия имени

    // Описание игрушки
    const description = document.createElement('p');
    description.textContent = toy.description || 'No Description'; // Обработка отсутствия описания

    // Цена игрушки
    const cost = document.createElement('p');
    cost.classList.add('cost');
    cost.textContent = `Cost: $${toy.cost || 0}`; // Обработка отсутствия цены

    // Ссылка на маркет
    const marketLink = document.createElement('a');
    marketLink.href = "#"; // Placeholder для ссылки
    marketLink.textContent = `Shop at: ${toy.market?.name || 'Unknown Market'}`; // Маркет с fallback

    // Количество игрушек
    const quantityBox = document.createElement('div');
    quantityBox.classList.add('quantity-box');
    quantityBox.textContent = count; // Отображаем количество игрушек

    // Кнопки "+" и "-"
    const minusButton = document.createElement('button');
    minusButton.textContent = '-';
    minusButton.classList.add('minus-button');
    minusButton.addEventListener('click', () => updateQuantity(toy.id, -1)); // Уменьшить количество

    const plusButton = document.createElement('button');
    plusButton.textContent = '+';
    plusButton.classList.add('plus-button');
    plusButton.addEventListener('click', () => updateQuantity(toy.id, 1)); // Увеличить количество

    const deleteButton = document.createElement('button');
    deleteButton.textContent = 'Delete';
    deleteButton.classList.add('delete-button');
    deleteButton.addEventListener('click', () => deleteToy(toy.id));



    // Добавляем информацию в infoDiv
    infoDiv.appendChild(name);
    infoDiv.appendChild(description);
    infoDiv.appendChild(cost);
    infoDiv.appendChild(marketLink);


    // Добавляем элементы на toyDiv
    toyDiv.appendChild(img);
    toyDiv.appendChild(infoDiv);
    toyDiv.appendChild(minusButton); // Кнопка уменьшения
    toyDiv.appendChild(quantityBox); // Блок с количеством
    toyDiv.appendChild(plusButton); // Кнопка увеличения
    toyDiv.appendChild(deleteButton);

    return toyDiv;
}

// Функция для обновления количества игрушки
function updateQuantity(toyId, change) {
    // Найти контейнер с количеством для этой игрушки
    const quantityBox = document.querySelector(`.toy-item[data-toy-id='${toyId}'] .quantity-box`);
    let currentQuantity = parseInt(quantityBox.textContent);

    // Проверяем, чтобы количество не становилось отрицательным
    if (currentQuantity + change >= 0) {
        currentQuantity += change;
        quantityBox.textContent = currentQuantity; // Обновляем количество на странице

        // Отправляем POST-запрос для обновления количества на сервере
        const url = `/api/bucket/update-quantity`;  // Убедись, что URL соответствует твоему API
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
            fetchToys(); // Обновляем список игрушек после удаления
        } else {
            alert('Failed to delete the toy');
        }
    });
}

