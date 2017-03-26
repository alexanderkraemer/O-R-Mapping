package swt6.orm.common;

import swt6.orm.domain.Phase;

import java.util.List;

public interface IPhaseDao {
    public Phase insert(Phase empl);
    public Phase update(Phase empl);
    public void delete(Phase empl);
    public Phase findById(long id);
    public List<Phase> getAll();
}
