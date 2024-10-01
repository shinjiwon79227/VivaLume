package com.sjw.vivalume

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.sjw.vivalume.databinding.FragmentMusicBinding
import org.json.JSONObject
import org.jsoup.Jsoup

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MusicFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentMusicBinding
    private lateinit var webView: WebView
    private var initialVideoUrl: String? = null // 처음 로드된 영상 URL 저장

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMusicBinding.inflate(inflater)

        webView = binding.youtubeWebview
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true // 자바스크립트 사용 가능하게 설정

        // JavaScript 인터페이스 추가
        webView.addJavascriptInterface(WebAppInterface(), "Android")

        // WebView 안에서 링크가 열리도록 설정
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                // 페이지 로드가 완료된 후 JavaScript로 클릭 이벤트 처리
                view?.loadUrl(
                    "javascript:(function() {" +
                            "document.addEventListener('click', function(event) {" +
                            "if (event.target.tagName === 'A') {" + // 링크 클릭 여부 확인
                            "event.preventDefault();" + // 기본 링크 이동 방지
                            "Android.checkLink(event.target.href);" + // Android 메서드 호출
                            "}" +
                            "}, true);" +
                            "})()"
                )
            }
        }

        // 지정된 검색어로 유튜브 검색 결과에서 랜덤으로 비디오 링크 추출
        fetchRandomVideoLinkWithAPI("YOUR_API_KEY", "힐링 명상 음악")

        return binding.root
    }

    private fun fetchRandomVideoLinkWithAPI(apiKey: String, searchQuery: String) {
        val url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=$searchQuery&type=video&key=$apiKey"

        Thread {
            try {
                val response = Jsoup.connect(url).ignoreContentType(true).execute().body()
                val jsonObject = JSONObject(response)
                val items = jsonObject.getJSONArray("items")

                if (items.length() > 0) {
                    val randomIndex = (0 until items.length()).random()
                    val videoId = items.getJSONObject(randomIndex).getJSONObject("id").getString("videoId")
                    initialVideoUrl = "https://www.youtube.com/watch?v=$videoId" // 초기 영상 URL 저장
                    webView.post { webView.loadUrl(initialVideoUrl!!) } // 랜덤 영상 URL 로드
                } else {
                    webView.post { Toast.makeText(requireContext(), "영상이 없습니다.", Toast.LENGTH_SHORT).show() }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                webView.post { Toast.makeText(requireContext(), "오류가 발생했습니다.", Toast.LENGTH_SHORT).show() }
            }
        }.start()
    }

    // JavaScript와 통신할 인터페이스
    inner class WebAppInterface {
        @JavascriptInterface
        fun checkLink(clickedLink: String) {
            // 클릭한 링크가 초기 영상 URL과 다르면 토스트 메시지 표시
            if (clickedLink != initialVideoUrl) {
                webView.post {
                    Toast.makeText(requireContext(), "이동이 제한되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 뒤로가기 버튼 처리
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (webView.canGoBack()) {
                webView.goBack() // 웹뷰에서 뒤로가기
            } else {
                requireActivity().onBackPressed() // 앱 내에서 뒤로가기
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MusicFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
