package keyStore.manager.interfaces;

import java.util.List;

import keyStore.manager.Key;

public interface KeysManagerInterface {

	public void save(Key key);

	public void delete(Key key);

	public List<Key> realAll();

	public void update(Key key);

}
