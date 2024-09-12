package ppm.backend.repo;

public interface SQLQueries {

  // INSERTS

  String INSERT_INTO_EXPENSE_GROUP =
  """
    INSERT INTO expense_group (gid, group_name, token, default_currency) VALUES (?, ?, ?, ?)
  """;

  String INSERT_INTO_MEMBER =
  """
    INSERT INTO member (mid, name) VALUES (?, ?)
  """;

  String INSERT_INTO_GROUP_MEMBERS = """
    INSERT INTO group_members (gid, mid) VALUES (?, ?)
  """;

  String INSERT_INTO_EXPENSE =
  """
    INSERT INTO Expense (eid, gid, owner_mid, title, currency, total_cost) VALUES (?, ?, ?, ?, ?, ?)
  """;

  String INSERT_INTO_EXPENSE_PARTICIPANTS =
  """
    INSERT INTO Expense_Participants (eid, mid) VALUES (?, ?)
  """;

  // GET
  String GET_EXPENSE_GROUP_FROM_TOKEN = """
          SELECT gid FROM expense_group WHERE token = ?;
          """;

  String GET_EXPENSE_DETAILS = """
          SELECT e.eid, e.gid, e.title, e.currency, e.total_cost, e.created_at, e.updated_at, e.owner_mid AS mid, m.name, m.uid
             FROM expense e
             JOIN member m ON e.owner_mid = m.mid
             WHERE e.eid = ?;
          """;

  // UPDATES


  // COUNT
  String UNIQUE_CHECK_FOR_TOKEN = """
          SELECT count(*) AS count FROM expense_group WHERE token = ?;
          """;
} 
