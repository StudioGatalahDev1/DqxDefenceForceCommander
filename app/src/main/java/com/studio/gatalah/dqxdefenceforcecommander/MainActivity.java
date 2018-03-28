package com.studio.gatalah.dqxdefenceforcecommander;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.util.Log;		// Log出力用
import android.view.View;		// View使用のため
import java.util.Locale;		// 言語選択用

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

	private TextView mTextMessage;

	TextToSpeech tts;
	String contents="ドラえもんっ";

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
			= new BottomNavigationView.OnNavigationItemSelectedListener() {

		@Override
		public boolean onNavigationItemSelected(@NonNull MenuItem item) {
			switch (item.getItemId()) {
				case R.id.navigation_home:
					mTextMessage.setText(R.string.title_home);
					return true;
				case R.id.navigation_dashboard:
					mTextMessage.setText(R.string.title_dashboard);
					return true;
				case R.id.navigation_notifications:
					mTextMessage.setText(R.string.title_notifications);
					return true;
			}
			return false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tts = new TextToSpeech(this, this);
		Button btn =findViewById(R.id.action_read);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			// ボタンクリック時
			public void onClick(View v) {
				if( v.getId() == R.id.action_read ){
					speechText();
				}
			}
		});

		mTextMessage = findViewById(R.id.message);
		BottomNavigationView navigation = findViewById(R.id.navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
	}

	@Override
	public void onInit(int status) {
		if(TextToSpeech.SUCCESS == status ) {
			// 言語選択
			Locale locale = Locale.JAPAN;
			if (tts.isLanguageAvailable(locale) >= TextToSpeech.LANG_AVAILABLE) {
				tts.setLanguage(locale);
			} else {
				Log.d("Error", "Locale");
			}
		}else{
			Log.d("Error","init");
		}

		//初期設定
		tts.setPitch(1.5f);
		tts.setSpeechRate(2.0f);
//		tts.setVoice();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if( null != tts ){
			// ttsのリソース解放
			tts.shutdown();
		}
	}

	private void speechText(){
		if( 0 < contents.length() ){
			if( tts.isSpeaking() ){
				// 読み上げ中なら停止
				tts.stop();
			}

			//読み上げられているテキストを確認
			System.out.println( contents );
			//読み上げ開始
			tts.speak(contents,TextToSpeech.QUEUE_FLUSH,null);
		}
	}
}
