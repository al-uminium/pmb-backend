package ppm.backend.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ppm.backend.Model.Expense;
import ppm.backend.Model.User;

@Service
public class ExpenditureService {

  public Map<User, Double> calcCostIncurredPerUser(List<Expense> expenses) {
    Map<User, Double> costMap = expenses.stream()
        .flatMap(expense -> expense.getExpenseSplit().entrySet().stream())
        .collect(Collectors.toMap(
            Map.Entry::getKey,
            Map.Entry::getValue,
            Double::sum));

    return costMap;
  }
}
