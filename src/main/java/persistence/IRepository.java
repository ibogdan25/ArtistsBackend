package persistence;

public interface IRepository<ID,E> {
    void save(E entity) throws Exception;
    void delete(ID id);
    void update(E entity);
    Iterable<E> findAll();
    E findOne(ID id);
}
