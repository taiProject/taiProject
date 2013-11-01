package pl.edu.agh.dfs.daos;


public interface GenericDao<T, V> {

	void create(T t);

	void delete(V id);

	T find(V id);

	void update(T t);
}
