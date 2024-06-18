package ppm.backend.Repository.MySQLRepo;

public interface SQLQueries {
  public static final String INSERT_INTO_EXPENDITURES = """
    insert into Expenditure (expenditure_id, default_currency) VALUES (?, ?)
  """;

  public static final String INSERT_INTO_EXPENSES = """
    insert into Expenses (expense_id, owner_id, mongo_split_id, total_cost, expenditure_id) VALUES (?, ?, ?, ?, ?)    
  """;

  public static final String INSERT_INTO_USER = """
    insert into User (user_id, username) VALUES (?, ?);    
  """;

  public static final String INSERT_INTO_EXPENDITURE_USERS = """
    insert into Expenditure_Users (expenditure_id, user)    
  """;  
}
