package stats.persistence;

import org.iq80.leveldb.DB;
import org.iq80.leveldb.Logger;
import org.iq80.leveldb.Options;


import static org.iq80.leveldb.impl.Iq80DBFactory.factory;

import java.io.File;
import java.io.IOException;

public class FactoryManagementSystem {

	private DB levelDBStore = null;
	
	private static FactoryManagementSystem sharedinstance = new FactoryManagementSystem();
	
	private FactoryManagementSystem() {
		
	}
	
	public static FactoryManagementSystem getSharedinstance() {
		return sharedinstance;
	}
	
	public DB getLevelDBStore() throws DAOException {
		if(this.levelDBStore == null) {
			Logger logger = new Logger() {
				  public void log(String message) {
				    System.out.println(message);
				  }
			};
			Options options = new Options();
			options.logger(logger);
			options.createIfMissing(true);
			try {
				File file = new File("levelDBStore");
				this.levelDBStore = factory.open(file, options);
			} catch (IOException e) {
				throw new DAOException(e);
			}
		}
		return this.levelDBStore;
	}
	
	public void dbClose() throws DAOException {
		try {
			if(this.levelDBStore != null) {
				this.levelDBStore.close();
			}
		} catch (IOException e) {
			throw new DAOException(e);
		}
	}
	
}

