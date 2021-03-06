import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TesteFramesEjanelas {
	
	private WebDriver driver; 
	private DSL dsl;
	
	@Before
	public void inicializa() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\t_felipe.barbosa\\Documents\\Projetos\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		dsl = new DSL(driver);
		driver.manage().window().setSize(new Dimension(1200, 765));

		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html"); //informando o diretorio raiz de onde estao os arquivos
	}

	@After
		public void finaliza() {
		driver.quit();
	}
	
	@Test
	public void deveInteragirComFrame() {
		
		dsl.entrarFrame("frame1");
		dsl.clicarBotao("frameButton");
		
		String msg = dsl.alertaObterTextoEAceita();
		Assert.assertEquals("Frame OK!", msg);
		
		dsl.sairFrame();
		dsl.escrever("elementosForm:nome", msg);
	}
	
	@Test
	public void deveInteragirComJanelas() {
		dsl.clicarBotao("buttonPopUpEasy");
		
		dsl.trocarJanela("Popup");
		dsl.escrever(By.tagName("textarea"), "Deu certo?");
		driver.close();
		
		dsl.trocarJanela("");
		dsl.escrever(By.tagName("textarea"), "E agora?");
	}
	
	@Test
	public void deveInteragirComJanelaSemTitulo() {
	
		dsl.clicarBotao("buttonPopUpHard");
		System.out.println(driver.getWindowHandle());
		/**GetWindowHandles traz o ID da janela(sempre que executa, esse id muda) **/
		System.out.println(driver.getWindowHandles());
		/**Driver retorna um webelement, o getWindowHandles foi armazenado pro m?todo toArray
		 * e passado o indice 1 que corresponde o da janela pequena, o casting foi feito pois o driver
		 * n?o aceita uma string e sim um objeto webdriver **/
		dsl.trocarJanela((String) driver.getWindowHandles().toArray()[1]);
		dsl.escrever(By.tagName("textarea"), "Deu certo?");
	
		
		dsl.trocarJanela((String) driver.getWindowHandles().toArray()[0]);
		dsl.escrever(By.tagName("textarea"), "E agora?");

	}
}
