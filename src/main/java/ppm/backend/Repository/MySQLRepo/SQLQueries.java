package ppm.backend.Repository.MySQLRepo;

public interface SQLQueries {
  public static final String INSERT_INTO_EXPENDITURES = 
    """
      insert into Expenditures (expenditure_id, expenditure_name, default_currency) VALUES (?, ?, ?)
    """;

  // public static final String INSERT_INTO_EXPENSES = """
  // insert into Expense (expense_id, owner_id, expenditure_id, expense_name,
  // total_cost) VALUES (?, ?, ?, ?, ?, ?)
  // """;

  public static final String INSERT_INTO_EXPENSES = 
    """
      insert into Expense (expense_id, owner_id, expenditure_id, total_cost, expense_name) VALUES (?, ?, ?, ?, ?)
    """;

  public static final String INSERT_INTO_USER =
    """
      insert into User (user_id, username) VALUES (?, ?);
    """;

  public static final String INSERT_INTO_EXPENDITURE_USERS = 
    """
      insert into Expenditure_User (expenditure_id, user_id) VALUES (?, ?)
    """;

  public static final String INSERT_INTO_INVITES = 
    """
      insert into Invites (expenditure_id, invite_token) VALUES (?, ?)
    """;

  // FOR GET QUERIES
  public static final String GET_EXPENDITURE_ID_FROM_PATH = 
    """
      SELECT expenditure_id FROM Invites WHERE invite_token = ?;
    """;
  public static final String GET_EXPENSES_FOR_EXPENDITURE = 
    """
      SELECT e.expense_id, e.expense_name, e.owner_id, e.total_cost
      FROM Expense e
      JOIN Invites i ON e.expenditure_id = i.expenditure_id
      WHERE i.invite_token = ?;
    """;

  public static final String GET_EXPENSES_FOR_EXPENDITURE_FOR_USER = 
    """
      SELECT e.expense_id, e.expense_name, e.owner_id, e.total_cost
      FROM Expense e
      JOIN Invites i ON e.expenditure_id = i.expenditure_id
      WHERE i.invite_token = ? AND e.owner_id = ?;
    """;

  public static final String GET_USERS_FOR_EXPENDITURE = 
    """
      SELECT u.username, eu.user_id
      FROM Expenditure_User eu
      JOIN Invites i ON eu.expenditure_id = i.expenditure_id
      JOIN User u ON u.user_id = eu.user_id
      WHERE i.invite_token = ?;
    """;
}
