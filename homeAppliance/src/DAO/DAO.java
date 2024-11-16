package DAO;
import java.util.ArrayList;

public abstract class DAO<T> {
	Connector connector;
	
	public DAO(){
		
	}
	
	public void changeConnection(Connector connector) {
		this.connector = connector;
	}
	
	public void initializeDBConnection() {
		connector.initializeDBConnection();
		
	}
	
	public abstract ArrayList<T> findAll();
	public abstract T getById(int id);
	public abstract boolean addNew(T add);
	public abstract boolean deleteById(int id);
	public abstract boolean updateById(int id, Object update);


}