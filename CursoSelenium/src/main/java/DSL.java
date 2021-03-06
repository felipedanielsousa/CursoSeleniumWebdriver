import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class DSL {
	
	private WebDriver driver;
	
	public DSL(WebDriver driver) {
		this.driver = driver;
	}
	/****************** TextField e textArea ***********************/
	
    public void escrever(By by, String texto) {
		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(texto);
	}
	public void escrever(String id_campo, String texto) {
		driver.findElement(By.id(id_campo)).clear();
		driver.findElement(By.id(id_campo)).sendKeys(texto);
	}
	
	public String obterValorCampo(String id_campo) {
		return driver.findElement(By.id(id_campo)).getAttribute("value");
	}
	/******************* Radio e Check **************************/
	public void clicarRadioButton(String id_button) {
		driver.findElement(By.id(id_button)).click();
	}
	
	public boolean isRadioMarcado(String id_button) {
		return driver.findElement(By.id(id_button)).isSelected();
	}
	
	public void clicarCheck(String id_button) {
		driver.findElement(By.id(id_button)).click();
	}
	
	public boolean isCheckMarcado(String id_button) {
		return driver.findElement(By.id(id_button)).isSelected();
	}
	
	/*******************  Combo  ********************************/
	public void selecionarCombo(String id_combo, String valor) {
		WebElement element = driver.findElement(By.id(id_combo));
		Select combo = new Select(element);

		combo.selectByVisibleText(valor);
		
	}
	
	public void selecionarCombo(String id_combo, String valor1, String valor2) {
		WebElement element = driver.findElement(By.id(id_combo));
		Select combo = new Select(element);

		combo.selectByVisibleText(valor1);
		combo.selectByVisibleText(valor2);
	}
	
	public void descelecionarCombo(String id, String valor) {
		WebElement element = driver.findElement(By.id(id));
		Select combo = new Select(element);
		combo.deselectByValue(valor);
	}
	
	public String obterValorCombo(String id_combo) {
		WebElement element = driver.findElement(By.id(id_combo));
		Select combo = new Select(element);

		return combo.getFirstSelectedOption().getText();
	}
	
	public List<String> obterValoresCombo(String id){
		WebElement element = driver.findElement(By.id("elementosForm:esportes"));
		Select combo = new Select(element);
		List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();
		List<String> valores = new ArrayList<String>();
		
		for(WebElement opcao: allSelectedOptions) {
			valores.add(opcao.getText());
		}
		return valores;
	}
	
	public int obterQuantidadeOpcoesCombo(String id) {
		WebElement element = driver.findElement(By.id(id));
		Select combo = new Select(element);
		List<WebElement> options = combo.getOptions();
		return options.size();
	}
	
	public boolean verificaOpcaoCombo(String id, String opcao) {
		WebElement element = driver.findElement(By.id(id));
		Select combo = new Select(element);
		List<WebElement> options = combo.getOptions();
		for(WebElement option: options) {
			if(option.getText().equals(opcao)) {
				return true;
			}
		}	
		return false;
	}
	
	/*********************** Botao *****************************/
	
	public void clicarBotao(String id) {
		driver.findElement(By.id(id)).click();
	}
	
	public String obterValue(String id) {
		return driver.findElement(By.id(id)).getAttribute("value");
	}
	
	/***********************  Links  *************************/
	public void clicarLink(String id_link) {
		driver.findElement(By.linkText(id_link)).click();
	}
	
	/*********************** Texto********************************/
	public String obterTexto(By by) {
		return driver.findElement(by).getText();
	}
	
	public String obterTexto(String id) {
		return driver.findElement(By.id(id)).getText();
	}
	/********************** Alerts *******************************/
	
	public String alertaObterTexto() {
		Alert alert = driver.switchTo().alert();
		return alert.getText();
	}
	
	public String alertaObterTextoEAceita() {
		Alert alert = driver.switchTo().alert();
		String valor = alert.getText();
		alert.accept();
		return valor;
		
	}
	
	public String alertaObterTextoENega() {
		Alert alert = driver.switchTo().alert();
		String valor = alert.getText();
		alert.dismiss();
		return valor;
	}
	
	public void alertaEscrever(String valor){
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(valor);
		alert.accept();
	}
	
	/********************* Frames e Janelas **********************/
	
	public void entrarFrame(String id) {
		driver.switchTo().frame(id);
	}
	
	public void sairFrame() {
		driver.switchTo().defaultContent();
	}
	
	public void trocarJanela(String id) {
		driver.switchTo().window(id);
	}
	
}