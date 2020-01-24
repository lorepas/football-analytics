package stats.persistence;

public interface IDAOUser {
	public boolean Login(String username, String pwd) throws DAOException;

}
