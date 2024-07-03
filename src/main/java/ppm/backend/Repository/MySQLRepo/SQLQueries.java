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
    
  // public static final String GET_EXPENSES_FOR_EXPENDITURE = 
  //   """
  //     SELECT e.expense_id, e.expense_name, e.owner_id, e.total_cost
  //     FROM Expense e
  //     JOIN Invites i ON e.expenditure_id = i.expenditure_id
  //     WHERE i.invite_token = ?;
  //   """;

  public static final String GET_EXPENSES_FOR_EXPENDITURE = 
  """
    SELECT 
      e.expense_id, 
      e.owner_id, 
      u_owner.username AS owner_username,
      e.total_cost, 
      e.expenditure_id, 
      e.expense_name,
      u.username AS username, 
      eu.user_id, 
      u.email, 
      e.created_at
    FROM Expense e
    JOIN Expenditure_User eu ON e.expenditure_id = eu.expenditure_id
    JOIN User u ON eu.user_id = u.user_id
    JOIN Invites i ON eu.expenditure_id = i.expenditure_id
    JOIN User u_owner ON e.owner_id = u_owner.user_id  
    WHERE i.invite_token = ?
    ORDER BY e.created_at;
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

  public static final String GET_EXPENDITURE_DETAILS = 
  """
    SELECT ex.expenditure_id, ex.expenditure_name, ex.default_currency, ex.created_at, ex.updated_at
    FROM Expenditures ex
    JOIN Invites i on ex.expenditure_id = i.expenditure_id
    WHERE i.invite_token = ?;    
  """;
}
