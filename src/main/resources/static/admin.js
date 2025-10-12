document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('add-product-form');
    const responseMessage = document.getElementById('response-message');

    // IMPORTANT: Replace this with a real token from your login system (for an ADMIN user)
    const authToken = 'YOUR_ADMIN_JWT_TOKEN_HERE';

    form.addEventListener('submit', async (event) => {
        event.preventDefault(); // Prevent default browser submission

        // Create an object from the form data that matches your ProductDto
        const productData = {
            name: document.getElementById('name').value,
            description: document.getElementById('description').value,
            price: parseFloat(document.getElementById('price').value),
            stockQuantity: parseInt(document.getElementById('stockQuantity').value, 10),
            imageUrl: document.getElementById('imageUrl').value,
            categoryId: parseInt(document.getElementById('categoryId').value, 10)
        };

        try {
            // Your controller is at "/api/products/add"
            const response = await fetch('http://localhost:8080/api/products/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    // Your backend expects an Authorization header for admin actions
                    'Authorization': `Bearer ${authToken}`
                },
                body: JSON.stringify(productData)
            });

            const resultText = await response.text(); // Your backend returns a simple string

            if (!response.ok) {
                throw new Error(resultText || `HTTP error! Status: ${response.status}`);
            }

            responseMessage.style.color = 'green';
            responseMessage.textContent = resultText;
            form.reset(); // Clear the form on success
        } catch (error) {
            console.error('Failed to add product:', error);
            responseMessage.style.color = 'red';
            responseMessage.textContent = `Error: ${error.message}`;
        }
    });
});