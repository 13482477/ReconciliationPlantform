/**
 * http://usejsdoc.org/
 */
$('.date-range-picker').daterangepicker({
    autoUpdateInput: false,
    locale: {
    	applyLabel: '确定',
        cancelLabel: '取消',
    }
});

$('.date-range-picker').on('apply.daterangepicker', function(ev, picker) {
    $(this).val(picker.startDate.format('YYYY-MM-DD') + ' - ' + picker.endDate.format('YYYY-MM-DD'));
});

$('.date-range-picker').on('cancel.daterangepicker', function(ev, picker) {
    $(this).val('');
});
