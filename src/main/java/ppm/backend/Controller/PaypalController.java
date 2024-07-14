package ppm.backend.Controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import ppm.backend.Model.User;
import ppm.backend.Service.DataService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/paypal")
@CrossOrigin
public class PaypalController {
  @Value("${paypal.client.id}")
  private String clientId;

  @Value("${paypal.client.secret}")
  private String clientSecret;

  @Value("${paypal.redirect.uri}")
  private String redirectUri;

  @Autowired
  private DataService dataSvc;

  private ObjectMapper mapper = new ObjectMapper();

  @GetMapping("link-account")
  public Map<String, String> linkAccount() {
    Map<String, String> response = new HashMap<>();
    String scope = "openid email https://uri.paypal.com/services/paypalattributes";
    String encondedRedirectUri = URLEncoder.encode(redirectUri, StandardCharsets.UTF_8).toString();
    String redirectUrl = "https://www.sandbox.paypal.com/signin/authorize?flowEntry=static&client_id=" + 
      clientId + 
      "&scope="+
      scope +
      "&redirect_uri=" +
      encondedRedirectUri;
      response.put("approvalUrl", redirectUrl);
    return response;
  }

  @PostMapping("/link-account/code={authCode}")
  public ResponseEntity<String> completeLinkAccount(@PathVariable String authCode, @RequestBody User authUser) throws JsonProcessingException {
    try {
      System.out.println(authCode);
      APIContext apiContext = new APIContext(clientId, clientSecret, "sandbox");
      Userinfo userinfo = Userinfo.getUserinfo(apiContext);
      dataSvc.insertIntoPaypalInfo(authUser.getUserId(), userinfo);
      return ResponseEntity.ok(mapper.writeValueAsString(userinfo));
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

      Payee payee = new Payee();
      payee.setEmail(data.get("email"));

      Payer payer = new Payer();
      payer.setPaymentMethod("paypal");

      Transaction transaction = new Transaction();
      transaction.setAmount(amount);
      transaction.setPayee(payee);

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