(() => {
    const administradorCheckbox = document.getElementById('administrador');

    const togglePasswordGroup = () => {
        const passwordGroup = document.getElementById('password-group');
        const passwordInput = document.getElementById('password');
        const confirmPasswordInput = document.getElementById('repassword');

        if (administradorCheckbox.checked) {
            passwordInput.setAttribute('required', 'required');
            confirmPasswordInput.setAttribute('required', 'required');
            passwordGroup.classList.remove('hidden');
        } else {
            passwordInput.removeAttribute('required');
            confirmPasswordInput.removeAttribute('required');
            passwordGroup.classList.add('hidden');
        }
    }

    administradorCheckbox.addEventListener('change', togglePasswordGroup);

    togglePasswordGroup();
})();
