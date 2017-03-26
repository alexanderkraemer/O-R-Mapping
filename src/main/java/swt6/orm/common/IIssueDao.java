package swt6.orm.common;

import swt6.orm.domain.Employee;
import swt6.orm.domain.Issue;

import java.util.List;


public interface IIssueDao {
    public Issue insert(Issue empl);
    public Issue update(Issue empl);
    public void delete(Issue empl);
    public Issue findById(long id);
    public List<Issue> getAll();
}
