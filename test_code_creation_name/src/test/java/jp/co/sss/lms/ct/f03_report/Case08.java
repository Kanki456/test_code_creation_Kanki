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
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// 9月2日の詳細ボタンをクリック　※2日目なのでget(1)
		// 週報を提出済みである必要がある
		driver.findElements(By.cssSelector("input[type='submit'][value='詳細']")).get(1).click();

		pageLoadTimeout(50);
		// タイトル 一致確認
		assertEquals("セクション詳細 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("アルゴリズム、フローチャート 2026年9月2日", driver.findElement(By.tagName("h2")).getText());
		// 週報提出/確認ボタン 一致確認　※3つ目のボタンなのでget(2)
		assertEquals("提出済み週報【デモ】を確認する",
				driver.findElements(By.cssSelector("input[type='submit']")).get(2).getAttribute("value"));
		getEvidence(new Object() {
		}, "1");
		// 結果がスクショ範囲に収まるようにスクロール
		scrollTo("200");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// 提出済み週報【デモ】を確認するボタンをクリック
		// 提出済みである必要がある
		driver.findElement(By.cssSelector("input[type='submit'][value='提出済み週報【デモ】を確認する']")).click();

		pageLoadTimeout(50);
		// タイトル 一致確認
		assertEquals("レポート登録 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("週報【デモ】 2026年9月2日", driver.findElement(By.tagName("h2")).getText());
		// 提出ボタン 一致確認
		assertEquals("提出する", driver.findElement(By.cssSelector("button[type='submit']")).getText());
		scrollTo("0");
		getEvidence(new Object() {
		}, "1");
		// 結果がスクショ範囲に収まるようにスクロール
		scrollTo("400");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// 学習項目と所感を空白にしておく
		driver.findElement(By.id("intFieldName_0")).clear();
		driver.findElement(By.id("content_1")).clear();
		// 学習項目に「修正後」、所感に「修正後です」を入力してから提出するボタンをクリック
		driver.findElement(By.id("intFieldName_0")).sendKeys("修正後");
		driver.findElement(By.id("content_1")).sendKeys("修正後です");
		scrollTo("400");
		driver.findElement(By.className("btn-primary")).click();

		pageLoadTimeout(50);
		// タイトル 一致確認
		assertEquals("セクション詳細 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("アルゴリズム、フローチャート 2026年9月2日", driver.findElement(By.tagName("h2")).getText());
		// 週報提出/確認ボタン 一致確認　※3つ目のボタンなのでget(2)
		assertEquals("提出済み週報【デモ】を確認する",
				driver.findElements(By.cssSelector("input[type='submit']")).get(2).getAttribute("value"));
		getEvidence(new Object() {
		}, "1");
		// 結果がスクショ範囲に収まるようにスクロール
		scrollTo("200");
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
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
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// 該当レポートの詳細ボタンをクリック
		driver.findElement(
				By.cssSelector("form[action='/lms/report/detail']:has(input[value='6']) input[type='submit']")).click();
		
		pageLoadTimeout(50);
		// タイトル 一致確認
		assertEquals("レポート詳細 | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("週報【デモ】 2026年9月2日", driver.findElement(By.tagName("h2")).getText());

		// 2つ目のtable-hoverクラスから、
		// 2つ目のtrタグ中の、1つ目のtdタグ中の、pタグ中のテキストを代入
		String text1 = driver.findElements(By.className("table-hover")).get(1)
				.findElement(By.cssSelector("table tr:nth-child(2) td:nth-child(1) p")).getText();
		// 学習項目 一致確認
		assertEquals("修正後", text1);

		// 3つ目のtable-hoverクラスから、
		// 2つ目のtrタグ中の、tdタグ中のテキストを代入
		String text2 = driver.findElements(By.className("table-hover")).get(2)
				.findElement(By.cssSelector("table tr:nth-child(2) td")).getText();
		// 所感 一致確認
		assertEquals("修正後です", text2);
		getEvidence(new Object() {
		});
	}

}
