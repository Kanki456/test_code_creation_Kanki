package jp.co.sss.lms.ct.f06_login2;

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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト ログイン機能②
 * ケース15
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース15 受講生 初回ログイン 利用規約に不同意")
public class Case15 {

	// ポート番号8080番
	private final int PORT = 8080;

	/** Driver */
	private WebDriver driver = WebDriverUtils.webDriver;

	/** 前処理 */
	@BeforeAll
	static void before() {
		// パスワードに関するポップアップを回避するため、設定を追加
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		// シークレットモードで起動するオプションを追加
		options.addArguments("--incognito");
		webDriver = new ChromeDriver(options);
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
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		// ログインIDの入力欄を空白にしておく
		driver.findElement(By.name("loginId")).clear();

		driver.findElement(By.name("loginId")).sendKeys("StudentAA04");
		driver.findElement(By.name("password")).sendKeys("StudentAA04");
		driver.findElement(By.className("btn-primary")).click();

		pageLoadTimeout(50);
		// タイトル 一致確認
		assertEquals("セキュリティ規約 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("利用規約", driver.findElement(By.tagName("h2")).getText());
		// 次へボタン 一致確認
		assertEquals("次へ", driver.findElement(By.className("btn-primary")).getText());
		getEvidence(new Object() {
		}, "1");
		// 検索結果がスクショ範囲に収まるようにスクロール
		scrollTo("300");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックをせず「次へ」ボタンを押下")
	void test03() {
		scrollTo("200");
		driver.findElement(By.className("btn-primary")).click();

		pageLoadTimeout(50);
		// タイトル 一致確認
		assertEquals("セキュリティ規約 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("利用規約", driver.findElement(By.tagName("h2")).getText());
		// エラーメッセージ 一致確認
		assertEquals("セキュリティ規約への同意は必須です。", driver.findElement(By.className("error")).getText());
		// 次へボタン 一致確認
		assertEquals("次へ", driver.findElement(By.className("btn-primary")).getText());
		getEvidence(new Object() {
		}, "1");
		// 検索結果がスクショ範囲に収まるようにスクロール
		scrollTo("300");
		getEvidence(new Object() {
		}, "2");
	}

}
