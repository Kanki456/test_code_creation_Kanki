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
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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

		// 未ログインの受講生ユーザーでログイン
		driver.findElement(By.name("loginId")).sendKeys("StudentBD07");
		driver.findElement(By.name("password")).sendKeys("StudentBD07");
		driver.findElement(By.className("btn-primary")).click();

		visibilityTimeout(By.className("navbar-header"), 500);
		// タイトル 一致確認
		assertEquals("セキュリティ規約 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("利用規約", driver.findElement(By.tagName("h2")).getText());
		// 次へボタン 一致確認
		assertEquals("次へ", driver.findElement(By.className("btn-primary")).getText());
		getEvidence(new Object() {
		}, "1");
		scrollTo("300");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {
		scrollTo("200");
		driver.findElement(By.name("securityFlg")).click();
		driver.findElement(By.className("btn-primary")).click();

		visibilityTimeout(By.className("navbar-header"), 500);
		// タイトル 一致確認
		assertEquals("パスワード変更 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("パスワード変更", driver.findElement(By.tagName("h2")).getText());
		// 戻るボタン 一致確認
		assertEquals("戻る",
				driver.findElement(By.cssSelector("button[type='button'][onclick='history.back();']")).getText());
		// 変更ボタン 一致確認
		assertEquals("変更", driver.findElement(By.cssSelector("button[type='submit']")).getText());
		getEvidence(new Object() {
		}, "1");
		scrollTo("300");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {
		driver.findElement(By.cssSelector("button[type='submit']")).click();

		// モーダルウィンドウの出現を待ってからクリック
		visibilityTimeout(By.className("modal-dialog"), 500);
		driver.findElement(By.cssSelector("button[type='button'][id='upd-btn']")).click();

		visibilityTimeout(By.className("navbar-header"), 500);
		// タイトル 一致確認
		assertEquals("パスワード変更 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("パスワード変更", driver.findElement(By.tagName("h2")).getText());
		// 「現在のパスワード」のエラーメッセージ確認
		String errorText1 = driver.findElements(By.className("col-lg-10")).get(0)
				.findElement(By.cssSelector("ul li span.error")).getText();
		assertTrue(errorText1.contains("現在のパスワードは必須です。"));
		// 「新しいパスワード」のエラーメッセージ確認
		String errorText2 = driver.findElements(By.className("col-lg-10")).get(1)
				.findElement(By.cssSelector("ul li span.error")).getText();
		assertTrue(errorText2.contains("パスワードは必須です。")
				&& errorText2.contains("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。"));
		// 「確認パスワード」のエラーメッセージ確認
		String errorText3 = driver.findElements(By.className("col-lg-10")).get(2)
				.findElement(By.cssSelector("ul li span.error")).getText();
		assertTrue(errorText3.contains("確認パスワードは必須です。"));
		// 戻るボタン 一致確認
		assertEquals("戻る",
				driver.findElement(By.cssSelector("button[type='button'][onclick='history.back();']")).getText());
		// 変更ボタン 一致確認
		assertEquals("変更", driver.findElement(By.cssSelector("button[type='submit']")).getText());
		getEvidence(new Object() {
		}, "1");
		scrollTo("300");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {
		driver.findElement(By.id("currentPassword")).sendKeys("StudentBD07");
		driver.findElement(By.id("password")).sendKeys("01234567890123456789Ab");
		driver.findElement(By.id("passwordConfirm")).sendKeys("01234567890123456789Ab");
		driver.findElement(By.cssSelector("button[type='submit']")).click();

		// モーダルウィンドウの出現を待ってからクリック
		visibilityTimeout(By.className("modal-dialog"), 500);
		driver.findElement(By.cssSelector("button[type='button'][id='upd-btn']")).click();

		visibilityTimeout(By.className("navbar-header"), 500);
		// タイトル 一致確認
		assertEquals("パスワード変更 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("パスワード変更", driver.findElement(By.tagName("h2")).getText());
		// 「新しいパスワード」のエラーメッセージ確認
		String errorText2 = driver.findElements(By.className("col-lg-10")).get(1)
				.findElement(By.cssSelector("ul li span.error")).getText();
		assertTrue(errorText2.contains("パスワードの長さが最大値(20)を超えています。"));
		// 戻るボタン 一致確認
		assertEquals("戻る",
				driver.findElement(By.cssSelector("button[type='button'][onclick='history.back();']")).getText());
		// 変更ボタン 一致確認
		assertEquals("変更", driver.findElement(By.cssSelector("button[type='submit']")).getText());
		getEvidence(new Object() {
		}, "1");
		scrollTo("300");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {
		driver.findElement(By.id("currentPassword")).sendKeys("StudentBD07");
		driver.findElement(By.id("password")).sendKeys("AAAAAAAAAA");
		driver.findElement(By.id("passwordConfirm")).sendKeys("AAAAAAAAAA");
		driver.findElement(By.cssSelector("button[type='submit']")).click();

		// モーダルウィンドウの出現を待ってからクリック
		visibilityTimeout(By.className("modal-dialog"), 500);
		driver.findElement(By.cssSelector("button[type='button'][id='upd-btn']")).click();

		visibilityTimeout(By.className("navbar-header"), 500);
		// タイトル 一致確認
		assertEquals("パスワード変更 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("パスワード変更", driver.findElement(By.tagName("h2")).getText());
		// 「新しいパスワード」のエラーメッセージ確認
		String errorText2 = driver.findElements(By.className("col-lg-10")).get(1)
				.findElement(By.cssSelector("ul li span.error")).getText();
		assertTrue(errorText2.contains("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。"));
		// 戻るボタン 一致確認
		assertEquals("戻る",
				driver.findElement(By.cssSelector("button[type='button'][onclick='history.back();']")).getText());
		// 変更ボタン 一致確認
		assertEquals("変更", driver.findElement(By.cssSelector("button[type='submit']")).getText());
		getEvidence(new Object() {
		}, "1");
		scrollTo("300");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {
		driver.findElement(By.id("currentPassword")).sendKeys("StudentBD07");
		driver.findElement(By.id("password")).sendKeys("TruePass04");
		driver.findElement(By.id("passwordConfirm")).sendKeys("WrongPass04");
		driver.findElement(By.cssSelector("button[type='submit']")).click();

		// モーダルウィンドウの出現を待ってからクリック
		visibilityTimeout(By.className("modal-dialog"), 500);
		driver.findElement(By.cssSelector("button[type='button'][id='upd-btn']")).click();

		visibilityTimeout(By.className("navbar-header"), 500);
		// タイトル 一致確認
		assertEquals("パスワード変更 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("パスワード変更", driver.findElement(By.tagName("h2")).getText());
		// 「新しいパスワード」のエラーメッセージ確認
		String errorText2 = driver.findElements(By.className("col-lg-10")).get(1)
				.findElement(By.cssSelector("ul li span.error")).getText();
		assertTrue(errorText2.contains("パスワードと確認パスワードが一致しません。"));
		// 戻るボタン 一致確認
		assertEquals("戻る",
				driver.findElement(By.cssSelector("button[type='button'][onclick='history.back();']")).getText());
		// 変更ボタン 一致確認
		assertEquals("変更", driver.findElement(By.cssSelector("button[type='submit']")).getText());
		getEvidence(new Object() {
		}, "1");
		scrollTo("300");
		getEvidence(new Object() {
		}, "2");
	}

}
