# SuprFiles

A Java file library allowing you to access local, SFTP and SMB files using the same functions.

- [Downloads](https://github.com/timmyrs/SuprFiles/releases)
- [Documentation](https://timmyrs.github.io/SuprFiles/)

## An Example

	FileInterface fileInterface;
	if(chosenProtocol.equals("local"))
	{
		fileInterface = new FSInterface();
	}
	else if(chosenProtocol.equals("sftp"))
	{
		fileInterface = new SFTPInterface(host, name, pass);
	}
	else if(chosenProtocol.equals("smb"))
	{
		fileInterface = new SMBInterface(host, name, pass);
	}
	SuprFile file = fileInterface.getFile("test.txt");
	file.write("This file has been created using SuprFiles <https://github.com/timmyrs/SuprFiles>.");

## Used Libraries

- [Apache Commons IO](https://commons.apache.org/proper/commons-io/) ([License](http://www.apache.org/licenses/)) made working with the objects a lot easier,
- [jCIFS](https://jcifs.samba.org/) ([License](http://www.gnu.org/licenses/lgpl-2.1.txt)) made working with SMB/CIFS possible in the first place, and
- [JSch](http://www.jcraft.com/jsch/) ([License](http://www.jcraft.com/jsch/LICENSE.txt)) made working with SSH/SFTP possible in the first place.
