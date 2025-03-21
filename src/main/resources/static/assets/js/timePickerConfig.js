$(document).ready(function () {
    $('#datetimepicker').datetimepicker({
        format: 'DD/MM/YYYY',
        stepping: 30,
        icons: {
            time: 'fe fe-clock',
            date: 'fe fe-calendar',
            up: 'fe fe-arrow-up',
            down: 'fe fe-arrow-down',
            previous: 'fe fe-chevron-left',
            next: 'fe fe-chevron-right',
            today: 'fe fe-calendar-check',
            clear: 'fe fe-trash',
            close: 'fe fe-times'
        }
    });
});