package textInputSubmitJava;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class Main {

	public static void main(String[] args) {
		

	}
	
	public void submittingForm() throws Exception {
	    try (final WebClient webClient = new WebClient()) {

	        // Get the first page
	        final HtmlPage page1 = webClient.getPage("https://www.adt.com/natural-disasters");
	        //wait some time???
	        //??????

	        // Get the form that we are dealing with
	        // The form on the website does not have a button
	        //2 options: 
	        //1. simulate "enter" keypress within text input box
	        //2. create button and append it to the form
	        DomElement e = page1.getElementById("pac-input");							//2 separate trials
	        DomElement eHtml = page1.getHtmlElementById("pac-input");					//(both should work)
	        final HtmlForm form1 = (HtmlForm) e;
	        final HtmlForm form2 = (HtmlForm) eHtml;
	       
	        HtmlElement button = (HtmlElement) page1.createElement("button");			//create button to submit
	        button.setAttribute("type", "submit");
	        
	        form1.appendChild(button);

	        final HtmlSubmitInput button1 = (HtmlSubmitInput) button;
	        //final HtmlTextInput textField = form.getInputByName("userid");

	        // Change the value of the text field
	        //textField.type("houston Texas");

	        // Now submit the form by clicking the button and get back the second page.
	        //final HtmlPage page2 = button.click();
	    }
	}
}
