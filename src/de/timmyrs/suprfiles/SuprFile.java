package de.timmyrs.suprfiles;

public abstract class SuprFile
{
	public static String formatPath(String path, boolean prefix)
	{
		path = path.replaceAll("\\\\", "/");
		if(prefix && !path.startsWith("/"))
		{
			path = "/" + path;
		}
		if(!path.endsWith("/"))
		{
			path += "/";
		}
		return path;
	}

	/**
	 * Returns the name of the file/dir
	 *
	 * @return The name of the file/dir
	 */
	public abstract String getName();

	/**
	 * Returns the path of the file/dir
	 *
	 * @return The path of the file/dir
	 */
	public abstract String getPath();

	/**
	 * Returns the full file path (dir + name)
	 *
	 * @return The full file path (dir + name)
	 */
	public abstract String getFilePath();

	/**
	 * Tells you whether the file/dir exists.
	 *
	 * @return True if the file/dir exists.
	 */
	public abstract boolean exists();

	/**
	 * Tells you weather the file/dir exists.
	 *
	 * @return True if the file/dir exists.
	 */
	public abstract boolean isDirectory();

	/**
	 * Returns all files in the directory or an empty array if not applicable.
	 *
	 * @return All files in the directory or an empty array if not applicable.
	 */
	public abstract SuprFile[] files();

	/**
	 * Reads the contents of the file.
	 *
	 * @return The contents of the file.
	 */
	public abstract String read();

	/**
	 * (Over)writes the contents of the file.
	 *
	 * @param cont The new content of the file.
	 * @return <code>this</code>
	 */
	public abstract SuprFile write(String cont);

	/**
	 * Deletes the file/dir
	 */
	public abstract void delete();
}
