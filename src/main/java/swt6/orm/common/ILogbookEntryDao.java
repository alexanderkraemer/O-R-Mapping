package swt6.orm.common;

import swt6.orm.domain.Employee;
import swt6.orm.domain.LogbookEntry;

import java.util.List;

public interface ILogbookEntryDao {
    public LogbookEntry insert(LogbookEntry empl);
    public LogbookEntry update(LogbookEntry empl);
    public void delete(LogbookEntry empl);
    public LogbookEntry findById(long id);
    public List<LogbookEntry> getAll();
}
