package de.timmyrs.suprfiles.fs;

import de.timmyrs.suprfiles.SuprFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;

public class FSFile extends SuprFile
{
	private String path;
	private String name;
	private File file;

	protected FSFile(final String path, final String name)
	{
		this.path = path;
		this.name = name;
		file = new File(path + name);
		if(!file.exists())
		{
			if(name.equals(""))
			{
				file.mkdirs();
			}
			else
			{
				if(!file.getParentFile().exists())
				{
					file.getParentFile().mkdirs();
				}
				try
				{
					file.createNewFile();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		this.file = file;
	}

	public String getName()
	{
		return this.name;
	}

	public String getPath()
	{
		return this.path;
	}

	public String getFilePath()
	{
		return this.path + this.name;
	}

	public boolean exists()
	{
		return this.file.exists();
	}

	public boolean isDirectory()
	{
		return this.file.isDirectory();
	}

	public FSFile[] files()
	{
		if(this.exists() && this.isDirectory())
		{
			ArrayList<FSFile> list = new ArrayList<>();
			for(File f : file.listFiles())
			{
				list.add(new FSFile(this.path, f.getName()));
			}
			FSFile[] ret = new FSFile[list.size()];
			ret = list.toArray(ret);
			return ret;
		}
		return new FSFile[]{};
	}

	public String read()
	{
		InputStream s = null;
		String res = "";
		try
		{
			s = new BufferedInputStream(new FileInputStream(this.file));
			res = IOUtils.toString(s, "UTF-8");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(s != null)
		{
			try
			{
				s.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return res;
	}

	public FSFile write(String cont)
	{
		OutputStream s = null;
		try
		{
			s = new BufferedOutputStream(new FileOutputStream(this.file));
			IOUtils.write(cont, s, "UTF-8");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		if(s != null)
		{
			try
			{
				s.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return this;
	}

	public void delete()
	{
		try
		{
			FileUtils.forceDelete(this.file);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public InputStream getInputStream()
	{
		try
		{
			return new BufferedInputStream(new FileInputStream(file));
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
