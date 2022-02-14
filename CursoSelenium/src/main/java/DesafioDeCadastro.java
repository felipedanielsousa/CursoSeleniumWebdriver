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
	
	@Before
	public void inicializa() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\t_felipe.barbosa\\Documents\\Projetos\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().window().setSize(new Dimension(1200, 765));	
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html"); //informando o diretorio raiz de onde estao os arquivos	
		dsl = new DSL(driver);
	}
	
	@After
	public void finaliza() {
		driver.quit();
	}
	
	
	@Test
	public void deveCadastrarEvalidarResultado() {
		dsl.escrever("elementosForm:nome","Felipe"); //escreve nome
		dsl.escrever("elementosForm:sobrenome","Daniel"); //escreve sobrenome
		dsl.clicarRadioButton("elementosForm:sexo:0"); //clica no radio button correspondete ao sexo
		dsl.clicarRadioButton("elementosForm:comidaFavorita:0");
		
		//iteracao com combo
		dsl.selecionarCombo("elementosForm:escolaridade", "Superior");
		//Combo multiplo
		dsl.selecionarCombo("elementosForm:esportes", "Corrida");
		dsl.selecionarCombo("elementosForm:esportes", "Karate");
		dsl.selecionarCombo("elementosForm:esportes", "Natacao");
		
		//validando o numero de selecoes no combo
		/** List<WebElement> allSelectedoptions = dsl.getAllSelectedOptions();
		Assert.assertEquals(3, allSelectedoptions.size()); **/
		
		//clicar em cadastrar
		dsl.clicarBotao("elementosForm:cadastrar");
		
		//validando resultado final
		Assert.assertTrue(dsl.obterTexto("resultado").startsWith("Cadastrado!"));
		Assert.assertTrue(dsl.obterTexto("descNome").endsWith("Felipe"));
		Assert.assertTrue(dsl.obterTexto("descSobrenome").endsWith("Daniel"));
		Assert.assertTrue(dsl.obterTexto("descSexo").endsWith("Masculino"));
		Assert.assertTrue(dsl.obterTexto("descComida").endsWith("Carne"));
		Assert.assertTrue(dsl.obterTexto("descEscolaridade").endsWith("superior"));
		Assert.assertTrue(dsl.obterTexto("descEsportes").endsWith("Natacao Corrida Karate"));							
	}
	
		@Test
		public void deveValidarNomeObrigatorio() {
			dsl.clicarBotao("elementosForm:cadastrar");
			Assert.assertEquals("Nome eh obrigatorio", dsl.alertaObterTextoEAceita());
			
		}
		
		@Test
		public void deveValidarSobrenomeNomeObrigatorio() {
			dsl.escrever("elementosForm:nome", "Nome qualquer");
			dsl.clicarBotao("elementosForm:cadastrar");
			Assert.assertEquals("Sobrenome eh obrigatorio", dsl.alertaObterTextoEAceita());
			
		}
		
		@Test
		public void deveValidarSexoObrigatorio() {
			dsl.escrever("elementosForm:nome", "Nome qualquer");
			dsl.escrever("elementosForm:sobrenome", "Sobrenome qualquer");
			dsl.clicarBotao("elementosForm:cadastrar");
			Assert.assertEquals("Sexo eh obrigatorio", dsl.alertaObterTextoEAceita());
		}
		
		@Test
		public void deveValidarComidaVegetariana() {
			dsl.escrever("elementosForm:nome", "Nome qualquer");
			dsl.escrever("elementosForm:sobrenome", "Sobrenome qualquer");
			dsl.clicarRadioButton("elementosForm:sexo:1");
			dsl.clicarRadioButton("elementosForm:comidaFavorita:0");
			dsl.clicarRadioButton("elementosForm:comidaFavorita:3");
			dsl.clicarBotao("elementosForm:cadastrar");
			Assert.assertEquals("Tem certeza que voce eh vegetariano?", dsl.alertaObterTextoEAceita());
		}
}