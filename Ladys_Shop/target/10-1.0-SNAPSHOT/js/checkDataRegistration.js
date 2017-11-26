$(function () {
    $("#registr").validate({
        rules: {
            fullname: {
                required: true,
                minlength: 1
            },
            login: {
                required: true,
                email: true
            },
            password: {
                required: true,
                minlength: 6
            },
            repassword: {
                required: true,
                minlength: 3,
                equalTo: "#password"
            }
        },
        messages: {
            fullname: {
                required: "Заполните поле",
                minlength: "Поле должно содержать не менее одного символа"
            },
            login: {
                required: "Заполните поле",
                email: "Введенный e-mail некорректен",
            },
            password: {
                required: "Заполните поле",
                minlength: "Поле должно содержать не менее 6 символов"
            },
            repassword: {
                required: "Заполните поле",
                equalTo: "Пароли должны совпадать",
                minlength: "Поле должно содержать не менее 6 символов"
            }

        },
        focusCleanup: true,
        focusInvalid: false

    });
});

