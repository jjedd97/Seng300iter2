import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;


public class TestProgram {
	
	// Targets base directory
	String baseDir = "C:\\Users\\Heavenel\\Desktop\\test\\";
	
	// Targets sub-directory
	String subDir = baseDir + "subDirectory";
	
	// Targets JAR file
	String jarDir = baseDir + "subDirectory";
	
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
	
	@Test //Tests for a flat directory
	public void flatDirectoryTest() throws IOException {
		String[] dir = {subDir};
		Main.main(dir);
		assertEquals("SubCode. Declarations found: 3; References found: 0.", systemOutRule.getLogWithNormalizedLineSeparator());
	}
	
//	@Test //Tests for a tree directory
//	public void treeDirectoryTest() throws IOException {
//		String[] dir = {baseDir};
//		Main.main(dir);
//		
//		String log = systemOutRule.getLog();
//		String subFile = "SubCode";
//		
//		boolean check = log.toLowerCase().indexOf(subFile.toLowerCase()) != -1;
//		assertEquals(true, check);
//		systemOutRule.clearLog();
//	}


}
