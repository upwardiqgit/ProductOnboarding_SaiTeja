document.addEventListener('DOMContentLoaded', function() {

    // Validate product name to check if it's unique
    function validateProductName() {
        let productName = document.getElementById('product-name').value;
        if (productName) {
            let xhr = new XMLHttpRequest();
            xhr.open("POST", "/validate-product-name", true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    let response = JSON.parse(xhr.responseText);
                    if (response.isValid) {
                        document.getElementById('name-error').textContent = '';
                        document.getElementById('submit-btn').disabled = false;
                    } else {
                        document.getElementById('name-error').textContent = 'Product name already exists';
                        document.getElementById('submit-btn').disabled = true;
                    }
                }
            };
            xhr.send(JSON.stringify({ productName: productName }));
        }
    }

    // Check product name uniqueness when user types or leaves input
    document.getElementById('product-name').addEventListener('blur', validateProductName);
    document.getElementById('product-name').addEventListener('input', validateProductName);

    // Handle form submission
    document.getElementById('submit-btn').addEventListener('click', function() {
        let form = document.getElementById('product-form');
        if (form.checkValidity()) {
            let formData = new FormData(form);
            let productData = {};
            formData.forEach((value, key) => {
                productData[key] = value;
            });

            let xhr = new XMLHttpRequest();
            xhr.open("POST", "/save-product", true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    alert('Product saved successfully!');
                    form.reset();
                } else {
                    alert('Error saving product');
                }
            };
            xhr.send(JSON.stringify(productData));
        }
    });
});
