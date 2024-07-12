package ppm.backend.Controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.openidconnect.Userinfo;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/paypal")
@CrossOrigin(origins = "http://localhost:4200")
public class PaypalGetController {
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
  
}