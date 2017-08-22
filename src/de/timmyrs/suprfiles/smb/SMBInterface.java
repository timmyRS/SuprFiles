package de.timmyrs.suprfiles.smb;

import de.timmyrs.suprfiles.FileInterface;
import de.timmyrs.suprfiles.SuprFile;
import jcifs.smb.NtlmPasswordAuthentication;

public class SMBInterface extends FileInterface
{
	String host = "";
	NtlmPasswordAuthentication auth = null;

	public SMBInterface(final String host)
	{
		this.host = host;
	}

	public SMBInterface(final String host, final String name, final String pass)
	{
		this.host = host;
		this.auth = new NtlmPasswordAuthentication(host, name, pass);
	}

	@Override
	public SMBFile getFile(final String path, final String name)
	{
		try
		{
			return new SMBFile(this, SuprFile.formatPath(path, true), name);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SMBFile getDir(final String path)
	{
		return getFile(path, "");
	}
}
