package jp.co.sss.lms.ct.f01_login1;

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
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

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
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02_1() {
		// ログインIDの入力欄を空白にしておく
		driver.findElement(By.name("loginId")).clear();

		driver.findElement(By.name("loginId")).sendKeys("WRONG");
		driver.findElement(By.name("password")).sendKeys("WRONG");
		driver.findElement(By.className("btn-primary")).click();
		
		// タイトル 一致確認
		assertEquals("ログイン | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("ログイン", driver.findElement(By.tagName("h2")).getText());
		// エラーメッセージ 一致確認
		assertEquals("* ログインに失敗しました。", driver.findElement(By.cssSelector(".help-inline.error")).getText());
		// ログインボタン 一致確認
		assertEquals("ログイン", driver.findElement(By.className("btn-primary")).getAttribute("value"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02_2() {
		// ログインIDの入力欄を空白にしておく
		driver.findElement(By.name("loginId")).clear();

		driver.findElement(By.name("loginId")).sendKeys("StudentAA02");
		driver.findElement(By.name("password")).sendKeys("StudentAA03");
		driver.findElement(By.className("btn-primary")).click();
		
		// タイトル 一致確認
		assertEquals("ログイン | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("ログイン", driver.findElement(By.tagName("h2")).getText());
		// エラーメッセージ 一致確認
		assertEquals("* ログインに失敗しました。", driver.findElement(By.cssSelector(".help-inline.error")).getText());
		// ログインボタン 一致確認
		assertEquals("ログイン", driver.findElement(By.className("btn-primary")).getAttribute("value"));
		getEvidence(new Object() {
		});
	}

}
