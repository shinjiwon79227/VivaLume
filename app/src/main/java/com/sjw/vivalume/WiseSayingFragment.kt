import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sjw.vivalume.NotificationReceiver
import com.sjw.vivalume.WiseSayingManager
import com.sjw.vivalume.databinding.FragmentWiseSayingBinding
import java.util.Calendar

class WiseSayingFragment : Fragment() {

    private lateinit var binding: FragmentWiseSayingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWiseSayingBinding.inflate(layoutInflater)
        val wiseSayingManager = WiseSayingManager(requireContext())
        val wiseSayings = wiseSayingManager.getWiseSayings()

        // 명언을 화면에 표시
        wiseSayings?.let {
            val randomWiseSaying = it.random()
            binding.wiseSayingText.text = randomWiseSaying.wiseSaying
            Glide.with(binding.bgImg)
                .load(randomWiseSaying.imgUrl)
                .centerCrop()
                .into(binding.bgImg)
        }

        // 알람 설정 버튼 클릭 이벤트
        binding.settingBtn.setOnClickListener {
            showAlarmDialog()
        }

        // 기본적으로 오후 12시에 알람 설정
        val preferences = requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val alarmEnabled = preferences.getBoolean("alarmEnabled", true) // 기본값은 true (알람 활성화)
        if (alarmEnabled) {
            val hour = preferences.getInt("alarmHour", 12) // 기본값은 12시
            val minute = preferences.getInt("alarmMinute", 0) // 기본값은 0분
            setupCustomAlarm(hour, minute)
        }

        return binding.root
    }

    private fun showAlarmDialog() {
        val preferences = requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val alarmEnabled = preferences.getBoolean("alarmEnabled", true) // 알람이 활성화 상태인지 확인

        val options = if (alarmEnabled) {
            arrayOf("알람 끄기") // 알람이 켜져 있으면 "알람 끄기"
        } else {
            arrayOf("알람 켜기") // 알람이 꺼져 있으면 "알람 켜기"
        }

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("알람 설정")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> {
                        if (alarmEnabled) {
                            // 알람 끄기
                            disableAlarm()
                        } else {
                            // 알람 시간 설정
                            val calendar = Calendar.getInstance()
                            val hour = calendar.get(Calendar.HOUR_OF_DAY)
                            val minute = calendar.get(Calendar.MINUTE)

                            val timePickerDialog = TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                                saveAlarmTime(selectedHour, selectedMinute)
                                setupCustomAlarm(selectedHour, selectedMinute)
                            }, hour, minute, true)

                            timePickerDialog.show()
                        }
                    }
                }
            }
        builder.show()
    }

    // 알람 시간을 SharedPreferences에 저장
    private fun saveAlarmTime(hour: Int, minute: Int) {
        val preferences = requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putInt("alarmHour", hour)
        editor.putInt("alarmMinute", minute)
        editor.putBoolean("alarmEnabled", true) // 알람 활성화
        editor.apply()
    }

    // 알람을 설정하는 함수
    @SuppressLint("ScheduleExactAlarm")
    private fun setupCustomAlarm(hour: Int, minute: Int) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1) // 현재 시간보다 이전이라면 다음 날로 설정
            }
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    // 알람을 비활성화하는 함수
    private fun disableAlarm() {
        val preferences = requireContext().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putBoolean("alarmEnabled", false) // 알람 비활성화
        editor.apply()

        // 설정된 알람을 취소
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}
