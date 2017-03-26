package swt6.orm.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(LogbookEntry.class)
public class LogbookEntry_ {
	public static volatile SingularAttribute<LogbookEntry, Long> id;
	public static volatile SingularAttribute<LogbookEntry, String> activity;
	public static volatile SingularAttribute<LogbookEntry, Date> startTime;
	public static volatile SingularAttribute<LogbookEntry, Date> endTime;
	public static volatile SingularAttribute<LogbookEntry, Employee> employee;
}
