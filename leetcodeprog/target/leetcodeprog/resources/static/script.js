const container = document.getElementById('container');
const registerBtn = document.getElementById('register');
const loginBtn = document.getElementById('login');

registerBtn.addEventListener('click', () => {
    container.classList.add("active");
});

loginBtn.addEventListener('click', () => {
    container.classList.remove("active");
});





function togglePassword() {
    var passwordField = document.getElementById("password");
    var showPasswordCheckbox = document.getElementById("showPassword");

    // Если чекбокс отмечен, показываем пароль, в противном случае скрываем
    passwordField.type = showPasswordCheckbox.checked ? "text" : "password";
    console.log(passwordField.type);
}

