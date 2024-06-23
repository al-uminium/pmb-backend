package ppm.backend.Repository.MySQLRepo;

public interface SQLQueries {
  public static final String INSERT_INTO_EXPENDITURES = """
        insert into Expenditures (expenditure_id, expenditure_name, default_currency) VALUES (?, ?, ?)
      """;

  // public static final String INSERT_INTO_EXPENSES = """
  // insert into Expense (expense_id, owner_id, expenditure_id, expense_name,
  // total_cost) VALUES (?, ?, ?, ?, ?, ?)
  // """;

  public static final String INSERT_INTO_EXPENSES = """
      insert into Expense (expense_id, owner_id, expenditure_id, total_cost, expense_name) VALUES (?, ?, ?, ?, ?)
      """;

  public static final String INSERT_INTO_USER = """
        insert into User (user_id, username) VALUES (?, ?);
      """;

  public static final String INSERT_INTO_EXPENDITURE_USERS = """
        insert into Expenditure_User (expenditure_id, user_id) VALUES (?, ?)
      """;

  public static final String INSERT_INTO_INVITES = """
        insert into Invites (expenditure_id, invite_token) VALUES (?, ?)
      """;

  // FOR GET QUERIES
  public static final String GET_EXPENDITURE_ID_FROM_PATH = """
          SELECT expenditure_id FROM Invites WHERE invite_token = ?;
      """;
  
}
