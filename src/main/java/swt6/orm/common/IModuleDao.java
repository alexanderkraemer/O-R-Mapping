package swt6.orm.common;

import swt6.orm.domain.Module;

import java.util.List;

public interface IModuleDao {
    public Module insert(Module empl);
    public Module update(Module empl);
    public void delete(Module empl);
    public Module findById(long id);
    public List<Module> getAll();
}
