import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;



public class TesteAlert {
	
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
	public void deveInteragirComAlertSimples() {
		dsl.clicarBotao("alert");
		String texto = dsl.alertaObterTextoEAceita();
		Assert.assertEquals("Alert Simples", texto);
		/**
		 * pega o texto do alert e escreve no campo nome
		 * */
		dsl.escrever("elementosForm:nome", texto);
	}	
	
		
		@Test
		public void deveInteragirComAlertfirmConfirm() {
	
			dsl.clicarBotao("confirm");
			Assert.assertEquals("Confirm Simples", dsl.alertaObterTextoEAceita());
			Assert.assertEquals("Confirmado", dsl.alertaObterTextoEAceita());
			
			dsl.clicarBotao("confirm");
			Assert.assertEquals("Confirm Simples", dsl.alertaObterTextoENega());
			Assert.assertEquals("Negado", dsl.alertaObterTextoENega());
			
		}	
		
		@Test
		public void deveInteragirComComfirmnegado() {

			//cen?rio de valida??o do botqao cancel
			dsl.clicarBotao("confirm");
			Assert.assertEquals("Confirm Simples", dsl.alertaObterTextoENega());
			Assert.assertEquals("Negado", dsl.alertaObterTextoENega());
			
		}
		
		@Test
		public void deveInteragirComAlertPrompt() {

			dsl.clicarBotao("prompt");
			
			Assert.assertEquals("Digite um numero", dsl.alertaObterTexto());
			dsl.alertaEscrever("12345");
			Assert.assertEquals("Era 12345?",dsl.alertaObterTextoEAceita());
			Assert.assertEquals(":D",dsl.alertaObterTextoEAceita());

		}	
}
