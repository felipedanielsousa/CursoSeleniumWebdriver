import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class deveTestarRegraDeNegocio {
	
	private WebDriver driver;
	private DSL dsl;
	private CampoTreinamentoPage page;
	
	@Before
	public void inicializa() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\t_felipe.barbosa\\Documents\\Projetos\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		dsl = new DSL(driver);
		page = new CampoTreinamentoPage(driver);
		driver.manage().window().setSize(new Dimension(1200, 765));
		
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html"); //informando o diretorio raiz de onde estao os arquivos
	}

	@After
		public void finaliza() {
		driver.quit();
	}

	
	@Test
	public void deveValidarNomeObrigatorio() {

		page.cadastrar();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Nome eh obrigatorio", alert.getText());
	}
	
	@Test
	public void deveValidarSobrenomeObrigatorio() {
		page.setNome("Felipe");
		page.cadastrar();
		
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Sobrenome eh obrigatorio", alert.getText());
	}
	
	@Test
	public void deveValidarSexoObrigatorio() {
		page.setNome("Nome qualquer");
		page.setSobrenome("Sobrenome qualquer");
		page.cadastrar();
		Assert.assertEquals("Sexo eh obrigatorio", dsl.alertaObterTextoEAceita());
	}
	
	@Test
	public void deveValidarComidaVegetariana() {
		page.setNome("Nome qualquer");
		page.setSobrenome("Sobrenome qualquer");
		page.setSexoFeminino();
		page.setComidaCarne();
		page.setComidaVegetariano();
		page.cadastrar();
		Assert.assertEquals("Tem certeza que voce eh vegetariano?", dsl.alertaObterTextoEAceita());
	}
	
	@Test
	public void deveValidaEsportisraIndeciso() {
		page.setNome("Nome qualquer");
		page.setSobrenome("Sobrenome qualquer");
		page.setSexoMasculino();
		page.setEsporte("Corrida", "O que eh esporte?");
		dsl.selecionarCombo("elementosForm:esportes", "Corrida", "O que eh esporte?");
		page.cadastrar();
		Assert.assertEquals("Voce faz esporte ou nao?", dsl.alertaObterTextoEAceita());
	}
}
