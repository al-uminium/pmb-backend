package ppm.backend.repo;

public interface SQLQueries {

  // INSERTS

  public static final String INSERT_INTO_GROUP = 
  """
    INSERT INTO Group_ (gid, group_name, token, default_currency) (?, ?, ?, ?)    
  """;
  
  public static final String INSERT_INTO_MEMBER = 
  """
    INSERT INTO Member (mid, name) (?, ?)    
  """;

  public static final String INSERT_INTO_EXPENSE =
  """
    INSERT INTO Expense (eid, gid, owner_mid, title, currency, total_cost) (?, ?, ?, ?, ?)  
  """;

  public static final String INSERT_INTO_EXPENSE_PARTICIPANTS =
  """
    INSERT INTO Expense_Participants (eid, mid) (?, ?)    
  """;

  // UPDATES
} 
