import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class DesafioDeCadastro {
	
	private WebDriver driver;
	private DSL dsl;
	private CampoTreinamentoPage page;
	
	@Before
	public void inicializa() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\t_felipe.barbosa\\Documents\\Projetos\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		dsl = new DSL(driver);
		page =  new CampoTreinamentoPage(driver);
		driver.manage().window().setSize(new Dimension(1200, 765));	
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html"); //informando o diretorio raiz de onde estao os arquivos	
		
	}
	@After
	public void finaliza() {
		driver.quit();
	}
	
	
	@Test
	public void deveCadastrarEvalidarResultado() {
		page.setNome("Felipe"); //escreve nome
		page.setSobrenome("Daniel"); //escreve sobrenome
		page.setSexoMasculino(); //clica no radio button correspondete ao sexo
		page.setComidaCarne(); 
		page.setEscolaridade("Superior");//iteracao com combo
		//Combo multiplo
		page.setEsporte("Corrida");
		page.setEsporte("Karate");
		page.setEsporte("Natacao");
		//validando o numero de selecoes no combo
		/** List<WebElement> allSelectedoptions = dsl.getAllSelectedOptions();
		Assert.assertEquals(3, allSelectedoptions.size()); **/
		
		//clicar em cadastrar
		page.cadastrar();
		
		//validando resultado final
		Assert.assertTrue(page.obterResultadoCadastro().startsWith("Cadastrado!"));
		Assert.assertTrue(page.obterNomeCadastro().endsWith("Felipe"));
		Assert.assertTrue(page.obterSobrenomeCadastro().endsWith("Daniel"));
		Assert.assertTrue(page.obterSexoCadastro().endsWith("Masculino"));
		Assert.assertTrue(page.obterComidaCadastro().endsWith("Carne"));
		Assert.assertTrue(page.obterEscolaridadeCadastro().endsWith("superior"));
		Assert.assertTrue(page.obterEsporte().endsWith("Natacao Corrida Karate"));							
	}
	
		@Test
		public void deveValidarNomeObrigatorio() {
			page.cadastrar();
			Assert.assertEquals("Nome eh obrigatorio", dsl.alertaObterTextoEAceita());
			
		}
		
		@Test
		public void deveValidarSobrenomeNomeObrigatorio() {
			page.setNome("Nome qualquer");
			page.cadastrar();
			Assert.assertEquals("Sobrenome eh obrigatorio", dsl.alertaObterTextoEAceita());
			
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
