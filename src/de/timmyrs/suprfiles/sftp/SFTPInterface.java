package de.timmyrs.suprfiles.sftp;

import com.jcraft.jsch.*;
import de.timmyrs.suprfiles.FileInterface;
import de.timmyrs.suprfiles.SuprFile;

public class SFTPInterface extends FileInterface
{
	ChannelSftp sftp;

	public SFTPInterface(final String host, final String username, final String password) throws JSchException
	{
		JSch ssh = new JSch();
		final Session session = ssh.getSession(username, host, 22);
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		session.setPassword(password);
		session.connect();
		final Channel channel = session.openChannel("sftp");
		channel.connect();
		sftp = (ChannelSftp) channel;
	}

	@Override
	public SFTPFile getFile(final String path, final String name)
	{
		return new SFTPFile(this, name, SuprFile.formatPath(path, true));
	}

	@Override
	public SFTPFile getDir(final String path)
	{
		return getFile(path, "");
	}
}
