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
