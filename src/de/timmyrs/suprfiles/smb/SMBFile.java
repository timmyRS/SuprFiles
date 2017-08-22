package de.timmyrs.suprfiles.smb;

import de.timmyrs.suprfiles.SuprFile;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class SMBFile extends SuprFile
{
	private SMBInterface si;
	private String path;
	private String name;
	private SmbFile file;

	SMBFile(final SMBInterface si, final String path, final String name) throws MalformedURLException
	{
		this.si = si;
		this.path = path;
		this.name = name;
		if(this.si.auth == null)
		{
			this.file = new SmbFile("smb://" + si.host + getFilePath());
		}
		else
		{
			this.file = new SmbFile("smb://" + si.host + getFilePath(), this.si.auth);
		}
		if(!this.exists())
		{
			if(this.name.equals(""))
			{
				try
				{
					this.file.mkdirs();
				}
				catch(SmbException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				try
				{
					this.file.createNewFile();
				}
				catch(SmbException e)
				{
					e.printStackTrace();
				}
			}
		}
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
		try
		{
			return this.file.exists();
		}
		catch(SmbException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean isDirectory()
	{
		try
		{
			return this.file.isDirectory();
		}
		catch(SmbException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public SMBFile[] files()
	{
		if(this.exists() && this.isDirectory())
		{
			ArrayList<SMBFile> list = new ArrayList<>();
			try
			{
				for(SmbFile f : file.listFiles())
				{
					list.add(new SMBFile(this.si, this.path, f.getName()));
				}
				SMBFile[] ret = new SMBFile[list.size()];
				ret = list.toArray(ret);
				return ret;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		return new SMBFile[]{};
	}

	public String read()
	{
		InputStream s = null;
		String res = "";
		try
		{
			s = new BufferedInputStream(this.file.getInputStream());
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

	public SMBFile write(String cont)
	{
		OutputStream s = null;
		try
		{
			s = new BufferedOutputStream(this.file.getOutputStream());
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
			this.file.delete();
		}
		catch(SmbException e)
		{
			e.printStackTrace();
		}
	}
}
