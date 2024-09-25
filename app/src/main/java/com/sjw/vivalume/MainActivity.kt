package com.sjw.vivalume

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.sjw.vivalume.R
import com.sjw.vivalume.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    // 명언 리스트
    val wiseSayings = listOf(
        WiseSaying(
            "일단 시작해라. 나중에 완벽해질 것이다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "어려움은 성공의 디딤돌이다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "할 수 있다는 믿음이 없으면 아무것도 시작할 수 없다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "작은 것들이 모여 큰 변화를 만든다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "성공은 준비된 자에게 온다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "도전하지 않으면 아무것도 얻지 못한다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "시간은 누구에게나 공평하다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "넘어져도 다시 일어나는 것이 중요하다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "꾸준함이 성공을 만든다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "변화는 스스로 시작하는 것이다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "희망은 삶의 원동력이다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "배움은 끝이 없다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "좋은 생각은 좋은 결과를 만든다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "오늘의 노력이 내일의 성공을 만든다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "가장 큰 위험은 아무런 도전을 하지 않는 것이다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "실패는 성공의 어머니이다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "성공은 목표를 설정하는 것에서 시작한다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "내일의 나는 오늘의 나보다 나아질 것이다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "긍정적인 생각은 긍정적인 결과를 가져온다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "노력 없는 성공은 없다.",
            "https://wallpapers-clan.com/wp-content/uploads/2023/02/lavender-aesthetic-background.jpg"
        ),
        WiseSaying(
            "인내는 쓰지만 그 열매는 달다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "도전은 실패를 두려워하지 않는 것이다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "목표가 없는 사람은 항해를 하지 않는 배와 같다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "시간은 뒤돌아보지 않는다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "오늘을 소중히 여겨라.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "미래는 현재 우리가 만드는 것이다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "지식은 힘이다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "꿈을 꾸는 자만이 꿈을 이룰 수 있다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "가장 위대한 승리는 자기 자신을 이기는 것이다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "변명하지 말고 변화를 만들어라.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "지금 하지 않으면 내일은 없다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "노력은 배신하지 않는다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "세상에 불가능은 없다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "성공은 작은 일들을 모아서 이루어진다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "목표가 있으면 길이 있다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "항상 새로운 것을 배우는 자세를 가져라.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "자신을 믿어라.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "포기는 실패의 시작이다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "오늘의 나는 어제의 나를 이기는 사람이다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "기회는 기다리지 않는다.",
            "https://wallpapers.com/images/featured/light-purple-aesthetic-rw2vix1r99tji173.jpg"
        ),
        WiseSaying(
            "꿈은 이루어진다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "실패는 끝이 아니다. 새로운 시작이다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "과거는 바꿀 수 없지만 미래는 바꿀 수 있다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "성공은 실수를 통해 배우는 것이다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "성공한 사람은 항상 배우는 사람이다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "목표 없이 사는 것은 방향 없이 항해하는 것과 같다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "한 걸음 한 걸음이 성공으로 가는 길이다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "할 수 있다는 믿음이 절반의 성공이다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "시간은 돈이다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "성공은 작은 노력이 쌓여 이루어진다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "꿈을 크게 가지면 크게 이룬다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "실패를 두려워하지 말고 도전하라.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "꿈은 노력하는 자에게 이루어진다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "삶은 도전이다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "포기하지 않으면 기회는 온다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "노력은 성공의 어머니이다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "실패는 성공을 위한 교훈이다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "성공은 끈기에서 온다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "성공은 열심히 일하는 사람에게 온다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "어떤 어려움도 이겨낼 수 있다.",
            "https://wallpapercave.com/wp/wp6241541.jpg"
        ),
        WiseSaying(
            "모든 일은 작은 것에서 시작된다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "노력은 결과로 보답한다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "꾸준한 노력이 큰 성과를 만든다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "행동하지 않으면 아무 일도 일어나지 않는다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "도전은 실패를 두려워하지 않는 것이다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "성공은 준비된 자에게 온다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "꿈을 이루기 위해서는 포기하지 마라.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "성공은 목표를 설정하는 것에서 시작한다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "성공은 실패를 통해 배우는 것이다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "꾸준한 노력은 큰 성과를 만든다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "성공은 도전에서 시작된다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "목표가 있으면 길이 있다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "성공은 끊임없는 도전에서 온다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "노력 없이는 아무것도 얻을 수 없다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "어떤 어려움도 극복할 수 있다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "행동하지 않으면 기회도 없다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "노력은 절대 배신하지 않는다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "실패는 성공으로 가는 과정이다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "항상 긍정적으로 생각하라.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "목표가 없으면 도착지도 없다.",
            "https://i.pinimg.com/236x/72/8d/d3/728dd3cd0f2b50ca6f941cfcd5f7ae8a.jpg"
        ),
        WiseSaying(
            "작은 일에도 최선을 다하라.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        ),
        WiseSaying(
            "성공은 준비된 자에게만 온다.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        ),
        WiseSaying(
            "시간은 금이다.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        ),
        WiseSaying(
            "기회는 잡는 자에게 온다.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        ),
        WiseSaying(
            "포기하지 않으면 기회는 온다.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        ),
        WiseSaying(
            "꿈을 크게 가지면 크게 이룬다.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        ),
        WiseSaying(
            "성공은 끈기에서 온다.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        ),
        WiseSaying(
            "도전하지 않으면 아무 일도 일어나지 않는다.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        ),
        WiseSaying(
            "꿈을 이루기 위해서는 도전하라.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        ),
        WiseSaying(
            "목표가 있어야 방향도 있다.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        ),
        WiseSaying(
            "어떤 어려움도 이겨낼 수 있다.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        ),
        WiseSaying(
            "성공은 실패를 통해 얻는 것이다.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        ),
        WiseSaying(
            "작은 일이라도 최선을 다하라.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        ),
        WiseSaying(
            "노력은 결코 배신하지 않는다.",
            "https://mcdn.wallpapersafari.com/medium/64/35/XMBJmIR.webp"
        )
    )
    // --------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.fragment_wise_saying)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // SharedPreferences 인스턴스
        val preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)

        // 앱이 최초 실행인지 확인하는 플래그 (기본값 true)
        val isFirstRun = preferences.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            // 최초 실행 시 명언과 이미지 URL 저장
            val quoteManager = WiseSayingManager(this)
            quoteManager.saveWiseSayings(wiseSayings)

            // 플래그를 false로 변경하여 이후에는 저장하지 않도록 설정
            val editor = preferences.edit()
            editor.putBoolean("isFirstRun", false)
            editor.apply()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 알림 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1
                )
            } else {
                setupAlarm() // 권한이 이미 허용된 경우 알람 설정
            }
        } else {
            setupAlarm() // Android 12 이하에서는 권한이 필요 없음
        }

        setFragment(WiseSayingFragment())

        with(binding) {
            bottomNavigation.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.wise_saying -> {
                        setFragment(WiseSayingFragment())
                        true
                    }

                    R.id.music -> { // 두 번째 아이템의 ID를 수정
                        setFragment(MusicFragment())
                        true
                    }

                    else -> false
                }
            }

        }
    }

    /**
     * fragment를 전환해주는 함수
     *
     * @param fragment
     */
    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setReorderingAllowed(true)
            .addToBackStack("")
            .commit()
    }

    private fun setupAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        // 1시간마다 알림을 보내도록 설정
        // 1시간: 60 * 60 * 1000L
        val intervalMillis = 5 * 1000L // 테스트: 5초
        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + intervalMillis,
            intervalMillis,
            pendingIntent
        )
    }
}