package swt6.orm.persistence;

import swt6.orm.domain.Employee;
import swt6.orm.hibernate.dao.*;

/**
 * Created by Alexander on 19/03/2017.
 */
public abstract class DAOFactory {
    protected AddressDao addressDao;
    protected EmployeeDao employeeDao;
    protected IssueDao issueDao;
    protected LogbookEntryDao logbookEntryDao;
    protected ModuleDao moduleDao;
    protected PhaseDao phaseDao;
    protected ProjectDao projectDao;

    public abstract AddressDao getAddressDao();
    public abstract EmployeeDao getEmployeeDao();
    public abstract IssueDao getIssueDao();
    public abstract LogbookEntryDao getLogbookEntryDao();
    public abstract ModuleDao getModuleDao();
    public abstract PhaseDao getPhaseDao();
    public abstract ProjectDao getProjectDao();
}
