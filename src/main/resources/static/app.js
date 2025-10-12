document.addEventListener('DOMContentLoaded', () => {
    const productContainer = document.getElementById('product-container');

    // IMPORTANT: Replace this with a real token from your login system
    const authToken = 'YOUR_JWT_TOKEN_HERE';

    // Fetch products from your Spring Boot backend
    async function fetchProducts() {
        try {
            // Your controller is at "/api/products/all"
            const response = await fetch('http://localhost:8080/api/products/all', {
                method: 'GET',
                headers: {
                    // Your backend expects an Authorization header
                    'Authorization': `Bearer ${authToken}`
                }
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const products = await response.json();
            renderProducts(products);
        } catch (error) {
            console.error('Failed to fetch products:', error);
            productContainer.innerHTML = `<p>Error loading products. Check console for details.</p>`;
        }
    }

    // Render products to the page
    function renderProducts(products) {
        if (!products || products.length === 0) {
            productContainer.innerHTML = '<p>No products found.</p>';
            return;
        }

        productContainer.innerHTML = ''; // Clear loading/error message

        products.forEach(product => {
            const productCard = document.createElement('div');
            productCard.className = 'product-card';

            // Based on your ProductServiceImpl, the response has these fields
            productCard.innerHTML = `
                <img src="${product.imageUrl}" alt="${product.name}">
                <div class="product-info">
                    <h3>${product.name}</h3>
                    <p class="price">$${product.price.toFixed(2)}</p>
                    <p class="category">${product.category.categoryName}</p>
                    <p class="description">${product.discription}</p>
                </div>
            `;
            productContainer.appendChild(productCard);
        });
    }

    // Initial fetch
    fetchProducts();
});