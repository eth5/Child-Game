package pro.it_dev.childgame.presentation.activity

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import pro.it_dev.childgame.presentation.navigation.Navigation
import pro.it_dev.childgame.presentation.screen.cards.CardsScreen
import pro.it_dev.childgame.presentation.ui.theme.ChildGameTheme
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	lateinit var TTS:TextToSpeech
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		TTS = TextToSpeech(this) {initStatus->

			if (initStatus == TextToSpeech.SUCCESS) {
				if (TTS.isLanguageAvailable( Locale(Locale.getDefault().language))
					== TextToSpeech.LANG_AVAILABLE) {
					TTS.language = Locale(Locale.getDefault().language);
				} else {
					TTS.language = Locale.US;
				}
				TTS.setPitch(1.3f);
				TTS.setSpeechRate(0.7f);

			}
		}
		val d = TTS.speak("hello!", TextToSpeech.QUEUE_FLUSH,null,"pidor" )


		setContent {
			ChildGameTheme {
				Surface(
					color = MaterialTheme.colors.background,
					modifier = Modifier.fillMaxSize()
				) {
					Navigation()
				}
			}
		}
//		private void signIn() {
//			Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//			startActivityForResult(signInIntent, RC_SIGN_IN);
//		}
	}
}

