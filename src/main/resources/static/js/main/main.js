let navTradingBtn;

document.addEventListener("DOMContentLoaded", function() {
    navTradingBtn = document.querySelector('.trading-anchor');

    navTradingBtn.addEventListener('click', function() {
        $.ajax({
            url: "/api/v1/sessions",
            method: "get",
            success: function(response) {
                console.log("response", response);
                //already login
                if (response === "Session is valid.") {
                    $.ajax({
                        url: "/champions",
                        method: "get",
                        success: function(data) {
                            document.open();
                            document.write(data);
                            document.close();
                        },
                        error: function(xhr, status, error) {
                            console.log("Error(/champions):", error)
                            alert("로그인 후 이용가능합니다");
                        }
                    });
                }
                else {
                    alert("알 수 없는 오류 발생");
                }
            },
            //not login
            error: function(xhr, status, error) {
                alert("로그인 후 이용해주세요");
                signInModal();
            }
        });
    });
});

