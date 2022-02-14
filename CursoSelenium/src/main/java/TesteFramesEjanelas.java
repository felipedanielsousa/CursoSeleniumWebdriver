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
	
	@Before
	public void inicializa() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\t_felipe.barbosa\\Documents\\Projetos\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1200, 765));

		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html"); //informando o diretorio raiz de onde estao os arquivos
	}

	@After
		public void finaliza() {
		driver.quit();
	}
	
	@Test
	public void deveInteragirComFrame() {

		driver.switchTo().frame("frame1");
		driver.findElement(By.id("frameButton")).click();
		
		Alert alert = driver.switchTo().alert();
		String mensagemAlert = alert.getText();
		Assert.assertEquals("Frame OK!", mensagemAlert);
		alert.accept();
		
		driver.switchTo().defaultContent();
		driver.findElement(By.id("elementosForm:nome")).sendKeys(mensagemAlert);
	}
	
	@Test
	public void deveInteragirComJanelas() {
	
		driver.findElement(By.id("buttonPopUpEasy")).click();
		
		driver.switchTo().window("Popup");
		driver.findElement(By.tagName("textarea")).sendKeys("Deu certo?");
		driver.close();
		
		driver.switchTo().window("");
		driver.findElement(By.tagName("textarea")).sendKeys("E agora?");

	}
	
	@Test
	public void deveInteragirComJanelaSemTitulo() {
	
		driver.findElement(By.id("buttonPopUpHard")).click();
		System.out.println(driver.getWindowHandle());
		/**GetWindowHandles traz o ID da janela(sempre que executa, esse id muda) **/
		System.out.println(driver.getWindowHandles());
		/**Driver retorna um webelement, o getWindowHandles foi armazenado pro m�todo toArray
		 * e passado o indice 1 que corresponde o da janela pequena, o casting foi feito pois o driver
		 * n�o aceita uma string e sim um objeto webdriver **/
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
		driver.findElement(By.tagName("textarea")).sendKeys("Deu certo?");
	
		
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[0]);
		driver.findElement(By.id("elementosForm:sugestoes")).sendKeys("E agora?");

	}
}