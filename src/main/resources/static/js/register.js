const administradorCheckbox = document.getElementById('administrador');

administradorCheckbox.addEventListener('change', () => {
    const passwordGroup = document.getElementById('password-group');
    const passwordInput = document.getElementById('password');
    const confirmPasswordInput = document.getElementById('repassword');

    if (administradorCheckbox.checked) {
        passwordInput.setAttribute('required', 'required');
        confirmPasswordInput.setAttribute('required', 'required');
    } else {
        passwordInput.removeAttribute('required');
        confirmPasswordInput.removeAttribute('required');
    }

    passwordGroup.classList.toggle('hidden');
})
