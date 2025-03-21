var hidden = document.querySelector('.body_custom');
var nav_top = document.querySelector('.navbar-nav');
var sidebar = document.querySelector('#leftSidebar');
var btn_menu = document.querySelector('.btn_menu');
var show_menu = document.querySelector('.show_menu');
var user_show = document.querySelector('#user_show');
var dashboard_show = document.querySelector('#dashboard_show');
var schedule_show = document.querySelector('#schedule_show');

var main = document.querySelector('.add');

hidden.addEventListener('click', e => {
  // trường hợp width < 992px thì bấm ra ngoài thì sẽ ẩn menu sẽ mất
  if ((e.target !== nav_top || e.target !== btn_menu) && window.innerWidth < 992) {
    hidden.classList.remove('collapsed');
  }
});

show_menu.addEventListener('click', e => {
  hidden.classList.toggle('collapsed');
});

sidebar.addEventListener('click', e => {
  e.stopPropagation();
})

schedule_show.addEventListener('click', e =>{
  main.innerHTML = `<div class="col-12">
                            <div class="card shadow">
                                <div class="card-body d-flex justify-content-between align-items-center flex-wrap">
                                    <h3 class="card-title mb-0">Trạng thái đặt sân</h3>
                                    <button type="button" class="btn btn-primary" data-toggle="modal"
                                        data-target="#eventModal"><span class="fe fe-plus fe-16 mr-3"></span>Đặt
                                        sân</button>

                                    <table class="table col-12 mt-3 court_schedule">
                                        <thead>
                                            <tr>
                                                <td></td>
                                                <td>Thứ 2</td>
                                                <td>Thứ 3</td>
                                                <td>Thứ 4</td>
                                                <td>Thứ 5</td>
                                                <td>Thứ 6</td>
                                                <td>Thứ 7</td>
                                                <td>Thứ CN</td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <!-- 7h lặp từ thứ 2 đến thứ CN -->
                                                <td>07:00</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                            </tr>
                                            <tr>
                                                <!-- 7h lặp từ thứ 2 đến thứ CN -->
                                                <td>07:30</td>
                                                <td class="is_available">NORMAL</td>
                                                <td class="is_available">NORMAL</td>
                                                <td class="">NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                            </tr>
                                            <tr>
                                                <!-- 7h lặp từ thứ 2 đến thứ CN -->
                                                <td>08:00</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                            </tr>
                                            <tr>
                                                <!-- 7h lặp từ thứ 2 đến thứ CN -->
                                                <td>08:30</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                            </tr>
                                            <tr>
                                                <!-- 7h lặp từ thứ 2 đến thứ CN -->
                                                <td>09:00</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                            </tr>
                                            <tr>
                                                <!-- 7h lặp từ thứ 2 đến thứ CN -->
                                                <td>09:30</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                            </tr>
                                            <tr>
                                                <!-- 7h lặp từ thứ 2 đến thứ CN -->
                                                <td>10:00</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                            </tr>
                                            <tr>
                                                <!-- 7h lặp từ thứ 2 đến thứ CN -->
                                                <td>10:30</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                            </tr>
                                            <tr>
                                                <!-- 7h lặp từ thứ 2 đến thứ CN -->
                                                <td>11:00</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                                <td>NORMAL</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        `

})