package ppm.backend.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ppm.backend.Model.Expenditure;
import ppm.backend.Service.DataService;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(value = "/api/post", method = RequestMethod.POST)
public class PostController {
  private ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private DataService dataSvc;

  @PostMapping("/initializeexpenditure")
  public ResponseEntity<String> createExpenditure(@RequestBody Expenditure expenditure) {
    try {
      dataSvc.initializeNewExpenditure(expenditure.getExpenditureName(),
          expenditure.getDefaultCurrency(),
          expenditure.getUsers(),
          expenditure.getInviteToken());
      return ResponseEntity.ok("Expenditure created");
    } catch (SQLException e) {
      e.printStackTrace();
      ResponseEntity.badRequest().body("Failed to create expenditure");
    }
    return null;

  }

}
