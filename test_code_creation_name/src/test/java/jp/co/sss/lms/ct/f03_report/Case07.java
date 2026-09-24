package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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

		pageLoadTimeout(50);
		// タイトル 一致確認
		assertEquals("コース詳細 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("DEMOコース 2026年9月1日(火)～2026年9月30日(水)", driver.findElement(By.tagName("h2")).getText());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 9月1日（最初）の詳細ボタンをクリック
		// 未入力である必要がある
		driver.findElement(By.cssSelector("input[type='submit'][value='詳細']")).click();

		pageLoadTimeout(50);
		// タイトル 一致確認
		assertEquals("セクション詳細 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("ハードウェア、ソフトウェア、WWW 2026年9月1日", driver.findElement(By.tagName("h2")).getText());
		// 日報提出/確認ボタン 一致確認
		assertEquals("日報【デモ】を提出する", driver.findElement(By.cssSelector("input[type='submit']")).getAttribute("value"));
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 日報【デモ】を提出するボタンをクリック
		// 未入力である必要がある
		driver.findElement(By.cssSelector("input[type='submit'][value='日報【デモ】を提出する']")).click();

		pageLoadTimeout(50);
		// タイトル 一致確認
		assertEquals("レポート登録 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("日報【デモ】 2026年9月1日", driver.findElement(By.tagName("h2")).getText());
		// 提出ボタン 一致確認
		assertEquals("提出する", driver.findElement(By.cssSelector("button[type='submit']")).getText());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// 「あいうえおabcde」を入力してから提出するボタンをクリック
		driver.findElement(By.id("content_0")).sendKeys("あいうえおabcde");
		driver.findElement(By.className("btn-primary")).click();

		pageLoadTimeout(50);
		// タイトル 一致確認
		assertEquals("セクション詳細 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("ハードウェア、ソフトウェア、WWW 2026年9月1日", driver.findElement(By.tagName("h2")).getText());
		// 日報提出/確認ボタン 一致確認
		assertEquals("提出済み日報【デモ】を確認する",
				driver.findElement(By.cssSelector("input[type='submit']")).getAttribute("value"));
		getEvidence(new Object() {
		});
	}

}
