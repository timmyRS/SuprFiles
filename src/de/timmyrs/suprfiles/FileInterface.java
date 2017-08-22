package de.timmyrs.suprfiles;

public abstract class FileInterface
{
	/**
	 * Gets a file from the filesystem.
	 * If the file doesn't exist, it will be created.
	 *
	 * @param path The file's parent folder.
	 * @param name The file's name.
	 * @return The file
	 */
	public abstract SuprFile getFile(String path, String name);

	/**
	 * Gets a directory from the filesystem.
	 * If the directory doesn't exist, it will be created.
	 *
	 * @param path The directory's path.
	 * @return The directory
	 */
	public abstract SuprFile getDir(String path);
}
