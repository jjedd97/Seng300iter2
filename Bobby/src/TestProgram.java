
import org.eclipse.jdt.core.dom.*;
import java.io.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestProgram {
	private static String BASEDIR = "/home/ugc/robert.harley/300/Group1/src/";
	
	@Test
	public void testJLS8() {
		assertNotNull(ASTParser.newParser(AST.JLS8));
	}

	@Test(expected = IllegalArgumentException.class)
	public void test0() {
		assertNotNull(ASTParser.newParser(0));
	}

	@Test(expected = NullPointerException.class)
	public void testInvalidDirectory(){
		String invalidDirectory = "/home/fjkdsfdl/";
		Program.main(new String[] {invalidDirectory, "String"});
	}

	@Test
	public void testConstructor() {
		Program.main(new String[]{BASEDIR, "String"});
	}

	@Test
	public void testSimple() {
		String simple = new Program(BASEDIR, "String").type;
		String expected = "String";
		assertEquals(expected, simple);
	}

	@Test
	public void testQualified() {
		String qualified = new Program(BASEDIR, "java.lang.String").type;
		String expected = "String";
		assertEquals(expected, qualified);
	}

	@Test
	public void testReferences() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String code = "public class Test { \n" + "int a;\n" + "}";
		Program test = new Program(BASEDIR, "int");
		test.parse(code);
		assertEquals("int. Declarations found: 0; references found: 1.", output.toString());
	}

	@Test
	public void testDeclarations() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String code = "public class Test { \n" + "int a;\n" + "}";
		Program test = new Program(BASEDIR, "Test");
		test.parse(code);
		assertEquals("Test. Declarations found: 1; references found: 0.", output.toString());
	}
}
