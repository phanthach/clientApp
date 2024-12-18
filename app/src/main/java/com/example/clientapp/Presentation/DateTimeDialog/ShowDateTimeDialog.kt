import android.app.DatePickerDialog
import android.content.Context
import java.util.*

fun showDateTimeDialog(
    context: Context,
    minDate: Long? = null, // Bổ sung tham số minDate với giá trị mặc định là null
    onDateSelected: (String) -> Unit
) {
    val calendar = Calendar.getInstance()

    // DatePickerDialog để chọn ngày
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, monthOfYear, dayOfMonth ->
            // Cập nhật lịch với ngày được chọn
            calendar.set(year, monthOfYear, dayOfMonth)

            // Định dạng ngày được chọn
            val selectedDate = String.format(
                "%02d/%02d/%04d",
                dayOfMonth, monthOfYear + 1, year
            )
            onDateSelected(selectedDate) // Trả về ngày đã chọn
        },
        // Thiết lập ngày mặc định là ngày hiện tại
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // Nếu có giá trị minDate thì thiết lập ngày tối thiểu cho DatePicker
    minDate?.let {
        datePickerDialog.datePicker.minDate = it
    }
    if(minDate == null){
        datePickerDialog.datePicker.minDate = minDate ?: Calendar.getInstance().timeInMillis
    }
    datePickerDialog.show()
}
