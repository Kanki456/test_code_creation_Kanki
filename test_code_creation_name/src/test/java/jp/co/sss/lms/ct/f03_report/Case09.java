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
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		// aタグの「ようこそ受講生ＡＡ１さん」リンクをクリック
		driver.findElement(By.linkText("ようこそ受講生ＡＡ１さん")).click();

		pageLoadTimeout(50);
		// タイトル 一致確認
		assertEquals("ユーザー詳細", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("ユーザー詳細", driver.findElement(By.tagName("h2")).getText());
		// 1つ目のh3タグ 一致確認
		assertEquals("基本情報", driver.findElements(By.tagName("h3")).get(0).getText());
		// 2つ目のh3タグ 一致確認
		assertEquals("試験", driver.findElements(By.tagName("h3")).get(1).getText());
		// 3つ目のh3タグ 一致確認
		assertEquals("レポート", driver.findElements(By.tagName("h3")).get(2).getText());
		getEvidence(new Object() {
		}, "1");
		// 結果がスクショ範囲に収まるようにスクロール
		scrollTo("300");
		getEvidence(new Object() {
		}, "2");
		scrollTo("600");
		getEvidence(new Object() {
		}, "3");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		scrollTo("600");
		// 該当レポートの修正するボタンをクリック
		driver.findElement(
				By.cssSelector(
						"form[action='/lms/report/regist']:has(input[value='6']) input[type='submit'][value='修正する']"))
				.click();

		pageLoadTimeout(50);
		// タイトル 一致確認
		assertEquals("レポート登録 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("週報【デモ】 2026年9月2日", driver.findElement(By.tagName("h2")).getText());
		// 提出ボタン 一致確認
		assertEquals("提出する", driver.findElement(By.className("btn-primary")).getText());
		getEvidence(new Object() {
		}, "1");
		// 結果がスクショ範囲に収まるようにスクロール
		scrollTo("600");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		// TODO ここに追加
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// TODO ここに追加
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// TODO ここに追加
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// TODO ここに追加
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// TODO ここに追加
	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：所感・一週間の振り返りが2000文字超")
	void test10() {
		// TODO ここに追加
	}

}
