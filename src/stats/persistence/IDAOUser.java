package stats.persistence;

import java.io.IOException;

import stats.model.User;

public interface IDAOUser {
	public boolean login(User user) throws DAOException, IOException;
	
}
