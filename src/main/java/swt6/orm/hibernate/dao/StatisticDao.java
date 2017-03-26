package swt6.orm.hibernate.dao;

import org.hibernate.Session;
import swt6.orm.domain.*;
import swt6.orm.hibernate.HibernatePersistenceManager;
import swt6.orm.persistence.PersistenceManager;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class StatisticDao {
    private HibernatePersistenceManager manager = (HibernatePersistenceManager) PersistenceManager.getPersistenceManager();

    private Time timeFromMillis(long milliseconds) {

        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)));

        return new Time(hours, minutes, seconds);

    }

    public List<Issue> getIssuesWithState(Project project, State state) {
        CriteriaBuilder cb = manager.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Issue> cQuery = cb.createQuery(Issue.class);
        Root<Issue> issue = cQuery.from(Issue.class);

        cQuery.select(issue)
            .where(cb.equal(issue.get("zustand"), state));
        if(state == null) {
            cQuery.where(cb.equal(issue.get("project"), project));
        }

        return manager.getCurrentSession().createQuery(cQuery).getResultList();
    }

    public Time neededTimeSum(Project project, boolean timeRemaining) {
        CriteriaBuilder cb = manager.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Issue> criteriaQuery = cb.createQuery(Issue.class);
        Root<Issue> issue = criteriaQuery.from(Issue.class);

        criteriaQuery.select(issue)
                .where(cb.or(cb.equal(issue.get("zustand"), State.NEW), cb.equal(issue.get("zustand"), State.OPEN)))
                .where(cb.equal(issue.get("project"), project));

        List<Issue> list = manager.getCurrentSession().createQuery(criteriaQuery).getResultList();
        long milliseconds = 0;
        for(Issue i: list) {
            long sec = i.getEstimatedTime().getSeconds() + (i.getEstimatedTime().getMinutes()*60) + (i.getEstimatedTime().getHours() * 3600);
            sec = sec * 1000;
            milliseconds += sec * i.getFinished();
        }


        return this.timeFromMillis(milliseconds);
    }

    public Map<Employee, Time> neededTimePerEmployee(Project project, boolean timeRemaining) {
        CriteriaBuilder cb = manager.getCurrentSession().getCriteriaBuilder();

        Map<Employee, Time> retMap = new HashMap<>();
        project.getEmployees().forEach(e -> {

            CriteriaQuery<Issue> criteriaQuery = cb.createQuery(Issue.class);
            Root<Issue> issue = criteriaQuery.from(Issue.class);

            criteriaQuery.select(issue)
                    .where(cb.equal(issue.get("project"), project))
                    .where(cb.or(cb.equal(issue.get("zustand"), State.NEW), cb.equal(issue.get("zustand"), State.OPEN)))
                    .where(cb.equal(issue.get("employee"), e));

            List<Issue> list = manager.getCurrentSession().createQuery(criteriaQuery).getResultList();
            System.out.println(list);
            long seconds = 0;
            for(Issue i: list) {
                int sec = i.getEstimatedTime().getSeconds() + (i.getEstimatedTime().getMinutes()*60) + (i.getEstimatedTime().getHours() * 3600);
                seconds += Math.round(sec* (timeRemaining ? (1-i.getFinished()) : i.getFinished()));
            }

            retMap.put(e, this.timeFromMillis(seconds*1000));

        });
        return retMap;
    }

    public List<Issue> getIssuesForEmployee(Employee employee, State state) {

        CriteriaBuilder cb = manager.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Issue> allIssuesQuery = cb.createQuery(Issue.class);
        Root<Issue> issue = allIssuesQuery.from(Issue.class);
        ParameterExpression<Employee> e = cb.parameter(Employee.class);
        ParameterExpression<State> s = cb.parameter(State.class);

        Predicate predicate = cb.equal(issue.get("employee"), e);
        if (state != null) {
            predicate = cb.and(predicate, cb.equal(issue.get("zustand"), s));
        }

        CriteriaQuery<Issue> issuesQuery = allIssuesQuery.select(issue).where(predicate);

        TypedQuery<Issue> qry = manager.getCurrentSession().createQuery(issuesQuery);
        qry.setParameter(e, employee);
        if (state != null) {
            qry.setParameter(s, state);
        }
        return qry.getResultList();
    }

    public Time getIssueTimeForEmployee(Employee employee, State state, boolean getRemaining) {
        List<Issue> issues = getIssuesForEmployee(employee, state);
        double timeSum = 0;
        for (Issue issue : issues) {
            if (issue.getEstimatedTime() != null) {
                long time = issue.getEstimatedTime().getSeconds() + (issue.getEstimatedTime().getMinutes()*60) + (issue.getEstimatedTime().getHours() * 3600);
                time = time*1000;
                time *= getRemaining ? (1 - issue.getFinished()) : issue.getFinished();
                timeSum += time;
            }
        }

        return this.timeFromMillis(Math.round(timeSum));
    }

    public Map<Phase, Time> getTimeByPhases(Project project) {
        Map<Phase, Long> resultMap = new HashMap<>();

        CriteriaBuilder cb = manager.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<LogbookEntry> allLogbookEntriesQuery = cb.createQuery(LogbookEntry.class);
        Root<LogbookEntry> logbookEntry = allLogbookEntriesQuery.from(LogbookEntry.class);
        ParameterExpression<Project> p = cb.parameter(Project.class);

        Predicate predicate = cb.equal(logbookEntry.get("module").get("project"), p);

        CriteriaQuery<LogbookEntry> logbookEntriesQuery = allLogbookEntriesQuery.select(logbookEntry).where(predicate)
                .orderBy(cb.asc(logbookEntry.get("phase").get("id")));

        TypedQuery<LogbookEntry> qry = manager.getCurrentSession().createQuery(logbookEntriesQuery);
        qry.setParameter(p, project);

        List<LogbookEntry> orderedLogbookEntries = qry.getResultList();
        Phase phase = null;
        for (LogbookEntry l : orderedLogbookEntries) {
            if(l.getPhase() != phase){
                phase = l.getPhase();
                resultMap.put(phase, 0L);
            }
            final long time = Math.round((l.getEndTime().getTime() - l.getStartTime().getTime()));

            resultMap.compute(phase, (k,v) -> v==null ? time : v+time);
        }

        Map<Phase, Time> retMap = new HashMap<>();

        for (Map.Entry<Phase, Long> entry : resultMap.entrySet())
        {
            retMap.put(entry.getKey(), this.timeFromMillis(entry.getValue()));
        }
        return retMap;
    }
}