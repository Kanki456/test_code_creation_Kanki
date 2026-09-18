package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

	// ポート番号8080番
	private final int PORT = 8080;

	/** Driver */
	private WebDriver driver = WebDriverUtils.webDriver;

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		goTo("http://localhost:" + PORT + "/lms");
		// タイトル 一致確認
		assertEquals("ログイン | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("ログイン", driver.findElement(By.tagName("h2")).getText());
		// ログインボタン 一致確認
		assertEquals("ログイン", driver.findElement(By.className("btn-primary")).getAttribute("value"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// ログインIDの入力欄を空白にしておく
		driver.findElement(By.name("loginId")).clear();

		driver.findElement(By.name("loginId")).sendKeys("StudentAA01");
		driver.findElement(By.name("password")).sendKeys("ItTest2026");
		driver.findElement(By.className("btn-primary")).click();

		pageLoadTimeout(5);
		// タイトル 一致確認
		assertEquals("コース詳細 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("DEMOコース 2026年9月1日(火)～2026年9月30日(水)", driver.findElement(By.tagName("h2")).getText());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// ドロップダウンメニュー「機能」をクリック
		driver.findElement(By.className("dropdown-toggle")).click();
		// その中からaタグの「ヘルプ」リンクをクリック
		driver.findElement(By.linkText("ヘルプ")).click();

		// タイトル 一致確認
		assertEquals("ヘルプ | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("ヘルプ", driver.findElement(By.tagName("h2")).getText());
		getEvidence(new Object() {
		});
	}

	/** 
	 * @see <a href="https://qiita.com/tsuttie/items/60544fe50fe2969b63d3">【Java】Selenium2で新規タブを開いたときのお話 - Qiita</a>
	 */
	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 元のページのハンドルを記憶
		String Handle = driver.getWindowHandle();
		// その中からaタグの「よくある質問」リンクをクリック
		driver.findElement(By.linkText("よくある質問")).click();

		pageLoadTimeout(5);

		//次のタブのハンドルを用意し、タブが新しく開かれていたらnewHandleに代入する
		String newHandle = null;
		for (String id : driver.getWindowHandles()) {
			if (!id.equals(Handle)) {
				newHandle = id;
			}
		}
		//newHandleにハンドルを移す
		driver.switchTo().window(newHandle);
		// タイトル 一致確認
		assertEquals("よくある質問 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("よくある質問", driver.findElement(By.tagName("h2")).getText());
		getEvidence(new Object() {
		});
	}

}
