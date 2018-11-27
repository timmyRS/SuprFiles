import de.timmyrs.suprfiles.FileInterface;
import de.timmyrs.suprfiles.SuprFile;
import de.timmyrs.suprfiles.fs.FSInterface;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Tests
{
	private static void fileTests(FileInterface i, String path)
	{
		SuprFile file = i.getDir(path);
		assertTrue("Directory should have been created", file.exists());
		assertTrue("Directory should be a directory", file.isDirectory());
		SuprFile f = i.getFile(path, "test.txt");
		assertTrue("File should have been created", f.exists());
		assertFalse("File should not be a directory", f.isDirectory());
		f.write("Hello, world!");
		assertEquals("File should read 'Hello, world!'", "Hello, world!", f.read());
		boolean found = false;
		for(SuprFile fi : file.files())
		{
			if(f.getFilePath().equals(fi.getFilePath()))
			{
				found = true;
			}
		}
		assertTrue("File should have been found", found);
		f.delete();
		assertFalse("File should have been deleted", f.exists());
		file.delete();
		assertFalse("Directory should have been deleted", file.exists());
	}

	@Test(timeout = 10000)
	public void testFSFile()
	{
		fileTests(new FSInterface(), System.getProperty("user.home") + "/fstest/");
	}

	/*
	@Test(timeout = 40000)
	public void testSFTPFile() throws Exception
	{
		fileTests(
				new SFTPInterface("localhost", "root", ""),
				"/unit/test/"
		);
	}

	@Test(timeout = 2000)
	public void testSMBFile()
	{
		fileTests(
				new SMBInterface("127.0.0.1", "smb", "smb"),
				"/smbtest/test"
		);
	}
	*/
}
