package digiwin.library.datepicker;


import android.app.Activity;
import android.widget.DatePicker;


import java.util.Calendar;

import digiwin.library.R;
import digiwin.library.utils.StringUtils;

/**
 * @author xiemeng
 * @des 日期选择
 * @date 2017/3/3
 */
public class DatePickerUtils {
    /**
     * 获取时间格式yyyy-MM-dd
     */
    public interface GetDateListener {
        public void getDate(String date);
    }

    /**
     * 获取时间格式yyyy-MM-dd
     *
     * @param context
     * @return
     */
//    public static void getDate(Activity context, final GetDateListener listener) {
//        /**
//         * 时间接口
//         */
//        DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                monthOfYear = monthOfYear + 1;
//                listener.getDate(year + "-" + monthOfYear + "-" + dayOfMonth);
//            }
//        };
//        Calendar now = Calendar.getInstance();
//        DatePickerDialog dialog = DatePickerDialog.newInstance(
//                pickerListener,
//                now.get(Calendar.YEAR),
//                now.get(Calendar.MONTH),
//                now.get(Calendar.DAY_OF_MONTH)
//        );
//        dialog.setAccentColor(context.getResources().getColor(R.color.Base_color));
//        dialog.setMaxDate(Calendar.getInstance());
//        dialog.show(context.getFragmentManager(), "Datepickerdialog");
//        dialog.setOnDateSetListener(pickerListener);
//    }

    /**
     * 获取开始和结束日期
     */
    public interface GetDoubleDateListener {
        public void getTime(String mStartDate, String mEndDate,String showDate);
    }

    /**
     * 获取开始和结束日期
     */
    public static void getDoubleDate(final Activity activity, final GetDoubleDateListener listener) {
        Calendar c = Calendar.getInstance();
        new DoubleDatePickerDialog(activity, 0, new DoubleDatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                  int endDayOfMonth) {
                String startMonth = String.valueOf(startMonthOfYear + 1);
                String startDay = String.valueOf(startDayOfMonth);
                String endMonth = String.valueOf(endMonthOfYear + 1);
                String endDay = String.valueOf(endDayOfMonth);
                if (StringUtils.string2Float(startMonth) < 10) {
                    startMonth = "0" + startMonth;
                }
                if (StringUtils.string2Float(startDay) < 10) {
                    startDay = "0" + startDay;
                }
                if (StringUtils.string2Float(endMonth) < 10) {
                    endMonth = "0" + endMonth;
                }
                if (StringUtils.string2Float(endDay) < 10) {
                    endDay = "0" + endDay;
                }
                String mStartDate = startYear + "-" + startMonth + "-" + startDay;
                String mEndDate= endYear + "-" + endMonth + "-" + endDay;
                String mshowDate=mStartDate
                        +"~"+mEndDate;
                listener.getTime(mStartDate, mEndDate,mshowDate);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();

    }
    /**
     * 获取开始和结束日期--竖屏
     */
    public static void getDoubleDateV(final Activity activity, final GetDoubleDateListener listener) {
        Calendar c = Calendar.getInstance();
        new DoubleDatePickerDialog(activity, 0, new DoubleDatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                  int endDayOfMonth) {
                String startMonth = String.valueOf(startMonthOfYear + 1);
                String startDay = String.valueOf(startDayOfMonth);
                String endMonth = String.valueOf(endMonthOfYear + 1);
                String endDay = String.valueOf(endDayOfMonth);
                if (StringUtils.string2Float(startMonth) < 10) {
                    startMonth = "0" + startMonth;
                }
                if (StringUtils.string2Float(startDay) < 10) {
                    startDay = "0" + startDay;
                }
                if (StringUtils.string2Float(endMonth) < 10) {
                    endMonth = "0" + endMonth;
                }
                if (StringUtils.string2Float(endDay) < 10) {
                    endDay = "0" + endDay;
                }
                String mStartDate = startYear + "-" + startMonth + "-" + startDay;
                String mEndDate= endYear + "-" + endMonth + "-" + endDay;
                String mshowDate=mStartDate
                        +"~"+mEndDate;
                listener.getTime(mStartDate, mEndDate,mshowDate);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), "").show();

    }

}
