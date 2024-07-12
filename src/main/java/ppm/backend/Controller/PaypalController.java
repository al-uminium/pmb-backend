package ppm.backend.Controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.openidconnect.Userinfo;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payee;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/paypal")
@CrossOrigin(origins = "http://localhost:4200")
public class PaypalController {
  @Value("${paypal.client.id}")
  private String clientId;

  @Value("${paypal.client.secret}")
  private String clientSecret;

  @Value("${paypal.redirect.uri}")
  private String redirectUri;

  @GetMapping("link-account")
  public Map<String, String> linkAccount() {
    String encondedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8).toString();
    System.out.println(clientId);
    System.out.println(clientSecret);
    System.out.println(redirectUri);
    Map<String, String> response = new HashMap<>();
    String redirectUrl = "https://www.sandbox.paypal.com/signin/authorize?flowEntry=static&client_id=" + 
      clientId + 
      "&scope=openid email https://uri.paypal.com/services/paypalattributes&redirect_uri=" + 
      encondedRedirectUri;
      response.put("approvalUrl", redirectUrl);
    return response;
  }

  @GetMapping("/link-account/complete")
  public Userinfo completeLinkAccount(@RequestParam("code") String authorizationCode) {
    try {
      APIContext apiContext = new APIContext(clientId, clientSecret, "sandbox");
      Userinfo userinfo = Userinfo.getUserinfo(apiContext);
      System.out.println(userinfo.toString());
      return userinfo;
    } catch (PayPalRESTException e) {
      e.printStackTrace(); 
      return null;
    }
  }

  @PostMapping("/create-order")
  public Map<String, String> createOrder(@RequestBody Map<String,String> data) {
    Map<String, String> response = new HashMap<>();
    APIContext apiContext = new APIContext(clientId, clientSecret, "sandbox");
    try {
      Amount amount = new Amount();
      amount.setCurrency("USD");
      amount.setTotal(data.get("amount"));

      Transaction transaction = new Transaction();
      transaction.setAmount(amount);
      transaction.setPayee(new Payee().setEmail(data.get("email")));

      Payer payer = new Payer();
      payer.setPaymentMethod("paypal");

      Payment payment = new Payment();
      payment.setIntent("order");
      payment.setPayer(payer);
      payment.setTransactions(Collections.singletonList(transaction));

      RedirectUrls redirectUrls = new RedirectUrls();
      redirectUrls.setCancelUrl("http://localhost:4200/cancel");
      redirectUrls.setReturnUrl("http://localhost:4200/success");
      payment.setRedirectUrls(redirectUrls);

      Payment createdPayment = payment.create(apiContext);
      for (Links link : createdPayment.getLinks()) {
        if (link.getRel().equalsIgnoreCase("approval_url")) {
          response.put("approvalUrl", link.getHref());
          break;
        }
      }

    } catch (PayPalRESTException e) {
      e.printStackTrace();;
    }
    
    return response;
  }
  
  @PostMapping("/capture-order")
  public Map<String,String> captureOrder(@RequestBody Map<String,String> data) {
    Map<String, String> response = new HashMap<>();
    APIContext apiContext = new APIContext(clientId, clientSecret, "sandbox");
    try {
      Payment payment = Payment.get(apiContext, data.get("orderId"));
      PaymentExecution paymentExecution = new PaymentExecution();
      paymentExecution.setPayerId(data.get("payerId"));
      Payment executedPayment = payment.execute(apiContext, paymentExecution);
      response.put("status", executedPayment.getState());
    } catch (PayPalRESTException e) {
      e.printStackTrace();
    }
      
    return response;
  }
  
}