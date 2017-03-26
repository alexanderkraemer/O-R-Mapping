package swt6.orm.hibernate;

import swt6.orm.hibernate.dao.*;
import swt6.orm.persistence.DAOFactory;


public class HibernateDAOFactory extends DAOFactory {


    @Override
    public AddressDao getAddressDao() {
        if(addressDao == null) {
            addressDao = new AddressDao();
        }
        return addressDao;
    }

    @Override
    public EmployeeDao getEmployeeDao() {
        if(employeeDao== null) {
            employeeDao = new EmployeeDao();
        }
        return employeeDao;
    }

    @Override
    public IssueDao getIssueDao() {
        if(issueDao == null) {
            issueDao = new IssueDao();
        }
        return issueDao;
    }

    @Override
    public LogbookEntryDao getLogbookEntryDao() {
        if(logbookEntryDao == null) {
            logbookEntryDao = new LogbookEntryDao();
        }
        return logbookEntryDao;
    }

    @Override
    public ModuleDao getModuleDao() {
        if(moduleDao == null) {
            moduleDao = new ModuleDao();
        }
        return moduleDao;
    }

    @Override
    public PhaseDao getPhaseDao() {
        if(phaseDao == null) {
            phaseDao = new PhaseDao();
        }
        return phaseDao;
    }

    @Override
    public ProjectDao getProjectDao() {
        if(projectDao == null) {
            projectDao = new ProjectDao();
        }
        return projectDao;
    }
}
