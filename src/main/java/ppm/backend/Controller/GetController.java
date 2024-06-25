package ppm.backend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ppm.backend.Service.DataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value = "/api/get", method = RequestMethod.GET)
public class GetController {
  @Autowired
  private DataService dataSvc;
  @GetMapping("/expenditure/{path}")
  public String getMethodName(@PathVariable String path) {
      dataSvc.getExpenseDetails(path);
      return path;
  }
  
}
