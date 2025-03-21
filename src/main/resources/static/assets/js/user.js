var api = "http://localhost:8080/dashboard/user"
var check_info = document.querySelectorAll(".check-info")

console.log(check_info)
check_info.forEach(info => {
    info.addEventListener("click", e =>{
        sendGet(info.dataset.id)
    })
})
function sendGet(id){
    fetch(api + "/" + id)
        .then(response => {
            return response.json();
        })
        .then(data => {
            console.log(data)
            fillInfo(data)
        })
        .catch(e =>{
            console.log("heelo")
        })
}

function fillInfo(data){
    var bodyElement = document.querySelector("#body_info")
    var role_name = "";
    var gender = "";

    if(data.gender === "MALE"){
        gender = "nam"
    }else{
        gender = "nữ"
    }

    data.roles.forEach(role => {
        role_name += role.description;
        console.log(role)
    })
    var body =` <div class="modal_body">
                        <div class="row">
                            <div class="col-4">
                                <div class="image">
                                    <img src="/assets/avatars/${data.avatar}" alt="">

                                </div>
                            </div>
                            <div class="col-8">
                                <div class="info_text">
                                    Họ và tên: ${data.fullname}
                                </div>
                                <div class="info_text">
                                    Giới tính: ${gender}
                                </div>
                                <div class="info_text">
                                    Ngày sinh: ${data.birth}
                                </div>
                                <div class="info_text">
                                    Số điện thoại: ${data.phone}
                                </div>
                                <div class="info_text">
                                    Tài khoản: ${data.username}
                                </div>
                                <div class="info_text">
                                    Vai trò: ${role_name}
                                </div>
                            </div>
                        </div>
                    </div>
`
    bodyElement.innerHTML = body;
}