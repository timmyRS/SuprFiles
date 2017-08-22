package de.timmyrs.suprfiles.fs;

import de.timmyrs.suprfiles.FileInterface;
import de.timmyrs.suprfiles.SuprFile;

public class FSInterface extends FileInterface
{
	@Override
	public FSFile getFile(final String path, final String name)
	{
		return new FSFile(SuprFile.formatPath(path, false), name);
	}

	@Override
	public FSFile getDir(final String path)
	{
		return getFile(path, "");
	}
}
