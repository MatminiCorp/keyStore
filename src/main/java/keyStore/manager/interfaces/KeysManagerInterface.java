package keyStore.manager.interfaces;

import java.util.List;

import keyStore.manager.Registry;

public interface KeysManagerInterface {

	public void save(Registry key);

	public void delete(Registry key);

	public List<Registry> realAll();

	public void update(Registry key);

}
