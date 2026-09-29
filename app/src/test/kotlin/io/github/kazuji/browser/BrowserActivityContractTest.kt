package io.github.kazuji.browser

import android.app.Activity
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BrowserActivityContractTest {

	@Test
	fun onlyExplicitCompletionReturnsSuccess() {
		val contract = BrowserActivity.Contract()

		assertTrue(contract.parseResult(Activity.RESULT_OK, null))
		assertFalse(contract.parseResult(Activity.RESULT_CANCELED, null))
	}
}
