import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TesteCampoTreinamento {
	
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
	public void interacaoTextFild() {
		/** M?todo recebe como par?metro o ID, e a string a ser digitada no textField(sendKeys)  **/
	    dsl.escrever("elementosForm:nome", "Felipe"); //sendKeys m?todo para escrever no campo text
	    /**Metodo obter valor campo traz o valor do campo passando como parametro o ID **/
	    Assert.assertEquals("Felipe", dsl.obterValorCampo("elementosForm:nome"));
	 }
	
	@Test
	public void interacaoTextDuplo() {
		dsl.escrever("elementosForm:nome", "Felipe");
		Assert.assertEquals("Felipe", dsl.obterValorCampo("elementosForm:nome"));
		
		dsl.escrever("elementosForm:nome", "Daniel");
		Assert.assertEquals("Daniel", dsl.obterValorCampo("elementosForm:nome"));
	}
	
	@Test
	public void interacaoTextArea() {
		
		dsl.escrever("elementosForm:sugestoes", "Inclus?o da muscula??o");
		Assert.assertEquals("Inclus?o da muscula??o", dsl.obterValorCampo("elementosForm:sugestoes"));
	
	}
	

	
	@Test
	public void interacaoRadioButton() {
		
		dsl.clicarRadioButton("elementosForm:sexo:0");
		dsl.isRadioMarcado("elementosForm:sexo:0");
	}
	
	@Test
	public void interacaoCheckBox() {
		dsl.clicarCheck("elementosForm:comidaFavorita:0");
		dsl.isCheckMarcado("elementosForm:comidaFavorita:0");
	}
	
	@Test
	public void intecaoComboBox() {
		dsl.selecionarCombo("elementosForm:escolaridade", "2o grau completo");
		Assert.assertEquals("2o grau completo", dsl.obterValorCombo("elementosForm:escolaridade"));
	}
	
	@Test
	public void deveVerificarValoresCombo() {
		Assert.assertEquals(8,dsl.obterQuantidadeOpcoesCombo("elementosForm:escolaridade"));
		Assert.assertTrue(dsl.verificaOpcaoCombo("elementosForm:escolaridade", "Mestrado"));
		
	}
	@Test
	public void deveVerificarValoresComboMultiplo() {
		dsl.selecionarCombo("elementosForm:esportes", "Natacao");
		dsl.selecionarCombo("elementosForm:esportes", "Corrida");
		dsl.selecionarCombo("elementosForm:esportes", "O que eh esporte?");
		
		List<String> opcoesMarcadas = dsl.obterValoresCombo("elementosForm:esportes");
		Assert.assertEquals(3, opcoesMarcadas.size());
		
		dsl.descelecionarCombo("elementosForm:esportes", "Corrida");
		opcoesMarcadas = dsl.obterValoresCombo("elementosForm:esportes");
		
		Assert.assertEquals(2,opcoesMarcadas.size());
		
		Assert.assertTrue(opcoesMarcadas.containsAll(Arrays.asList("Natacao", "O que eh esporte?")));
	}
	
	@Test
	public void naoDeveEncontrarOvalorNoCombo() {
		
		WebElement element = driver.findElement(By.id("elementosForm:escolaridade"));
		 
		Select combo = new Select(element);
	
		List<WebElement> options = combo.getOptions();
		Assert.assertEquals(8, options.size()); //valida a quantidade de op??es
		
		boolean encontrou = false;
		for(WebElement option: options) {
			if(option.getText().equals("Valor qualquer")) {
				encontrou = true;
				break;
			}
		}
		Assert.assertEquals(encontrou, false);	
	}
	
	@Test
	public void deveInteragirComBotoes() {
		dsl.clicarBotao("buttonSimple");
		Assert.assertEquals("Obrigado!", dsl.obterValue("buttonSimple"));
	}
	

	@Test
	public void deveInteragirLinks() {
		dsl.clicarLink("Voltar");
		Assert.assertEquals("Voltou!", dsl.obterTexto("resultado"));
	}
	
	@Test
	public void deveBuscarTextosNaPagina() {			
		Assert.assertEquals("Campo de Treinamento", dsl.obterTexto(By.tagName("h3")));
		Assert.assertEquals("Cuidado onde clica, muitas armadilhas...", 
				dsl.obterTexto(By.className("facilAchar"))); 
		
	}
	
	
	
}
