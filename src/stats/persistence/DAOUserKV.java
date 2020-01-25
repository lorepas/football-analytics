package stats.persistence;

import java.io.IOException;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBIterator;

import stats.model.User;


public class DAOUserKV implements IDAOUser{
	public void putUser(User user) throws DAOException {
		try {
			DB levelDB = FactoryManagementSystem.getSharedinstance().getLevelDBStore();
			String key = "user:$"+user.getUsername();
			String value = user.getPwd();
			levelDB.put(key.getBytes(), value.getBytes());
			System.out.println("User inserted: "+key+" "+value);
		} catch(Exception ex){
			throw new DAOException(ex);
		}
	}
	
	public boolean login(User user) throws DAOException, IOException{
		DBIterator keyIterator = FactoryManagementSystem.getSharedinstance().getLevelDBStore().iterator();
		boolean res = false;
		String str = "user";
		keyIterator.seek(str.getBytes());
		try {
			while (keyIterator.hasNext()) {
				String key = new String(keyIterator.peekNext().getKey());
				String[] keySplit = key.split(":");
				String username = keySplit[1].substring(1);
				if (!keySplit[0].equals("user")) {
					break;
				}
				if (keySplit[1].substring(1).equals(String.valueOf(user.getUsername()))) {
					String storedValue = new String(FactoryManagementSystem.getSharedinstance().getLevelDBStore().get(key.getBytes()));
					if(storedValue.equals(String.valueOf(user.getPwd()))) {
						res = true;
					}
				}
				keyIterator.next();
			}
			return res;
		} catch(Exception ex){
			throw new DAOException(ex);
		} finally {
			try {
			keyIterator.close();
			} catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
}
