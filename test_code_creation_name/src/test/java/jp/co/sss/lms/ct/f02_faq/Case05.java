package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト よくある質問機能
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// ドロップダウンメニュー「機能」をクリック
		driver.findElement(By.className("dropdown-toggle")).click();
		// その中からaタグの「ヘルプ」リンクをクリック
		driver.findElement(By.linkText("ヘルプ")).click();

		pageLoadTimeout(50);
		// タイトル 一致確認
		assertEquals("ヘルプ | LMS", driver.getTitle());
		// h2タグ 一致確認
		assertEquals("ヘルプ", driver.findElement(By.tagName("h2")).getText());
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// 元のページのハンドルを記憶
		String Handle = driver.getWindowHandle();
		// その中からaタグの「よくある質問」リンクをクリック
		driver.findElement(By.linkText("よくある質問")).click();

		pageLoadTimeout(50);

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

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// キーワードの入力欄を空白にしておく
		driver.findElement(By.className("form-control")).clear();

		driver.findElement(By.className("form-control")).sendKeys("研修");
		driver.findElement(By.className("btn-primary")).click();

		pageLoadTimeout(50);
		// 入力キーワード 一致確認
		assertEquals("研修", driver.findElement(By.className("form-control")).getAttribute("value"));

		List<WebElement> dtElements = driver.findElements(By.tagName("dt"));
		// 検索結果 一致確認（1つめ）
		WebElement dtElement1 = dtElements.get(0);
		assertEquals("助成金書類の作成方法が分かりません", dtElement1.findElements(By.tagName("span")).get(1).getText());
		// 検索結果 一致確認（2つめ）
		WebElement dtElement2 = dtElements.get(1);
		assertEquals("研修の申し込みはどのようにすれば良いですか？", dtElement2.findElements(By.tagName("span")).get(1).getText());
		// 入力キーワードの結果
		getEvidence(new Object() {
		}, "1");
		// 検索結果がスクショ範囲に収まるようにスクロール
		scrollTo("300");
		// 検索結果
		getEvidence(new Object() {
		}, "2");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// TODO ここに追加
	}

}
