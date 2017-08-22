package de.timmyrs.suprfiles.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpException;
import de.timmyrs.suprfiles.SuprFile;
import de.timmyrs.suprfiles.fs.FSFile;
import de.timmyrs.suprfiles.fs.FSInterface;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

public class SFTPFile extends SuprFile
{
	private SFTPInterface si;
	private String name;
	private String path;
	private int isDir = -1;

	SFTPFile(final SFTPInterface si, final String name, final String path)
	{
		this.si = si;
		this.name = name;
		this.path = path;
		if(!this.exists())
		{
			if(this.name.equals(""))
			{
				try
				{
					si.sftp.mkdir(path);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				this.write("");
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
			si.sftp.cd(this.path);
		}
		catch(Exception e)
		{
			return false;
		}
		if(this.name.equals(""))
		{
			return true;
		}
		try
		{
			for(LsEntry entry : (Vector<LsEntry>) si.sftp.ls("*"))
			{
				if(entry.getFilename().equals(this.name))
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean isDirectory()
	{
		if(!this.exists())
		{
			return false;
		}
		if(this.isDir == -1)
		{
			try
			{
				si.sftp.cd(this.path + this.name);
			}
			catch(Exception e)
			{
				this.isDir = 0;
			}
			if(this.isDir == -1)
			{
				this.isDir = 1;
			}
		}
		return this.isDir == 1;
	}

	@Override
	public SFTPFile[] files()
	{
		if(this.exists() && this.isDirectory())
		{
			ArrayList<SuprFile> list = new ArrayList<>();
			try
			{
				String path = this.getFilePath();
				si.sftp.cd(path);
				for(LsEntry entry : (Vector<LsEntry>) si.sftp.ls("*"))
				{
					list.add(new SFTPFile(si, entry.getFilename(), path));
				}
			}
			catch(SftpException e)
			{
				return new SFTPFile[]{};
			}
			SFTPFile[] ret = new SFTPFile[list.size()];
			ret = list.toArray(ret);
			return ret;
		}
		return new SFTPFile[]{};
	}

	public String read()
	{
		try
		{
			return IOUtils.toString(si.sftp.get(this.path + this.name), "UTF-8");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public SFTPFile write(String cont)
	{
		try
		{
			File f;
			do
			{
				f = new File(System.getProperty("user.home") + "/" + String.valueOf(ThreadLocalRandom.current().nextInt(1, 999999)) + ".tmp");
			}
			while(f.exists());
			FSFile file = new FSInterface().getFile(f.getParentFile().getAbsolutePath(), f.getName());
			si.sftp.put(file.write(cont).getInputStream(), this.path + this.name, ChannelSftp.OVERWRITE);
			if(!f.delete())
			{
				f.deleteOnExit();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}

	public void delete()
	{
		try
		{
			if(this.isDirectory())
			{
				si.sftp.rmdir(this.path + this.name);
			}
			else
			{
				si.sftp.rm(this.path + this.name);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
